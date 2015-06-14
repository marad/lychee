package io.github.marad.lychee.common.net.decoders;

import io.github.marad.lychee.common.messages.Message;
import io.github.marad.lychee.common.messages.StatePatchMessage;
import io.netty.buffer.ByteBuf;

import static io.github.marad.lychee.common.messages.MessageType.STATE_PATCH;

public class StatePatchDecoder implements MessageDecoder {
    @Override
    public int getMessageType() {
        return STATE_PATCH.code();
    }

    @Override
    public Message decode(ByteBuf frame) {
        long stateSeq = frame.readUnsignedInt();
        int dataSize = frame.readableBytes();
        byte[] data = new byte [dataSize];
        frame.readBytes(data);
        return new StatePatchMessage(stateSeq, currentStateVersion, data);
    }
}
