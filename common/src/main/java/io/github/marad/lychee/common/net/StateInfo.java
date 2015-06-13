package io.github.marad.lychee.common.net;

import com.google.common.base.Objects;
import io.github.marad.lychee.api.State;

public class StateInfo {
    private final Class<? extends State> stateType;

    public StateInfo(Class<? extends State> stateType) {
        this.stateType = stateType;
    }

    public Class<? extends State> getStateType() {
        return stateType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StateInfo stateInfo = (StateInfo) o;
        return Objects.equal(stateType, stateInfo.stateType);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(stateType);
    }

    @Override
    public String toString() {
        return String.format("StateInfo{stateType=%s}", stateType);
    }
}
