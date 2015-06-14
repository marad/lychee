package io.github.marad.lychee.server.state.broadcast;

public class StateVersionNotFound extends RuntimeException {
    public StateVersionNotFound(long version) {
        super(String.format("State version %d not found.", version));
    }
}
