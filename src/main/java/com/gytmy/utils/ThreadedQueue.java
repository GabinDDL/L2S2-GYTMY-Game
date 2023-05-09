package com.gytmy.utils;

import java.util.concurrent.*;

public class ThreadedQueue {
    private static final int MAX_THREADS = 10;
    private static final ExecutorService executor = Executors.newFixedThreadPool(MAX_THREADS);

    public static void initialize() {

    }

    public static void executeTask(Runnable task) {
        executor.execute(task);
    }

    public static void shutdown() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(30, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }
}