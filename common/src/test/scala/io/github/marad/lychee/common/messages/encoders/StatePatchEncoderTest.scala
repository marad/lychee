package io.github.marad.lychee.common.messages.encoders

import io.github.marad.lychee.common.UnitTest
import io.github.marad.lychee.common.messages.StatePatch
import io.github.marad.lychee.common.net.decoders.StatePatchDecoder
import io.github.marad.lychee.common.net.encoders.StatePatchEncoder

class StatePatchEncoderTest extends UnitTest {

  it should "create decodable buffer" in {
    Given
    val encoder = new StatePatchEncoder
    val decoder = new StatePatchDecoder
    val statePatch = new StatePatch(100, Array(1.toByte, 2.toByte, 3.toByte))

    When
    val buffer = encoder.encode(statePatch)

    Then
    decoder.decode(buffer) shouldBe statePatch
  }

}
