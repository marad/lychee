package io.github.marad.lychee.server.netty;

import com.google.inject.Singleton;
import io.github.marad.lychee.common.Message;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.ChannelGroupFuture;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

@Singleton
public class TcpBroadcaster implements MessageBroadcaster {
    private final ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public void addChannel(Channel channel) {
        channelGroup.add(channel);
    }

    @Override
    public ChannelGroupFuture broadcast(Message message) {
        return channelGroup.writeAndFlush(message);
    }
}
