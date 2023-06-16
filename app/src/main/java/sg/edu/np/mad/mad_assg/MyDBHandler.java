package sg.edu.np.mad.mad_assg;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHandler extends SQLiteOpenHelper {

    public static int DATABASE_VERSION = 1;
    public static String DATABASE_NAME = "account.db";
    public static String ACCOUNTS = "User";
    public static String COLUMN_USERNAME = "UserName";
    public static String COLUMN_PASSWORD = "Password";
    public static String COLUMN_EMAIL = "Email";

    public static String RECIPE = "Recipe";

    public static String COLUMN_CATEGORY = "Category";

    public static String COLUMN_NAME = "Name";

    public static String COLUMN_INGREDIENTS = "Ingredients";

    public static String COLUMN_DESCRIPTION = "Description";

    public static String COLUMN_STEPS = "Steps";

    public static String COLUMN_USER = "Publisher";
    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        /*String CREATE_RECIPE_TABLE = "CREATE TABLE " + RECIPE + "(" + COLUMN_NAME + " TEXT," + COLUMN_CATEGORY + " TEXT," + COLUMN_USER + " TEXT," + COLUMN_DESCRIPTION + " TEXT," + COLUMN_INGREDIENTS + " TEXT," + COLUMN_STEPS + " TEXT)";  */
        /*db.execSQL(CREATE_RECIPE_TABLE);*/
        String CREATE_ACCOUNTS_TABLE = "CREATE TABLE " + ACCOUNTS + "(" + COLUMN_USERNAME + " TEXT," + COLUMN_PASSWORD + " TEXT," + COLUMN_EMAIL + " TEXT)";
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

    //registration check function
    public boolean user_IsUsernameFree(String userName) {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {COLUMN_USERNAME};
        String selection = COLUMN_USERNAME + "=?";
        String[] selectionArgs = {userName};
        Cursor cursor = db.query(ACCOUNTS, columns, selection, selectionArgs, null, null, null);
        boolean isUsernameFree = cursor.getCount() == 0;
        cursor.close();
        return isUsernameFree;
    }

    public boolean user_IsEmailFree(String email) {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {COLUMN_EMAIL};
        String selection = COLUMN_EMAIL + "=?";
        String[] selectionArgs = {email};
        Cursor cursor = db.query(ACCOUNTS, columns, selection, selectionArgs, null, null, null);
        boolean isEmailFree = cursor.getCount() == 0;
        cursor.close();
        return isEmailFree;
    }

    //login function
   /* public boolean user_Login(String userName, String password)
    {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + ACCOUNTS + " WHERE " + COLUMN_USERNAME +  "= \"" + userName + " \" " + " AND " + COLUMN_PASSWORD + "= \"" + password + " \" ", null);
        if (cursor != null)
        {
            while (cursor.moveToNext()) {
                return true;
            }
        }
        cursor.close();
        db.close();
        return false;
    }*/
    public boolean user_checkUsername(String userName) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + ACCOUNTS + " WHERE " + COLUMN_USERNAME + "= ?", new String[]{userName});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }


    public boolean user_checkPassword(String userName, String password) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + ACCOUNTS + " WHERE " + COLUMN_USERNAME + "= ? AND " + COLUMN_PASSWORD + "= ?", new String[]{userName, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }
    public UserData getUserName(String username){
        String query = "SELECT * FROM " + ACCOUNTS + " WHERE " + COLUMN_USERNAME + "= \"" + username + "\"";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        UserData queryResult = new UserData();

        if (cursor.moveToFirst()){
            queryResult.setUsername(cursor.getColumnName(0));
            cursor.close();
        }
        else{
            queryResult=null;
        }
        db.close();
        return queryResult;
    }

    @SuppressLint("Range")
    public String getUsername() {
        String query = "SELECT " + COLUMN_USERNAME + " FROM " + ACCOUNTS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        String username = null;

        if (cursor.moveToFirst()) {
            username = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME));
        }

        cursor.close();
        db.close();

        return username;
    }

}