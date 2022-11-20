package com.tsa.file.services;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class FileManager {

    private static final String FILE = "file";
    private static final String DIR = "directory";
    private static final String OPERATION_MOVE = "move";
    private static final String OPERATION_COPY = "copy";

    public static int countFiles(String path) {
        return treeWalkerCounter(getListOfContentByPath(path), FILE);
    }

    public static int countDirs(String path) {
        return treeWalkerCounter(getListOfContentByPath(path), DIR);
    }

    public static void copy(String from, String to) {
        moveOrCopy(from, to, OPERATION_COPY);
    }

    public static void move(String from, String to) {
        moveOrCopy(from, to, OPERATION_MOVE);
    }

    public static void delete(String path) {
        File file = Paths.get(path).toFile();
        if (!file.canExecute()) {
            throw new RuntimeException("There is a problem with the Path");
        }
        if (file.listFiles() == null || Objects.requireNonNull(file.listFiles()).length == 0) {
            throw new RuntimeException("There is nothing to delete");
        }
        if (file.isFile()) {
            file.delete();
        } else if (file.isDirectory()) {
            treeWalkerDeleter(file);
            file.delete();
        }
    }

    private static void treeWalkerDeleter(File fileForDeletion) {
        for (File file : Objects.requireNonNull(fileForDeletion.listFiles())) {
            if (file.isFile()) {
                file.delete();
            }
            if (file.isDirectory()) {
                treeWalkerDeleter(file);
                file.delete();
            }
        }
    }

    private static int treeWalkerCounter(File[] files, String type) {
        Objects.requireNonNull(files);
        int count = 0;
        for (File file : files) {
            if (file.isDirectory() && file.listFiles() != null) {
                count += treeWalkerCounter(file.listFiles(), type);
            }
            int result = type.equals(FILE) && file.isFile() ? 1 : type.equals(DIR) && file.isDirectory() ? 1 : 0;
            count = result + count;
        }
        return count;
    }

    private static File[] getListOfContentByPath(String path) {
        return Paths.get(path).toFile().listFiles();
    }

    private static void copyFile(File from, File to, String typeOfOperation) {
        File newFile = Paths.get(to.getAbsolutePath(), from.getName()).toFile();
        if (newFile.exists()) {
            throw new RuntimeException("File is already exist");
        }
        try (var input = new FileInputStream(from);
             var output = new FileOutputStream(newFile)) {
            input.transferTo(output);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (typeOfOperation.equals(OPERATION_MOVE)) {
            from.delete();
        }

    }

    private static void treeWalkerCopier(File fileFrom, File to, String typeOfOperation) {
        for (File file : Objects.requireNonNull(fileFrom.listFiles())) {
            if (file.isFile()) {
                copyFile(file, to, typeOfOperation);
            }
            if (file.isDirectory()) {
                File newDir = Paths.get(to.getAbsolutePath(), Path.of(fileFrom.getAbsolutePath()).relativize(Path.of(file.getAbsolutePath())).toString()).toFile();
                if (newDir.exists()) {
                    throw new RuntimeException("The Directory is already exist");
                } else {
                    newDir.mkdirs();
                }
                treeWalkerCopier(file, newDir, typeOfOperation);
                if (typeOfOperation.equals(OPERATION_MOVE)) {
                    file.delete();
                }
            }
        }
    }

    private static void moveOrCopy(String from, String to, String typeOfOperation) {
        File fileFrom = Paths.get(from).toFile();
        File fileTo = Paths.get(to).toFile();
        if (!fileFrom.canExecute()) {
            throw new RuntimeException("There is a problem with the Path FROM which you want to " + typeOfOperation);
        }
        if (!fileTo.canExecute()) {
            throw new RuntimeException("There is a problem with the Path TO which you want to " + typeOfOperation);
        }
        if (fileTo.isFile()) {
            throw new RuntimeException("You cannot " + typeOfOperation + " to file, only to directory");
        }
        if (fileFrom.isFile()) {
            copyFile(fileFrom, fileTo, typeOfOperation);
        } else if (fileFrom.isDirectory() && fileFrom.listFiles() != null) {
            File newDir = Paths.get(fileTo.getAbsolutePath(), fileFrom.getName()).toFile();
            newDir.mkdirs();
            treeWalkerCopier(fileFrom, newDir, typeOfOperation);
            if (typeOfOperation.equals(OPERATION_MOVE)) {
                fileFrom.delete();
            }
        }
    }
}
