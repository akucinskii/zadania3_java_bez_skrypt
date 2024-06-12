package Zadanie4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Zadanie4 {
    private static final int NUMBER_OF_ELEMENTS = 1_000_000;

    public static void main(String[] args) {
        List<Integer> arrayList = new ArrayList<>();
        List<Integer> synchronizedList = Collections.synchronizedList(new ArrayList<>());

        // Measure time for adding elements to ArrayList
        long startTime = System.currentTimeMillis();
        addRandomNumbers(arrayList, NUMBER_OF_ELEMENTS);
        long endTime = System.currentTimeMillis();
        long arrayListTime = endTime - startTime;
        System.out.println("Czas dodawania elementów arrayList: " + arrayListTime + " ms");

        // Measure time for adding elements to synchronizedList
        startTime = System.currentTimeMillis();
        addRandomNumbers(synchronizedList, NUMBER_OF_ELEMENTS);
        endTime = System.currentTimeMillis();
        long synchronizedListTime = endTime - startTime;
        System.out
                .println("Czas dodawania elementów na odpowiedniku zsynchronizowanym " + synchronizedListTime + " ms");
    }

    private static void addRandomNumbers(List<Integer> list, int numberOfElements) {
        Random random = new Random();
        for (int i = 0; i < numberOfElements; i++) {
            list.add(random.nextInt());
        }
    }
}
