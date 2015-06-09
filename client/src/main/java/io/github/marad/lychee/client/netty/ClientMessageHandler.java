package io.github.marad.lychee.client.netty;

import io.github.marad.lychee.common.messages.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
@Named("ClientMessageHandler")
public class ClientMessageHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        Message message = new StatePatch(100, new byte[] {0x1, 0x2});
//        Message message = new StringMessage("Zażółć gęślą jaźń");
//        logger.info("Sending {}", message);
//        ctx.writeAndFlush(message);
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("Received {}", msg);
        Message message = (Message) msg;
    }

    private static final Logger logger = LoggerFactory.getLogger(ClientMessageHandler.class);
}