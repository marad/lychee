package io.github.marad.lychee.common;

import com.nothome.delta.GDiffPatcher;

import java.io.IOException;

public class Patcher {
    public byte[] patch(byte[] oldState, byte[] patch) throws IOException {
        GDiffPatcher patcher = new GDiffPatcher();
        return patcher.patch(oldState, patch);
    }
}
