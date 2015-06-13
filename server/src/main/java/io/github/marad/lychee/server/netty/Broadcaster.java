package io.github.marad.lychee.server.netty;

import io.github.marad.lychee.common.messages.Message;
import io.netty.channel.group.ChannelGroupFuture;

public interface Broadcaster {
    ChannelGroupFuture broadcast(Message message);
}
