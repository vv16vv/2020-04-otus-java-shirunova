package ru.otus.vsh.hw05.spy;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import javax.annotation.Nonnull;

public class ParamPrinter {

    private final MethodVisitor visitor;
    private final Type type;

    public ParamPrinter(@Nonnull MethodVisitor visitor, Type type){
        this.visitor = visitor;
        this.type = type;
    }

    public void logParameter(int paramOffset) {
        String descriptor = type.getDescriptor();
        switch (descriptor) {
            case "I":
            case "S":
            case "B":
                callIntInsn(visitor, "I", paramOffset);
                break;
            case "J":
                callVarInsn(visitor, Opcodes.LLOAD, "J", paramOffset);
                break;
            case "C":
                callIntInsn(visitor, "C", paramOffset);
                break;
            case "Z":
                callIntInsn(visitor, "Z", paramOffset);
                break;
            case "D":
                callVarInsn(visitor, Opcodes.DLOAD, "D", paramOffset);
                break;
            case "F":
                callVarInsn(visitor, Opcodes.FLOAD, "F", paramOffset);
                break;
            default:
                if (descriptor.startsWith("[[") || descriptor.equals("[Ljava/lang/String;"))
                    callMultiDArray(visitor, paramOffset);
                else if (descriptor.startsWith("[")) callOneDArray(visitor, descriptor, paramOffset);
                else if (descriptor.equals("Ljava/lang/String;"))
                    callVarInsn(visitor, Opcodes.ALOAD, descriptor, paramOffset);
                else callVarInsn(visitor, Opcodes.ALOAD, "Ljava/lang/Object;", paramOffset);
        }
    }

    public boolean doubleOrLong(){
        return type.getDescriptor().equals("D") || type.getDescriptor().equals("J");
    }

    private void preparePrintln(@Nonnull MethodVisitor visitor) {
        visitor.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
    }

    private void callPrintln(@Nonnull MethodVisitor visitor, String descriptor) {
        visitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", descriptor, false);
    }

    private void callIntInsn(@Nonnull MethodVisitor visitor, String descriptor, int paramOffset) {
        preparePrintln(visitor);
        visitor.visitIntInsn(Opcodes.ILOAD, paramOffset);
        callPrintln(visitor, String.format("(%s)V", descriptor));
    }

    private void callVarInsn(@Nonnull MethodVisitor visitor, final int opcode, String descriptor, int paramOffset) {
        preparePrintln(visitor);
        visitor.visitVarInsn(opcode, paramOffset);
        callPrintln(visitor, String.format("(%s)V", descriptor));
    }

    private void callOneDArray(@Nonnull MethodVisitor visitor, String descriptor, int paramOffset) {
        preparePrintln(visitor);
        visitor.visitVarInsn(Opcodes.ALOAD, paramOffset);
        visitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/util/Arrays", "toString", String.format("(%s)Ljava/lang/String;", descriptor), false);
        callPrintln(visitor, "(Ljava/lang/String;)V");
    }

    public void callMultiDArray(@Nonnull MethodVisitor visitor, int paramOffset) {
        preparePrintln(visitor);
        visitor.visitVarInsn(Opcodes.ALOAD, paramOffset);
        visitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/util/Arrays", "deepToString", "([Ljava/lang/Object;)Ljava/lang/String;", false);
        callPrintln(visitor, "(Ljava/lang/String;)V");
    }

}
