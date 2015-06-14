package io.github.marad.lychee.common.messages.decoders

import io.github.marad.lychee.common.UnitTest
import io.github.marad.lychee.common.messages.StatePatchMessage
import io.github.marad.lychee.common.net.decoders.StatePatchDecoder
import io.netty.buffer.Unpooled

class StatePatchMessageDecoderTest extends UnitTest {

  it should "decode valid packet" in {
    Given
    val stateSeq = 100
    val patch = Array[Byte](0x11.toByte, 0x22.toByte, 0x33.toByte, 0x44.toByte)
    val packet = createPacket(stateSeq, patch)

    When
    val message = (new StatePatchDecoder).decode(packet).asInstanceOf[StatePatchMessage]

    Then
    message.getPreviousStateVersion shouldBe stateSeq
    message.getPatch shouldBe patch
  }

  def createPacket(stateSeq: Int, patch: Array[Byte]) = {
    val packet = Unpooled.buffer()
    packet.writeInt(stateSeq)
    packet.writeBytes(patch)
    packet
  }
}
