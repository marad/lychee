package io.github.marad.lychee.common.net.encoders

import com.google.inject.{Guice, AbstractModule}
import io.github.marad.lychee.common.UnitTest
import io.github.marad.lychee.common.messages.Message
import io.github.marad.lychee.common.net.encoders.EncodersTest.TestEncoder
import io.netty.buffer.ByteBuf

object EncodersTest {
  class TestEncoder extends MessageEncoder {
    override def getMessageType: Int = 1
    override def encode(message: Message): ByteBuf = ???
  }
}

class EncodersTest extends UnitTest {

  class TestModule extends AbstractModule {
    override def configure(): Unit = {
      bind(classOf[TestEncoder])
      bind(classOf[Encoders])
    }
  }

  val injector = Guice.createInjector(new TestModule)

  it should "find registered encoder" in {
    Given
    val encoders = injector.getInstance(classOf[Encoders])

    When
    val result = encoders.findEncoder(1)

    Then
    result should not be null
  }

  it should "fail when asked for unregistered encoder" in {
    Given
    val encoders = injector.getInstance(classOf[Encoders])

    Expect
    intercept[EncoderNotFound] {
      encoders.findEncoder(12345)
    }
  }
}
