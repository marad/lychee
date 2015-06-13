package io.github.marad.lychee.client.netty;

import com.google.inject.Inject;
import io.github.marad.lychee.api.State;
import io.github.marad.lychee.client.state.StatePatchApplier;
import io.github.marad.lychee.client.state.StateTracker;
import io.github.marad.lychee.common.messages.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;

@Singleton
public class ClientMessageHandler extends ChannelInboundHandlerAdapter {

    private final StateTracker stateTracker;
    private final StatePatchApplier patchApplier;

    @Inject
    public ClientMessageHandler(StateTracker stateTracker, StatePatchApplier patchApplier) {
        this.stateTracker = stateTracker;
        this.patchApplier = patchApplier;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("Received {}", msg);
        if (msg instanceof InitialStateMessage) {
            InitialStateMessage initialStateMessage = (InitialStateMessage) msg;
            stateTracker.update(0, initialStateMessage.getState());
            ctx.writeAndFlush(new ConfirmStateMessage(0));
        }
        else if (msg instanceof StatePatchMessage) {
            StatePatchMessage statePatchMessage = (StatePatchMessage) msg;
            State newState = patchApplier.apply(stateTracker.getCurrentState(), statePatchMessage.getPatch());
            stateTracker.update(statePatchMessage.getStateToPatchSeq(), newState);
            ctx.writeAndFlush(new ConfirmStateMessage(statePatchMessage.getStateToPatchSeq()));
        }
        else {
            // TODO: support custom message handlers
        }
    }

    private static final Logger logger = LoggerFactory.getLogger(ClientMessageHandler.class);
}
