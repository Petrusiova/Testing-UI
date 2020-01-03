package util;

import javax.swing.*;

public class ImageFrame extends JFrame {
    public static final int DEFAULT_WIDTH = 300;
    public static final int DEFAULT_HEIGHT = 200;


    public ImageFrame(String filePath)
    {
        setTitle("ImageTest");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        // Добавление компонента к фрейму.

        ImageComponent component = new ImageComponent(filePath);
        add(component);
    }

}
