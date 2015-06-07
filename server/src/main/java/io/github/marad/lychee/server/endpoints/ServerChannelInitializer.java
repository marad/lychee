package io.github.marad.lychee.server.endpoints;

import io.github.marad.lychee.common.net.InboundMessageDecoder;
import io.github.marad.lychee.common.net.OutboundMessageEncoder;
import io.github.marad.lychee.common.net.decoders.Decoders;
import io.github.marad.lychee.common.net.encoders.Encoders;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    private final Encoders encoders;
    private final Decoders decoders;
    private ChannelInboundHandlerAdapter messageHandler;

    public ServerChannelInitializer(Encoders encoders, Decoders decoders, ChannelInboundHandlerAdapter messageHandler) {
        this.encoders = encoders;
        this.decoders = decoders;
        this.messageHandler = messageHandler;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(
                new InboundMessageDecoder(decoders),
                new OutboundMessageEncoder(encoders),
                messageHandler
        );
    }
}
