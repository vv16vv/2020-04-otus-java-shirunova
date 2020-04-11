package ru.otus.vsh.hw02;

import javax.annotation.Nonnull;
import java.lang.reflect.Array;
import java.util.*;

@SuppressWarnings("unused")
public class DiyArrayList<T> implements List<T> {
    private static final int INCREASE_STEP = 10;
    static final int INDEX_NOT_FOUND = -1;
    private T[] innerArray;
    private int size = 0;

    private void unsupported() {
        throw new UnsupportedOperationException();
    }

    /**
     * Taken from https://stackoverflow.com/questions/529085/how-to-create-a-generic-array-in-java
     */
    @SuppressWarnings("unchecked")
    public DiyArrayList(Class<T> clazz, int initialCapacity) {
        int initialLength = getIncreaseSize(initialCapacity);
        innerArray = (T[]) Array.newInstance(clazz, initialLength);
    }

    public DiyArrayList(Class<T> clazz) {
        this(clazz, 0);
    }

    public DiyArrayList() {
        this(0);
    }

    @SuppressWarnings("unchecked")
    public DiyArrayList(int initialCapacity) {
        int initialLength = getIncreaseSize(initialCapacity);
        innerArray = (T[]) new Object[initialLength];
    }

    @SuppressWarnings("unchecked")
    public DiyArrayList(Collection<T> collection) {
        int initialLength = getIncreaseSize(collection.size());
        innerArray = (T[]) new Object[initialLength];
        addAll(collection);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) > INDEX_NOT_FOUND;
    }

    @Override
    @Nonnull
    public Iterator<T> iterator() {
        return new DiyIterator();
    }

    @Override
    @Nonnull
    public Object[] toArray() {
        return Arrays.copyOf(innerArray, size);
    }

    @Override
    @Nonnull
    public <T1> T1[] toArray(@Nonnull T1[] a) {
        unsupported();
        return a;
    }

    private int getIncreaseSize(int initial) {
        int increaseSize;
        if (initial <= INCREASE_STEP) increaseSize = INCREASE_STEP;
        else increaseSize = (initial / INCREASE_STEP + 1) * INCREASE_STEP;
        return increaseSize;
    }

    private void increaseIfRequired() {
        increaseIfRequired(1);
    }

    private void increaseIfRequired(int addedQuantity) {
        if (size + addedQuantity >= innerArray.length) {
            // increase size of the inner array
            innerArray = Arrays.copyOf(innerArray, innerArray.length + getIncreaseSize(size + addedQuantity - innerArray.length));
        }
    }

    @Override
    public boolean add(T t) {
        increaseIfRequired();
        innerArray[size++] = t;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        // 1. search index of the removing object
        int index = indexOf(o);
        // 2. remove this object
        if (index >= 0 && index < size) {
            remove(index);
            return true;
        } else return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return c.stream().allMatch(this::contains);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        increaseIfRequired(c.size());
        c.forEach(this::add);
        return true;
    }

    @SuppressWarnings({"SuspiciousSystemArraycopy"})
    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        checkIndexOnAdding(index);
        increaseIfRequired(c.size());
        shiftArray(index, index + c.size());
        System.arraycopy(c.toArray(), 0, innerArray, index, c.size());
        size += c.size();
        return true;
    }

    @Override
    public boolean removeAll(@Nonnull Collection<?> c) {
        unsupported();
        return false;
    }

    @Override
    public boolean retainAll(@Nonnull Collection<?> c) {
        unsupported();
        return false;
    }

    @Override
    public void clear() {
        size = 0;
    }

    @Override
    public T get(int index) {
        checkIndexOnReading(index);
        return innerArray[index];
    }

    @Override
    public T set(int index, T element) {
        checkIndexOnReading(index);
        T old = innerArray[index];
        innerArray[index] = element;
        return old;
    }

    @Override
    public void add(int index, T element) {
        checkIndexOnAdding(index);
        increaseIfRequired();
        size++;
        // 1. Shift items from index-th position to the right
        shiftArray(index, index + 1);
        innerArray[index] = element;
    }

    @Override
    public T remove(int index) {
        checkIndexOnReading(index);
        T removed = innerArray[index];
        if (index < size - 1)
            shiftArray(index + 1, index);
        size--;
        return removed;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (innerArray[i].equals(o)) return i;
        }
        return INDEX_NOT_FOUND;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = size - 1; i >= 0; i--) {
            if (innerArray[i].equals(o)) return i;
        }
        return INDEX_NOT_FOUND;
    }

    @Override
    @Nonnull
    public ListIterator<T> listIterator() {
        return new DiyListIterator();
    }

    @Override
    @Nonnull
    public ListIterator<T> listIterator(int index) {
        return new DiyListIterator(index);
    }

    @Override
    @Nonnull
    public DiyArrayList<T> subList(int fromIndex, int toIndex) {
        unsupported();
        return null;
    }

    private void checkIndexOnAdding(int index) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException(index);
    }

    private void checkIndexOnReading(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException(index);
    }

    private void shiftArray(int srcIndex, int dstIndex) {
        System.arraycopy(innerArray, srcIndex, innerArray, dstIndex, size - srcIndex);
    }

    private class DiyIterator implements Iterator<T> {
        private int current;

        public DiyIterator() {
            current = 0;
        }

        @Override
        public boolean hasNext() {
            return current < size;
        }

        @Override
        public T next() {
            if(current >= size) throw new NoSuchElementException("No more elements");
            return innerArray[current++];
        }
    }

    private class DiyListIterator implements ListIterator<T> {
        private int cursor;
        private boolean mutableCalled = true;

        private void resetCalled() {
            mutableCalled = false;
        }

        public DiyListIterator() {
            this(INDEX_NOT_FOUND);
        }

        public DiyListIterator(int initial) {
            cursor = initial;
        }

        @Override
        public boolean hasNext() {
            return cursor < size;
        }

        @Override
        public T next() {
            resetCalled();
            return innerArray[++cursor];
        }

        @Override
        public boolean hasPrevious() {
            return cursor > 0;
        }

        @Override
        public T previous() {
            resetCalled();
            return innerArray[--cursor];
        }

        @Override
        public int nextIndex() {
            return cursor + 1;
        }

        @Override
        public int previousIndex() {
            return cursor - 1;
        }

        @Override
        public void remove() {
            if (!mutableCalled) {
                mutableCalled = true;
                DiyArrayList.this.remove(cursor);
            } else
                throw new IllegalStateException("Neither 'next' nor 'previous' have been called, or 'remove' or 'add' have been called after the last call to 'next' or 'previous'");
        }

        @Override
        public void set(T t) {
            if (!mutableCalled) {
                innerArray[cursor] = t;
            } else
                throw new IllegalStateException("Neither 'next' nor 'previous' have been called, or 'remove' or 'add' have been called after the last call to 'next' or 'previous'");
        }

        @Override
        public void add(T t) {
            DiyArrayList.this.add(cursor, t);
            mutableCalled = true;
        }
    }
}
