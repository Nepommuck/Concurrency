package lab2.task2;

public class Customer extends Thread {
    private final Shop shop;
    private final String name;

    public Customer(Shop shop, int id) {
        this.shop = shop;
        this.name = "CUSTOMER " + id;
    }

    private void log(String message) {
        Logger.log(this.name, message);
    }

    private void simulateShopping() {
        final long MIN_SHOPPING_TIME = 10 * 1000;
        final long MAX_SHOPPING_TIME = 25 * 1000;

        long sleepTime = MIN_SHOPPING_TIME + (long) (Math.random() * (MAX_SHOPPING_TIME - MIN_SHOPPING_TIME));
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void doShopping() {
        log("Wants to take a cart");
        shop.takeCart();
        log("Took cart. Starts shopping");

        simulateShopping();

        log("Finished shopping. Returned cart");
        shop.returnCart();
    }

    @Override
    public void run() {
        ThreadRandomSleep.sleep(0.0, 4.0);
        doShopping();
    }
}
