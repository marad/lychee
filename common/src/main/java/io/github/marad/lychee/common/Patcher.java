package io.github.marad.lychee.common;

import com.nothome.delta.GDiffPatcher;

import java.io.IOException;

public class Patcher {
    public static byte[] patch(byte[] oldState, byte[] patch) {
        try {
            GDiffPatcher patcher = new GDiffPatcher();
            return patcher.patch(oldState, patch);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
