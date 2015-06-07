package io.github.marad.lychee.server.endpoints;

import io.github.marad.lychee.common.net.decoders.Decoders;
import io.github.marad.lychee.common.net.encoders.Encoders;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.io.Closeable;
import java.io.IOException;

public class TcpServer implements Closeable {
    private static final Encoders encoders = Encoders.getInstance();
    private static final Decoders decoders = Decoders.getInstance();

    private final int port;

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private ChannelFuture closeFuture;
    private boolean serverStarted = false;

    public TcpServer(int port) {
        this.port = port;
    }

    public void start(ChannelInboundHandlerAdapter messageHandler) throws InterruptedException {
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        ServerBootstrap tcpServerSetup = setupTcpServer(messageHandler);
        closeFuture = tcpServerSetup.bind(port)
                .sync().channel().closeFuture();
        serverStarted = true;
    }

    @Override
    public void close() throws IOException {
        assertStarted();
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

    public boolean isStarted() {
        return serverStarted;
    }

    public void await() throws InterruptedException {
        assertStarted();
        closeFuture.await();
    }

    private ServerBootstrap setupTcpServer(ChannelInboundHandlerAdapter messageHandler) {
        return new ServerBootstrap()
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ServerChannelInitializer(encoders, decoders, messageHandler));
    }

    private void assertStarted() {
        if (!isStarted()) {
            throw new ServerNotStarted();
        }
    }
}
