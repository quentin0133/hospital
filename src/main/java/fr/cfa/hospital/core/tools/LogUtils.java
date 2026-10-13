package fr.cfa.hospital.core.tools;

import org.slf4j.Logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class providing standardized method entry and exit logging.
 * Formats diagnostic messages with a consistent timestamp layout.
 */
public class LogUtils {

    /**
     * Timestamp formatter pattern applied to all entry and exit log lines.
     */
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Private constructor to prevent instantiation of static utility class.
     */
    private LogUtils() {
    }

    /**
     * Logs an entry event using the string representation of the given object.
     *
     * @param logger the SLF4J logger instance to emit the log event to
     * @param object the contextual object whose string representation will be logged
     */
    public static void logEnter(Logger logger, Object object) {
        String objectString = (object != null) ? object.toString() : "null";
        logEnter(logger, objectString);
    }

    /**
     * Logs an entry event with a timestamp and the provided descriptive message.
     *
     * @param logger the SLF4J logger instance to emit the log event to
     * @param text   the message detailing the entry context or method
     */
    public static void logEnter(Logger logger, String text) {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        logger.info("[ENTER] [{}] - {}", timestamp, text);
    }

    /**
     * Logs an exit event using the string representation of the given object.
     *
     * @param logger the SLF4J logger instance to emit the log event to
     * @param object the contextual object or return value to log
     */
    public static void logExit(Logger logger, Object object) {
        String objectString = (object != null) ? object.toString() : "null";
        logExit(logger, objectString);
    }

    /**
     * Logs an exit event with a timestamp and the provided descriptive message.
     *
     * @param logger the SLF4J logger instance to emit the log event to
     * @param text   the message detailing the exit context or result
     */
    public static void logExit(Logger logger, String text) {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        logger.info("[EXIT]  [{}] - {}", timestamp, text);
    }
}