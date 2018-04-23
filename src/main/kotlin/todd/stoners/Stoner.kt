package todd.stoners

data class Stoner(
        val name: String,
        val material: Material,
        val neighborName: String,
        val messageQueue: StonerMessageQueue
) {
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

    }

    // TODO: can this be a lazy val?
    var hippyCircle: HippyCircle? = null

}
