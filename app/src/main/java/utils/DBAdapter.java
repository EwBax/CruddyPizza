package utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBAdapter {

    // Column keys
    public static final String KEY_ROWID = "_id";
    public static final String KEY_SIZE = "size";
    public static final String KEY_TOPPING = "topping";
    public static final String KEY_CUST_NAME = "customer_name";
    public static final String KEY_ORDER_DATE = "order_date";
    public static final String TAG = "DBAdapter";

    // DB info
    private static final String DATABASE_NAME = "CruddyPizza";
    private static final String DATABASE_TABLE = "'order'";
    private static final int DATABASE_VERSION = 1;

    // Query to create table & db
    private static final String DATABASE_CREATE =
            "CREATE TABLE 'order'" +
            "(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "size INTEGER NOT NULL," +
                "topping1 INTEGER," +
                "topping2 INTEGER," +
                "topping3 INTEGER," +
                "customer_name TEXT NOT NULL," +
                "order_date TEXT NOT NULL" +
            ");";


    private final DatabaseHelper DBHelper;
    private SQLiteDatabase db;


    public DBAdapter(Context context) {
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(DATABASE_CREATE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrade database from version " + oldVersion + " to " + newVersion
                    + ", which will destroy all old data.");
            db.execSQL("DROP TABLE IF EXISTS topping");
            onCreate(db);
        }

    } // End DatabaseHelper class


    // Opening the database
    public DBAdapter open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    // Closing the database
    public void close() {
        DBHelper.close();
    }

    // Inserting a new order into the database
    public long insertOrder(int size, int topping1, int topping2, int topping3, String customerName, String orderDate) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_SIZE, size);
        initialValues.put(KEY_TOPPING + "1", topping1);
        initialValues.put(KEY_TOPPING + "2", topping2);
        initialValues.put(KEY_TOPPING + "3", topping3);
        initialValues.put(KEY_CUST_NAME, customerName);
        initialValues.put(KEY_ORDER_DATE, orderDate);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    // Deleting a specific order
    public boolean deleteOrder(long orderID) {
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + orderID, null) > 0;
    }

    // Retrieving every order
    public Cursor getAllOrders() {
        return db.query(DATABASE_TABLE, new String[]{KEY_ROWID, KEY_SIZE, KEY_TOPPING + "1", KEY_TOPPING + "2", KEY_TOPPING + "3", KEY_CUST_NAME, KEY_ORDER_DATE}, null, null, null, null, KEY_ROWID + " DESC");
    }

    // Updating a specific order
    // Does not update order_date to keep original date/time of order
    public boolean updateOrder(int orderID, int size, int topping1, int topping2, int topping3, String customerName) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_SIZE, size);
        contentValues.put(KEY_TOPPING + "1", topping1);
        contentValues.put(KEY_TOPPING + "2", topping2);
        contentValues.put(KEY_TOPPING + "3", topping3);
        contentValues.put(KEY_CUST_NAME, customerName);
        return db.update(DATABASE_TABLE, contentValues, KEY_ROWID + "=" + orderID, null) > 0;
    }


}
