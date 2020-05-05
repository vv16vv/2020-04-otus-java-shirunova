package ru.otus.vsh.hw05.spy;

import org.objectweb.asm.MethodVisitor;

import javax.annotation.Nonnull;

public interface ParamPrinter {
    void logParameter(@Nonnull MethodVisitor visitor, int paramOffset);
    boolean doubleOrLong();
}
