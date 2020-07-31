package ru.otus.vsh.hw15.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Lock lock = new ReentrantLock(true);
        var quantity = 50;
        var ct1 = new Thread(new Counter(quantity, "fst", lock));
        var ct2 = new Thread(new Counter(quantity, "snd", lock));
        ct1.start();
        ct2.start();
        ct1.join();
        ct2.join();
    }
}
