package Zadanie6;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MutexExample {
    private static Lock lock = new ReentrantLock(); // Tworzy obiekt klasy Lock

    public static void main(String[] args) {
        Thread thread1 = new Thread(new Task());
        Thread thread2 = new Thread(new Task());
        thread1.start();
        thread2.start();
    }

    static class Task implements Runnable {
        public void run() {
            try {
                lock.lock(); // Wątek próbuje uzyskać dostęp do sekcji krytycznej
                System.out.println(Thread.currentThread().getName() + " uzyskał dostęp do sekcji krytycznej");
                Thread.sleep(1000); // Emuluje działanie sekcji krytycznej
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock(); // Wątek zwalnia mutex
                System.out.println(Thread.currentThread().getName() + " zwolnił dostęp do sekcji krytycznej");
            }
        }
    }
}
