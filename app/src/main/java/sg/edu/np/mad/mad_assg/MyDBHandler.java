package sg.edu.np.mad.mad_assg;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHandler extends SQLiteOpenHelper {

    public static int DATABASE_VERSION = 1;
    public static String DATABASE_NAME = "accounts.db";
    public static String ACCOUNTS = "Accounts";
    public static String COLUMN_USERNAME = "UserName";
    public static String COLUMN_PASSWORD = "Password";

    public static String COLUMN_EMAIL = "Email";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_ACCOUNTS_TABLE = "CREATE TABLE " + ACCOUNTS + "(" + COLUMN_USERNAME + "TEXT," + COLUMN_PASSWORD + " TEXT," + COLUMN_EMAIL + " TEXT)";
        db.execSQL(CREATE_ACCOUNTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + ACCOUNTS);
        onCreate(db);
    }

    public void addUser(UserData userDate){
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, userDate.getUsername());
        values.put(COLUMN_PASSWORD, userDate.getPassword());
        values.put(COLUMN_EMAIL, userDate.getEmail());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(ACCOUNTS,null, values);
        db.close();
    }

    public UserData findUser(String username){
        String query = "SELECT * FROM " + ACCOUNTS + " WHERE " + COLUMN_USERNAME + "= \"" + username + " \"";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        UserData queryResult = new UserData();

        if (cursor.moveToFirst()){
            queryResult.setUsername(cursor.getColumnName(0));
            queryResult.setPassword(cursor.getColumnName(1));
            queryResult.setEmail(cursor.getColumnName(2));
            cursor.close();
        }
        else{
            queryResult=null;
        }
        db.close();
        return queryResult;
    }
}
