package io.github.marad.lychee.server.netty;

import com.google.inject.Inject;
import io.github.marad.lychee.common.netty.InboundMessageDecoder;
import io.github.marad.lychee.common.netty.OutboundMessageEncoder;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    private final InboundMessageDecoder messageDecoder;
    private final OutboundMessageEncoder messageEncoder;
    private final ChannelInboundHandlerAdapter messageHandler;

    @Inject
    public ServerChannelInitializer(
            InboundMessageDecoder messageDecoder,
            OutboundMessageEncoder messageEncoder,
            ServerMessageHandler messageHandler) {
        this.messageDecoder = messageDecoder;
        this.messageEncoder = messageEncoder;
        this.messageHandler = messageHandler;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(messageDecoder, messageEncoder, messageHandler);
    }
}
