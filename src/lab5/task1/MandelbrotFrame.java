package lab5.task1;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MandelbrotFrame extends JFrame {
    private final BufferedImage image;

    public MandelbrotFrame(Array2d<Integer> calculatedValues, FrameParameters frameParams) {
        super("Mandelbrot Set");

        setBounds(frameParams.margin, frameParams.margin, frameParams.width, frameParams.height);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);

        for (var point : frameParams.getAllPoints())
            calculatedValues.get(point.x, point.y).ifPresent(value ->
              image.setRGB(point.x, point.y, value)
            );
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(image, 0, 0, this);
    }
}
