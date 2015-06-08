package io.github.marad.lychee.client;

import io.github.marad.lychee.api.State;
import io.github.marad.lychee.client.state.StateChangeListener;
import io.github.marad.lychee.client.state.StateChangeNotifier;
import io.github.marad.lychee.client.state.StateTracker;
import io.github.marad.lychee.client.state.TcpClient;

public class LycheeClient<S extends State> {
    private final TcpClient client;
    private final StateChangeNotifier<S> stateChangeNotifier = new StateChangeNotifier<S>();
    private final StateTracker<S> stateTracker = new StateTracker<S>(stateChangeNotifier);

    public LycheeClient(String hostname, int tcpPort) {
        client = new TcpClient(hostname, tcpPort);
    }

    public void connect() {
        client.connect();
    }

    public void await() throws InterruptedException {
        client.await();
    }

    public void addStateChangeListener(StateChangeListener<S> listener) {
        stateChangeNotifier.addListener(listener);
    }

    public void removeStateChangeListener(StateChangeListener<S> listener) {
        stateChangeNotifier.removeListener(listener);
    }

    public S getState() {
        return stateTracker.getCurrentState();
    }
}
