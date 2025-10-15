import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NetworkClient {

    private static final Logger logger = Logger.getLogger(NetworkClient.class.getName());

    private static final Map<String, String> cache = new ConcurrentHashMap<>();
    private static final ThreadLocal<String> requestCtx = new ThreadLocal<>();

    /**
     * Fetch data from a URL using HTTP GET
     */
    public String fetchData(String urlStr) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000); // 5 seconds
            conn.setReadTimeout(5000);

            try (InputStream in = conn.getInputStream();
                 BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {

                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line).append('\n');
                }
                return sb.toString().trim();
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "Failed to fetch data from " + urlStr, e);
            return null;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    /**
     * Post data to a URL using HTTP POST
     */
    public boolean postData(String urlStr, String payload, String authToken) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");
            if (authToken != null && !authToken.isEmpty()) {
                conn.setRequestProperty("Authorization", "Bearer " + authToken);
            }

            logger.info("Posting data to URL");

            byte[] payloadBytes = payload.getBytes(StandardCharsets.UTF_8);
            try (OutputStream out = conn.getOutputStream()) {
                out.write(payloadBytes);
                out.flush();
            }

            int responseCode = conn.getResponseCode();
            return responseCode >= 200 && responseCode < 300;

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to post data to " + urlStr, e);
            return false;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    /**
     * Start threads to fetch data from multiple URLs concurrently
     */
    public void startRequestThreads(List<String> urls) {
        for (String u : urls) {
            Thread t = new Thread(() -> {
                requestCtx.set("req-" + Thread.currentThread().getId());
                try {
                    String data = fetchData(u);
                    if (data != null) {
                        cache.put(u, data);
                    }
                } catch (Exception ex) {
                    logger.log(Level.WARNING, "Error fetching data in thread for " + u, ex);
                } finally {
                    requestCtx.remove(); 
                }
            });
            t.start();
        }
    }

    /**
     * Get cached value or fetch and cache it
     */
    public String getCached(String url) {
        return cache.computeIfAbsent(url, this::fetchData);
    }
}
