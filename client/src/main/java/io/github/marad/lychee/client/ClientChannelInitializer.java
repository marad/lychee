package io.github.marad.lychee.client;

import io.github.marad.lychee.common.net.InboundMessageDecoder;
import io.github.marad.lychee.common.net.OutboundMessageEncoder;
import io.github.marad.lychee.common.net.decoders.Decoders;
import io.github.marad.lychee.common.net.encoders.Encoders;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {
    private final Encoders encoders;
    private final Decoders decoders;

    public ClientChannelInitializer(Encoders encoders, Decoders decoders) {
        this.encoders = encoders;
        this.decoders = decoders;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(
                new InboundMessageDecoder(decoders),
                new OutboundMessageEncoder(encoders),
                new ClientMessageHandler()
        );
    }
}
