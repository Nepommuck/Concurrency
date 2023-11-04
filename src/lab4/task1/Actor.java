package lab4.task1;

public abstract class Actor implements Runnable {
    protected final Buffer buffer;
    protected int currentProductIndex = 0;

    protected Actor(Buffer buffer) {
        this.buffer = buffer;
    }

    protected void move() {
        currentProductIndex = (currentProductIndex + 1) % buffer.getNumberOfProducts();
    }

    protected Product getCurrentProduct() {
        return buffer.getProduct(currentProductIndex);
    }

    abstract protected void act();

    @Override
    public void run() {
        while (true) {
            act();
            move();
        }
    }
}
