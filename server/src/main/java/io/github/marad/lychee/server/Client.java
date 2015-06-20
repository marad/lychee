package io.github.marad.lychee.server;

import io.github.marad.lychee.common.Message;
import io.netty.channel.Channel;

public class Client {
    private final Channel tcpChannel;
    private long stateVersion;

    public Client(Channel tcpChannel) {
        this.tcpChannel = tcpChannel;
        stateVersion = 1;
    }

    public Channel getTcpChannel() {
        return tcpChannel;
    }

    public long getStateVersion() {
        return stateVersion;
    }

    public void setStateVersion(long stateVersion) {
        this.stateVersion = stateVersion;
    }

    public void sendTcpMessage(Message message) {
        tcpChannel.writeAndFlush(message);
    }
}
