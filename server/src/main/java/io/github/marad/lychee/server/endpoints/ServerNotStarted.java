package io.github.marad.lychee.server.endpoints;

public class ServerNotStarted extends RuntimeException {
    public ServerNotStarted() {
        super("Server not started yet");
    }
}
