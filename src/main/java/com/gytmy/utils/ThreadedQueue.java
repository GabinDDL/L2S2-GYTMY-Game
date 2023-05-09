package com.gytmy.utils;

import java.util.concurrent.*;

public class ThreadedQueue {
    private static final int MAX_THREADS = 10;
    private static final int MAX_QUEUE_SIZE = 1000;
    private static final ExecutorService executor = Executors.newFixedThreadPool(MAX_THREADS);
    private static final BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(MAX_QUEUE_SIZE);
    private static Boolean isRunning = false;

    public static void add(Runnable runnable) {
        try {
            queue.put(runnable);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void start() {
        isRunning = true;

        while (isRunning) {
            try {
                Runnable task = queue.take();
                executor.execute(task);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void stop() {
        isRunning = false;
    }
}



