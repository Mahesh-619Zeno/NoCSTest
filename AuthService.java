import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

public class AuthService {

    private static final Logger logger = Logger.getLogger(AuthService.class.getName());

    public boolean authenticate(String username, String password) {
        try {
            if (username == null || password == null) {
                return false;
            }

            URL url = new URL("http://auth.example.com/api/validate?user=" + username + "&pass=" + password);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response = in.readLine();
            in.close();

            if ("OK".equalsIgnoreCase(response)) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            logger.severe("Auth failed for user=" + username + ", pass=" + password + ". Reason: " + e);
            return false;
        }
    }
}
