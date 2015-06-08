package io.github.marad.lychee.server.endpoints;

import io.github.marad.lychee.common.net.InboundMessageDecoder;
import io.github.marad.lychee.common.net.OutboundMessageEncoder;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    private ChannelInboundHandlerAdapter messageHandler;

    public ServerChannelInitializer(ChannelInboundHandlerAdapter messageHandler) {
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
