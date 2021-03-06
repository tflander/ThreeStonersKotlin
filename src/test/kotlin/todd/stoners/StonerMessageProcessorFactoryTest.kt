package todd.stoners

import org.junit.Test
import assertk.assert
import assertk.assertions.*

class StonerMessageProcessorFactoryTest {

    val processorFactory = StonerMessageProcessorFactory()

    @Test
    fun `handles request for material`() {
        assert(processorFactory.processorFor("Todd requested material from Harpreet.") is MaterialRequestedProcessor).isTrue()
    }

    @Test
    fun `handles stoner's turn to roll`() {
        assert(processorFactory.processorFor("Todd needs to roll") is StonersTurnToRollProcessor).isTrue()
    }

    @Test
    fun `handles message that material was put on the table`() {
        assert(processorFactory.processorFor("Todd placed weed on the table.") is MaterialPlacedProcessor).isTrue()
    }

    @Test
    fun `handles message that roller took material from the table`() {
        assert(processorFactory.processorFor("Todd took matches.") is RollFattyProcessor).isTrue()
    }

    @Test
    fun `handles message that joint was received after being lit`() {
        assert(processorFactory.processorFor("Todd rolled a joint with 7 tokes, takes a hit, and passes to Harpreet.") is ReceiveFirstPassProcessor).isTrue()
    }

    @Test
    fun `handles message that joint was received after first pass`() {
        assert(processorFactory.processorFor("Todd takes a hit from a joint with 7 tokes, then passes to Harpreet.") is ReceivePassProcessor).isTrue()
    }

    @Test
    fun `handles unknown message by returning null`() {
        assert(processorFactory.processorFor("Unknown Message")).isNull()
    }

}
