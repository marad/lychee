package io.github.marad.lychee.common.net.encoders;

import io.github.marad.lychee.common.StateSerializer;
import io.github.marad.lychee.common.messages.Message;
import io.github.marad.lychee.common.messages.MessageType;
import io.github.marad.lychee.common.messages.StateMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.IOException;

public class StateMessageEncoder implements MessageEncoder {
    @Override
    public int getMessageType() {
        return MessageType.STATE.code();
    }

    @Override
    public ByteBuf encode(Message message) {
        StateMessage stateMessage = (StateMessage) message;
        try {
            byte[] data = StateSerializer.serialize(stateMessage.getState());
            return Unpooled.copiedBuffer(data);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
