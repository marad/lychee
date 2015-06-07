package io.github.marad.lychee.client;

public class ClientNotConnected extends RuntimeException {
    public ClientNotConnected() {
        super("Client not connected yet");
    }
}
