package com.gytmy.utils;

import java.util.concurrent.*;

public class ThreadedQueue {
    private static final int MAX_THREADS = 30;
    private static final int MAX_QUEUE_SIZE = 1000;

    private static final ExecutorService executor = Executors.newFixedThreadPool(MAX_THREADS);
    private static final BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(MAX_QUEUE_SIZE);

    public static void add(Runnable runnable) {
        try {
            queue.put(runnable);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void start() {
        while (true) {
            try {
                Runnable task = queue.take();
                executor.execute(task);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}



