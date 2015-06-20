package io.github.marad.lychee.server;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.github.marad.lychee.common.Message;
import io.github.marad.lychee.server.netty.TcpServer;
import io.github.marad.lychee.server.state.ServerStateTracker;
import io.netty.channel.group.ChannelGroupFuture;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Singleton
public class LycheeServer implements Closeable {
    private final TcpServer tcpServer;
    private final ServerStateTracker serverStateTracker;

    @Inject
    public LycheeServer(TcpServer tcpServer, ServerStateTracker serverStateTracker) {
        this.tcpServer = tcpServer;
        this.serverStateTracker = serverStateTracker;
    }

    public void start() throws InterruptedException {
        tcpServer.start();
    }

    @Override
    public void close() throws IOException {
        tcpServer.close();
    }

    public void await() throws InterruptedException {
        tcpServer.await();
    }

    public void await(long timeoutMillis) throws InterruptedException {
        tcpServer.await(timeoutMillis);
    }

    public void await(long timeout, TimeUnit timeUnit) throws InterruptedException {
        tcpServer.await(timeout, timeUnit);
    }

    public ChannelGroupFuture sendTCP(Message message) {
        return tcpServer.send(message);
    }

    public ChannelGroupFuture sendUDP(Message message) {
        throw new NotImplementedException();
    }

}
