package io.github.marad.lychee.common.handlers;

import io.github.marad.lychee.common.Message;
import io.netty.channel.ChannelHandlerContext;

public interface MessageHandler {
    void handleMessage(ChannelHandlerContext ctx, Message message);
}
