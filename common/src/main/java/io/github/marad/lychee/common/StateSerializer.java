package io.github.marad.lychee.common;

import io.github.marad.lychee.api.State;

import java.io.*;

public class StateSerializer {
    public static byte[] serialize(State state) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(state);
        baos.close();
        return baos.toByteArray();
    }

    public static State deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bais);
        State state = (State) ois.readObject();
        bais.close();
        return state;
    }
}
