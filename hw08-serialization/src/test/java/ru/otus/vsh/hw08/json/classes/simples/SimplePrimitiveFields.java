package ru.otus.vsh.hw08.json.classes.simples;

import java.util.Objects;

public class SimplePrimitiveFields {
    int aInt;
    byte aByte;
    long aLong;
    short aShort;
    boolean aBoolean;
    float aFloat;
    double aDouble;
    char aChar;

    public SimplePrimitiveFields(int aInt, byte aByte, long aLong, short aShort, boolean aBoolean, float aFloat, double aDouble, char aChar) {
        this.aInt = aInt;
        this.aByte = aByte;
        this.aLong = aLong;
        this.aShort = aShort;
        this.aBoolean = aBoolean;
        this.aFloat = aFloat;
        this.aDouble = aDouble;
        this.aChar = aChar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimplePrimitiveFields that = (SimplePrimitiveFields) o;
        return aInt == that.aInt &&
                aByte == that.aByte &&
                aLong == that.aLong &&
                aShort == that.aShort &&
                aBoolean == that.aBoolean &&
                Float.compare(that.aFloat, aFloat) == 0 &&
                Double.compare(that.aDouble, aDouble) == 0 &&
                aChar == that.aChar;
    }

    @Override
    public int hashCode() {
        return Objects.hash(aInt, aByte, aLong, aShort, aBoolean, aFloat, aDouble, aChar);
    }
}
