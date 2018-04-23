package todd.stoners

interface StonerMessageProcessorStrategy {
    fun processorFor(message: String): MessageProcessor?
}

class MessageLookupStrategy : StonerMessageProcessorStrategy {
    private val map = mapOf(
            "Material Requested" to MaterialRequestedProcessor()
    )

    override fun processorFor(message: String): MessageProcessor? {
        return map.get(message)
    }

}

class MaterialPlacedStrategy : StonerMessageProcessorStrategy {
    override fun processorFor(message: String): MessageProcessor? {
        val wordsInMessage = message.split(" ")
        // Todd placed weed on the table
        if(wordsInMessage.size == 6) {
            if(wordsInMessage.get(1) == "placed") {
                return MaterialPlacedProcessor()
            }
        }
        return null
    }

}

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

