package io.github.marad.lychee.common.net;

import com.google.inject.Inject;
import io.github.marad.lychee.common.messages.Message;
import io.github.marad.lychee.common.net.decoders.MessageDecoder;
import io.github.marad.lychee.common.net.decoders.Decoders;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

import static io.github.marad.lychee.common.net.DecoderState.READ_CONTENT;
import static io.github.marad.lychee.common.net.DecoderState.READ_SIZE;

public class InboundMessageDecoder extends ReplayingDecoder<DecoderState> {
    private final Decoders decoders;
    private int size;

    @Inject
    public InboundMessageDecoder(Decoders decoders) {
        super(READ_SIZE);
        this.decoders = decoders;
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
        int messageType = frame.readInt();
        MessageDecoder decoder = decoders.findDecoder(messageType);
        return decoder.decode(frame);
    }
}
