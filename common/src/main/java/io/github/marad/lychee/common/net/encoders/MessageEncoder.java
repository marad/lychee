package io.github.marad.lychee.common.net.encoders;

import io.github.marad.lychee.common.messages.Message;
import io.netty.buffer.ByteBuf;

public interface MessageEncoder {
    int getMessageType();
    ByteBuf encode(Message message);
}
