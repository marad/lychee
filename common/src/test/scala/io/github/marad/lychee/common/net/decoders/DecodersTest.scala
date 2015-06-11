package io.github.marad.lychee.common.net.decoders

import com.google.inject.{Guice, AbstractModule}
import io.github.marad.lychee.common.UnitTest
import io.github.marad.lychee.common.messages.Message
import io.github.marad.lychee.common.net.decoders.DecodersTest.TestDecoder
import io.netty.buffer.ByteBuf

object DecodersTest {
  class TestDecoder extends MessageDecoder {
    override def getMessageType: Int = 1
    override def decode(byteBuf: ByteBuf): Message = ???
  }
}

class DecodersTest extends UnitTest {
  class TestModule extends AbstractModule {
    override def configure(): Unit = {
      bind(classOf[TestDecoder])
      bind(classOf[Decoders])
    }
  }

  it should "find registered decoder for message type" in {
    Given
    val injector = Guice.createInjector(new TestModule)
    val decoders = injector.getInstance(classOf[Decoders])

    When
    val result = decoders findDecoder 1

    Then
    result should not be null
  }

  it should "fail when asked for not registered decoder" in {
    When
    val injector = Guice.createInjector(new TestModule)
    val decoders = injector.getInstance(classOf[Decoders])
    val unknownMessageType = 12345

    Expect
    intercept[DecoderNotFound] {
      decoders.findDecoder(unknownMessageType)
    }
  }
}
