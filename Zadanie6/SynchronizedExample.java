package Zadanie6;

public class SynchronizedExample {

    private static Object lock = new Object(); // Tworzy obiekt klasy Object

    public static void main(String[] args) {
        Thread thread1 = new Thread(new Task());
        Thread thread2 = new Thread(new Task());
        thread1.start();
        thread2.start();
    }

    static class Task implements Runnable {
        public void run() {
            synchronized (lock) { // Wątek próbuje uzyskać dostęp do sekcji krytycznej
                System.out.println(Thread.currentThread().getName() + " uzyskał dostęp do sekcji krytycznej");
                try {
                    Thread.sleep(1000); // Emuluje działanie sekcji krytycznej
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " zwolnił dostęp do sekcji krytycznej");
            }
        }
    }
}