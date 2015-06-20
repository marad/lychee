package io.github.marad.lychee.client.netty;

import com.google.inject.Inject;
import io.github.marad.lychee.common.netty.InboundMessageDecoder;
import io.github.marad.lychee.common.netty.OutboundMessageEncoder;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {
    private final InboundMessageDecoder messageDecoder;
    private final OutboundMessageEncoder messageEncoder;
    private final ChannelInboundHandlerAdapter messageHandler;

    @Inject
    public ClientChannelInitializer(
            InboundMessageDecoder messageDecoder,
            OutboundMessageEncoder messageEncoder,
            ClientMessageHandler messageHandler) {
        this.messageDecoder = messageDecoder;
        this.messageEncoder = messageEncoder;
        this.messageHandler = messageHandler;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(messageDecoder, messageEncoder, messageHandler);
    }
}
