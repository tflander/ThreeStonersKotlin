package todd.stoners

import java.util.*

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
                    if (processTestingForExit(messages)) return
                }
            }
            Thread.sleep(300)
        }
    }

    private fun processTestingForExit(messages: ArrayDeque<Message>): Boolean {
        val topMessage = messages.first
        if (topMessage.reciepientName == name) {
            messages.remove()
            if (topMessage.message == "Exit") {
                return true
            }
            processMessage(topMessage)
        }
        return false
    }

    private fun processMessage(message: Message) {
        println("tid=" + Thread.currentThread().id + ": processing $message")

        val messageProcessor = processorFactory.processorFor(message.message)
        messageProcessor?.process(message, this)
    }

}
