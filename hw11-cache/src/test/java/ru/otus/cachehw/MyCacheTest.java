package ru.otus.cachehw;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

class MyCacheTest {

    private HwCache<String, String> myCache;
    private PrintStream backup;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void beforeEach() {
        backup = System.out;
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        myCache = new MyCache<>();
    }

    @AfterEach
    void afterEach() {
        System.setOut(backup);
    }

    @Test
    void getNonExisting() {
        var value = myCache.get("nonexisting");
        assertThat(value).isNull();
    }

    @Test
    void getExisting() {
        var key = "first";
        var expectedValue = "value";
        myCache.put(key, expectedValue);
        var actualValue = myCache.get(key);
        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    void remove() {
        var key = "first";
        var expectedValue = "value";
        myCache.put(key, expectedValue);
        var actualValue = myCache.get(key);
        assertThat(actualValue).isEqualTo(expectedValue);
        myCache.remove(key);
        var removedValue = myCache.get(key);
        assertThat(removedValue).isNull();
    }

    @Test
    void listenerGet() {
        var listener = new HwListener<String, String>() {
            @Override
            public void notify(String key, String value, String action) {
                System.out.printf("action %s on key %s with value %s", action, key, value);
            }
        };
        myCache.addListener(listener);
        var key = "nonexisting";
        var value = myCache.get(key);
        Assertions.assertNull(value);
        var expectedOutput = String.format("action %s on key %s with value %s", "get", key, value);
        assertThat(outputStream.toString()).isEqualTo(expectedOutput);
    }

    @Test
    void listenerPut() {
        var listener = new HwListener<String, String>() {
            @Override
            public void notify(String key, String value, String action) {
                System.out.printf("action %s on key %s with value %s", action, key, value);
            }
        };
        myCache.addListener(listener);
        var key = "first";
        var expectedValue = "value";
        myCache.put(key, expectedValue);
        var expectedOutput = String.format("action %s on key %s with value %s", "put", key, expectedValue);
        assertThat(outputStream.toString()).isEqualTo(expectedOutput);
    }

    @Test
    void listenerRemove() {
        var listener = new HwListener<String, String>() {
            @Override
            public void notify(String key, String value, String action) {
                System.out.printf("action %s on key %s with value %s", action, key, value);
            }
        };
        var key = "first";
        var expectedValue = "value";
        myCache.put(key, expectedValue);
        myCache.addListener(listener);
        myCache.remove(key);
        var expectedOutput = String.format("action %s on key %s with value %s", "remove", key, expectedValue);
        assertThat(outputStream.toString()).isEqualTo(expectedOutput);
    }

    @Test
    void removeListener() {
        var listener = new HwListener<String, String>() {
            @Override
            public void notify(String key, String value, String action) {
                System.out.printf("action %s on key %s with value %s", action, key, value);
            }
        };
        myCache.addListener(listener);
        var key = "first";
        var expectedValue = "value";
        myCache.put(key, expectedValue);
        var expectedOutput = String.format("action %s on key %s with value %s", "put", key, expectedValue);
        assertThat(outputStream.toString()).isEqualTo(expectedOutput);
        myCache.removeListener(listener);
        outputStream.reset();
        var anotherValue = myCache.get(key);
        assertThat(anotherValue).isEqualTo(expectedValue);
        assertThat(outputStream.toString()).isEmpty();
    }
}