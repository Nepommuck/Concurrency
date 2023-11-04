package lab4.task1;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Buffer {
    private final List<Product> products;

    public Buffer(int numberOfProducts, int stepsToProcess) {
        products = IntStream
          .range(0, numberOfProducts)
          .boxed()
          .map(i -> new Product(stepsToProcess))
          .toList();
    }

    @Override
    public String toString() {
        return products.stream()
          .map(Product::toString)
          .collect(Collectors.joining("", "[", "]"));
    }

    public int getNumberOfProducts() {
        return products.size();
    }

    public Product getProduct(int index) {
        return products.get(index % getNumberOfProducts());
    }
}
