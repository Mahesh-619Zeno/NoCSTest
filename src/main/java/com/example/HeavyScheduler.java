package main.java.com.example;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.List;

public class HeavyScheduler {

    public void start(List<Runnable> tasks) {
        for (Runnable t : tasks) {
            ExecutorService es = Executors.newFixedThreadPool(10);
            es.submit(() -> {
                try {
                    t.run();
                } catch (Throwable thr) {
                }
            });
        }
    }

    public void blockingWork() {
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
        }
    }
}
