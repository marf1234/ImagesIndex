package org.example;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.IIOImage;

public class ImageCompressor {
    public static void main(String[] args) {
        try {
            // Загрузка исходного изображения
            File input = new File("C:\\bw-motorcycle.jpg");
            BufferedImage image = ImageIO.read(input);

            // Создание объекта для записи сжатого изображения в файл
            File output = new File("D:\\compressedImage.jpg");

            // Настройка параметров сжатия
            float quality = 0.001f; // Качество изображения (от 0.0 до 1.0)

            // Сжатие изображения и запись его в файл
            ImageWriter writer = ImageIO.getImageWritersByFormatName("jpg").next();
            ImageWriteParam param = writer.getDefaultWriteParam();
            JPEGImageWriteParam jpegParams = (JPEGImageWriteParam) param;
            jpegParams.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            jpegParams.setCompressionQuality(quality);
            jpegParams.setProgressiveMode(ImageWriteParam.MODE_DEFAULT);
            writer.setOutput(ImageIO.createImageOutputStream(output));

            // Запись сжатого изображения в файл
            writer.write(null, new IIOImage(image, null, null), jpegParams);
            writer.dispose();
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}