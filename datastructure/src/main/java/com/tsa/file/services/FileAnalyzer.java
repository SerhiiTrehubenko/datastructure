package com.tsa.file.services;

//import com.tsa.list.implementations.MyLinkedList;
//import com.tsa.list.interfaces.List;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileAnalyzer {
    public static void main(String[] args) {
        if(args.length <= 0 || args.length > 2) throw new IllegalArgumentException("You should provide only TWO arguments");
        String search = args[1];
        Path path = Path.of(args[0]);
        File file = path.toFile();
        if (!file.canExecute()) throw new RuntimeException("there is s problem with the Path");
        //List<String> coincides = new MyLinkedList<>();
        List<String> coincides = new ArrayList<>();
        try (var in = new FileInputStream(file)) {
            int avaliable = in.available();
            byte[] bytesFromFile = new byte[avaliable];
            in.read(bytesFromFile);
            String[] lines = new String(bytesFromFile, "windows-1251").split("\n");
            Arrays.stream(lines)
                    .filter(x -> x.contains(search))
                    .map(x -> x.replaceAll(search, search.toUpperCase()))
                    .map(String::trim)
                    .filter(x -> x.matches("[\\w\\d\\W\\D\\s\\S]+[,.?]"))
                    .forEach(coincides::add);
                    //.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Number of coincides: " + coincides.size());
        for (String coincide : coincides) {
            System.out.println(coincide);
        }
    }

}
