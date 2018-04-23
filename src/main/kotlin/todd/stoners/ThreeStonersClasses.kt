package todd.stoners

import java.util.*


enum class Material {
    WEED, PAPERS, MATCHES
}

data class Message(
        val reciepientName: String,
        val senderName: String,
        val message: String
)

class StonerMessageQueue() {

    val messages = Stack<Message>()

    fun sendMessage(message: Message) {
        messages.push(message)
    }
}
