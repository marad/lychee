package io.github.marad.lychee.server;

import io.github.marad.lychee.api.State;
import io.github.marad.lychee.common.messages.StateMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerMessageHandler extends ChannelInboundHandlerAdapter {
    private State initialState;

    public ServerMessageHandler(State initialState) {
        this.initialState = initialState;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        StateMessage message = new StateMessage(initialState);
        logger.info("Sending initial state {}", message);
        ctx.writeAndFlush(message);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("Received {}", msg);
        ctx.writeAndFlush(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    private static final Logger logger = LoggerFactory.getLogger(ServerMessageHandler.class);
}
