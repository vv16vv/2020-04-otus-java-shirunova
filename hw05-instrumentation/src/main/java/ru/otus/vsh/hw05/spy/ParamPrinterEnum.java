package ru.otus.vsh.hw05.spy;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import javax.annotation.Nonnull;

/**
 * Я отказалась от enum т.к. этот вариант получился очень многословным
 * и еще в функции print оказался лишний параметр typeDescriptor, который
 * по сути нужен только в одном случае - для одномерного массива
 */
public enum ParamPrinterEnum {
    BOOLEAN {
        @Override
        public void print(@Nonnull MethodVisitor visitor, String typeDescriptor, int paramOffset) {
            callVarInsn(visitor, Opcodes.ILOAD, typeDescriptor, paramOffset);
        }
    }, CHAR {
        @Override
        public void print(@Nonnull MethodVisitor visitor, String typeDescriptor, int paramOffset) {
            callVarInsn(visitor, Opcodes.ILOAD, typeDescriptor, paramOffset);
        }
    }, DOUBLE {
        @Override
        public void print(@Nonnull MethodVisitor visitor, String typeDescriptor, int paramOffset) {
            callVarInsn(visitor, Opcodes.DLOAD, typeDescriptor, paramOffset);
        }
    }, FLOAT {
        @Override
        public void print(@Nonnull MethodVisitor visitor, String typeDescriptor, int paramOffset) {
            callVarInsn(visitor, Opcodes.FLOAD, typeDescriptor, paramOffset);
        }
    }, INT {
        @Override
        public void print(@Nonnull MethodVisitor visitor, String typeDescriptor, int paramOffset) {
            callVarInsn(visitor, Opcodes.ILOAD, "I", paramOffset);
        }
    }, LONG {
        @Override
        public void print(@Nonnull MethodVisitor visitor, String typeDescriptor, int paramOffset) {
            callVarInsn(visitor, Opcodes.LLOAD, typeDescriptor, paramOffset);
        }
    }, STRING {
        @Override
        public void print(@Nonnull MethodVisitor visitor, String typeDescriptor, int paramOffset) {
            callVarInsn(visitor, Opcodes.ALOAD, typeDescriptor, paramOffset);
        }
    }, OBJECT {
        @Override
        public void print(@Nonnull MethodVisitor visitor, String typeDescriptor, int paramOffset) {
            callVarInsn(visitor, Opcodes.ALOAD, "Ljava/lang/Object;", paramOffset);
        }
    }, ONE_ARRAY {
        @Override
        public void print(@Nonnull MethodVisitor visitor, String typeDescriptor, int paramOffset) {
            preparePrintln(visitor);
            visitor.visitVarInsn(Opcodes.ALOAD, paramOffset);
            visitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/util/Arrays", "toString", String.format("(%s)Ljava/lang/String;", typeDescriptor), false);
            callPrintln(visitor, "(Ljava/lang/String;)V");
        }
    }, MULTIPLE_ARRAY {
        @Override
        public void print(@Nonnull MethodVisitor visitor, String typeDescriptor, int paramOffset) {
            preparePrintln(visitor);
            visitor.visitVarInsn(Opcodes.ALOAD, paramOffset);
            visitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/util/Arrays", "deepToString", "([Ljava/lang/Object;)Ljava/lang/String;", false);
            callPrintln(visitor, "(Ljava/lang/String;)V");
        }
    };

    @Nonnull
    public static ParamPrinterEnum fromType(@Nonnull Type type) {
        String descriptor = type.getDescriptor();
        switch (descriptor) {
            case "I":
            case "S":
            case "B":
                return INT;
            case "J":
                return LONG;
            case "C":
                return CHAR;
            case "Z":
                return BOOLEAN;
            case "D":
                return DOUBLE;
            case "F":
                return FLOAT;
            default:
                if (descriptor.startsWith("[[") || descriptor.equals("[Ljava/lang/String;")) return MULTIPLE_ARRAY;
                else if (descriptor.startsWith("[")) return ONE_ARRAY;
                else if (descriptor.equals("Ljava/lang/String;")) return STRING;
                else return OBJECT;
        }
    }

    private static void preparePrintln(@Nonnull MethodVisitor visitor) {
        visitor.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
    }

    private static void callPrintln(@Nonnull MethodVisitor visitor, String descriptor) {
        visitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", descriptor, false);
    }

    private static void callVarInsn(@Nonnull MethodVisitor visitor, final int opcode, String descriptor, int paramOffset) {
        preparePrintln(visitor);
        visitor.visitVarInsn(opcode, paramOffset);
        callPrintln(visitor, String.format("(%s)V", descriptor));
    }

    abstract public void print(@Nonnull MethodVisitor visitor, String typeDescriptor, int paramOffset);
}
