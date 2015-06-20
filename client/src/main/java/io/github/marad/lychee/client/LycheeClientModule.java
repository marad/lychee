package io.github.marad.lychee.client;

import com.google.inject.AbstractModule;
import io.github.marad.lychee.client.annotations.Client;
import io.github.marad.lychee.client.sync.ClientSyncModule;
import io.github.marad.lychee.common.LycheeModule;
import io.github.marad.lychee.common.handlers.BroadcastingMessageRouter;
import io.github.marad.lychee.common.handlers.MessageHandlers;
import io.github.marad.lychee.common.handlers.MessageRouter;

public class LycheeClientModule extends AbstractModule {
    private final LycheeClientConfig lycheeClientConfig;

    public LycheeClientModule(LycheeClientConfig lycheeClientConfig) {
        this.lycheeClientConfig = lycheeClientConfig;
    }

    @Override
    protected void configure() {
        install(new LycheeModule());
        install(new ClientSyncModule());
        bindLycheeClient();
        bindCustomMessageSystem();
    }

    private void bindLycheeClient() {
        bind(LycheeClientConfig.class).toInstance(lycheeClientConfig);
        bind(LycheeClient.class).asEagerSingleton();
    }

    private void bindCustomMessageSystem() {
        MessageHandlers messageHandlers = new MessageHandlers();
        BroadcastingMessageRouter router = new BroadcastingMessageRouter(messageHandlers);

        bind(MessageHandlers.class)
                .annotatedWith(Client.class)
                .toInstance(messageHandlers);
        bind(MessageRouter.class)
                .annotatedWith(Client.class)
                .toInstance(router);
    }
}
