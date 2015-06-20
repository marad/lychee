package io.github.marad.lychee.common.netty;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import io.github.marad.lychee.common.messages.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class OutboundMessageEncoder extends MessageToByteEncoder<Message> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        try {
            byte[] data = encode(msg);
            out.writeInt(data.length);
            out.writeBytes(data);
        } catch (IOException ex){
            throw new RuntimeException(ex);
        }
    }

    private byte[] encode(Message msg) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Kryo kryo = new Kryo();
        Output output = new Output(baos);
        kryo.writeClassAndObject(output, msg);
        output.close();
        baos.close();
        return baos.toByteArray();
    }
}
