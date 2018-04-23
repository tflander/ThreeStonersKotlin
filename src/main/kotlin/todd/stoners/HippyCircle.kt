package todd.stoners

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
