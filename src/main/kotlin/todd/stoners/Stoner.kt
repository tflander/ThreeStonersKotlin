package todd.stoners

import org.springframework.beans.factory.annotation.Autowired

data class Stoner(
        val name: String,
        val material: Material,
        val neighborName: String,
        val messageQueue: StonerMessageQueue
) {
    @Autowired
    lateinit var processorFactory: StonerMessageProcessorFactory

    fun start() {

        while (true) {
            val messages = messageQueue.messages
            if(messages.isNotEmpty()) {
                val topMessage = messages.peek()
                if (topMessage.reciepientName == name) {
                    messages.pop(); // TODO: thread-safe?
                    processMessage(topMessage)
                }
            }
            Thread.sleep(300)
        }
    }

    private fun processMessage(message: Message) {
        println("processing message $message")

        // TODO: Use processor factory
        val messageProcessor = processorFactory.processorFor(message.message)

    }

    // TODO: can this be a lazy val?
    var hippyCircle: HippyCircle? = null

}
