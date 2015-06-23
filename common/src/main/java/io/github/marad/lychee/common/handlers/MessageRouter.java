package io.github.marad.lychee.common.handlers;

import io.github.marad.lychee.common.Message;
import io.netty.channel.ChannelHandlerContext;

public interface MessageRouter {
    void routeConnected(ChannelHandlerContext ctx);
    void routeDisconnected(ChannelHandlerContext ctx);
    void routeMessage(ChannelHandlerContext ctx, Message message);
}
