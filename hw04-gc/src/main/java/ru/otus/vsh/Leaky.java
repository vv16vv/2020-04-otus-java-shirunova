package ru.otus.vsh;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SuppressWarnings("BusyWait")
class Leaky implements LeakyMBean {
    private static final Logger logger = LoggerFactory.getLogger(Leaky.class);

    private final List<User> users = new ArrayList<>();
    private final Random random = new Random();
    private int quantAdd;
    private int quantRemove;

    public Leaky(int quantAdd, int quantRemove) {
        this.quantAdd = quantAdd;
        this.quantRemove = quantRemove;
    }

    void add() throws InterruptedException {
        logger.info("-- Start to add users");
        for (int i = 0; i < quantAdd; i++) {
            users.add(new User(String.format("Item #%d", i + 1)));
            Thread.sleep(10);
        }
        logger.info("-- End to add users");
    }

    void remove() throws InterruptedException {
        logger.info("-- Start to remove users");
        for (int i = 0; i < quantRemove; i++) {
            users.remove(random.nextInt(users.size()));
            Thread.sleep(10);
        }
        logger.info("-- End to remove users");
    }

    @Override
    public int getQuantAdd() {
        logger.info("getQuantAdd: value = {}", this.quantAdd);
        return quantAdd;
    }

    @Override
    public void setQuantAdd(int quantAdd) {
        logger.info("setQuantAdd: old value = {}; new value = {}", this.quantAdd, quantAdd);
        this.quantAdd = quantAdd;
    }

    @Override
    public int getQuantRemove() {
        logger.info("getQuantRemove: value = {}", this.quantRemove);
        return quantRemove;
    }

    @Override
    public void setQuantRemove(int quantRemove) {
        logger.info("setQuantRemove: old value = {}; new value = {}", this.quantRemove, quantRemove);
        this.quantRemove = quantRemove;
    }
}
