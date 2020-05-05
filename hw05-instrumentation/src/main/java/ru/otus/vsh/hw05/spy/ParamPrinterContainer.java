package ru.otus.vsh.hw05.spy;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * ParamPrinterContainer содержит объекты только для уникальных типов параметров.
 */
public class ParamPrinterContainer {
    private static final ParamPrinter BOOLEAN = new OrdinaryParamPrinterImpl(Opcodes.ILOAD, "Z");
    private static final ParamPrinter CHAR = new OrdinaryParamPrinterImpl(Opcodes.ILOAD, "C");
    private static final ParamPrinter DOUBLE = new OrdinaryParamPrinterImpl(Opcodes.DLOAD, "D");
    private static final ParamPrinter FLOAT = new OrdinaryParamPrinterImpl(Opcodes.FLOAD, "F");
    private static final ParamPrinter INT = new OrdinaryParamPrinterImpl(Opcodes.ILOAD, "I");
    private static final ParamPrinter LONG = new OrdinaryParamPrinterImpl(Opcodes.LLOAD, "J");
    private static final ParamPrinter STRING = new OrdinaryParamPrinterImpl(Opcodes.ALOAD, "Ljava/lang/String;");
    private static final ParamPrinter OBJECT = new OrdinaryParamPrinterImpl(Opcodes.ALOAD, "Ljava/lang/Object;");
    private static final ParamPrinter MULTIPLE_ARRAY = new ArrayParamPrinterImpl("deepToString", "([Ljava/lang/Object;)Ljava/lang/String;");
    private static final Map<String, ArrayParamPrinterImpl> ONE_ARRAY = new HashMap<>();

    @Nonnull
    public static ParamPrinter fromType(@Nonnull Type type) {
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
                else if (descriptor.startsWith("[")) {
                    String arrayDescriptor = String.format("(%s)Ljava/lang/String;", descriptor);
                    ONE_ARRAY.putIfAbsent(arrayDescriptor, new ArrayParamPrinterImpl("toString", arrayDescriptor));
                    return ONE_ARRAY.get(arrayDescriptor);
                } else if (descriptor.equals("Ljava/lang/String;")) return STRING;
                else return OBJECT;
        }
    }

    private abstract static class AbstractParamPrinter implements ParamPrinter {
        protected final String descriptor;

        private AbstractParamPrinter(String descriptor) {
            this.descriptor = descriptor;
        }

        @Override
        public boolean doubleOrLong() {
            return descriptor.equals("D") || descriptor.equals("J");
        }

        protected void preparePrintln(@Nonnull MethodVisitor visitor) {
            visitor.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        }

        protected void callPrintln(@Nonnull MethodVisitor visitor, String descriptor) {
            visitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", descriptor, false);
        }

        protected void callVarInsn(@Nonnull MethodVisitor visitor, final int opcode, String descriptor, int paramOffset) {
            preparePrintln(visitor);
            visitor.visitVarInsn(opcode, paramOffset);
            callPrintln(visitor, String.format("(%s)V", descriptor));
        }
    }

    private static class OrdinaryParamPrinterImpl extends AbstractParamPrinter {
        private final int opcode;

        public OrdinaryParamPrinterImpl(int opcode, String descriptor) {
            super(descriptor);
            this.opcode = opcode;
        }

        @Override
        public void logParameter(@Nonnull MethodVisitor visitor, int paramOffset) {
            callVarInsn(visitor, opcode, descriptor, paramOffset);
        }
    }

    private static class ArrayParamPrinterImpl extends AbstractParamPrinter {
        private final String methodToString;

        public ArrayParamPrinterImpl(String methodToString, String descriptor) {
            super(descriptor);
            this.methodToString = methodToString;
        }

        @Override
        public void logParameter(@Nonnull MethodVisitor visitor, int paramOffset) {
            preparePrintln(visitor);
            visitor.visitVarInsn(Opcodes.ALOAD, paramOffset);
            visitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/util/Arrays", methodToString, descriptor, false);
            callPrintln(visitor, "(Ljava/lang/String;)V");
        }
    }
}
