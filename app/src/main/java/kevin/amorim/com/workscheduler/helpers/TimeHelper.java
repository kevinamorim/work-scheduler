package kevin.amorim.com.workscheduler.helpers;

public class TimeHelper {

    public static String formatTimeString(int hour, int minute) {
        String result = "";

        if(hour < 10) {
            result += "0" + hour;
        } else {
            result += hour;
        }

        result += ":";

        if(minute < 10) {
            result += "0" + minute;
        } else {
            result += minute;
        }

        return result;
    }
}
