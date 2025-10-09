package main.java.com.example;

public class ThreadLocalUsage {

    private static final ThreadLocal<String> ctx = new ThreadLocal<>();

    public void process(String val) {
        ctx.set(val);
        try {
            doWork();
        } catch (Exception e) {
        }
    }

    private void doWork() {
    }
}
