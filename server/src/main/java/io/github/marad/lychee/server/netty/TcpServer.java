package io.github.marad.lychee.server.netty;

import io.github.marad.lychee.server.LycheeServerConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.TimeUnit;

@Singleton
public class TcpServer {
    private final ChannelInitializer<SocketChannel> initializer;
    private final int port;

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private Channel channel;
    private boolean serverStarted = false;

    @Inject
    public TcpServer(
            ServerChannelInitializer initializer,
            LycheeServerConfig config) {
        this.initializer = initializer;
        this.port = config.getTcpPort();
    }

    public void start() throws InterruptedException {
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        ServerBootstrap tcpServerSetup = setupTcpServer();

        channel = tcpServerSetup.bind(port)
                .sync().channel();
        serverStarted = true;
    }

    public ChannelFuture close() {
        assertStarted();
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
        channel.close();
        return channel.closeFuture();
    }

    public boolean isStarted() {
        return serverStarted;
    }

    public void await() throws InterruptedException {
        assertStarted();
        channel.closeFuture().await();
    }

    public void await(long timeoutMillis) throws InterruptedException {
        assertStarted();
        channel.closeFuture().await(timeoutMillis);
    }

    public void await(long timeout, TimeUnit timeUnit) throws InterruptedException {
        assertStarted();
        channel.closeFuture().await(timeout, timeUnit);
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
