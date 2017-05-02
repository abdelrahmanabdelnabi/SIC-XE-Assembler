package sicXE.assembler;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by ahmed on 4/9/17.
 */

// This Class is just used for logging and error messages
public class Logger {
    private static final Logger logger = new Logger();
    private static StringBuilder logString;
    private static int errorsCnt = 0;
    private final SimpleDateFormat simpleDateFormat;

    private Logger() {
        simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd_HH/mm");
        logString = new StringBuilder();
    }

    // Singleton pattern, Log
    public static void Log(String message) {
        logger.LogMessage(message);
    }

    // Signlton pattern, to Log Error
    public static void LogError(String message) {
        errorsCnt++;
        logger.LogMessage(message);
    }

    public static String getLogString() {
        logger.LogMessage(errorsCnt + " Errors Found, TERMINATED");
        return logString.toString();
    }

    public static int getErrorsCnt() {
        return errorsCnt;
    }


    // console logging
    private void LogMessage(String message) {
        System.out.println(message);
        String timeStamp = simpleDateFormat.format(Calendar.getInstance().getTime());
        logString.append(String.format("%-20s    %s", timeStamp, message));
        logString.append("\n");
    }
}
