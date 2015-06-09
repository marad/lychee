package io.github.marad.lychee.server;

import io.github.marad.lychee.common.LycheeModule;
import io.github.marad.lychee.server.netty.TcpServer;
import io.github.marad.lychee.server.netty.ServerMessageHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class LycheeServerModule extends LycheeModule {
    private final LycheeServerConfig config;

    public LycheeServerModule(LycheeServerConfig config) {
        this.config = config;
    }

    @Override
    protected void configure() {
        super.configure();
        bind(LycheeServerConfig.class).toInstance(config);
        bind(ChannelInboundHandlerAdapter.class).to(ServerMessageHandler.class);
        bind(TcpServer.class);
        bind(LycheeServer.class);
    }
}
