package kevin.amorim.com.workscheduler.database;


public final class SqlDataTypes {

    public static final String TEXT = "TEXT";
    public static final String INTEGER = "INTEGER";
    public static final String PRIMARY_KEY = "INTEGER PRIMARY KEY";

    public static final String createForeignKeyConstraint(String foreignKeyFieldName, String foreignTableName, String primaryKeyFieldName) {
        return "FOREIGN KEY(" + foreignKeyFieldName + ") REFERENCES " + foreignTableName + "(" + primaryKeyFieldName + ")";
    }

}
