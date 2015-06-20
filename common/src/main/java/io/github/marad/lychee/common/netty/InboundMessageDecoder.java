package io.github.marad.lychee.common.netty;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import io.github.marad.lychee.common.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import static io.github.marad.lychee.common.netty.DecoderState.READ_CONTENT;
import static io.github.marad.lychee.common.netty.DecoderState.READ_SIZE;

public class InboundMessageDecoder extends ReplayingDecoder<DecoderState> {
    private int size;

    public InboundMessageDecoder() {
        super(READ_SIZE);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        switch(state()) {
            case READ_SIZE:
                size = in.readInt();
                checkpoint(READ_CONTENT);

            case READ_CONTENT:
                ByteBuf frame = in.readBytes(size);
                checkpoint(READ_SIZE);
                Message message = decode(frame);
                out.add(message);
                break;

            default:
                throw new Error("Oops, this should not happen!");
        }
    }

    private Message decode(ByteBuf frame) {
        try {
            Kryo kryo = new Kryo();
            ByteArrayInputStream bais = new ByteArrayInputStream(frame.array());
            Input input = new Input(bais);
            Message state = (Message) kryo.readClassAndObject(input);
            input.close();
            bais.close();
            return state;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
