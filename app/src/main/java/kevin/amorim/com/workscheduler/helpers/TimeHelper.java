package kevin.amorim.com.workscheduler.helpers;

import android.util.Log;

public class TimeHelper {

    private static final String TAG = "TimeHelper";

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

    public static boolean isTimeLessThan(String timeA, String timeB) {
        boolean result = false;

        int hourA = getHourFromTimeString(timeA);
        int minuteA = getMinuteFromTimeString(timeA);

        int hourB = getHourFromTimeString(timeB);
        int minuteB = getMinuteFromTimeString(timeB);

        if(hourA < hourB) {
            result = true;
        } else if(hourA == hourB) {
            if(minuteA < minuteB) {
                result = true;
            }
        }

        Log.e(TAG, "A:" + hourA + ":" + minuteA + " vs. " + "B: " + hourB + ":" + minuteB + "(" + result + ")");

        return result;
    }

    public static int countHoursBetween(String timeA, String timeB) {
        int startTotalMinutes = getHourFromTimeString(timeA) * 60 + getMinuteFromTimeString(timeA);
        int endTotalMinutes = getHourFromTimeString(timeB) * 60 + getMinuteFromTimeString(timeB);

        int totalMinutes = endTotalMinutes - startTotalMinutes;


        return Math.round((float)(totalMinutes/60.0));
    }

    private static int getHourFromTimeString(String time) {
        return Integer.parseInt(time.substring(0, 2));
    }

    private static int getMinuteFromTimeString(String time) {
        return Integer.parseInt(time.substring(3, 5));
    }
}
