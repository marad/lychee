package io.github.marad.lychee.common.net;

import com.google.inject.Inject;
import io.github.marad.lychee.common.messages.Message;
import io.github.marad.lychee.common.net.encoders.Encoders;
import io.github.marad.lychee.common.net.encoders.MessageEncoder;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class OutboundMessageEncoder extends MessageToByteEncoder<Message> {
    private final Encoders encoders;

    @Inject
    public OutboundMessageEncoder(Encoders encoders) {
        this.encoders = encoders;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        MessageEncoder encoder = encoders.findEncoder(msg.getType());
        ByteBuf encoded = encoder.encode(msg);
        out.writeInt(4 + encoded.readableBytes());
        out.writeInt(msg.getType());
        out.writeBytes(encoded);
    }
}
