package io.github.marad.lychee.common.net.decoders;

import io.github.marad.lychee.common.messages.Message;
import io.netty.buffer.ByteBuf;

public interface MessageDecoder {
    int getMessageType();
    Message decode(ByteBuf byteBuf);
}
