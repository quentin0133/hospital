package fr.cfa.hospital.core.tools;

import org.slf4j.Logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogUtils {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void logEnter(Logger logger, Object object) {
        String objectString = (object != null) ? object.toString() : "null";
        logEnter(logger, objectString);
    }

    public static void logEnter(Logger logger, String text) {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        logger.info("[ENTER] [{}] - {}", timestamp, text);
    }

    public static void logExit(Logger logger, Object object) {
        String objectString = (object != null) ? object.toString() : "null";
        logExit(logger, objectString);
    }

    public static void logExit(Logger logger, String text) {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        logger.info("[EXIT]  [{}] - {}", timestamp, text);
    }
}
