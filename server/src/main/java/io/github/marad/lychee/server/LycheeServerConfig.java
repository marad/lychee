package io.github.marad.lychee.server;

import io.github.marad.lychee.api.State;

public class LycheeServerConfig {
    private final int tcpPort;
    private final State initialState;

    public LycheeServerConfig(int tcpPort, State initialState) {
        this.tcpPort = tcpPort;
        this.initialState = initialState;
    }

    public int getTcpPort() {
        return tcpPort;
    }

    public State getInitialState() {
        return initialState;
    }
}
