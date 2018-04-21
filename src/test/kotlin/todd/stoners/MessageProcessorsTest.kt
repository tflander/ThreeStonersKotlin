package todd.stoners

import org.junit.Before
import org.junit.Test
import assertk.assert
import assertk.assertions.*

class MessageProcessorsTest {

    var messageQueue: StonerMessageQueue? = null
    var stoner: Stoner? = null

    @Before
    fun init() {
        messageQueue = StonerMessageQueue()
        stoner = Stoner("Todd", Material.WEED, "Harpreet", messageQueue!!)
    }

    @Test
    fun `material requested processor adds message that material was placed on table`() {
        val processor = MaterialRequestedProcessor();
        processor.process(Message(stoner!!.name, "Jibin", ""), stoner!!)
        assert(messageQueue?.messages?.get(0)).isEqualTo(Message("Jibin", stoner!!.name, "Todd placed weed on the table."))
    }
}