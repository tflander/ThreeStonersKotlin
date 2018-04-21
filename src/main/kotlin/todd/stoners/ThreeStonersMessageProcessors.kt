package todd.stoners

abstract class MessageProcessor {
    abstract fun process(message: Message, stoner: Stoner)
}

class MaterialRequestedProcessor : MessageProcessor() {

    override fun process(message: Message, stoner: Stoner) {
        val messages = stoner.messageQueue.messages
        val msg = stoner.name + " placed " + stoner.material.name.toLowerCase() + " on the table."
        messages.push(Message(message.senderName, stoner.name, msg))
    }

}