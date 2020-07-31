package ru.otus.vsh.hw15.barrier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Counter implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(Counter.class);
    private final int max;
    private final String name;
    private final CyclicBarrier barrier;
    private int state = 1;
    private boolean increasing = true;

    public Counter(int max, String name, CyclicBarrier barrier) {
        this.max = max;
        this.name = name;
        this.barrier = barrier;
    }

    @Override
    public void run() {
        while (state > 0) {
            if (increasing)
                logger.info("{}: {}", name, state++);
            else
                logger.info("{}: {}", name, state--);
            if (increasing && state >= max) increasing = false;
            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}
