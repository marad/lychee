package io.github.marad.lychee.common.sync;

import com.google.common.collect.Lists;
import io.github.marad.lychee.api.State;

import java.util.List;

public class StateChangeNotifier {
    private List<StateChangeListener> listeners = Lists.newLinkedList();

    public void addListener(StateChangeListener listener) {
        listeners.add(listener);
    }

    public void removeListener(StateChangeListener listener) {
        listeners.remove(listener);
    }

    public void notifyStateChanged(State previousState, State currentState) {
        for(StateChangeListener listener : listeners) {
            listener.stateUpdated(previousState, currentState);
        }
    }
}
