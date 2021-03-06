package io.github.marad.lychee.server.netty;

import io.github.marad.lychee.api.State;
import io.github.marad.lychee.common.handlers.MessageRouter;
import io.github.marad.lychee.common.sync.messages.InitialStateMessage;
import io.github.marad.lychee.common.Message;
import io.github.marad.lychee.server.sync.clients.Client;
import io.github.marad.lychee.server.sync.clients.ClientTracker;
import io.github.marad.lychee.server.LycheeServerConfig;
import io.github.marad.lychee.server.annotations.Server;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
@Named("ServerMessageHandler")
public class ServerMessageHandler extends ChannelInboundHandlerAdapter {
    private final MessageRouter messageRouter;
    private final ClientTracker clientTracker;
    private State initialState;

    @Inject
    public ServerMessageHandler(
            LycheeServerConfig lycheeServerConfig,
            @Server MessageRouter messageRouter,
            ClientTracker clientTracker) {
        this.messageRouter = messageRouter;
        this.clientTracker = clientTracker;
        this.initialState = lycheeServerConfig.getInitialState();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        messageRouter.routeConnected(ctx);
//        broadcaster.addChannel(ctx.channel());
//        clientTracker.add(new Client(ctx.channel()));
//        InitialStateMessage message = new InitialStateMessage(initialState);
//        logger.info("Sending initial state {}", message);
//        ctx.writeAndFlush(message);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("Received {}", msg);
        messageRouter.routeMessage(ctx, (Message) msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("Error caught", cause);
        ctx.close();
    }

    private static final Logger logger = LoggerFactory.getLogger(ServerMessageHandler.class);
}
