package todd.stoners

abstract class MessageProcessor {
    abstract fun process(message: Message, stoner: Stoner)
}

class MaterialRequestedProcessor : MessageProcessor() {
    override fun process(message: Message, stoner: Stoner) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}