package io.github.marad.lychee.client;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.github.marad.lychee.api.State;
import io.github.marad.lychee.client.state.StateChangeListener;
import io.github.marad.lychee.client.state.StateChangeNotifier;
import io.github.marad.lychee.client.state.StateTracker;
import io.github.marad.lychee.client.state.TcpClient;

@Singleton
public class LycheeClient {
    private final TcpClient client;
    private final StateChangeNotifier stateChangeNotifier;
    private final StateTracker stateTracker;

    @Inject
    public LycheeClient(TcpClient tcpClient, StateChangeNotifier stateChangeNotifier, StateTracker stateTracker) {
        client = tcpClient;
        this.stateChangeNotifier = stateChangeNotifier;
        this.stateTracker = stateTracker;
    }

    public void connect() {
        client.start();
    }

    public void await() throws InterruptedException {
        client.await();
    }

    public void addStateChangeListener(StateChangeListener listener) {
        stateChangeNotifier.addListener(listener);
    }

    public void removeStateChangeListener(StateChangeListener listener) {
        stateChangeNotifier.removeListener(listener);
    }

    public State getState() {
        return stateTracker.getCurrentState();
    }
}
