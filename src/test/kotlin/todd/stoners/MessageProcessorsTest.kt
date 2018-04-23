package todd.stoners

import assertk.Assert
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
        val processor = MaterialRequestedProcessor()
        processor.process(Message(stoner!!.name, "Jibin", ""), stoner!!)
        assert(messageQueue?.messages?.get(0)).isEqualTo(Message("Jibin", stoner!!.name, "Todd placed weed on the table."))
    }

    @Test
    fun `material placed processor adds message that material was picked up`() {
        val processor = MaterialPlacedProcessor()
        processor.process(Message(stoner!!.name, "Jibin", "Jibin placed papers on the table"), stoner!!)
        assert(messageQueue?.messages?.get(0)).isEqualTo(Message(stoner!!.name, "Todd","Todd took papers."))
    }

    @Test
    fun `given all material placed on table, roll fatty processor adds message that joint was rolled`() {
        val processor = RollFattyProcessor()
        processor.process(Message(stoner!!.name, "Todd", "Todd took papers"), stoner!!)
        assert(messageQueue?.messages?.isEmpty())
        processor.process(Message(stoner!!.name, "Todd", "Todd took matches"), stoner!!)
        val messageOnQueue = messageQueue?.messages?.get(0)!!
        assert(messageOnQueue).hasSender("Todd")
        assert(messageOnQueue).hasRecipient("Todd")
        assert(messageOnQueue).hasMessageMatching("""Todd rolled a joint with \d+ tokes.""")
    }

    @Test
    fun regexSpike() {
        assert("testing 123 testing").matches("""testing \d+ testing""".toRegex())
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