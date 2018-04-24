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

class MaterialPlacedStrategy : StonerMessageProcessorStrategy {
    override fun processorFor(message: String): MessageProcessor? {
        val wordsInMessage = message.split(" ")
        if(wordsInMessage.size == 6) {
            if(wordsInMessage.get(1) == "placed") {
                return MaterialPlacedProcessor()
            }
        }
        return null
    }

}

@Component
class StonerMessageProcessorFactory : StonerMessageProcessorStrategy{
    val strategies = listOf(
            MessageLookupStrategy(),
            MaterialPlacedStrategy()
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

