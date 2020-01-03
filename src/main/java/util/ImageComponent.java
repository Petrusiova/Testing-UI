package util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ImageComponent extends JComponent {
    private Image image;

    public ImageComponent(String filePath) {
        // Получаем изображения.
        try {
            image = ImageIO.read(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void paintComponent(Graphics g) {
        if (image == null) return;
        int imageWidth = image.getWidth(this);
        int imageHeight = image.getHeight(this);

        // Отображение рисунка в левом верхнем углу.
        g.drawImage(image, 0, 0, null);

        // Многократный вывод изображения в панели.

        for (int i = 0; i * imageWidth <= getWidth(); i++)
            for (int j = 0; j * imageHeight <= getHeight(); j++)
                if (i + j > 0)
                    g.copyArea(0, 0, imageWidth, imageHeight, i * imageWidth, j * imageHeight);
    }
}
