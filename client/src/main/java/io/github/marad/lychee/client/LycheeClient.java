package io.github.marad.lychee.client;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.github.marad.lychee.api.State;
import io.github.marad.lychee.client.state.StateTracker;
import io.github.marad.lychee.client.state.TcpClient;

import java.util.concurrent.TimeUnit;

@Singleton
public class LycheeClient {
    private final TcpClient client;
    private final StateTracker stateTracker;

    @Inject
    public LycheeClient(TcpClient tcpClient, StateTracker stateTracker) {
        client = tcpClient;
        this.stateTracker = stateTracker;
    }

    public void connect() {
        client.start();
    }

    public void close() {
        client.close();
    }

    public void await() throws InterruptedException {
        client.await();
    }

    public void await(long timeoutMillis) throws InterruptedException {
        client.await(timeoutMillis);
    }

    public void await(long timeout, TimeUnit timeUnit) throws InterruptedException {
        client.await(timeout, timeUnit);
    }

    public State getState() {
        return stateTracker.getCurrentState();
    }
}
