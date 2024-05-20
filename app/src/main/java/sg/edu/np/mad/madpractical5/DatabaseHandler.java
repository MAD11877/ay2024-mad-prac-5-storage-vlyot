package sg.edu.np.mad.madpractical5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Random;
import android.database.sqlite.SQLiteException;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "users.db";
    private static final String USERS = "Users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESC = "description";
    private static final String COLUMN_FOLLOW = "followed";

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){

        Log.i("Database Operations", "Creating a Table.");
        try {
            String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS " + USERS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME + " TEXT,"
                    + COLUMN_DESC + " TEXT," + COLUMN_FOLLOW + " INTEGER" + ")";
            db.execSQL(CREATE_USERS_TABLE);

            // Generate 20 Random Users
            for (int i = 1; i <= 20; i++) {
                String nameRandom = String.valueOf((int) Math.round(Math.random() * 1000000));
                String descRandom = String.valueOf((int) Math.round(Math.random() * 100000000));

                String name = "Name" + nameRandom;
                String desc = "Description " + descRandom;
                String id = String.valueOf(i);
                int temp = new Random().nextInt(2);
                String followed = "false";
                if (temp == 1)
                    followed = "true";

                ContentValues values = new ContentValues();
//              Don't need this. ID is auto create and incremented by SQLite
//              values.put(COLUMN_ID, id);
                values.put(COLUMN_NAME, name);
                values.put(COLUMN_DESC, desc);
                values.put(COLUMN_FOLLOW, followed);
                db.insert(USERS, null, values);
            }
            Log.i("Database Operations", "Table created successfully.");
//          db.close();
        } catch (SQLiteException e) {
            Log.i("Database Operations", "Error creating table", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + USERS);
        onCreate(db);
    }

    // READ all user records
    public ArrayList<User> getUsers() {
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<User> userList = new ArrayList<>();
        String query = "SELECT * FROM " + USERS;
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt((int)cursor.getColumnIndex("id"));
            String name = cursor.getString((int)cursor.getColumnIndex("name"));
            String description = cursor.getString((int)cursor.getColumnIndex("description"));
            boolean follow = Boolean.parseBoolean(cursor.getString((int)cursor.getColumnIndex("followed")));
            User user = new User(name, description, id, follow);
            userList.add(user);
        }
        cursor.close();
//      db.close();
        return userList;
    }

    //Get 1 user record based on unique user id.
    public User getUser(int user_id) {
        SQLiteDatabase db = getReadableDatabase();
        User user = new User("John Doe", "MAD Developer", 1, false);
        Cursor cursor = db.query(USERS, new String[] { COLUMN_ID,
                        COLUMN_NAME, COLUMN_DESC,  COLUMN_FOLLOW}, COLUMN_ID + "=?",
                new String[] { String.valueOf(user_id) }, null, null, null, null);

        if (cursor != null)
        {
            cursor.moveToFirst();
            int id = cursor.getInt((int)cursor.getColumnIndex("id"));
            String name = cursor.getString((int)cursor.getColumnIndex("name"));
            String description = cursor.getString((int)cursor.getColumnIndex("description"));
            boolean follow = Boolean.parseBoolean(cursor.getString((int)cursor.getColumnIndex("followed")));
            user.setName(name);
            user.setDescription(description);
            user.setId(id);
            user.setFollowed(follow);
            cursor.close();
        }
//      db.close();
        return user;
    }

    // UPDATE user record
    public void updateUser(User user){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, String.valueOf(user.getId()));
        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_DESC, user.getDescription());
        values.put(COLUMN_FOLLOW, String.valueOf(user.getFollowed()));
        String clause = "id=?";
        String[] args = {String.valueOf(user.getId())};
        db.update(USERS, values, clause, args);
//      db.close();
    }

    public void deleteAllEntries() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM entries");
//      db.close();
    }

    @Override
    public void close() {
        Log.i("Database Operations", "Database is closed.");
        super.close();
    }
}
