package todd.stoners

import org.junit.Test
import assertk.assert
import assertk.assertions.*

class StonerMessageProcessorFactoryTest {

    val processorFactory = StonerMessageProcessorFactory()

    @Test
    fun `handles request for material`() {
        assert(processorFactory.processorFor("Material Requested") is MaterialRequestedProcessor).isTrue()
    }

    @Test
    fun `handles stoner's turn to roll`() {
        assert(processorFactory.processorFor("Your turn to roll") is StonersTurnToRollProcessor).isTrue()
    }

    @Test
    fun `handles message that material was put on the table`() {
        assert(processorFactory.processorFor("Todd placed weed on the table.") is MaterialPlacedProcessor).isTrue()
    }

    @Test
    fun `handles unknown message by returning null`() {
        assert(processorFactory.processorFor("Unknown Message")).isNull()
    }

}
