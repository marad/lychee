package io.github.marad.lychee.server.state;

public class StateVersionNotFound extends RuntimeException {
    public StateVersionNotFound(long version) {
        super(String.format("State version %d not found.", version));
    }
}
