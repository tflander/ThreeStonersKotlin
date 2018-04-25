package todd.stoners

data class Stoner(
        val name: String,
        val material: Material,
        val neighborName: String,
        val messageQueue: StonerMessageQueue
) {
    lateinit var processorFactory: StonerMessageProcessorFactory
    lateinit var hippyCircle: HippyCircle

    fun start() {

        while (true) {
            val messages = messageQueue.messages
            synchronized(messages) {
                if (messages.isNotEmpty()) {
                    val topMessage = messages.first
                    if (topMessage.reciepientName == name) {
                        messages.remove()
                        if (topMessage.message == "Exit") {
                            return
                        }
                        processMessage(topMessage)
                    }
                }
            }
            Thread.sleep(300)
        }
    }

    private fun processMessage(message: Message) {
        println("processing message $message")

        val messageProcessor = processorFactory.processorFor(message.message)
        messageProcessor?.process(message, this)
    }

}
