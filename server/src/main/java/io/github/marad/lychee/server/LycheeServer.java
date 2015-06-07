package io.github.marad.lychee.server;

import io.github.marad.lychee.api.State;
import io.github.marad.lychee.server.endpoints.TcpServer;

import java.io.Closeable;
import java.io.IOException;

public class LycheeServer implements Closeable {
    private TcpServer tcpServer;

    public LycheeServer(int tcpPort) {
        tcpServer = new TcpServer(tcpPort);
    }

    public void start(State initialState) throws InterruptedException {
        ServerMessageHandler serverMessageHandler = new ServerMessageHandler(initialState);
        tcpServer.start(serverMessageHandler);
    }

    public void await() throws InterruptedException {
        tcpServer.await();
    }

    @Override
    public void close() throws IOException {
        tcpServer.close();
    }

}
