package lychee;

import com.google.common.base.Objects;
import io.github.marad.lychee.api.State;

public class ExampleState implements State {
    private int value;
    private int hello = 8;

    public ExampleState() {
    }

    public ExampleState(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExampleState that = (ExampleState) o;
        return Objects.equal(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return String.format("ExampleState{value=%d}", value);
    }
}
