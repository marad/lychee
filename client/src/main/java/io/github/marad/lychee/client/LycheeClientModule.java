package io.github.marad.lychee.client;

import io.github.marad.lychee.client.netty.ClientMessageHandler;
import io.github.marad.lychee.client.state.StateChangeNotifier;
import io.github.marad.lychee.client.state.StateTracker;
import io.github.marad.lychee.client.state.TcpClient;
import io.github.marad.lychee.common.LycheeModule;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class LycheeClientModule extends LycheeModule {
    private final LycheeClientConfig lycheeClientConfig;

    public LycheeClientModule(LycheeClientConfig lycheeClientConfig) {
        this.lycheeClientConfig = lycheeClientConfig;
    }

    @Override
    protected void configure() {
        super.configure();
        bind(ChannelInboundHandlerAdapter.class).to(ClientMessageHandler.class);
        bind(TcpClient.class);
        bind(StateChangeNotifier.class);
        bind(StateTracker.class);
        bind(LycheeClient.class);
        bind(LycheeClientConfig.class).toInstance(lycheeClientConfig);
    }
}
