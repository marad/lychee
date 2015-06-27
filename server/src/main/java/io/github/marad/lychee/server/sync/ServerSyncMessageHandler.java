package io.github.marad.lychee.server.sync;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.github.marad.lychee.common.Message;
import io.github.marad.lychee.common.handlers.MessageHandlerAdapter;
import io.github.marad.lychee.common.handlers.MessageHandlers;
import io.github.marad.lychee.common.sync.messages.ConfirmStateMessage;
import io.github.marad.lychee.common.sync.messages.InitialStateMessage;
import io.github.marad.lychee.server.annotations.Server;
import io.github.marad.lychee.server.sync.clients.Client;
import io.github.marad.lychee.server.sync.clients.ClientTracker;
import io.github.marad.lychee.server.sync.state.ServerStateTracker;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class ServerSyncMessageHandler extends MessageHandlerAdapter {
    private final ServerStateTracker stateTracker;
    private final ClientTracker clientTracker;

    @Inject
    public ServerSyncMessageHandler(
            @Server MessageHandlers messageHandlers,
            ServerStateTracker stateTracker,
            ClientTracker clientTracker) {
        this.stateTracker = stateTracker;
        this.clientTracker = clientTracker;
        messageHandlers.register(this);
    }

    @Override
    public void handleConnected(ChannelHandlerContext ctx) {
        InitialStateMessage message = new InitialStateMessage(stateTracker.getState());
        logger.info("Sending initial state {}", message);
        ctx.writeAndFlush(message);
    }

    @Override
    public void handleMessage(ChannelHandlerContext ctx, Message message) {
        if (message instanceof ConfirmStateMessage) {
            ConfirmStateMessage confirmStateMessage = (ConfirmStateMessage) message;
            Client client = clientTracker.findByChannel(ctx.channel());
            client.setStateVersion(confirmStateMessage.getStateVersion());
        }
    }

    private static final Logger logger = LoggerFactory.getLogger(ServerSyncMessageHandler.class);
}
