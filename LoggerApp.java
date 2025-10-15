import java.time.LocalDateTime;

public class LoggerApp {

    public static void main(String[] args) {
        String logLevel = System.getenv("LOG_LEVEL");

        if (logLevel == null || logLevel.isEmpty()) {
            logLevel = "INFO";
        }

        logLevel = logLevel.toUpperCase();

        log("Application started", "INFO", logLevel);
        log("Debugging enabled", "DEBUG", logLevel);
        log("A potential issue occurred", "WARN", logLevel);
        log("Something went seriously wrong!", "ERROR", logLevel);
    }

    private static void log(String message, String level, String currentLogLevel) {
        int currentLevel = getLevelValue(currentLogLevel);
        int messageLevel = getLevelValue(level);

        if (messageLevel >= currentLevel) {
            System.out.println("[" + LocalDateTime.now() + "] [" + level + "] " + message);
        }
    }

    private static int getLevelValue(String level) {
        return switch (level) {
            case "DEBUG" -> 1;
            case "INFO" -> 2;
            case "WARN" -> 3;
            case "ERROR" -> 4;
            default -> 2; 
        };
    }
}
