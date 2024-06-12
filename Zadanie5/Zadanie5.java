package Zadanie5;

import java.util.concurrent.atomic.AtomicInteger;

public class Zadanie5 {

    // volatile example
    private static volatile boolean flag = false;

    // Atomic example
    private static AtomicInteger counter = new AtomicInteger(0);

    // ThreadLocal example
    private static ThreadLocal<Integer> threadLocalValue = ThreadLocal.withInitial(() -> 0);

    public static void main(String[] args) throws InterruptedException {
        // Volatile example threads
        Thread volatileThread1 = new Thread(() -> {
            while (!flag) {
                // Busy-wait until flag becomes true
            }
            System.out.println("Volatile: Flag is true!");
        });

        Thread volatileThread2 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            flag = true;
            System.out.println("Volatile: Flag set to true");
        });

        // Atomic example threads
        Runnable atomicTask = () -> {
            for (int i = 0; i < 1000; i++) {
                counter.incrementAndGet();
            }
        };

        Thread atomicThread1 = new Thread(atomicTask);
        Thread atomicThread2 = new Thread(atomicTask);

        // ThreadLocal example threads
        Runnable threadLocalTask = () -> {
            int initialValue = threadLocalValue.get();
            threadLocalValue.set(initialValue + 1);
            System.out.println(Thread.currentThread().getName() + " ThreadLocal Value: " + threadLocalValue.get());
        };

        Thread threadLocalThread1 = new Thread(threadLocalTask);
        Thread threadLocalThread2 = new Thread(threadLocalTask);

        // Start volatile example threads
        volatileThread1.start();
        volatileThread2.start();
        volatileThread1.join();
        volatileThread2.join();

        // Start atomic example threads
        atomicThread1.start();
        atomicThread2.start();
        atomicThread1.join();
        atomicThread2.join();
        System.out.println("Atomic Counter: " + counter.get());

        // Start ThreadLocal example threads
        threadLocalThread1.start();
        threadLocalThread2.start();
        threadLocalThread1.join();
        threadLocalThread2.join();
    }

}
