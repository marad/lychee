package io.github.marad.lychee.common.net.decoders;

import io.github.marad.lychee.common.messages.ConfirmStateMessage;
import io.github.marad.lychee.common.messages.Message;
import io.github.marad.lychee.common.messages.MessageType;
import io.netty.buffer.ByteBuf;

public class ConfirmStateMessageDecoder implements MessageDecoder {
    @Override
    public int getMessageType() {
        return MessageType.CONFIRM_STATE.code();
    }

    @Override
    public Message decode(ByteBuf byteBuf) {
        return new ConfirmStateMessage(byteBuf.readLong());
    }
}
