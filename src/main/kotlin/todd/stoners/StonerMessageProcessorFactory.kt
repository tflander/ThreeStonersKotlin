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

abstract class MultiWordStrategy(val processor: MessageProcessor, val testWords: (words: List<String>) -> Boolean) : StonerMessageProcessorStrategy {

    override fun processorFor(message: String): MessageProcessor? {
        val wordsInMessage = message.split(" ")
        if(testWords(wordsInMessage)) {
            return processor
        }

        return null
    }
}

class MaterialRequestedStrategy : MultiWordStrategy(MaterialRequestedProcessor(), {
    wordsInMessage -> wordsInMessage.size == 5 && wordsInMessage.get(1) == "requested"
})

class MaterialPlacedStrategy : MultiWordStrategy(MaterialPlacedProcessor(), {
    wordsInMessage -> wordsInMessage.size == 6 && wordsInMessage.get(1) == "placed"
})

class RollFattyStrategy : MultiWordStrategy(RollFattyProcessor(), {
    wordsInMessage -> wordsInMessage.size == 3 && wordsInMessage.get(1) == "took"
})

class ReceiveFirstPassStrategy : MultiWordStrategy(ReceiveFirstPassProcessor(), {
    wordsInMessage -> wordsInMessage.size == 14 && wordsInMessage.get(1) == "rolled"
})

class ReceivePassStrategy : MultiWordStrategy(ReceivePassProcessor(), {
    wordsInMessage -> wordsInMessage.size == 14 && wordsInMessage.get(1) == "takes"
})

@Component
open class StonerMessageProcessorFactory : StonerMessageProcessorStrategy{
    val strategies = listOf(
            MaterialRequestedStrategy(),
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

