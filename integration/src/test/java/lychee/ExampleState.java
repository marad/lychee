package lychee;

import com.google.common.base.Objects;
import io.github.marad.lychee.api.State;

public class ExampleState implements State {
    private int version;

    public ExampleState(int version) {
        this.version = version;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExampleState that = (ExampleState) o;
        return Objects.equal(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(version);
    }

    @Override
    public String toString() {
        return String.format("ExampleState{version=%d}", version);
    }
}
