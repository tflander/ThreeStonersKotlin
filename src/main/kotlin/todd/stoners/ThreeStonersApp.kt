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
        stoner.hippyCircle = hippyCircle
        async {
            stoner.start()
        }
    }

    val firstStoner = hippyCircle.stoners.first()
    messageQueue.sendMessage(Message(firstStoner.name, "", "Your turn to roll"))

    runBlocking {
        deferred.forEach() { deferredStoner ->
            deferredStoner.await()
        }
    }
    println("Done");
}