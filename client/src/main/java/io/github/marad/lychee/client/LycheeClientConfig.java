package io.github.marad.lychee.client;

import io.github.marad.lychee.api.State;

public class LycheeClientConfig {
    private final String hostname;
    private final int tcpPort;
    private final Class<? extends State> stateType;

    public LycheeClientConfig(String hostname, int tcpPort, Class<? extends State> stateType) {
        this.hostname = hostname;
        this.tcpPort = tcpPort;
        this.stateType = stateType;
    }

    public String getHostname() {
        return hostname;
    }

    public int getTcpPort() {
        return tcpPort;
    }

    public Class<? extends State> getStateType() {
        return stateType;
    }
}
