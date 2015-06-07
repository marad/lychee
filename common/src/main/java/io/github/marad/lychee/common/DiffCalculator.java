package io.github.marad.lychee.common;

import com.nothome.delta.Delta;

import java.io.IOException;

public class DiffCalculator {

    public byte[] calculate(byte[] old, byte[] current) throws IOException {
        return calculateDiff(old, current);
    }

    private byte[] calculateDiff(byte[] old, byte[] current) throws IOException {
        Delta delta = new Delta();
        return delta.compute(old, current);
    }

}
