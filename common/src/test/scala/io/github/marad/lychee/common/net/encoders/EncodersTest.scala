package io.github.marad.lychee.common.net.encoders

import io.github.marad.lychee.common.UnitTest

class EncodersTest extends UnitTest {

  it should "find registered encoder" in {
    Given
    val statePatchEncoder = new StatePatchEncoder
    val encoders = new Encoders
    encoders.register(statePatchEncoder)

    When
    val result = encoders.findEncoder(statePatchEncoder.getMessageType)

    Then
    result shouldBe statePatchEncoder
  }

  it should "fail when asked for unregistered encoder" in {
    Given
    val encoders = new Encoders

    Expect
    intercept[EncoderNotFound] {
      encoders.findEncoder(12345)
    }
  }
}
