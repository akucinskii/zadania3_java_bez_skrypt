package Zadanie3;

import java.util.Random;

public class Zadanie3 {

    private static final int ARRAY_SIZE = 100000000;
    private static final int THREAD_COUNT = 2;

    private static int[] generateArray(int size) {
        Random rand = new Random();
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = rand.nextInt(10);
        }
        return arr;
    }

    private static long sum(int[] arr) {
        long result = 0;
        for (int i = 0; i < arr.length; i++) {
            result += arr[i];
        }
        return result;
    }

    private static class SumThread implements Runnable {
        private int[] arr;
        private int startIndex;
        private int endIndex;
        private long result;

        public SumThread(int[] arr, int startIndex, int endIndex) {
            this.arr = arr;
            this.startIndex = startIndex;
            this.endIndex = endIndex;
            this.result = 0;
        }

        public long getResult() {
            return result;
        }

        @Override
        public void run() {
            for (int i = startIndex; i < endIndex; i++) {
                result += arr[i];
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int[] arr1 = generateArray(ARRAY_SIZE);

        // Single-threaded sum
        long startTime = System.currentTimeMillis();
        long result1 = sum(arr1);
        long elapsedTime1 = System.currentTimeMillis() - startTime;
        System.out.println("Elapsed time with one thread: " + elapsedTime1 + "ms");
        System.out.println("Result with one thread: " + result1);

        // Multi-threaded sum
        startTime = System.currentTimeMillis();

        SumThread[] sumThreads = new SumThread[THREAD_COUNT];
        Thread[] threads = new Thread[THREAD_COUNT];
        int chunkSize = ARRAY_SIZE / THREAD_COUNT;

        for (int i = 0; i < THREAD_COUNT; i++) {
            int startIndex = i * chunkSize;
            int endIndex = (i == THREAD_COUNT - 1) ? ARRAY_SIZE : startIndex + chunkSize;
            sumThreads[i] = new SumThread(arr1, startIndex, endIndex);
            threads[i] = new Thread(sumThreads[i]);
            threads[i].start();
        }

        long result2 = 0;
        for (int i = 0; i < THREAD_COUNT; i++) {
            threads[i].join();
            result2 += sumThreads[i].getResult();
        }

        long elapsedTime2 = System.currentTimeMillis() - startTime;
        System.out.println("Elapsed time with " + THREAD_COUNT + " threads: " + elapsedTime2 + "ms");
        System.out.println("Result with " + THREAD_COUNT + " threads: " + result2);

        // Multi-threaded sum using available processors
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        startTime = System.currentTimeMillis();

        sumThreads = new SumThread[availableProcessors];
        threads = new Thread[availableProcessors];
        chunkSize = ARRAY_SIZE / availableProcessors;

        for (int i = 0; i < availableProcessors; i++) {
            int startIndex = i * chunkSize;
            int endIndex = (i == availableProcessors - 1) ? ARRAY_SIZE : startIndex + chunkSize;
            sumThreads[i] = new SumThread(arr1, startIndex, endIndex);
            threads[i] = new Thread(sumThreads[i]);
            threads[i].start();
        }

        long result3 = 0;
        for (int i = 0; i < availableProcessors; i++) {
            threads[i].join();
            result3 += sumThreads[i].getResult();
        }

        long elapsedTime3 = System.currentTimeMillis() - startTime;
        System.out.println("Elapsed time with " + availableProcessors + " threads: " + elapsedTime3 + "ms");
        System.out.println("Result with " + availableProcessors + " threads: " + result3);
    }
}
