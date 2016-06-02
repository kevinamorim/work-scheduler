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

        int hourA = Integer.parseInt(timeA.substring(0, 2));
        int minuteA = Integer.parseInt(timeA.substring(3, 5));

        int hourB = Integer.parseInt(timeB.substring(0, 2));
        int minuteB = Integer.parseInt(timeB.substring(3, 5));

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
}
