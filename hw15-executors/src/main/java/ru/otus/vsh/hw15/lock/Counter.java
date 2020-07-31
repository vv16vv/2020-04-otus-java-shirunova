package ru.otus.vsh.hw15.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.Lock;

public class Counter implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(Counter.class);
    private final int max = 10;
    private final int min = 1;
    private final String name;
    private final Lock lock;
    private int state = min;
    private int quantity;
    private boolean increasing = true;

    public Counter(int quantity, String name, Lock lock) {
        this.quantity = quantity;
        this.name = name;
        this.lock = lock;
    }

    @Override
    public void run() {
        for (int i = 0; i < quantity; i++) {
            lock.lock();
            try {
                if (increasing)
                    logger.info("{} #{}: {}", name, i, state++);
                else
                    logger.info("{} #{}: {}", name, i, state--);
                if (increasing && state >= max) increasing = false;
                if (!increasing && state <= min) increasing = true;
            } finally {
                lock.unlock();
            }
        }
    }
}
