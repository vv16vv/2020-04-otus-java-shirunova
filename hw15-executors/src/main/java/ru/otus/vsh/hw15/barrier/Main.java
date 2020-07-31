package ru.otus.vsh.hw15.barrier;

import java.util.concurrent.CyclicBarrier;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        var barrier = new CyclicBarrier(2);
        var ct1 = new Thread(new Counter(10, "first", barrier));
        var ct2 = new Thread(new Counter(10, "second", barrier));
        ct1.start();
        ct2.start();
        ct1.join();
        ct2.join();
    }
}
