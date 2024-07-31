package Singleton_Pattern;

public class Logger {
    // Private static instance of Logger
    private static Logger instance;

    // Private constructor to prevent instantiation
    private Logger() {
        // Private constructor to prevent instantiation
    }

    // Public static method to provide access to the instance
    public static Logger getInstance() {
        // Create a new instance if none exists
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    // Method to log messages
    public void log(String message) {
        System.out.println("Log: " + message);
    }
}

