package io.github.marad.lychee.server;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import io.github.marad.lychee.common.LycheeModule;
import io.github.marad.lychee.common.handlers.BroadcastingMessageRouter;
import io.github.marad.lychee.common.handlers.MessageHandlers;
import io.github.marad.lychee.common.handlers.MessageRouter;
import io.github.marad.lychee.server.annotations.Server;
import io.github.marad.lychee.server.netty.ServerChannelInitializer;
import io.github.marad.lychee.server.netty.TcpBroadcaster;
import io.github.marad.lychee.server.netty.TcpServer;
import io.github.marad.lychee.server.netty.ServerMessageHandler;
import io.github.marad.lychee.server.state.ServerStateTracker;
import io.github.marad.lychee.server.state.broadcast.StateHistory;

public class LycheeServerModule extends AbstractModule {
    private final LycheeServerConfig config;

    public LycheeServerModule(LycheeServerConfig config) {
        this.config = config;
    }

    @Override
    protected void configure() {
        install(new LycheeModule());
        bind(ServerChannelInitializer.class);
        bind(LycheeServerConfig.class).toInstance(config);
        bind(ServerMessageHandler.class);
        bind(TcpServer.class);
        bind(LycheeServer.class);
        bind(StateUpdater.class);
        bind(TcpBroadcaster.class);
        bind(StateHistory.class);
        bind(ServerStateTracker.class).toInstance(new ServerStateTracker(config.getInitialState()));

        bindCustomMessageSystem();
    }

    private void bindCustomMessageSystem() {
        MessageHandlers messageHandlers = new MessageHandlers();
        BroadcastingMessageRouter router = new BroadcastingMessageRouter(messageHandlers);

        bind(MessageHandlers.class)
                .annotatedWith(Server.class)
                .toInstance(messageHandlers);
        bind(MessageRouter.class)
                .annotatedWith(Server.class)
                .toInstance(router);
    }
}
