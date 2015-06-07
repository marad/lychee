package io.github.marad.lychee.common.net.decoders;

import io.github.marad.lychee.common.messages.Message;
import io.github.marad.lychee.common.messages.MessageType;
import io.github.marad.lychee.common.messages.StringMessage;
import io.netty.buffer.ByteBuf;

import java.io.UnsupportedEncodingException;

public class StringMessageDecoder implements MessageDecoder {
    @Override
    public int getMessageType() {
        return MessageType.STRING.code();
    }

    @Override
    public Message decode(ByteBuf byteBuf) {
        int length = byteBuf.readInt();
        byte[] data = new byte [length];
        byteBuf.readBytes(data);
        try {
            return new StringMessage(new String(data, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not decode string");
        }
    }
}
