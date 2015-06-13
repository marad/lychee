package io.github.marad.lychee.server;

import io.github.marad.lychee.api.State;

public class LycheeServerConfig<T extends State> {
    private final int tcpPort;
    private final T initialState;

    public LycheeServerConfig(int tcpPort, T initialState) {
        this.tcpPort = tcpPort;
        this.initialState = initialState;
    }

    public int getTcpPort() {
        return tcpPort;
    }

    public T getInitialState() {
        return initialState;
    }
}
