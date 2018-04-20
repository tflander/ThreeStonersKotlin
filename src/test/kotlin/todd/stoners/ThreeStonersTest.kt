package todd.stoners

import org.junit.Test
import assertk.assert
import assertk.assertions.*

class ThreeStonersTest {

    val messageQueue = StonerMessageQueue()

    val hippyCircle = HippyCircle(messageQueue,
            Pair("Todd", Material.WEED),
            Pair("Harpreet", Material.MATCHES),
            Pair("Jibin", Material.PAPERS)
    )

    @Test
    fun `hippy circle has stoners`() {

        assert(hippyCircle.stoners).isEqualTo(listOf(
                Stoner("Todd", Material.WEED, "Harpreet", messageQueue),
                Stoner("Harpreet", Material.MATCHES, "Jibin", messageQueue),
                Stoner("Jibin", Material.PAPERS, "Todd", messageQueue)
        ))
    }

}
