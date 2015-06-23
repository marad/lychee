package io.github.marad.lychee.server;

import com.google.inject.AbstractModule;
import io.github.marad.lychee.common.LycheeModule;
import io.github.marad.lychee.common.handlers.BroadcastingMessageRouter;
import io.github.marad.lychee.common.handlers.MessageHandlers;
import io.github.marad.lychee.common.handlers.MessageRouter;
import io.github.marad.lychee.server.annotations.Server;
import io.github.marad.lychee.server.sync.ServerSyncModule;

public class LycheeServerModule extends AbstractModule {
    private final LycheeServerConfig config;

    public LycheeServerModule(LycheeServerConfig config) {
        this.config = config;
    }

    @Override
    protected void configure() {
        install(new LycheeModule());
        install(new ServerSyncModule(config.getInitialState()));
        bind(LycheeServerConfig.class).toInstance(config);

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
