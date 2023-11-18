package lab5.task2;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PersistenceManager {
    private static class Record {
        public int numberOfThreads;
        public int numberOfTasks;
        public List<Double> timeMeasurementsMs;

        private Record(int numberOfIterations, int numberOfTasks, List<Double> timeMeasurementsMs) {
            this.numberOfThreads = numberOfIterations;
            this.numberOfTasks = numberOfTasks;
            this.timeMeasurementsMs = timeMeasurementsMs;
        }
    }

    final ObjectMapper objectMapper = new ObjectMapper();
    private final List<Record> data = new ArrayList<>();

    public void addRecord(int numberOfIterations, int numberOfTasks, List<Double> timeMeasurementsMs) {
        data.add(
          new Record(numberOfIterations, numberOfTasks, timeMeasurementsMs)
        );
    }

    public void exportToFile(Path filePath) throws IOException {
        objectMapper.writeValue(new File(filePath.toUri()), data);
    }
}
