package sg.edu.np.mad.mad_assg;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

public class MyDBHandler extends SQLiteOpenHelper {

    public static int DATABASE_VERSION = 1;
    public static String DATABASE_NAME = "account.db";
    public static String ACCOUNTS = "User";
    public static String COLUMN_USERNAME = "UserName";
    public static String COLUMN_PASSWORD = "Password";
    public static String COLUMN_EMAIL = "Email";

    public static String RECIPE = "Recipe";

    private static final String COLUMN_ID = "_id";

    public static String COLUMN_CATEGORY = "Category";

    public static String COLUMN_NAME = "Name";

    public static String COLUMN_INGREDIENTS = "Ingredients";

    public static String COLUMN_DESCRIPTION = "Description";

    public static String COLUMN_STEPS = "Steps";

    public static String COLUMN_USER = "Publisher";

    private static final String COLUMN_IMAGE_URL = "image_url";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the recipe table
        String CREATE_RECIPE_TABLE = "CREATE TABLE " + RECIPE + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME + " TEXT," + COLUMN_CATEGORY + " TEXT," + COLUMN_USERNAME + " TEXT," + COLUMN_USER + " TEXT," +COLUMN_DESCRIPTION + " TEXT," + COLUMN_INGREDIENTS + " TEXT," + COLUMN_STEPS + " TEXT," + COLUMN_IMAGE_URL + " TEXT" + ")";
        db.execSQL(CREATE_RECIPE_TABLE);
        String CREATE_ACCOUNTS_TABLE = "CREATE TABLE " + ACCOUNTS + "(" + COLUMN_USERNAME + " TEXT," + COLUMN_PASSWORD + " TEXT," + COLUMN_EMAIL + " TEXT)";
        db.execSQL(CREATE_ACCOUNTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ACCOUNTS);
        db.execSQL("DROP TABLE IF EXISTS " + RECIPE);
        onCreate(db);
    }

    public void addUser(UserData userDate) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, userDate.getUsername());
        values.put(COLUMN_PASSWORD, userDate.getPassword());
        values.put(COLUMN_EMAIL, userDate.getEmail());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(ACCOUNTS, null, values);
        db.close();
    }

    public boolean updatepassword(String username, String password) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("password", password);
        long result = db.update(ACCOUNTS, contentValues, "username = ?", new String[]{username});
        if (result == -1) return false;
        else
            return true;
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


    public boolean user_checkUsername(String userName) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + ACCOUNTS + " WHERE " + COLUMN_USERNAME + "= ?", new String[]{userName});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }

    public boolean user_checkEmail(String email) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + ACCOUNTS + " WHERE " + COLUMN_EMAIL + "= ?", new String[]{email});
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

    public void updateUser(UserData user) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, user.getUsername());
        values.put(COLUMN_EMAIL, user.getEmail());

        db.update(ACCOUNTS, values, COLUMN_USERNAME + " = ?",
                new String[]{String.valueOf(user.getUsername())});

        db.close();
    }

    public void deleteUser(long userId) {
        SQLiteDatabase db = getWritableDatabase();

        db.delete(ACCOUNTS, COLUMN_USERNAME + " = ?",
                new String[]{String.valueOf(userId)});

        db.close();
    }

    public long insertRecipe(RecipeList recipe) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, recipe.getRecipeName());
        values.put(COLUMN_CATEGORY, recipe.getCategory());
        values.put(COLUMN_USER, recipe.getUsername());
        values.put(COLUMN_DESCRIPTION, recipe.getDescription());
        values.put(COLUMN_INGREDIENTS, recipe.getIngredients());
        values.put(COLUMN_STEPS, recipe.getSteps());
        values.put(COLUMN_IMAGE_URL, recipe.getImageUrl());

        long id = db.insert(RECIPE, null, values);
        db.close();

        return id;
    }

    @SuppressLint("Range")
    public List<RecipeList> getAllRecipes() {
        List<RecipeList> recipes = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(RECIPE, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                RecipeList recipe = new RecipeList();
                recipe.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
                recipe.setRecipeName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                recipe.setCategory(cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY)));
                recipe.setUsername(cursor.getString(cursor.getColumnIndex(COLUMN_USER)));
                recipe.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
                recipe.setIngredients(cursor.getString(cursor.getColumnIndex(COLUMN_INGREDIENTS)));
                recipe.setSteps(cursor.getString(cursor.getColumnIndex(COLUMN_STEPS)));
                recipe.setImageUrl(cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE_URL)));

                recipes.add(recipe);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return recipes;
    }

    public RecipeList recipes_SelectByName(String name)
    {
        // Open available reading database
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM RECIPES WHERE recipeName = '" + name + "'", null);
        if (cursor != null)
        {
            while (cursor.moveToNext()) {
                RecipeList recipe = new RecipeList(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6)
                );
                cursor.close();
                db.close();
                return recipe;
            }


        }
        return  null;
    }

    public long insertCategory(CategoryData catgory) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_CATEGORY, CategoryData.get_category());
        values.put(COLUMN_IMAGE_URL, CategoryData.getImageUrl());

        long id = db.insert(RECIPE, null, values);
        db.close();

        return id;
    }


}

    /*public  void  Ingredients_Insert(String name, String ingreName)
    {
        // open read and write database
        SQLiteDatabase db = getWritableDatabase();
        // execute insert query
        db.execSQL("INSERT INTO INGREDIENTS VALUES(null, " + name + ", '" + ingreName + "');");

        db.close();
    }
    public ArrayList<RecipeList> recipes_SelectBest()
    {
        // Open available reading database
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<RecipeList> allRecipes = new ArrayList<>();
        // Get all recipes data
        Cursor cursor = db.rawQuery("SELECT * FROM RECIPES ORDER BY likeCount DESC LIMIT 3", null);
        if (cursor != null)
        {
            while (cursor.moveToNext()) {
                allRecipes.add(new RecipeList(
                        cursor.getColumnIndex(COLUMN_ID),
                        cursor.getColumnIndex(COLUMN_NAME),
                        cursor.getColumnIndex(COLUMN_CATEGORY),

                ));
            }
        }
        cursor.close();
        db.close();

        return allRecipes;
    }

    public ArrayList<RecipeList> recipes_SelectAll()
    {
        // Open available reading database
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<RecipeList> allRecipes = new ArrayList<>();
        // Get all recipes data
        Cursor cursor = db.rawQuery("SELECT * FROM RECIPES", null);
        if (cursor != null)
        {
            while (cursor.moveToNext()) {
                allRecipes.add(new RecipeList(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6)
                ));
            }
        }
        cursor.close();
        db.close();

        return allRecipes;
    }


    public RecipeList recipes_SelectById(int id)
    {
        // Open available reading database
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM RECIPES WHERE _id = " + id, null);
        if (cursor != null)
        {
            while (cursor.moveToNext()) {
                RecipeList recipe = new RecipeList(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6)
                );
                cursor.close();
                db.close();
                return recipe;
            }


        }
        return  null;
    }

}




//login function
   public boolean user_Login(String userName, String password)
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
    }

       public UserData getUserName(String username){
        String query = "SELECT * FROM " + ACCOUNTS + " WHERE " + COLUMN_USERNAME + "= \"" + username + "\"";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        UserData queryResult = new UserData();

        if (cursor.moveToFirst()){
            UserData.setUsername(cursor.getColumnName(0));
            cursor.close();
        }
        else{
            queryResult=null;
        }
        db.close();
        return queryResult;
    }
    */