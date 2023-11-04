package lab2.task2;

import common.ThreadSleep;

public class Worker extends Thread {
    private final Shop shop;

    public Worker(Shop shop) {
        this.shop = shop;
    }

    public void addCart() {
        Logger.log("WORKER", "Adds cart to the shop");
        shop.addCartToPool();
    }

    @Override
    public void run() {
        ThreadSleep.randomSleep(10, 20);
        addCart();
    }
}
