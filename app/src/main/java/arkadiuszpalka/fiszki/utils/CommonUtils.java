package arkadiuszpalka.fiszki.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Locale;

/**
 * Created by Arkadiusz Pałka on 03.05.2018.
 */

public final class CommonUtils {

    private CommonUtils() {}

    public static String formatApiDate(String inputDate) {
        final String RETURN_FORMAT = "dd.MM.yyyy HH:mm:ss";

        DateTime dt = new DateTime(inputDate);
        DateTimeFormatter fmt = DateTimeFormat.forPattern(RETURN_FORMAT).withLocale(Locale.getDefault());

        return fmt.print(dt);
    }
}
