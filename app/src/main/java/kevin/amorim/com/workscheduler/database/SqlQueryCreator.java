package kevin.amorim.com.workscheduler.database;

import android.util.Log;

import java.util.Map;

public final class SqlQueryCreator {

    public static String createTable(String tableName, Map<String, String> values) {

        String query = "CREATE TABLE " + tableName + " (";

        boolean first = true;
        for(Map.Entry<String, String> entry : values.entrySet()) {

            if(!first) {
                query += ", ";
            } else {
                first = false;
            }

            String valueName = entry.getKey();
            String valueType = entry.getValue();

            query += valueName + " " + valueType;
        }

        query += ");";

        Log.e("SqlQueryCreator", query);

        return query;
    }

    public static String dropTable(String tableName) {
        return "DROP TABLE IF EXISTS " + tableName;
    }

    public static String selectAllFrom(String tableName) {
        return "SELECT * FROM " + tableName;
    }

    public static String selectAllFromById(String tableName, String idFieldName, int id) { return "SELECT * FROM " + tableName + " WHERE " + idFieldName + " = " + id; }

    public static String selectAllFromByStringParameter(String tableName, String fieldName, String fieldValue) {

        String query = "SELECT * FROM " + tableName + " WHERE " + fieldName + " = '" + fieldValue + "'";

        Log.e("SqlQueryCreator", query);
        return query;

    }
}
