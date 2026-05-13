package util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class ListUtils {
    public ListUtils() {
    }

    public static void printLista(List<?> list) {
        for (Object obj : list) {
            System.out.println("\n" + obj);
        }
    }
    public static <T extends Comparable<? super T>> void writeLista(List<T> list) {
        String path = "C:\\temp\\bancoTeste.txt";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            Collections.sort(list);
            for (Object obj : list) {
                bw.write(String.valueOf(obj));
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> void copy(List<? extends T> origem, List<? super T> scr) {
        scr.addAll(origem);
    }
}
