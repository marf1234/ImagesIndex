package org.example;

import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;


public class ImageFinderAndCopier {
    public static void main(String[] args) throws IOException {

        FileInputStream propertyLoaderStream;
        Properties property = new Properties();


        propertyLoaderStream = new FileInputStream("src/main/resources/config.property");
        property.load(propertyLoaderStream);

        String sourceDirPath = property.getProperty("sourceDirPath");
        String destDirPath = property.getProperty("destDirPath");
        String extensionString = property.getProperty("imageAvailableExtensions");
        List<String> imageExtensions = new ArrayList<>(Arrays.asList(extensionString.split(",")));

        // Создаем объекты директорий и проверяем их существование, создавая их при необходимости
        File sourceDir = new File(sourceDirPath);
        if (!sourceDir.exists()) {
            throw new IllegalArgumentException("Source directory does not exist");
        }
        File destDir = new File(destDirPath);
        if (!destDir.exists()) {
            if (!destDir.mkdirs()) {
                throw new IOException("Failed to create destination directory");
            }
        }

        // Запускаем рекурсивный метод поиска и копирования изображений
        copyImages(sourceDir, destDir, imageExtensions, 10.0f);
    }

    private static void copyImages(File sourceDir, File destDir, List<String> imageExtensions, float compression) throws IOException {
        // Получаем список файлов и директорий в исходной директории
        File[] files = sourceDir.listFiles();

        // Если директория пуста, то выходим из метода
        if (files == null || files.length == 0) {
            return;
        }

        // Обходим все файлы и директории
        for (File file : files) {
            // Если это директория, то запускаем рекурсивный метод поиска и копирования изображений в этой директории
            if (file.isDirectory()) {
                File newDestDir = new File(destDir, file.getName());
                if (!newDestDir.exists() && !newDestDir.mkdir()) {
                    throw new IOException("Failed to create destination subdirectory");
                }
                copyImages(file, newDestDir, imageExtensions, compression);
            } else {
                // Если это файл, то проверяем его расширение на соответствие изображению
                String extension = getFileExtension(file);
                if (imageExtensions.contains(extension)) {
                    // Если расширение соответствует, то загружаем изображение и сжимаем его
                    BufferedImage image = ImageIO.read(file);
                    if (image != null) {
                        // Сжимаем изображение с заданным коэффициентом
                        BufferedImage compressedImage = Thumbnails.of(image).scale(1f / compression).asBufferedImage();
                        // Сохраняем сжатое изображение в новую директорию с сохранением структуры исходной директории
                        Path sourceFilePath = file.toPath();
                        Path destFilePath = new File(destDir, sourceDir.toPath().relativize(sourceFilePath).toString()).toPath();
                        ImageIO.write(compressedImage, extension, destFilePath.toFile());
                    }
                }
            }
        }
    }


    private static String getFileExtension(File file) {
        String fileName = file.getName();
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex == -1) {
            return ""; // если точка не найдена, то возвращаем пустую строку
        }
        return fileName.substring(lastDotIndex + 1).toLowerCase();
    }
}
