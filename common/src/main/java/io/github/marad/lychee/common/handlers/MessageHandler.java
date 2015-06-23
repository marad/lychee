package io.github.marad.lychee.common.handlers;

import io.github.marad.lychee.common.Message;
import io.netty.channel.ChannelHandlerContext;

public interface MessageHandler {
    void handleConnected(ChannelHandlerContext ctx);
    void handleDisconnected(ChannelHandlerContext ctx);
    void handleMessage(ChannelHandlerContext ctx, Message message);
}
