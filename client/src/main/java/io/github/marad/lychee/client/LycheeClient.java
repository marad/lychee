package io.github.marad.lychee.client;

import io.github.marad.lychee.client.state.StateChangeListener;
import io.github.marad.lychee.client.state.StateChangeNotifier;
import io.github.marad.lychee.client.state.StateTracker;
import io.github.marad.lychee.client.state.TcpClient;

public class LycheeClient {
    private final TcpClient client;
    private final StateChangeNotifier stateChangeNotifier = new StateChangeNotifier();
    private final StateTracker stateTracker = new StateTracker(stateChangeNotifier);

    public LycheeClient(String hostname, int tcpPort) {
        client = new TcpClient(hostname, tcpPort);
    }

    public void connect() {
        client.connect();
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
}
