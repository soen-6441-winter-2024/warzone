package ca.concordia.app.warzone.logging;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The LoggingService class provides methods for logging messages to a file.
 */
public class LoggingService {
    /**
     * Constructor
     */
    public LoggingService() {}
    private static final String LOG_FILE_NAME = "src/main/resources/warzone.log";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static LogEntryBuffer s_LogEntryBuffer;

    /**
     * Adds a log entry to the LogEntryBuffer.
     *
     * @param p_LogText the log text to be written
     */
    public static void log(String p_LogText) {
        s_LogEntryBuffer = new LogEntryBuffer();
        s_LogEntryBuffer.addObserver(new LogWriter(LOG_FILE_NAME));
        String l_ParentClassName = "";
        String l_ParentMethodName = "";
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace.length >= 3) {
            // Index 0 is getStackTrace, index 1 is this method, index 2 is the caller's method
            l_ParentClassName = stackTrace[2].getClassName();
            l_ParentMethodName = stackTrace[2].getMethodName();
        } else {
            System.out.println("Unable to determine parent class and method.");
        }

        String l_Timestamp = LocalDateTime.now().format(DATE_TIME_FORMATTER);
        String l_FormattedLog = l_Timestamp + " - " + l_ParentClassName + " - " + l_ParentMethodName + " - " + p_LogText;
        s_LogEntryBuffer.addEntry(l_FormattedLog);
    }
}
