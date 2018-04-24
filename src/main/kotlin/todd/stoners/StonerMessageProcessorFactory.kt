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


class RollFattyStrategy : StonerMessageProcessorStrategy {
    val processor = RollFattyProcessor()
    override fun processorFor(message: String): MessageProcessor? {
        val wordsInMessage = message.split(" ")
        if(wordsInMessage.size == 3) {
            if(wordsInMessage.get(1) == "took") {
                return processor
            }
        }
        return null
    }
}

@Component
open class StonerMessageProcessorFactory : StonerMessageProcessorStrategy{
    val strategies = listOf(
            MessageLookupStrategy(),
            MaterialPlacedStrategy(),
            RollFattyStrategy()
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

