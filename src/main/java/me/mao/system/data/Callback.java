package me.mao.system.data;

import jdk.internal.jline.internal.Nullable;

public interface Callback<V extends Object, T extends Throwable> {
    void call(V result, @Nullable T thrown);
}
