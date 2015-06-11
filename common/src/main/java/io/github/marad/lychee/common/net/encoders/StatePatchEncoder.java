package io.github.marad.lychee.common.net.encoders;

import io.github.marad.lychee.common.messages.Message;
import io.github.marad.lychee.common.messages.StatePatchMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import static io.github.marad.lychee.common.messages.MessageType.STATE_PATCH;

public class StatePatchEncoder implements MessageEncoder {
    @Override
    public int getMessageType() {
        return STATE_PATCH.code();
    }

    @Override
    public ByteBuf encode(Message message) {
        StatePatchMessage statePatchMessage = (StatePatchMessage) message;
        ByteBuf result = Unpooled.buffer(4 + statePatchMessage.getPatch().length);
        result.writeInt((int) statePatchMessage.getStateToPatchSeq());
        result.writeBytes(statePatchMessage.getPatch());
        return result;
    }
}
