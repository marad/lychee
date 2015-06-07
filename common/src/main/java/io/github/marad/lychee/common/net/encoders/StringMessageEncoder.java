package io.github.marad.lychee.common.net.encoders;

import io.github.marad.lychee.common.messages.Message;
import io.github.marad.lychee.common.messages.MessageType;
import io.github.marad.lychee.common.messages.StringMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.UnsupportedEncodingException;

public class StringMessageEncoder implements MessageEncoder {
    @Override
    public int getMessageType() {
        return MessageType.STRING.code();
    }

    @Override
    public ByteBuf encode(Message message) {
        StringMessage stringMessage = (StringMessage) message;
        try {
            byte[] data = stringMessage.getText().getBytes("UTF-8");
            ByteBuf result = Unpooled.buffer();
            result.writeInt(data.length);
            result.writeBytes(data);
            return result;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not encode UTF-8");
        }

    }
}
