package todd.stoners

abstract class MessageProcessor {
    abstract fun process(message: Message, stoner: Stoner)

    fun sendMessageToNeighbor(stoner: Stoner, msg: String) {
        val messages = stoner.messageQueue.messages
        messages.add(Message(stoner.neighborName, stoner.name, msg))
    }

    fun sendMessageToSelf(stoner: Stoner, msg: String) {
        val messages = stoner.messageQueue.messages
        messages.add(Message(stoner.name, stoner.name, msg))
    }

}

class StonersTurnToRollProcessor : MessageProcessor() {
    override fun process(message: Message, stoner: Stoner) {
        val messages = stoner.messageQueue.messages
        stoner.hippyCircle.stoners.forEach { stonerInCircle ->
            if(stonerInCircle.name != stoner.name) {
                messages.add(Message(stonerInCircle.name, stoner.name, stoner.name + " requested material from " + stonerInCircle.name + "."))
            }
        }
    }
}

class MaterialRequestedProcessor : MessageProcessor() {

    override fun process(message: Message, stoner: Stoner) {
        val msg = stoner.name + " placed " + stoner.material.name.toLowerCase() + " on the table."
        val messages = stoner.messageQueue.messages
        messages.add(Message(message.senderName, stoner.name, msg))
    }
}

class MaterialPlacedProcessor : MessageProcessor() {

    override fun process(message: Message, stoner: Stoner) {
        val wordsInMessage = message.message.split(" ")
        val material = wordsInMessage.get(2)
        val msg = stoner.name + " took " + material + "."
        sendMessageToSelf(stoner, msg)
    }
}

class RollFattyProcessor : MessageProcessor() {

    val materialCollected = HashSet<Material>()


    override fun process(message: Message, stoner: Stoner) {
        val wordsInMessage = message.message.split(" ")
        val material = wordsInMessage.get(2).replace('.', ' ').trim()

        materialCollected.add(Material.valueOf(material.toUpperCase()))
        materialCollected.add(stoner.material)
        if(materialCollected.size == 3) {
            val tokes = (Math.random() * 4 + 7).toInt()
            val msg = stoner.name + " rolled a joint with " + tokes + " tokes, takes a hit, and passes to Harpreet."
            sendMessageToNeighbor(stoner, msg)
            materialCollected.clear()
        }
    }
}

class ReceiveFirstPassProcessor : MessageProcessor() {
    override fun process(message: Message, stoner: Stoner) {
        val wordsInMessage = message.message.split(" ")
        val originalTokes = wordsInMessage.get(5).toInt()
        val msg = stoner.name + " takes a hit from a joint with " + (originalTokes - 1) +" tokes, then passes to " + stoner.neighborName + "."
        sendMessageToNeighbor(stoner, msg)
    }
}

class ReceivePassProcessor : MessageProcessor() {
    override fun process(message: Message, stoner: Stoner) {
        val wordsInMessage = message.message.split(" ")
        val originalTokes = wordsInMessage.get(8).toInt()
        val msg = if(originalTokes > 1) {
            stoner.name + " takes a hit from a joint with " + (originalTokes - 1) + " tokes, then passes to " + stoner.neighborName + "."
        } else {
            stoner.neighborName + " needs to roll"
        }
        sendMessageToNeighbor(stoner, msg)
    }

}
