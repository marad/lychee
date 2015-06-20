package io.github.marad.lychee.client.netty;

import com.google.inject.Inject;
import io.github.marad.lychee.client.ClientNotConnected;
import io.github.marad.lychee.client.LycheeClientConfig;
import io.github.marad.lychee.client.netty.ClientChannelInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.TimeUnit;

public class TcpClient {
    private final LycheeClientConfig lycheeClientConfig;
    private final ChannelInitializer<SocketChannel> initializer;
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();
    private Channel channel;
    private boolean connected = false;

    @Inject
    public TcpClient(LycheeClientConfig lycheeClientConfig, ClientChannelInitializer initializer) {
        this.lycheeClientConfig = lycheeClientConfig;
        this.initializer = initializer;
    }

    public void start() {
        Bootstrap tcpBootstrap = setupTcpClient();
        channel = tcpBootstrap.connect(lycheeClientConfig.getHostname(), lycheeClientConfig.getTcpPort()).channel();
        connected = true;
    }

    public ChannelFuture close() {
        return channel.close();
    }

    public void await() throws InterruptedException {
        assertConnected();
        channel.closeFuture().await();
    }

    public void await(long timeoutMillis) throws InterruptedException {
        assertConnected();
        channel.closeFuture().await(timeoutMillis);
    }

    public void await(long timeout, TimeUnit timeUnit) throws InterruptedException {
        assertConnected();
        channel.closeFuture().await(timeout, timeUnit);
    }

    private Bootstrap setupTcpClient() {
        return new Bootstrap().group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(initializer);
    }

    private void assertConnected() {
        if (!connected) {
            throw new ClientNotConnected();
        }
    }
}
