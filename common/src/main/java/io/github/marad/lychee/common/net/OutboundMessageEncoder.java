package io.github.marad.lychee.common.net;

import io.github.marad.lychee.common.messages.Message;
import io.github.marad.lychee.common.net.encoders.Encoders;
import io.github.marad.lychee.common.net.encoders.MessageEncoder;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class OutboundMessageEncoder extends MessageToByteEncoder<Message> {
    private final Encoders encoders;

    public OutboundMessageEncoder() {
        this.encoders = Encoders.getInstance();
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        MessageEncoder encoder = encoders.findEncoder(msg.getType().code());
        ByteBuf encoded = encoder.encode(msg);
        out.writeInt(2 + encoded.readableBytes());
        out.writeShort(msg.getType().code());
        out.writeBytes(encoded);
    }
}
