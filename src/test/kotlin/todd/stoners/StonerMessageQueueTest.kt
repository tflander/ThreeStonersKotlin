package todd.stoners

import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class StonerMessageQueueTest {

    @Mock
    lateinit var processorFactory: StonerMessageProcessorFactory

    val messageQueue = StonerMessageQueue()

    val todd = Stoner("Todd", Material.WEED, "Harpreet", messageQueue);

    @Test @Ignore // TODO: fails because stoner::start() loop is not run
    fun `stoner uses the processor factory to process messages from the message queue`() {
        todd.processorFactory = processorFactory
        messageQueue.sendMessage(Message("Todd", "System", "Roll one"))
        Mockito.verify(processorFactory).processorFor("Roll one")
    }
}