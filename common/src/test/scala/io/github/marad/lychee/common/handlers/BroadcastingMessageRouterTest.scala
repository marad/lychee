package io.github.marad.lychee.common.handlers

import io.github.marad.lychee.common.{Message, UnitTest}
import org.mockito.Mockito._

import scala.collection.JavaConversions._

class BroadcastingMessageRouterTest extends UnitTest {

  class TestMessage extends Message

  it should "broadcast message to all handlers" in {
    Given
    val firstHandlerStub = mock[MessageHandler]
    val secondHandlerStub = mock[MessageHandler]
    val messageHandlersMock = mock[MessageHandlers]
    val router = new BroadcastingMessageRouter(messageHandlersMock)
    val testMessage = new TestMessage

    when(messageHandlersMock.toList) thenReturn List(firstHandlerStub, secondHandlerStub)

    When
    router.routeMessage(null, testMessage)

    Then
    verify(firstHandlerStub).handleMessage(null, testMessage)
    verify(secondHandlerStub).handleMessage(null, testMessage)
  }
}
