package io.github.marad.lychee.client;

public class LycheeClientConfig {
    private final String hostname;
    private final int tcpPort;

    public LycheeClientConfig(String hostname, int tcpPort) {
        this.hostname = hostname;
        this.tcpPort = tcpPort;
    }

    public String getHostname() {
        return hostname;
    }

    public int getTcpPort() {
        return tcpPort;
    }
}
