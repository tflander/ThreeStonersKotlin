package todd.stoners

import java.util.*


enum class Material {
    WEED, PAPERS, MATCHES
}

class HippyCircle(messageQueue: StonerMessageQueue, vararg nameAndMaterialList: Pair<String, Material>) {
    val stoners = (0 until nameAndMaterialList.size).map { index ->
        val neighborIndex = if (index == nameAndMaterialList.size - 1) 0 else index + 1
        Stoner(
                nameAndMaterialList.get(index).first,
                nameAndMaterialList.get(index).second,
                nameAndMaterialList.get(neighborIndex).first,
                messageQueue
        )
    }
}

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

    }
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
