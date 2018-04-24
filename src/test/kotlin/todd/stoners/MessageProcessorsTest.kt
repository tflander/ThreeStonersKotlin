package todd.stoners

import assertk.Assert
import org.junit.Before
import org.junit.Test
import assertk.assert
import assertk.assertions.*

class MessageProcessorsTest {

    var messageQueue: StonerMessageQueue? = null
    var stoner: Stoner? = null
    var hippyCircle: HippyCircle? = null

    @Before
    fun init() {
        messageQueue = StonerMessageQueue()
        stoner = Stoner("Todd", Material.WEED, "Harpreet", messageQueue!!)
        hippyCircle = HippyCircle(messageQueue!!,
                Pair("Todd", Material.WEED),
                Pair("Harpreet", Material.MATCHES),
                Pair("Jibin", Material.PAPERS)
        )
        stoner!!.hippyCircle = hippyCircle!!
    }

    @Test
    fun `stoner's turn to roll processor adds material request message for other stoners`() {
        val processor = StonersTurnToRollProcessor()
        processor.process(Message(stoner!!.name, "", ""), stoner!!)
        val messages = messageQueue?.messages!!
        assert(messages.size).isEqualTo(2)
        assert(messages.first).isEqualTo(Message("Jibin", "Todd", "Material Requested"))
        assert(messages.last).isEqualTo(Message("Harpreet", "Todd", "Material Requested"))
    }

    @Test
    fun `material requested processor adds message that material was placed on table`() {
        val processor = MaterialRequestedProcessor()
        processor.process(Message(stoner!!.name, "Jibin", ""), stoner!!)
        assert(messageQueue?.messages?.first).isEqualTo(Message("Jibin", stoner!!.name, "Todd placed weed on the table."))
    }

    @Test
    fun `material placed processor adds message that material was picked up`() {
        val processor = MaterialPlacedProcessor()
        processor.process(Message(stoner!!.name, "Jibin", "Jibin placed papers on the table"), stoner!!)
        assert(messageQueue?.messages?.first).isEqualTo(Message(stoner!!.name, "Todd","Todd took papers."))
    }

    @Test
    fun `given all material placed on table, roll fatty processor adds message that joint was rolled`() {
        val processor = RollFattyProcessor()
        processor.process(Message(stoner!!.name, "Todd", "Todd took papers."), stoner!!)
        assert(messageQueue?.messages?.isEmpty())
        processor.process(Message(stoner!!.name, "Todd", "Todd took matches."), stoner!!)
        val messageOnQueue = messageQueue?.messages?.first!!
        assert(messageOnQueue).hasSender("Todd")
        assert(messageOnQueue).hasRecipient("Todd")
        assert(messageOnQueue).hasMessageMatching("""Todd rolled a joint with \d+ tokes.""")
    }

    fun Assert<Message>.hasSender(expected: String) {
        assert(actual.senderName, name="senderName").isEqualTo(expected)
    }

    fun Assert<Message>.hasRecipient(expected: String) {
        assert(actual.reciepientName, name="reciepientName").isEqualTo(expected)
    }

    fun Assert<Message>.hasMessageMatching(expected: String) {
        assert(actual.message, name="message").matches(expected.toRegex())
    }
}

