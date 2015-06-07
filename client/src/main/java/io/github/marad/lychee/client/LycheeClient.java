package io.github.marad.lychee.client;

import io.github.marad.lychee.common.net.decoders.Decoders;
import io.github.marad.lychee.common.net.encoders.Encoders;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class LycheeClient {
    private final String hostname;
    private final int tcpPort;
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();
    private ChannelFuture tcpChannelCloseFuture;
    private boolean connected = false;

    public LycheeClient(String hostname, int tcpPort) {
        this.hostname = hostname;
        this.tcpPort = tcpPort;
    }

    public void connect() {
        ClientChannelInitializer initializer = new ClientChannelInitializer(
                Encoders.getInstance(), Decoders.getInstance()
        );

        Bootstrap tcpBootstrap = setupTcpClient(initializer);
        tcpChannelCloseFuture = tcpBootstrap.connect(hostname, tcpPort)
                .channel().closeFuture();
        connected = true;
    }

    public void await() throws InterruptedException {
        if (!connected) {
            throw new ClientNotConnected();
        }
        tcpChannelCloseFuture.await();
    }

    private Bootstrap setupTcpClient(ChannelInitializer<SocketChannel> initializer) {
        return new Bootstrap().group(workerGroup)
            .channel(NioSocketChannel.class)
            .option(ChannelOption.SO_KEEPALIVE, true)
            .handler(initializer);
    }
}
