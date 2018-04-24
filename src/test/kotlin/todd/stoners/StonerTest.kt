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

    @Mock
    lateinit var mockMessageProcessor: MessageProcessor

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
        val msg = "Some message"
        Mockito.`when`(processorFactory.processorFor(msg)).thenReturn(mockMessageProcessor)
        val message = Message(stoner.name, "", msg)
        messageQueue.sendMessage(message)
        messageQueue.sendMessage(Message(stoner.name, "", "Exit"))
        runBlocking {
            deferredStoner.await()
        }

        Mockito.verify(processorFactory).processorFor(msg)
        Mockito.verify(mockMessageProcessor).process(message, stoner)
    }

}