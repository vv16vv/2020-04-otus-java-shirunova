package ru.otus.vsh.hw15.lock;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class OrderedLock extends ReentrantLock {
    private final AtomicInteger currentOrder = new AtomicInteger(1);
    private final Condition condition = newCondition();
    private int max = 0;

    public OrderedLock() {
        super(false);
    }

    public int getNextOrder() {
        return ++max;
    }

    public void waitForOrder(int order) {
        while (order != currentOrder.get()) {
            try {
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void signalAll() {
        condition.signalAll();
    }

    @Override
    public void unlock() {
        currentOrder.incrementAndGet();
        if (currentOrder.get() > max) currentOrder.set(1);
        super.unlock();
    }
}
