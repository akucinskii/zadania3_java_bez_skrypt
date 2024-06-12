package Zadanie6;

import java.util.concurrent.Semaphore;

public class SemaphoreExample {
    private static Semaphore semaphore = new Semaphore(1); // Tworzy semafor z jednym zasobem

    public static void main(String[] args) {
        Thread thread1 = new Thread(new Task());
        Thread thread2 = new Thread(new Task());
        thread1.start();
        thread2.start();
    }

    static class Task implements Runnable {
        public void run() {
            try {
                semaphore.acquire(); // Wątek próbuje uzyskać dostęp do semafora
                System.out.println(Thread.currentThread().getName() + " uzyskał dostęp do sekcji krytycznej");
                Thread.sleep(1000); // Emuluje działanie sekcji krytycznej
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release(); // Wątek zwalnia semafor
                System.out.println(Thread.currentThread().getName() + " zwolnił semafor");
            }
        }
    }
}