package io.github.marad.lychee.common;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import io.github.marad.lychee.api.State;

import java.io.*;

public class StateSerializer {

//    public static byte[] serialize(State state) {
//        try {
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            ObjectOutputStream oos = new ObjectOutputStream(baos);
//            oos.writeObject(state);
//            baos.close();
//            return baos.toByteArray();
//        } catch (IOException ex){
//            throw new RuntimeException(ex);
//        }
//    }

    public static byte[] serialize(State state) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Kryo kryo = new Kryo();
            Output output = new Output(baos);
            kryo.writeObject(output, state);
            output.close();
            baos.close();
            return baos.toByteArray();
        } catch (IOException ex){
            throw new RuntimeException(ex);
        }
    }

//    public static State deserialize(byte[] data) {
//        try {
//            ByteArrayInputStream bais = new ByteArrayInputStream(data);
//            ObjectInputStream ois = new ObjectInputStream(bais);
//            State state = (State) ois.readObject();
//            bais.close();
//            return state;
//        } catch (IOException ex) {
//            throw new RuntimeException(ex);
//        } catch (ClassNotFoundException ex) {
//            throw new RuntimeException(ex);
//        }
//    }

    public static State deserialize(byte[] data, Class<? extends State> stateType) {
    try {
        Kryo kryo = new Kryo();
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        Input input = new Input(bais);
        State state = kryo.readObject(input, stateType);
        input.close();
        bais.close();
        return state;
    } catch (IOException ex) {
        throw new RuntimeException(ex);
    }
}

}
