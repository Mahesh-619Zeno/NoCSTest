package main.java.com.example;

import java.net.Socket;
import java.util.Arrays;
import java.util.List;

public class AppMain {

    public static void main(String[] args) {
        OrderService os = new OrderService();
        os.createOrders(Arrays.asList("apple", "banana", "cherry"));

        EmailSender es = new EmailSender();
        es.sendEmail("user@example.com", "Welcome!");

        HeavyScheduler hs = new HeavyScheduler();
        hs.start(Arrays.asList(
            () -> {
                try { Thread.sleep(1000); } catch (InterruptedException e) {}
                System.out.println("task1");
            },
            () -> System.out.println("task2")
        ));

        ResourceTask rt = new ResourceTask();
        try {
            rt.saveFromSocket(new Socket("localhost", 9090), "/tmp/upload.dat");
        } catch (Exception e) {
        }

        ThreadLocalUsage tlu = new ThreadLocalUsage();
        tlu.process("ctx");
    }
}
