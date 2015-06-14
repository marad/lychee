package io.github.marad.lychee.server

import io.github.marad.lychee.common.UnitTest
import io.netty.channel.embedded.EmbeddedChannel
import io.netty.channel.{ChannelHandlerContext, ChannelHandler, Channel}
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._

class ClientTrackerTest extends UnitTest with MockitoSugar {
  it should "remove disconnected clients" in {
    Given
    val embeddedChannel = new EmbeddedChannel(new DummyHandler)
    val channelStub = mock[Channel]
    val closePromise = embeddedChannel.newPromise()
    when(channelStub.closeFuture) thenReturn closePromise
    val client = new Client(channelStub)
    val clientTracker = new ClientTracker
    clientTracker.add(client)

    Expect
    clientTracker.getClients should contain (client)
    closePromise.setSuccess()
    clientTracker.getClients should not contain client
  }

  class DummyHandler extends ChannelHandler {
    override def exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable): Unit = None
    override def handlerRemoved(ctx: ChannelHandlerContext): Unit = None
    override def handlerAdded(ctx: ChannelHandlerContext): Unit = None
  }
}
