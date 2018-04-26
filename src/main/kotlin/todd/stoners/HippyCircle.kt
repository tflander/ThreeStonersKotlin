package todd.stoners

class HippyCircle(messageQueue: StonerMessageQueue, vararg nameAndMaterialList: Pair<String, Material>) {

    val stoners = (0 until nameAndMaterialList.size).map { index ->
        val neighborIndex = if (index == nameAndMaterialList.size - 1) 0 else index + 1
        val neighborName = nameAndMaterialList.get(neighborIndex).first
        buildStoner(nameAndMaterialList.get(index), messageQueue, neighborName)
    }

    private fun buildStoner(nameAndMaterial: Pair<String, Material>, messageQueue: StonerMessageQueue, neighborName: String): Stoner {
        return Stoner(
                nameAndMaterial.first,
                nameAndMaterial.second,
                neighborName,
                messageQueue
        )
    }
}
