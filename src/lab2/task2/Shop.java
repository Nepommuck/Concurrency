package lab2.task2;

public class Shop extends Thread {
    private int totalNumberOfCarts;
    private final CountingSemaphore semaphore;

    public Shop(int initialNumberOfCarts) {
        totalNumberOfCarts = initialNumberOfCarts;
        semaphore = new CountingSemaphore(initialNumberOfCarts);
    }

    private void log(String message) {
        Logger.log("SHOP", message);
    }

    private String currentCartsMessage() {
        return "(" + semaphore.getCurrentFreeSlots() + " / " + totalNumberOfCarts + ")";
    }

    public void takeCart() {
        semaphore.lock();
        log("Cart taken. " + currentCartsMessage() + " carts left");
    }

    public void addCartToPool() {
        totalNumberOfCarts += 1;
        semaphore.release();
        log("Cart added to pool. " + currentCartsMessage() + " carts free");
    }
    public void returnCart() {
        semaphore.release();
        log("Cart returned. "  + currentCartsMessage() + " carts free");
    }

    @Override
    public void run() {
    }
}
