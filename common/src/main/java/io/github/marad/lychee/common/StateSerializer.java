package io.github.marad.lychee.common;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import io.github.marad.lychee.api.State;

import java.io.*;

public class StateSerializer {
    public static byte[] serialize(State state) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Kryo kryo = new Kryo();
            Output output = new Output(baos);
            kryo.writeClassAndObject(output, state);
            output.close();
            baos.close();
            return baos.toByteArray();
        } catch (IOException ex){
            throw new RuntimeException(ex);
        }
    }

    public static State deserialize(byte[] data) {
    try {
        Kryo kryo = new Kryo();
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        Input input = new Input(bais);
        State state = (State) kryo.readClassAndObject(input);
        input.close();
        bais.close();
        return state;
    } catch (IOException ex) {
        throw new RuntimeException(ex);
    }
}

}
