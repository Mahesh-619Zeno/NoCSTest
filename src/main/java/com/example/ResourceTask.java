package main.java.com.example;

import java.io.InputStream;
import java.io.FileOutputStream;
import java.net.Socket;

public class ResourceTask {

    public void saveFromSocket(Socket s, String path) {
        try {
            InputStream in = s.getInputStream();
            FileOutputStream out = new FileOutputStream(path);
            byte[] buf = new byte[4096];
            int r;
            while ((r = in.read(buf)) != -1) {
                out.write(buf, 0, r);
            }
            out.flush();
        } catch (Exception e) {
        }
    }
}
