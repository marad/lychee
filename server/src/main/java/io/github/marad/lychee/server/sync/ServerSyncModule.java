package io.github.marad.lychee.server.sync;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import io.github.marad.lychee.api.State;
import io.github.marad.lychee.common.sync.StateChangeNotifier;
import io.github.marad.lychee.common.sync.StateTracker;
import io.github.marad.lychee.server.annotations.Server;
import io.github.marad.lychee.server.sync.state.ServerStateTracker;
import io.github.marad.lychee.server.sync.state.broadcast.StateBroadcaster;
import io.github.marad.lychee.server.sync.state.broadcast.StateHistory;

public class ServerSyncModule extends AbstractModule {
    private final State initialState;

    public ServerSyncModule(State initialState) {
        this.initialState = initialState;
    }

    @Override
    protected void configure() {
        bindStateTracking();
        bind(ServerSyncMessageHandler.class).asEagerSingleton();
        bind(ClientTrackingMessageHandler.class).asEagerSingleton();
        bind(StateBroadcaster.class).asEagerSingleton();
    }

    private void bindStateTracking() {
        StateHistory stateHistory = new StateHistory(initialState);
        StateChangeNotifier stateChangeNotifier = new StateChangeNotifier();
        ServerStateTracker serverStateTracker = new ServerStateTracker(
                initialState,  stateChangeNotifier, stateHistory);

        bind(StateHistory.class).toInstance(stateHistory);
        bind(StateChangeNotifier.class)
                .annotatedWith(Server.class)
                .toInstance(stateChangeNotifier);
        bind(StateTracker.class)
                .annotatedWith(Server.class)
                .toInstance(serverStateTracker);
        bind(ServerStateTracker.class).toInstance(serverStateTracker);
    }
}
