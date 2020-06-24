package ru.otus.cachehw;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

/**
 * @author sergey
 * created on 14.12.18.
 */
public class MyCache<K, V> implements HwCache<K, V> {
    private final WeakHashMap<K, V> store = new WeakHashMap<>();
    private final List<HwListener<K, V>> listeners = new ArrayList<>();

    @Override
    public void put(@Nonnull K key, @Nonnull V value) {
        store.put(key, value);
        notifyRound(key, value, "put");
    }

    @Override
    public void remove(@Nonnull K key) {
        var value = store.remove(key);
        notifyRound(key, value, "remove");
    }

    @Override
    @Nullable
    public V get(K key) {
        var value = store.get(key);
        notifyRound(key, value, "get");
        return value;
    }

    @Override
    public void addListener(@Nonnull HwListener<K, V> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(@Nonnull HwListener<K, V> listener) {
        listeners.remove(listener);
    }

    private void notifyRound(@Nonnull K key, @Nonnull V value, @Nonnull String action) {
        listeners.forEach(listener -> listener.notify(key, value, action));
    }
}
