package io.github.marad.lychee.server;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.github.marad.lychee.server.netty.TcpServer;

import java.io.Closeable;
import java.io.IOException;

@Singleton
public class LycheeServer implements Closeable {
    private TcpServer tcpServer;

    @Inject
    public LycheeServer(TcpServer tcpServer) {
        this.tcpServer = tcpServer;
    }

    public void start() throws InterruptedException {
        tcpServer.start();
    }

    public void await() throws InterruptedException {
        tcpServer.await();
    }

    public void await(long timeoutMillis) throws InterruptedException {
        tcpServer.await(timeoutMillis);
    }

    @Override
    public void close() throws IOException {
        tcpServer.close();
    }

}
