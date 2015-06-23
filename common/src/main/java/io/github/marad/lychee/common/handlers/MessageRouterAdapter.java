package io.github.marad.lychee.common.handlers;

import io.github.marad.lychee.common.Message;
import io.netty.channel.ChannelHandlerContext;

public abstract class MessageRouterAdapter implements MessageRouter {
    @Override
    public void routeConnected(ChannelHandlerContext ctx) {
    }

    @Override
    public void routeDisconnected(ChannelHandlerContext ctx) {
    }

    @Override
    public void routeMessage(ChannelHandlerContext ctx, Message message) {
    }
}
