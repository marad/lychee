package io.github.marad.lychee.client;

import io.github.marad.lychee.common.net.InboundMessageDecoder;
import io.github.marad.lychee.common.net.OutboundMessageEncoder;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {
    private final ChannelInboundHandlerAdapter messageHandler;

    public ClientChannelInitializer(ChannelInboundHandlerAdapter messageHandler) {
        this.messageHandler = messageHandler;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(
                new InboundMessageDecoder(),
                new OutboundMessageEncoder(),
                messageHandler
        );
    }
}
