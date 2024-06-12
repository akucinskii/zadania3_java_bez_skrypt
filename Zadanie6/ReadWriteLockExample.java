package Zadanie6;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockExample {

    private static ReadWriteLock rwLock = new ReentrantReadWriteLock(); // Tworzy obiekt klasy ReadWriteLock

    public static void main(String[] args) {
        Thread thread1 = new Thread(new Reader());
        Thread thread2 = new Thread(new Reader());
        Thread thread3 = new Thread(new Writer());
        thread1.start();
        thread2.start();
        thread3.start();
    }

    static class Reader implements Runnable {
        public void run() {
            try {
                rwLock.readLock().lock(); // Wątek próbuje uzyskać dostęp do sekcji krytycznej do odczytu
                System.out
                        .println(Thread.currentThread().getName() + " uzyskał dostęp do sekcji krytycznej do odczytu");
                Thread.sleep(1000); // Emuluje działanie sekcji krytycznej
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                rwLock.readLock().unlock(); // Wątek zwalnia dostęp do sekcji krytycznej do odczytu
                System.out
                        .println(Thread.currentThread().getName() + " zwolnił dostęp do sekcji krytycznej do odczytu");
            }
        }
    }

    static class Writer implements Runnable {
        public void run() {
            try {
                rwLock.writeLock().lock(); // Wątek próbuje uzyskać dostęp do sekcji krytycznej do zapisu
                System.out.println(Thread.currentThread().getName() + " uzyskał dostęp do sekcji krytycznej do zapisu");
                Thread.sleep(1000); // Emuluje działanie sekcji krytycznej
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                rwLock.writeLock().unlock(); // Wątek zwalnia dostęp do sekcji krytycznej do zapisu
                System.out.println(Thread.currentThread().getName() + " zwolnił dostęp do sekcji krytycznej do zapisu");
            }
        }
    }
}
