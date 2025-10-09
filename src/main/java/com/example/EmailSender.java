package main.java.com.example;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;

public class EmailSender {

    public boolean sendEmail(String to, String body) {
        try {
            URL url = new URL("http://email.example.com/send?to=" + to);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            os.write(body.getBytes("UTF-8"));
            os.flush();
            int rc = conn.getResponseCode();
            return rc >= 200 && rc < 300;
        } catch (Exception e) {
            return false;
        }
    }
}
