package todd.stoners

import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking

fun main(args: Array<String>) {

    val messageQueue = StonerMessageQueue()
    val todd = Stoner("Todd", Material.WEED, "Harpreet", messageQueue);
    val harpreet = Stoner("Harpreet", Material.MATCHES, "Jibin", messageQueue);


    val deferred = (listOf(todd, harpreet)).map { stoner ->
        async {
            stoner.start()
        }
    }

    runBlocking {
        deferred
        println("Done");
    }
}