package todd.stoners

import org.junit.Test
import assertk.assert
import assertk.assertions.*

class StonerMessageQueueTest {

    val messageQueue = StonerMessageQueue()

//    class StonerSpy : Stoner(val name: String,
//    val material: Material,
//    val neighborName: String,
//    val messageQueue: StonerMessageQueue) {
//
//    }

    val todd = Stoner("Todd", Material.WEED, "Harpreet", messageQueue);
    val harpreet = Stoner("Harpreet", Material.MATCHES, "Jibin", messageQueue);

    @Test
    fun `stoner listens to message queue`() {
        messageQueue.sendMessage("Todd", "System", "Roll one")
    }
}