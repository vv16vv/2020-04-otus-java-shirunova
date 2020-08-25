package ru.otus.vsh.hw16.messagesystem;

import lombok.experimental.UtilityClass;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.Duration;
import java.util.function.Predicate;

@UtilityClass
public class MessageSystemHelper {
    public <T> void waitForAnswer(@Nullable T t, @Nonnull Predicate<T> predicate) {
        waitForAnswer(Duration.ofSeconds(5), t, predicate);
    }

    public <T> void waitForAnswer(@Nonnull Duration duration, @Nullable T t, @Nonnull Predicate<T> predicate) {
        var start = System.currentTimeMillis();
        var current = start;
        while (!predicate.test(t) && (current - start) < duration.toMillis()) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException ignore) {
            }
            current = System.currentTimeMillis();
        }
        if(!predicate.test(t))
            throw new TimeoutException();
    }
}
