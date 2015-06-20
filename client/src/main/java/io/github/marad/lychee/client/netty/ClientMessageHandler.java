package io.github.marad.lychee.client.netty;

import com.google.inject.Inject;
import io.github.marad.lychee.client.annotations.Client;
import io.github.marad.lychee.common.Message;
import io.github.marad.lychee.common.handlers.MessageRouter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import javax.inject.Singleton;

@Singleton
public class ClientMessageHandler extends ChannelInboundHandlerAdapter {
    private final MessageRouter messageRouter;

    @Inject
    public ClientMessageHandler(@Client MessageRouter messageRouter) {
        this.messageRouter = messageRouter;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        messageRouter.route(ctx, (Message)msg);
    }
}
