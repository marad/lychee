package io.github.marad.lychee.common.handlers;

import com.google.inject.Inject;
import io.github.marad.lychee.common.Message;
import io.netty.channel.ChannelHandlerContext;

public class BroadcastingMessageRouter implements MessageRouter {
    private final MessageHandlers messageHandlers;

    @Inject
    public BroadcastingMessageRouter(MessageHandlers messageHandlers) {
        this.messageHandlers = messageHandlers;
    }

    @Override
    public void routeConnected(ChannelHandlerContext ctx) {
        for(MessageHandler handler : messageHandlers.toList()) {
            handler.handleConnected(ctx);
        }
    }

    @Override
    public void routeDisconnected(ChannelHandlerContext ctx) {
        for(MessageHandler handler : messageHandlers.toList()) {
            handler.handleDisconnected(ctx);
        }
    }

    @Override
    public void routeMessage(ChannelHandlerContext ctx, Message message) {
        for(MessageHandler handler : messageHandlers.toList()) {
            handler.handleMessage(ctx, message);
        }
    }
}
