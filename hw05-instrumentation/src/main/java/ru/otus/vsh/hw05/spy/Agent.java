package ru.otus.vsh.hw05.spy;

import org.objectweb.asm.*;
import org.objectweb.asm.commons.AdviceAdapter;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class Agent {

    public static void premain(String agentArgs, Instrumentation inst) {
        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className,
                                    Class<?> classBeingRedefined,
                                    ProtectionDomain protectionDomain,
                                    byte[] classfileBuffer) {
                return className.startsWith("ru/otus/vsh/hw05/test")
                        ? logMethod(classfileBuffer)
                        : classfileBuffer;
            }
        });
    }

    private static byte[] logMethod(byte[] originalClass) {
        ClassReader cr = new ClassReader(originalClass);
        ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
        ClassVisitor cv = new ClassVisitor(Opcodes.ASM7, cw) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);
                return new LogMethodVisitor(methodVisitor, access, name, descriptor);
            }
        };
        cr.accept(cv, Opcodes.ASM7);

        return cw.toByteArray();
    }

    private static class LogMethodVisitor extends AdviceAdapter {
        private boolean annoVisiting = false;

        LogMethodVisitor(MethodVisitor methodVisitor, int access, String name, String descriptor) {
            super(Opcodes.ASM7, methodVisitor, access, name, descriptor);
        }

        @Override
        protected void onMethodEnter() {
            if (annoVisiting) {
//                System.out.println(String.format("Start to log method '%s' -->", getName()));
                Type[] params = getArgumentTypes();
                if (params.length != 0) {
                    int offset = 1;
                    for (Type param : params) {
                        ParamPrinter printer = ParamPrinterContainer.fromType(param);
                        printer.logParameter(this, offset);
                        if (printer.doubleOrLong()) offset++;
                        offset++;
                    }
                }
            } else super.onMethodEnter();
        }

        @Override
        public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
            if (descriptor.contains("Lru/otus/vsh/hw05/spy/Log")) {
                annoVisiting = true;
            }
            return super.visitAnnotation(descriptor, visible);
        }
    }
}
