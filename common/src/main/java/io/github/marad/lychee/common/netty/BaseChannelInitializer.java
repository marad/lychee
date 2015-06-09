package io.github.marad.lychee.common.netty;

import com.google.inject.Inject;
import io.github.marad.lychee.common.net.InboundMessageDecoder;
import io.github.marad.lychee.common.net.OutboundMessageEncoder;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;

public class BaseChannelInitializer extends io.netty.channel.ChannelInitializer<SocketChannel> {
    private final InboundMessageDecoder messageDecoder;
    private final OutboundMessageEncoder messageEncoder;
    private final ChannelInboundHandlerAdapter messageHandler;

    @Inject
    public BaseChannelInitializer(
            InboundMessageDecoder messageDecoder,
            OutboundMessageEncoder messageEncoder,
            ChannelInboundHandlerAdapter messageHandler) {
        this.messageDecoder = messageDecoder;
        this.messageEncoder = messageEncoder;
        this.messageHandler = messageHandler;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(messageDecoder, messageEncoder, messageHandler);
    }
}
