package lab4.task2;

import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Plotter implements Runnable {
    private final Map<Integer, Double> data;
    private final String title;
    private final String xLabel;
    private final String yLabel;

    public Plotter(Map<Integer, Double> data, String title, String xLabel, String ylabel) {
        this.data = data;
        this.title = title;
        this.xLabel = xLabel;
        this.yLabel = ylabel;
    }

    private void plot() {
        try {
            var sortedKeys = data.keySet()
              .stream()
              .sorted()
              .toList();

            List<Double> x = sortedKeys
              .stream()
              .map(Integer::doubleValue)
              .toList();

            List<Double> y = sortedKeys
              .stream()
              .map(data::get)
              .toList();

            Plot plt = Plot.create();
            plt.plot().add(x, y).label("sin");
//            plt.legend().loc("upper right");
            plt.title(this.title);
            plt.xlabel(this.xLabel);
            plt.ylabel(this.yLabel);

            plt.show();
        } catch (PythonExecutionException e) {
            System.out.println("Python execution exception: Make sure that you have `python` file in `/usr/bin`. " +
              "If not, it can be easiest done by `sudo ln -s /usr/bin/python3 /usr/bin/python`");
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        plot();
    }
}
