package todd.stoners

import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking

fun main(args: Array<String>) {

    val messageQueue = StonerMessageQueue()

    val hippyCircle = HippyCircle(messageQueue,
            Pair("Todd", Material.WEED),
            Pair("Harpreet", Material.MATCHES),
            Pair("Jibin", Material.PAPERS)
    )

    val deferred = (hippyCircle.stoners).map { stoner ->
        async {
            stoner.start()
        }
    }

    hippyCircle.stoners.forEach() {stoner ->
        messageQueue.sendMessage(Message(stoner.name, "", "Material Requested"))
    }

    runBlocking {
        deferred.forEach() { deferredStoner ->
            deferredStoner.await()
        }
    }
    println("Done");
}