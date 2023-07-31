package utility;

import java.time.*;

/**
 * TimeConverter Class and methods.
 *
 * It contains the logic for converting time from and to different timezones.
 *
 * @author Brian Clavadetscher
 */
public class TimeConverter {

    /**
     * Converts time from EST to the localized timezone.
     *
     * @param timeEST The time in EST.
     * @return The time in the localized timezone.
     */
    public static LocalTime getTimeESTToLocalized(LocalTime timeEST) {
        //get the date(unimportant) and time in EST to be formatted
        LocalDateTime timeDateEST = LocalDateTime.of(LocalDate.now(), timeEST);
        //add the EST zone to the imported time
        ZonedDateTime zonedTime = timeDateEST.atZone(ZoneId.of("US/Eastern"));
        //get the time equivalent in the localized time zone of the time in EST
        LocalTime localTime = zonedTime.withZoneSameInstant(ZoneId.systemDefault()).toLocalTime();

        return localTime;
    }

    /**
     * Converts the time in the localized timezone to EST.
     *
     * @param timeLocal The time in localized time zone.
     * @return The time in EST.
     */
    public static LocalTime getTimeLocalizedToEST(LocalTime timeLocal) {
        LocalDateTime timeDateLocal = LocalDateTime.of(LocalDate.now(), timeLocal);
        ZonedDateTime zonedTime = timeDateLocal.atZone(ZoneId.systemDefault());
        LocalTime timeEST = zonedTime.withZoneSameInstant(ZoneId.of("US/Eastern")).toLocalTime();

        return timeEST;
    }

    /**
     * Converts the time in the localized time zone to UTC.
     *
     * @param timeDateLocal The time in the localized time zone.
     * @return The time in UTC.
     */
    public static LocalDateTime getTimeLocalizedToUTC(LocalDateTime timeDateLocal) {
        //add the system default zone to the imported localized time
        ZonedDateTime zonedTime = timeDateLocal.atZone(ZoneId.systemDefault());
        //get the time equivalent in UTC of the time in localized timezone
        LocalDateTime timeUTC = zonedTime.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();

        return timeUTC;
    }
}
