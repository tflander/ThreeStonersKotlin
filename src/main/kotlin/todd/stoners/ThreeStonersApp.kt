package todd.stoners

import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class ThreeStonersApp {

    @Autowired
    lateinit var  processorFactory: StonerMessageProcessorFactory

    @Bean
    fun init() = CommandLineRunner {
        println("*** Hello ***")
        val messageQueue = StonerMessageQueue()

        val hippyCircle = HippyCircle(messageQueue,
                Pair("Todd", Material.WEED),
                Pair("Harpreet", Material.MATCHES),
                Pair("Jibin", Material.PAPERS)
        )

        val deferred = (hippyCircle.stoners).map { stoner ->
            stoner.hippyCircle = hippyCircle
            stoner.processorFactory = processorFactory
            async {
                stoner.start()
            }
        }

        val firstStoner = hippyCircle.stoners.first()
        messageQueue.sendMessage(Message(firstStoner.name, "", "Your turn to roll"))

        runBlocking {
            deferred.forEach { deferredStoner ->
                deferredStoner.await()
            }
        }
        println("Done")
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(ThreeStonersApp::class.java, *args)
}

