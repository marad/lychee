package io.github.marad.lychee.common.net.decoders

import io.github.marad.lychee.common.UnitTest

class DecodersTest extends UnitTest {

  it should "find registered decoder for message type" in {
    Given
    val decoders = new Decoders
    val statePatchDecoder = new StatePatchDecoder
    decoders register statePatchDecoder

    When
    val result = decoders findDecoder statePatchDecoder.getMessageType

    Then
    result shouldBe statePatchDecoder
  }

  it should "fail when asked for not registered decoder" in {
    When
    val decoders = new Decoders
    val unknownMessageType = 12345

    Expect
    intercept[DecoderNotFound] {
      decoders.findDecoder(unknownMessageType)
    }
  }

}
