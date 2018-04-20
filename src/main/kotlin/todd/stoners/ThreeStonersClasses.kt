package todd.stoners


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
        println("foo")
//        delay(300)
    }
}

class StonerMessageQueue() {
    fun sendMessage(reciepientName: String, senderName: String, message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
