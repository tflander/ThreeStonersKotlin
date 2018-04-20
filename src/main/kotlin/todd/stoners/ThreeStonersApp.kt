package todd.stoners

fun main(args: Array<String>) {
    print("hello");
}

enum class Material {
    WEED, PAPERS, MATCHES
}

class HippyCircle(vararg nameAndMaterialList: Pair<String, Material>) {
    val stoners = (0 until nameAndMaterialList.size).map { index ->
        val neighborIndex = if(index == nameAndMaterialList.size - 1) 0 else index + 1
        Stoner(
                nameAndMaterialList.get(index).first,
                nameAndMaterialList.get(index).second,
                nameAndMaterialList.get(neighborIndex).first
        )
    }
}

data class Stoner(
        val name: String,
        val material: Material,
        val neighborName: String
)