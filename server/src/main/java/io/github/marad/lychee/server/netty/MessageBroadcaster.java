package io.github.marad.lychee.server.netty;

import io.github.marad.lychee.common.Message;
import io.netty.channel.group.ChannelGroupFuture;

public interface MessageBroadcaster {
    ChannelGroupFuture broadcast(Message message);
}
