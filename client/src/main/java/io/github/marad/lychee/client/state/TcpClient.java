package io.github.marad.lychee.client.state;

import io.github.marad.lychee.client.ClientChannelInitializer;
import io.github.marad.lychee.client.ClientNotConnected;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class TcpClient {
    private final String hostname;
    private final int tcpPort;

    private final EventLoopGroup workerGroup = new NioEventLoopGroup();
    private Channel channel;
    private boolean connected = false;

    public TcpClient(String hostname, int tcpPort) {
        this.hostname = hostname;
        this.tcpPort = tcpPort;
    }

    public void start(ChannelInboundHandlerAdapter messageHandler) {
        ClientChannelInitializer initializer = new ClientChannelInitializer(messageHandler);
        Bootstrap tcpBootstrap = setupTcpClient(initializer);

        channel = tcpBootstrap.connect(hostname, tcpPort).channel();
        connected = true;
    }

    public void await() throws InterruptedException {
        assertConnected();
        channel.closeFuture().await();
    }

    public void disconnect() {
        assertConnected();
        channel.disconnect();
    }

    private Bootstrap setupTcpClient(ChannelInitializer<SocketChannel> initializer) {
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
