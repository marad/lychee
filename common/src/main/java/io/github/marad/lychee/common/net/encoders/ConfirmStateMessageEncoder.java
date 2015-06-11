package io.github.marad.lychee.common.net.encoders;

import io.github.marad.lychee.common.messages.ConfirmStateMessage;
import io.github.marad.lychee.common.messages.Message;
import io.github.marad.lychee.common.messages.MessageType;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class ConfirmStateMessageEncoder implements MessageEncoder {
    @Override
    public int getMessageType() {
        return MessageType.CONFIRM_STATE.code();
    }

    @Override
    public ByteBuf encode(Message message) {
        ConfirmStateMessage confirmStateMessage = (ConfirmStateMessage) message;
        ByteBuf result = Unpooled.buffer();
        result.writeLong(confirmStateMessage.getStateSeq());
        return result;
    }
}
