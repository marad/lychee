package io.github.marad.lychee.common;

import io.github.marad.lychee.api.State;

import java.io.*;

public class StateSerializer {
    public static byte[] serialize(State state) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(state);
            baos.close();
            return baos.toByteArray();
        } catch (IOException ex){
            throw new RuntimeException(ex);
        }
    }

    public static State deserialize(byte[] data) {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(data);
            ObjectInputStream ois = new ObjectInputStream(bais);
            State state = (State) ois.readObject();
            bais.close();
            return state;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }
}
