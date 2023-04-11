package org.example;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ImageFileChecker {

    private static final List<String> IMAGE_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png", "gif", "bmp");

    public static void main(String[] args) {
        File rootDir = new File("C:\\Images\\");
        processFiles(rootDir);
    }

    private static void processFiles(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File subFile : files) {
                    processFiles(subFile);
                }
            }
        } else {
            String extension = FilenameUtils.getExtension(file.getName());
            if (IMAGE_EXTENSIONS.contains(extension.toLowerCase())) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    byte[] header = IOUtils.toByteArray(fis, 8);
                    if (isImage(header)) {
                        System.out.println(file.getAbsolutePath() + " is an image");
                    } else {
                        System.out.println(file.getAbsolutePath() + " is not an image");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static boolean isImage(byte[] header) {
        if (header.length < 8) {
            return false;
        }
        if ((header[0] == (byte)0xFF && header[1] == (byte)0xD8 && header[2] == (byte)0xFF) // JPEG
                || (header[0] == (byte)0x89 && header[1] == (byte)0x50 && header[2] == (byte)0x4E && header[3] == (byte)0x47 // PNG
                && header[4] == (byte)0x0D && header[5] == (byte)0x0A && header[6] == (byte)0x1A && header[7] == (byte)0x0A)
                || (header[0] == (byte)0x47 && header[1] == (byte)0x49 && header[2] == (byte)0x46 && header[3] == (byte)0x38 // GIF
                && (header[4] == (byte)0x37 || header[4] == (byte)0x39) && header[5] == (byte)0x61)) {
            return true;
        }
        if (header[0] == (byte)0x42 && header[1] == (byte)0x4D) { // BMP
            return true;
        }
        return false;
    }
}
