package lab4.task2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.LongStream;

public class DataCollector {
    private final Map<Integer, List<Long>> producersData = new HashMap<>();
    private final Map<Integer, List<Long>> consumersData = new HashMap<>();

    private void add(Map<Integer, List<Long>> map, int argument, Long value) {
        map.put(
          argument,
          LongStream.concat(
            map.getOrDefault(argument, List.of())
              .stream()
              .mapToLong(i -> i),
            LongStream.of(value)
          ).boxed().toList()
        );
    }

    public void addProducerReading(int argument, Long value) {
        add(producersData, argument, value);
    }

    public void addConsumerReading(int argument, Long value) {
        add(consumersData, argument, value);
    }

    private Map<Integer, Double> calculateAverages(Map<Integer, List<Long>> map) {
        final Map<Integer, Double> result = new HashMap<>();
        for (Integer key : map.keySet())
            result.put(
              key,
              map.get(key)
                .stream()
                .mapToLong(i -> i)
                .average()
                .orElse(0.0) / 1_000_000
            );
        return result;
    }

    public Map<Integer, Double> getProducerAverages() {
        return calculateAverages(producersData);
    }

    public Map<Integer, Double> getConsumerAverages() {
        return calculateAverages(consumersData);
    }
}
