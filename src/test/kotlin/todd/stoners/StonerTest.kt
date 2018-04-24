package todd.stoners

import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class StonerTest {

    @Mock
    lateinit var processorFactory: StonerMessageProcessorFactory

    val messageQueue = StonerMessageQueue()
    val stoner: Stoner = Stoner("Todd", Material.WEED, "Harpreet", messageQueue)

    val deferredStoner = async { stoner.start() }

    @Before
    fun setup() {
        stoner.processorFactory = processorFactory
    }

    @Test
    fun `start() runs until exit message received`() {
        messageQueue.sendMessage(Message(stoner.name, "", "Exit"))
        runBlocking {
            deferredStoner.await()
        }
        println("Blocking Thread (Co-Routine) Terminated by Exit")
    }

    @Test
    fun `non-exit message is handled through the processor factory`() {
        messageQueue.sendMessage(Message(stoner.name, "", "Some message"))
        messageQueue.sendMessage(Message(stoner.name, "", "Exit"))
        runBlocking {
            deferredStoner.await()
        }

        Mockito.verify(processorFactory).processorFor("Some message")
    }

}