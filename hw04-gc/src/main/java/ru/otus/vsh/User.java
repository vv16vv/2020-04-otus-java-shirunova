package ru.otus.vsh;

import java.util.Random;

@SuppressWarnings("FieldCanBeLocal")
public class User {
    private static final Random random = new Random();
    private final String name;
    private final int fotoSize = 1536;
    private final byte[] foto;

    public User(String name) {
        this.name = name;
        this.foto = new byte[fotoSize];
        random.nextBytes(foto);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }
}
