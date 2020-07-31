package ru.otus.vsh.hw15.lock;

public class Main {
    public static void main(String[] args) {
        var lock = new OrderedLock();
        var quantity = 20;
        new Thread(new Counter(quantity, "aaa", lock)).start();
        new Thread(new Counter(quantity, "bbb", lock)).start();
    }
}
