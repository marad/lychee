package io.github.marad.lychee.common.net.decoders;

import io.github.marad.lychee.api.State;
import io.github.marad.lychee.common.StateSerializer;
import io.github.marad.lychee.common.messages.Message;
import io.github.marad.lychee.common.messages.MessageType;
import io.github.marad.lychee.common.messages.StateMessage;
import io.netty.buffer.ByteBuf;

import java.io.IOException;

public class StateMessageDecoder implements MessageDecoder {
    @Override
    public int getMessageType() {
        return MessageType.STATE.code();
    }

    @Override
    public Message decode(ByteBuf byteBuf) {
        int size = byteBuf.readableBytes();
        byte[] data = new byte [size];
        byteBuf.readBytes(data);
        try {
            State state = StateSerializer.deserialize(data);
            return new StateMessage(state);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
