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

abstract class MultiWordStrategy : StonerMessageProcessorStrategy {
    abstract fun getProcessor(): MessageProcessor
    abstract fun testWords(wordsInMessage: List<String>): Boolean

    override fun processorFor(message: String): MessageProcessor? {
        val wordsInMessage = message.split(" ")
        if(testWords(wordsInMessage)) {
            return getProcessor()
        }

        return null
    }
}

class MaterialPlacedStrategy : MultiWordStrategy() {

    override fun testWords(wordsInMessage: List<String>): Boolean {
        return wordsInMessage.size == 6 && wordsInMessage.get(1) == "placed"
    }

    override fun getProcessor(): MessageProcessor {
        return processor
    }

    val processor = MaterialPlacedProcessor()

}

class RollFattyStrategy : MultiWordStrategy() {
    override fun testWords(wordsInMessage: List<String>): Boolean {
        return wordsInMessage.size == 3 && wordsInMessage.get(1) == "took"
    }

    override fun getProcessor(): MessageProcessor {
        return processor
    }

    val processor = RollFattyProcessor()
}

class SparkItUpStrategy : MultiWordStrategy() {
    override fun getProcessor(): MessageProcessor {
        return processor
    }

    override fun testWords(wordsInMessage: List<String>): Boolean {
        return wordsInMessage.size == 7 && wordsInMessage.get(1) == "rolled"
    }

    val processor = SparkItUpProcessor()

}

@Component
open class StonerMessageProcessorFactory : StonerMessageProcessorStrategy{
    val strategies = listOf(
            MessageLookupStrategy(),
            MaterialPlacedStrategy(),
            RollFattyStrategy(),
            SparkItUpStrategy()
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

