package io.github.marad.lychee.client.sync;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.github.marad.lychee.api.State;
import io.github.marad.lychee.client.annotations.Client;
import io.github.marad.lychee.client.sync.state.ClientStateTracker;
import io.github.marad.lychee.client.sync.state.StatePatchApplier;
import io.github.marad.lychee.common.Message;
import io.github.marad.lychee.common.handlers.MessageHandler;
import io.github.marad.lychee.common.handlers.MessageHandlers;
import io.github.marad.lychee.common.sync.messages.ConfirmStateMessage;
import io.github.marad.lychee.common.sync.messages.InitialStateMessage;
import io.github.marad.lychee.common.sync.messages.StatePatchMessage;
import io.netty.channel.ChannelHandlerContext;

@Singleton
public class ClientSyncMessageHandler implements MessageHandler {
    private final ClientStateTracker clientStateTracker;
    private final StatePatchApplier statePatchApplier;

    @Inject
    public ClientSyncMessageHandler(
            @Client MessageHandlers messageHandlers,
            ClientStateTracker clientStateTracker,
            StatePatchApplier statePatchApplier) {
        this.clientStateTracker = clientStateTracker;
        this.statePatchApplier = statePatchApplier;
        messageHandlers.register(this);
    }

    @Override
    public void handleMessage(ChannelHandlerContext ctx, Message message) {
        if (message instanceof InitialStateMessage) {
            InitialStateMessage initialStateMessage = (InitialStateMessage) message;
            clientStateTracker.update(0, initialStateMessage.getState());
            ctx.writeAndFlush(new ConfirmStateMessage(0));
        }
        else if (message instanceof StatePatchMessage) {
            StatePatchMessage statePatchMessage = (StatePatchMessage) message;
            State newState = statePatchApplier.apply(clientStateTracker.getCurrentState(), statePatchMessage.getPatch());
            clientStateTracker.update(statePatchMessage.getPreviousStateVersion(), newState);
            ctx.writeAndFlush(new ConfirmStateMessage(statePatchMessage.getPreviousStateVersion()));
        }

    }
}
