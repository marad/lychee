package io.github.marad.lychee.server.netty;

public class ServerNotStarted extends RuntimeException {
    public ServerNotStarted() {
        super("Server not started yet");
    }
}
