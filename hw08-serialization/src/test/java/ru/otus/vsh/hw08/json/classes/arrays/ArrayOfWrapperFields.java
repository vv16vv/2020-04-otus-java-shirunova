package ru.otus.vsh.hw08.json.classes.arrays;

import java.util.Arrays;

public class ArrayOfWrapperFields {
    Integer[] aInt;
    Byte[] aByte;
    Long[] aLong;
    Short[] aShort;
    Boolean[] aBoolean;
    Float[] aFloat;
    Double[] aDouble;
    Character[] aChar;
    String[] aString;

    public ArrayOfWrapperFields(Integer[] aInt, Byte[] aByte, Long[] aLong, Short[] aShort, Boolean[] aBoolean, Float[] aFloat, Double[] aDouble, Character[] aChar, String[] aString) {
        this.aInt = aInt;
        this.aByte = aByte;
        this.aLong = aLong;
        this.aShort = aShort;
        this.aBoolean = aBoolean;
        this.aFloat = aFloat;
        this.aDouble = aDouble;
        this.aChar = aChar;
        this.aString = aString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArrayOfWrapperFields that = (ArrayOfWrapperFields) o;
        return Arrays.equals(aInt, that.aInt) &&
                Arrays.equals(aByte, that.aByte) &&
                Arrays.equals(aLong, that.aLong) &&
                Arrays.equals(aShort, that.aShort) &&
                Arrays.equals(aBoolean, that.aBoolean) &&
                Arrays.equals(aFloat, that.aFloat) &&
                Arrays.equals(aDouble, that.aDouble) &&
                Arrays.equals(aChar, that.aChar) &&
                Arrays.equals(aString, that.aString);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(aInt);
        result = 31 * result + Arrays.hashCode(aByte);
        result = 31 * result + Arrays.hashCode(aLong);
        result = 31 * result + Arrays.hashCode(aShort);
        result = 31 * result + Arrays.hashCode(aBoolean);
        result = 31 * result + Arrays.hashCode(aFloat);
        result = 31 * result + Arrays.hashCode(aDouble);
        result = 31 * result + Arrays.hashCode(aChar);
        result = 31 * result + Arrays.hashCode(aString);
        return result;
    }
}
