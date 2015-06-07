package io.github.marad.lychee.common.net.encoders;

import io.github.marad.lychee.common.messages.Message;
import io.github.marad.lychee.common.messages.StatePatch;
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
        StatePatch statePatch = (StatePatch) message;
        ByteBuf result = Unpooled.buffer(4 + statePatch.getPatch().length);
        result.writeInt((int)statePatch.getStateToPatchSeq());
        result.writeBytes(statePatch.getPatch());
        return result;
    }
}
