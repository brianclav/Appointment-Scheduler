package utility;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * UserLogger Class and methods.
 *
 * It contains the logic for logging user login attempts.
 *
 * @author Brian Clavadetscher
 */
public class UserLogger {
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String loggerFilename = "login_activity.txt";

    /**
     * Tracks the user login by writing to an external txt file. Text written depends on login success.
     *
     * @param user The username entered on the form.
     * @param success Whether the login attempt was successful or not.
     * @throws IOException If the file exists but is a directory rather than a regular file, does not exist but cannot be created, or cannot be opened for any other reason.
     */
    public static void trackUserLogin(String user, Boolean success) throws IOException {
        String text;
        FileWriter fw = new FileWriter(loggerFilename, true);
        PrintWriter logger = new PrintWriter(fw);
        if (success) {
            text = "User " + user + " successfully logged in at " + dtf.format(TimeConverter.getTimeLocalizedToUTC(LocalDateTime.now())) + " UTC";
        } else {
            text = "User " + user + " gave an invalid log-in at " + dtf.format(TimeConverter.getTimeLocalizedToUTC(LocalDateTime.now())) + " UTC";
        }
        logger.println(text);
        logger.close();
    }
}
