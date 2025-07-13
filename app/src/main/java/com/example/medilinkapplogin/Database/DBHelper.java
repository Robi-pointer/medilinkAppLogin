package com.example.medilinkapplogin.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.medilinkapplogin.DBParameters.parameters;
import com.example.medilinkapplogin.user.userInfo;
import com.example.medilinkapplogin.userDataModel.dataModel;

public class DBHelper extends SQLiteOpenHelper {
    private Context context;
    public DBHelper(Context context) {
        super(context, parameters.DATABASE_NAME, null, parameters.DATABASE_VERSION);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "CREATE TABLE " + parameters.USER_INFO_TABLE + "(" +
                parameters.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                parameters.KEY_NAME + " TEXT, " +
                parameters.KEY_EMAIL + " TEXT, " +
                parameters.KEY_PASS + " TEXT, " +
                parameters.KEY_PHONE + " TEXT, " +
                parameters.KEY_BLOOD_GROUP + " TEXT, " +
                parameters.KEY_DIST + " TEXT, " +
                parameters.KEY_PROFESSION + " TEXT " + ")";
        db.execSQL(create);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + parameters.USER_INFO_TABLE);
        onCreate(db);

    }
    public void addUserInfo(dataModel data)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(parameters.KEY_NAME,data.getName());
        values.put(parameters.KEY_EMAIL,data.getEmail());
        values.put(parameters.KEY_PASS,data.getPass());
        values.put(parameters.KEY_PHONE,data.getPhone());
        values.put(parameters.KEY_BLOOD_GROUP,data.getBloodGroup());
        values.put(parameters.KEY_DIST,data.getDistrict());
        values.put(parameters.KEY_PROFESSION,data.getProfession());
        long result = db.insert(parameters.USER_INFO_TABLE,null,values);
        if(result==-1)
        {
            Toast.makeText(context, "registration faild", Toast.LENGTH_SHORT).show();

        }
        else
        {
            Toast.makeText(context, "user added successfully", Toast.LENGTH_SHORT).show();
        }
    }
    public Boolean userExists(String email)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String select = "SELECT * FROM " + parameters.USER_INFO_TABLE + " where " + parameters.KEY_EMAIL + "=?";
        Cursor cursor = db.rawQuery(select,new String[]{email});
        if(cursor.moveToFirst())
        {
            Toast.makeText(context, "user mail exist", Toast.LENGTH_SHORT).show();
            cursor.close();
            return true;
        }
        else
        {
            Toast.makeText(context, "user mail dont exist", Toast.LENGTH_SHORT).show();
            cursor.close();
            return false;
        }
    }
    public Boolean checkPassword(String pass,String email)
    {
        String select = "SELECT * FROM " + parameters.USER_INFO_TABLE + " WHERE " + parameters.KEY_EMAIL + "=? AND " +
                parameters.KEY_PASS + "=? ";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(select,new String[]{email,pass});
        if(cursor != null && cursor.moveToFirst())
        {
            cursor.close();
            Toast.makeText(context, "pass and email are matched", Toast.LENGTH_SHORT).show();
            return true;
        }
        else
        {
            cursor.close();
            return false;
        }
    }
    public userInfo getUserInfo(String email)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String select = "SELECT * FROM " + parameters.USER_INFO_TABLE + " WHERE " + parameters.KEY_EMAIL + "=? ";
        Cursor cursor = db.rawQuery(select,new String[]{email});
        if(cursor.moveToFirst())
        {
            int nameIndex = cursor.getColumnIndexOrThrow("name");
            int phoneIndex = cursor.getColumnIndexOrThrow("phone_no");

            String name = cursor.getString(nameIndex);
            String phone = cursor.getString(phoneIndex);
            cursor.close();
            return new userInfo(name,email,phone);
        }
        cursor.close();
        return null;


    }

}
