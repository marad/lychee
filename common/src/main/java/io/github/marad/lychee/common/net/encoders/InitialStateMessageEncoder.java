package io.github.marad.lychee.common.net.encoders;

import io.github.marad.lychee.common.StateSerializer;
import io.github.marad.lychee.common.messages.Message;
import io.github.marad.lychee.common.messages.MessageType;
import io.github.marad.lychee.common.messages.InitialStateMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class InitialStateMessageEncoder implements MessageEncoder {
    @Override
    public int getMessageType() {
        return MessageType.STATE.code();
    }

    @Override
    public ByteBuf encode(Message message) {
        InitialStateMessage initialStateMessage = (InitialStateMessage) message;
        byte[] data = StateSerializer.serialize(initialStateMessage.getState());
        return Unpooled.copiedBuffer(data);
    }
}
