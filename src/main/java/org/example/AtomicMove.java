package org.example;

import java.io.File;

import java.io.IOException;

import java.nio.file.Files;

import static java.nio.file.StandardCopyOption.ATOMIC_MOVE;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class AtomicMove {
    public static void main(String[] argv) throws Exception {
        File source = new File("Main.java");
        File dest = new File("Main.java");
        atomicMove(source, dest);//  w  ww. j a  v  a2 s .c  o m
    }

    /**
     * Atomically move one file to another file.
     *
     * @param source the source file.
     * @param dest the destination file.
     * @throws IOException if an I/O error occurs.
     */
    public static void atomicMove(File source, File dest) throws IOException {
        Files.move(source.toPath(), dest.toPath(), ATOMIC_MOVE, REPLACE_EXISTING);
    }
}