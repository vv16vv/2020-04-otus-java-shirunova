package ru.otus.vsh.hw15.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.util.Random;

public class Counter implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(Counter.class);
    private static final int max = 10;
    private static final int min = 1;

    private final String name;
    private final OrderedLock lock;
    private final int quantity;
    private final int order;

    private final Random random = new Random();
    private int state = min;
    private boolean increasing = true;

    public Counter(int quantity, @Nonnull String name, @Nonnull OrderedLock lock) {
        this.quantity = quantity;
        this.name = name;
        this.lock = lock;
        this.order = lock.getNextOrder();
    }

    @Override
    public void run() {
        for (int i = 0; i < quantity; i++) {
            randomSleep();
            lock.lock();
            try {
                lock.waitForOrder(order);
                if (increasing)
                    logger.info("{} #{}: {}", name, i, state++);
                else
                    logger.info("{} #{}: {}", name, i, state--);
                if (increasing && state >= max) increasing = false;
                if (!increasing && state <= min) increasing = true;
                lock.signalAll();
            } finally {
                lock.unlock();
            }
        }
    }

    private void randomSleep() {
        try {
            Thread.sleep(random.nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
