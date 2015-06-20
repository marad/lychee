package io.github.marad.lychee.server;

import com.google.inject.AbstractModule;
import io.github.marad.lychee.common.LycheeModule;
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
    }
}
