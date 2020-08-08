package ru.otus.vsh.hw08.json.classes.simples;

import java.util.Objects;

public class SimpleWrapperFields {
    Integer aInt;
    Byte aByte;
    Long aLong;
    Short aShort;
    Boolean aBoolean;
    Float aFloat;
    Double aDouble;
    Character aChar;
    String aString;

    public SimpleWrapperFields(Integer aInt, Byte aByte, Long aLong, Short aShort, Boolean aBoolean, Float aFloat, Double aDouble, Character aChar, String aString) {
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
        SimpleWrapperFields that = (SimpleWrapperFields) o;
        return aInt.equals(that.aInt) &&
                aByte.equals(that.aByte) &&
                aLong.equals(that.aLong) &&
                aShort.equals(that.aShort) &&
                aBoolean.equals(that.aBoolean) &&
                aFloat.equals(that.aFloat) &&
                aDouble.equals(that.aDouble) &&
                aChar.equals(that.aChar) &&
                aString.equals(that.aString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aInt, aByte, aLong, aShort, aBoolean, aFloat, aDouble, aChar, aString);
    }
}
