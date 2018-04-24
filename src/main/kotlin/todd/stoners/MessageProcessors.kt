package todd.stoners

abstract class MessageProcessor {
    abstract fun process(message: Message, stoner: Stoner)
}

class StonersTurnToRollProcessor : MessageProcessor() {
    override fun process(message: Message, stoner: Stoner) {
        val messages = stoner.messageQueue.messages
        stoner.hippyCircle?.stoners?.forEach { stonerInCircle ->
            if(stonerInCircle.name != stoner.name) {
                messages.push(Message(stonerInCircle.name, stoner.name, "Material Requested"))
            }
        }
    }
}

class MaterialRequestedProcessor : MessageProcessor() {

    override fun process(message: Message, stoner: Stoner) {
        val msg = stoner.name + " placed " + stoner.material.name.toLowerCase() + " on the table."
        val messages = stoner.messageQueue.messages
        messages.push(Message(message.senderName, stoner.name, msg))
    }

}

class MaterialPlacedProcessor : MessageProcessor() {

    override fun process(message: Message, stoner: Stoner) {
        val wordsInMessage = message.message.split(" ")
        val material = wordsInMessage.get(2)
        val msg = stoner.name + " took " + material + "."
        val messages = stoner.messageQueue.messages
        messages.push(Message(stoner.name, stoner.name, msg))
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
            val messages = stoner.messageQueue.messages
            val tokes = (Math.random() * 4 + 7).toInt()
            val msg = stoner.name + " rolled a joint with " + tokes + " tokes."
            messages.push(Message(stoner.name, stoner.name, msg))
        }
    }

}