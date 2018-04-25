package todd.stoners

import org.springframework.stereotype.Component

interface StonerMessageProcessorStrategy {
    fun processorFor(message: String): MessageProcessor?
}

class MessageLookupStrategy : StonerMessageProcessorStrategy {
    private val map = mapOf(
            "Material Requested" to MaterialRequestedProcessor(),
            "Your turn to roll" to StonersTurnToRollProcessor()
    )

    override fun processorFor(message: String): MessageProcessor? {
        return map.get(message)
    }

}

abstract class MultiWordStrategy(val processor: MessageProcessor) : StonerMessageProcessorStrategy {
    abstract fun testWords(wordsInMessage: List<String>): Boolean

    override fun processorFor(message: String): MessageProcessor? {
        val wordsInMessage = message.split(" ")
        if(testWords(wordsInMessage)) {
            return processor
        }

        return null
    }
}

class MaterialPlacedStrategy : MultiWordStrategy(MaterialPlacedProcessor()) {

    override fun testWords(wordsInMessage: List<String>): Boolean {
        return wordsInMessage.size == 6 && wordsInMessage.get(1) == "placed"
    }
}

class RollFattyStrategy : MultiWordStrategy(RollFattyProcessor()) {
    override fun testWords(wordsInMessage: List<String>): Boolean {
        return wordsInMessage.size == 3 && wordsInMessage.get(1) == "took"
    }
}

class ReceiveFirstPassStrategy : MultiWordStrategy(ReceiveFirstPassProcessor()) {
    override fun testWords(wordsInMessage: List<String>): Boolean {
        return wordsInMessage.size == 14 && wordsInMessage.get(1) == "rolled"
    }
}

class ReceivePassStrategy : MultiWordStrategy(ReceivePassProcessor()) {
    override fun testWords(wordsInMessage: List<String>): Boolean {
        return  wordsInMessage.size == 14 && wordsInMessage.get(1) == "takes"
    }
}

@Component
open class StonerMessageProcessorFactory : StonerMessageProcessorStrategy{
    val strategies = listOf(
            MessageLookupStrategy(),
            MaterialPlacedStrategy(),
            RollFattyStrategy(),
            ReceiveFirstPassStrategy(),
            ReceivePassStrategy()
    )

    override fun processorFor(message: String): MessageProcessor? {
        strategies.forEach { strategy ->
            val processor = strategy.processorFor(message)
            if(processor != null) {
                return processor
            }
        }
        return null
    }
}

