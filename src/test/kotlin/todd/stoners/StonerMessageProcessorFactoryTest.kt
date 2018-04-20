package todd.stoners

import assertk.Assert
import org.junit.Test
import assertk.assert
import assertk.assertions.*

class StonerMessageProcessorFactoryTest {

    val processorFactory = StonerMessageProcessorFactory()

    @Test
    fun `handles request for material`() {
        val a = processorFactory.processorFor("Material Requested")
        println(a)
        assert(a is MaterialRequestedProcessor).isTrue()
    }
}

//fun Assert<MessageProcessor?>.isType(expected: String) {
//    if (actual.javaClass.class == expected) return
//    expected("age:${show(expected)} but was age:${show(actual.age)}")
//}