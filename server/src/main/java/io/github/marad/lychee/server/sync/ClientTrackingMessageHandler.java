package io.github.marad.lychee.server.sync;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.github.marad.lychee.common.handlers.MessageHandlerAdapter;
import io.github.marad.lychee.common.handlers.MessageHandlers;
import io.github.marad.lychee.server.annotations.Server;
import io.github.marad.lychee.server.sync.clients.Client;
import io.github.marad.lychee.server.sync.clients.ClientTracker;
import io.netty.channel.ChannelHandlerContext;

@Singleton
public class ClientTrackingMessageHandler extends MessageHandlerAdapter {
    private final ClientTracker clientTracker;

    @Inject
    public ClientTrackingMessageHandler(
            @Server MessageHandlers messageHandlers, ClientTracker clientTracker) {
        this.clientTracker = clientTracker;
        messageHandlers.register(this);
    }

    @Override
    public void handleConnected(ChannelHandlerContext ctx) {
        clientTracker.add(new Client(ctx.channel()));
    }

    @Override
    public void handleDisconnected(ChannelHandlerContext ctx) {
        clientTracker.removeByChannel(ctx.channel());
    }
}
