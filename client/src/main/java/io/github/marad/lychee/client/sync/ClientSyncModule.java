package io.github.marad.lychee.client.sync;

import com.google.inject.AbstractModule;
import io.github.marad.lychee.client.annotations.Client;
import io.github.marad.lychee.client.sync.state.ClientStateTracker;
import io.github.marad.lychee.common.sync.StateChangeNotifier;
import io.github.marad.lychee.common.sync.StateTracker;

public class ClientSyncModule extends AbstractModule {
    @Override
    protected void configure() {
        StateChangeNotifier stateChangeNotifier = new StateChangeNotifier();
        ClientStateTracker clientStateTracker = new ClientStateTracker(stateChangeNotifier);
        bind(StateChangeNotifier.class)
                .annotatedWith(Client.class)
                .toInstance(stateChangeNotifier);
        bind(StateTracker.class)
                .annotatedWith(Client.class)
                .toInstance(clientStateTracker);
        bind(ClientStateTracker.class).toInstance(clientStateTracker);
        bind(ClientSyncMessageHandler.class).asEagerSingleton();
    }
}
