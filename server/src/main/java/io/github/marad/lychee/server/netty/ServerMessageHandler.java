package io.github.marad.lychee.server.netty;

import io.github.marad.lychee.api.State;
import io.github.marad.lychee.common.messages.InitialStateMessage;
import io.github.marad.lychee.server.Client;
import io.github.marad.lychee.server.ClientTracker;
import io.github.marad.lychee.server.LycheeServerConfig;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
@Named("ServerMessageHandler")
public class ServerMessageHandler extends ChannelInboundHandlerAdapter {
    private final TcpBroadcaster broadcaster;
    private final ClientTracker clientTracker;
    private State initialState;

    @Inject
    public ServerMessageHandler(
            LycheeServerConfig lycheeServerConfig,
            TcpBroadcaster broadcaster,
            ClientTracker clientTracker) {
        this.broadcaster = broadcaster;
        this.clientTracker = clientTracker;
        this.initialState = lycheeServerConfig.getInitialState();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        broadcaster.addChannel(ctx.channel());
        clientTracker.add(new Client(ctx.channel()));
        InitialStateMessage message = new InitialStateMessage(initialState);
        logger.info("Sending initial state {}", message);
        ctx.writeAndFlush(message);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("Received {}", msg);
//        ctx.writeAndFlush(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("Error caught", cause);
        ctx.close();
    }

    private static final Logger logger = LoggerFactory.getLogger(ServerMessageHandler.class);
}
