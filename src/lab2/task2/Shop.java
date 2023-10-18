package lab2.task2;

public class Shop extends Thread {
    private final CountingSemaphore semaphore;

    public Shop(int initialNumberOfCarts) {
        semaphore = new CountingSemaphore(initialNumberOfCarts);
    }

    private void log(String message) {
        Logger.log("SHOP", message);
    }

    public void takeCart() {
        log("Cart taken. " + semaphore.getCurrentFreeSlots() + " carts left");
        semaphore.lock();
    }

    public void returnCart() {
        log("Cart returned. " + semaphore.getCurrentFreeSlots() + " carts are free");
        semaphore.release();
    }

    @Override
    public void run() {
    }
}
