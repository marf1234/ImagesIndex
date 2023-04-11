package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;


public class ImageFinderAndCopier {

    public static void main(String[] args) throws IOException {
        String sourceDirPath = "C:\\Images\\";
        String destDirPath = "C:\\Images2\\";
        List<String> imageExtensions = Arrays.asList("jpg", "jpeg", "png", "gif"); // список расширений изображений

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
        copyImages(sourceDir, destDir, imageExtensions);
    }

    private static void copyImages(File sourceDir, File destDir, List<String> imageExtensions) throws IOException {
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
                copyImages(file, newDestDir, imageExtensions);
            } else {
                // Если это файл, то проверяем его расширение на соответствие изображению
                String extension = getFileExtension(file);
                if (imageExtensions.contains(extension)) {
                    // Если расширение соответствует, то копируем файл в новую директорию с сохранением структуры исходной директории
                    Path sourceFilePath = file.toPath();
                    Path destFilePath = new File(destDir, sourceDir.toPath().relativize(sourceFilePath).toString()).toPath();
                    Files.copy(sourceFilePath, destFilePath, StandardCopyOption.REPLACE_EXISTING);
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
