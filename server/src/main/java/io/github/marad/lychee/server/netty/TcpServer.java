package io.github.marad.lychee.server.netty;

import io.github.marad.lychee.server.LycheeServerConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.Closeable;
import java.io.IOException;

@Singleton
public class TcpServer implements Closeable {
    private final ChannelInitializer<SocketChannel> initializer;
    private final int port;

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private ChannelFuture closeFuture;
    private boolean serverStarted = false;

    @Inject
    public TcpServer(ChannelInitializer<SocketChannel> initializer, LycheeServerConfig config) {
        this.initializer = initializer;
        this.port = config.getTcpPort();
    }

    public void start() throws InterruptedException {
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        ServerBootstrap tcpServerSetup = setupTcpServer();
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

    public void await(long timeoutMillis) throws InterruptedException {
        assertStarted();
        closeFuture.await(timeoutMillis);
    }

    private ServerBootstrap setupTcpServer() {
        return new ServerBootstrap()
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(initializer);
    }

    private void assertStarted() {
        if (!isStarted()) {
            throw new ServerNotStarted();
        }
    }
}
