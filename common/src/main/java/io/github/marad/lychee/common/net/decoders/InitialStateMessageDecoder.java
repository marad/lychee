package io.github.marad.lychee.common.net.decoders;

import com.google.inject.Inject;
import io.github.marad.lychee.api.State;
import io.github.marad.lychee.common.StateSerializer;
import io.github.marad.lychee.common.messages.Message;
import io.github.marad.lychee.common.messages.MessageType;
import io.github.marad.lychee.common.messages.InitialStateMessage;
import io.github.marad.lychee.common.net.StateInfo;
import io.netty.buffer.ByteBuf;

public class InitialStateMessageDecoder implements MessageDecoder {
    private final StateInfo stateInfo;

    @Inject
    public InitialStateMessageDecoder(StateInfo stateInfo) {
        this.stateInfo = stateInfo;
    }

    @Override
    public int getMessageType() {
        return MessageType.STATE.code();
    }

    @Override
    public Message decode(ByteBuf byteBuf) {
        int size = byteBuf.readableBytes();
        byte[] data = new byte [size];
        byteBuf.readBytes(data);
        State state = StateSerializer.deserialize(data, stateInfo.getStateType());
        return new InitialStateMessage(state);
    }
}
