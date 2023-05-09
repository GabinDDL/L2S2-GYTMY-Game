package com.gytmy.utils;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadedQueue {
    private static final int MAX_THREADS = 12;
    private static final ExecutorService executor = Executors.newFixedThreadPool(MAX_THREADS, new ThreadFactory() {
        private final AtomicInteger threadCount = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
            int threadNumber = threadCount.incrementAndGet();
            Thread thread = new Thread(r, "ThreadedQueue-" + threadNumber);
            return thread;
        }
    });

    private static final Object lock = new Object();
    private static int taskCount = 0;

    public static void initialize() {
        taskCount = 0;
    }

    public static boolean isThereATaskRunning() {
        synchronized (lock) {
            return taskCount > 0;
        }
    }

    public static void executeTask(Runnable task) {
        synchronized (lock) {
            Runnable wrappedTask = new Runnable() {
                @Override
                public void run() {
                    try {
                        task.run();
                    } finally {
                        synchronized (lock) {
                            taskCount--;
                        }
                    }
                }
            };
            executor.execute(wrappedTask);
            taskCount++;
        }
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

    public static void shutdownWhenIdle() {
        Thread thread = new Thread(() -> {
            while (true) {
                synchronized (lock) {
                    if (taskCount == 0) {
                        shutdown();
                        break;
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "ThreadedQueue-ShutdownThread");
        thread.setDaemon(true);
        thread.start();
    }
}
