package todd.stoners

import org.junit.Test
import assertk.assert
import assertk.assertions.*

class StonerMessageQueueTest {

    val messageQueue = StonerMessageQueue()

    @Test
    fun `send message pushes to stack`() {
        val messages = messageQueue.messages
        assert(messages).isEmpty()
        val message = Message("Todd", "System", "Roll one")
        messageQueue.sendMessage(message)
        assert(messages).containsExactly(message)
    }

}