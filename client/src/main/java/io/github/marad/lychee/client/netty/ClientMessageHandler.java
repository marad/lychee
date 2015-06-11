package io.github.marad.lychee.client.netty;

import com.google.inject.Inject;
import io.github.marad.lychee.api.State;
import io.github.marad.lychee.client.state.StatePatchApplier;
import io.github.marad.lychee.client.state.StateTracker;
import io.github.marad.lychee.common.messages.ConfirmStateMessage;
import io.github.marad.lychee.common.messages.Message;
import io.github.marad.lychee.common.messages.InitialStateMessage;
import io.github.marad.lychee.common.messages.StatePatchMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;

@Singleton
public class ClientMessageHandler extends ChannelInboundHandlerAdapter {

    private final StateTracker stateTracker;

    @Inject
    public ClientMessageHandler(StateTracker stateTracker) {
        this.stateTracker = stateTracker;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("Received {}", msg);
        Message message = (Message) msg;
        switch (message.getType()) {
            case STATE:
                InitialStateMessage initialStateMessage = (InitialStateMessage) message;
                stateTracker.update(0, initialStateMessage.getState());
                ctx.writeAndFlush(new ConfirmStateMessage(0));
                break;

            case STATE_PATCH:
                StatePatchMessage statePatchMessage = (StatePatchMessage) message;
                State newState = StatePatchApplier.apply(stateTracker.getCurrentState(), statePatchMessage.getPatch());
                stateTracker.update(statePatchMessage.getStateToPatchSeq(), newState);
                ctx.writeAndFlush(new ConfirmStateMessage(statePatchMessage.getStateToPatchSeq()));
                break;

            default:
                // TODO: support custom message handlers
        }
    }

    private static final Logger logger = LoggerFactory.getLogger(ClientMessageHandler.class);
}
