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

    public static int countFiles(String path){
        return treeWalkerCounter(getContent(path), FILE);
    }
    public static int countDirs(String path){
        return treeWalkerCounter(getContent(path), DIR);
    }
    public static void copy(String from, String to){
        moveOrCopy(from, to, OPERATION_COPY);
    }

    public static void move(String from, String to){
        moveOrCopy(from, to, OPERATION_MOVE);
    }

    public static void delete (String path) {
        Path pathFrom = Paths.get(path);
        File file = pathFrom.toFile();
        if (!file.canExecute()) throw new RuntimeException("There is a problem with the Path");
        if (file.listFiles() == null || Objects.requireNonNull(file.listFiles()).length == 0) throw new RuntimeException("There is nothing to delete");
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
        int counter = 0;
        for (File file : files) {
            if (file.isDirectory() && file.listFiles() != null) {
                counter += treeWalkerCounter(file.listFiles(), type);
            }
            int result = type.equals(FILE) && file.isFile() ? 1 : type.equals(DIR) && file.isDirectory() ? 1 : 0;
            counter = result + counter;
        }
        return counter;
    }

    private static File[] getContent(String path) {
        return Paths.get(path).toFile().listFiles();
    }
    private static void copyFile (File from, File to, String typeOfOperation) {
        File newFile = Path.of(to.getAbsolutePath() + "\\" + from.getName()).toFile();
        if (newFile.exists()) throw new RuntimeException("File is already exist");
        try (var buffInput = new BufferedInputStream(new FileInputStream(from));
             var buffOut = new BufferedOutputStream(new FileOutputStream(newFile))) {
            int counter = 0;
            int counterForRead;
            int availableData = buffInput.available();
            byte[] buffArray = new byte[8*1024];
            while (counter < availableData) {
                counterForRead = buffInput.read(buffArray, 0, buffArray.length-1);
                buffOut.write(buffArray, 0, counterForRead);
                counter += counterForRead;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (typeOfOperation.equals(OPERATION_MOVE)) from.delete();
    }
    private static void treeWalkerCopier(File fileFrom, File to, String typeOfOperation) {
        for (File file : Objects.requireNonNull(fileFrom.listFiles())) {
            if (file.isFile()) {
                copyFile(file, to, typeOfOperation);
            }
            if (file.isDirectory()) {
                File newDir = Path.of(to.getAbsolutePath() + "/" + Path.of(fileFrom.getAbsolutePath()).relativize(Path.of(file.getAbsolutePath()))).toFile();
                if (newDir.exists()) {
                    throw new RuntimeException("The Directory is exist");
                } else {
                    newDir.mkdirs();
                }
                treeWalkerCopier(file, newDir, typeOfOperation);
                if (typeOfOperation.equals(OPERATION_MOVE)) file.delete();
            }
        }
    }
    private static void moveOrCopy (String from, String to, String typeOfOperation) {
        Path pathFrom = Paths.get(from);
        Path pathTo = Paths.get(to);
        File fileFrom = pathFrom.toFile();
        File fileTo = pathTo.toFile();
        if (!fileFrom.canExecute()) throw new RuntimeException("There is a problem with the Path FROM which you want to " + typeOfOperation);
        if (!fileTo.canExecute()) throw new RuntimeException("There is a problem with the Path TO which you want to " + typeOfOperation);
        if (fileTo.isFile()) throw new RuntimeException("You cannot " + typeOfOperation + " to file, only to directory");
        if (fileFrom.isFile()) {
            copyFile(fileFrom, fileTo, typeOfOperation);
        } else if (fileFrom.isDirectory() && fileFrom.listFiles() !=null) {
            File newDir = Path.of(fileTo.getAbsolutePath() + "/" + Path.of(fileFrom.getName())).toFile();
            newDir.mkdirs();
            treeWalkerCopier(fileFrom, newDir, typeOfOperation);
            if (typeOfOperation.equals(OPERATION_MOVE)) fileFrom.delete();
        }
    }
}
