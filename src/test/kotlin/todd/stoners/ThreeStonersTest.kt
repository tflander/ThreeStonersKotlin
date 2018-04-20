package todd.stoners

import org.junit.Test
import assertk.assert
import assertk.assertions.*

class ThreeStonersTest {

    @Test
    fun `create hippy circle`() {
        val hippyCircle = HippyCircle(
                Pair("Todd", Material.WEED),
                Pair("Harpreet", Material.MATCHES),
                Pair("Jibin", Material.PAPERS)
        )

        assert(hippyCircle.stoners).isEqualTo(listOf(
                Stoner("Todd", Material.WEED, "Harpreet"),
                Stoner("Harpreet", Material.MATCHES, "Jibin"),
                Stoner("Jibin", Material.PAPERS, "Todd")
        ))
    }


}
