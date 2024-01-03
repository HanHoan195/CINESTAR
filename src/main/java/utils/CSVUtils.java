package utils;

import model.Film;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CSVUtils {
    public static <T> void write(String path, List<T> items){
        try {
            PrintWriter print = new PrintWriter(new FileWriter(path, true));
            for (Object item: items) {
                print.println(item.toString());
                print.flush();
            }
            print.close();
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(path + "invalid");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> void writeFilm(String path, T item){
        try {
//            File file = new File(path);
//            boolean append = file.exists();
            PrintWriter print = new PrintWriter( new FileWriter(path, true));
//            if(append) {
//                print.println();
//            }
//            for (Object item: items) {

                print.println(item.toString());
                print.flush();
//            }
            print.close();
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(path + "invalid");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> read(String path) {
        try {
            return Files.readAllLines(Paths.get(path));
        } catch (IOException e){
            throw new IllegalArgumentException(path + " invalid");
        }
    }
}
