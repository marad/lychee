package io.github.marad.lychee.client;

import com.google.inject.AbstractModule;
import io.github.marad.lychee.client.netty.ClientChannelInitializer;
import io.github.marad.lychee.client.netty.ClientMessageHandler;
import io.github.marad.lychee.client.state.StateChangeNotifier;
import io.github.marad.lychee.client.state.StateTracker;
import io.github.marad.lychee.client.state.TcpClient;
import io.github.marad.lychee.common.LycheeModule;

public class LycheeClientModule extends AbstractModule {
    private final LycheeClientConfig lycheeClientConfig;

    public LycheeClientModule(LycheeClientConfig lycheeClientConfig) {
        this.lycheeClientConfig = lycheeClientConfig;
    }

    @Override
    protected void configure() {
        install(new LycheeModule());
        bind(ClientChannelInitializer.class);
        bind(ClientMessageHandler.class);
        bind(TcpClient.class);
        bind(StateChangeNotifier.class);
        bind(StateTracker.class);
        bind(LycheeClient.class);
        bind(LycheeClientConfig.class).toInstance(lycheeClientConfig);
    }
}
