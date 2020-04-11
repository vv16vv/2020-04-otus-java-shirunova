package ru.otus.vsh.hw02;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@SuppressWarnings({"MismatchedQueryAndUpdateOfCollection", "AssertWithSideEffects", "ConstantConditions"})
class DiyArrayListTest {

    @Test
    void size0() {
        assert new DiyArrayList<String>().size() == 0;
    }

    @Test
    void size1() {
        DiyArrayList<String> diy = new DiyArrayList<>();
        diy.add("Hello");
        assert diy.size() == 1;
    }

    @Test
    void isEmptyTrue() {
        assert new DiyArrayList<String>().isEmpty();
    }

    @Test
    void isEmptyFalse() {
        DiyArrayList<String> diy = new DiyArrayList<>();
        diy.add("Hello");
        assert !diy.isEmpty();
    }

    @Test
    void containsTrue() {
        DiyArrayList<String> diy = new DiyArrayList<>();
        diy.add("Hello");
        assert diy.contains("Hello");
    }

    @Test
    void containsFalse() {
        DiyArrayList<String> diy = new DiyArrayList<>();
        diy.add("Hello");
        assert !diy.contains("Hell");
    }

    @Test
    void addWithIndexInside() {
        DiyArrayList<String> diy = new DiyArrayList<>();
        diy.add("Hello");
        diy.add(0, "World");
        assert diy.size() == 2 && Objects.equals(diy.get(0), "World") && Objects.equals(diy.get(1), "Hello");
    }

    @Test
    void addWithIndexSize() {
        DiyArrayList<String> diy = new DiyArrayList<>();
        diy.add("Hello");
        diy.add(1, "World");
        assert diy.size() == 2 && Objects.equals(diy.get(1), "World") && Objects.equals(diy.get(0), "Hello");
    }

    @Test
    void addWithIndexOut() {
        DiyArrayList<String> diy = new DiyArrayList<>();
        diy.add("Hello");
        diy.forEach(System.out::println);
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> diy.add(2, "World"));
    }

    @Test
    void removeObjectExisting() {
        DiyArrayList<String> diy = new DiyArrayList<>();
        diy.add("Hello");
        assert diy.remove("Hello") && diy.isEmpty();
    }

    @Test
    void removeObjectNonExisting() {
        DiyArrayList<String> diy = new DiyArrayList<>();
        diy.add("Hello");
        assert !diy.remove("Hell") && !diy.isEmpty();
    }

    @Test
    void removeWithIndexIn() {
        DiyArrayList<String> diy = new DiyArrayList<>();
        diy.add("Hello");
        assert Objects.equals(diy.remove(0), "Hello") && diy.isEmpty();
    }

    @Test
    void removeWithIndexOut() {
        DiyArrayList<String> diy = new DiyArrayList<>();
        diy.add("Hello");
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> diy.remove(1));
    }

    @Test
    void containsAllTrue() {
        DiyArrayList<String> diy = new DiyArrayList<>();
        diy.add("Hello");
        diy.add("World");
        assert diy.containsAll(List.of("World", "Hello"));
    }

    @Test
    void containsAllFalse() {
        DiyArrayList<String> diy = new DiyArrayList<>();
        diy.add("Hello");
        diy.add("World");
        assert !diy.containsAll(List.of("World", "Hell"));
    }

    @Test
    void addAll() {
        DiyArrayList<String> diy = new DiyArrayList<>();
        diy.addAll(Set.of("World", "Hello"));
        assert diy.size() == 2 && diy.contains("World") && diy.contains("Hello");
    }

    @Test
    void addAllWithIndexIn() {
        DiyArrayList<String> diy = new DiyArrayList<>();
        diy.add("Again");
        diy.addAll(0, List.of("World", "Hello"));
        assert diy.size() == 3 && Objects.equals(diy.get(0), "World") && Objects.equals(diy.get(1), "Hello");
    }

    @Test
    void addAllWithIndexSize() {
        DiyArrayList<String> diy = new DiyArrayList<>();
        diy.add("Again");
        diy.addAll(1, List.of("World", "Hello"));
        assert diy.size() == 3 && Objects.equals(diy.get(1), "World") && Objects.equals(diy.get(2), "Hello");
    }

    @Test
    void addAllWithIndexOut() {
        DiyArrayList<String> diy = new DiyArrayList<>();
        diy.add("Again");
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> diy.addAll(2, Set.of("World", "Hello")));
    }

    @Test
    void clear() {
        DiyArrayList<String> diy = new DiyArrayList<>();
        diy.addAll(Set.of("World", "Hello"));
        diy.clear();
        assert diy.isEmpty();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test
    void getOut() {
        DiyArrayList<String> diy = new DiyArrayList<>();
        diy.add("Hello");
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> diy.get(1));
    }

    @Test
    void getIn() {
        DiyArrayList<String> diy = new DiyArrayList<>();
        diy.add("Hello");
        assert Objects.equals(diy.get(0), "Hello");
    }

    @Test
    void setIn() {
        DiyArrayList<String> diy = new DiyArrayList<>();
        diy.add("Hello");
        String old = diy.set(0, "World");
        assert diy.size() == 1 && Objects.equals(old, "Hello") && Objects.equals(diy.get(0), "World");
    }

    @Test
    void setOut() {
        DiyArrayList<String> diy = new DiyArrayList<>();
        diy.add("Hello");
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> diy.set(1, "World"));
    }

    @Test
    void indexOfIn() {
        DiyArrayList<String> diy = new DiyArrayList<>();
        diy.add("Hello");
        diy.add("Hello");
        assert diy.indexOf("Hello") == 0;
    }

    @SuppressWarnings("ListIndexOfReplaceableByContains")
    @Test
    void indexOfOut() {
        DiyArrayList<String> diy = new DiyArrayList<>();
        diy.add("Hello");
        diy.add("Hello");
        assert diy.indexOf("Hell") == DiyArrayList.INDEX_NOT_FOUND;
    }

    @Test
    void lastIndexOfIn() {
        DiyArrayList<String> diy = new DiyArrayList<>();
        diy.add("Hello");
        diy.add("Hello");
        assert diy.lastIndexOf("Hello") == 1;
    }

    @Test
    void lastIndexOfOut() {
        DiyArrayList<String> diy = new DiyArrayList<>();
        diy.add("Hello");
        diy.add("Hello");
        assert diy.lastIndexOf("Hell") == DiyArrayList.INDEX_NOT_FOUND;
    }

    @Test
    void massiveAdding() {
        DiyArrayList<Integer> diy = new DiyArrayList<>();
        for (int i = 0; i < 100; i++)
            diy.add(i);
        long even = diy.stream().filter(i -> i % 2 == 0).count();
        assert even == 50;
    }

}