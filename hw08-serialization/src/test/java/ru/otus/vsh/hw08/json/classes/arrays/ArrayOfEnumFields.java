package ru.otus.vsh.hw08.json.classes.arrays;

import ru.otus.vsh.hw08.json.classes.Colors;

import java.util.Arrays;

public class ArrayOfEnumFields {
    Colors[] aColors;

    public ArrayOfEnumFields(Colors[] aColors) {
        this.aColors = aColors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArrayOfEnumFields that = (ArrayOfEnumFields) o;
        return Arrays.equals(aColors, that.aColors);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(aColors);
    }
}
