package io.github.marad.lychee.client.state;

import com.google.common.collect.Lists;
import io.github.marad.lychee.api.State;

import java.util.List;

public class StateChangeNotifier<S extends State> {
    private List<StateChangeListener<S>> listeners = Lists.newLinkedList();

    public void addListener(StateChangeListener<S> listener) {
        listeners.add(listener);
    }

    public void removeListener(StateChangeListener<S> listener) {
        listeners.remove(listener);
    }

    public void notifyStateChanged(S previousState, S currentState) {
        for(StateChangeListener<S> listener : listeners) {
            listener.stateUpdated(previousState, currentState);
        }
    }
}
