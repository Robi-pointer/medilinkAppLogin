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

import java.util.ArrayList;

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
                parameters.KEY_DIABETES + " TEXT, " +
                parameters.KEY_HEPATITIS + " TEXT, " +
                parameters.KEY_MEJOR_OPERATION + " TEXT, " +
                parameters.KEY_HIGH_BLOOD_PRESSURE + " TEXT, " +
                parameters.KEY_VACCINE + " TEXT, " +
                parameters.KEY_LAST_DONATION_DATE + " TEXT, " +
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
        values.put(parameters.KEY_DIABETES,data.getDiabetes());
        values.put(parameters.KEY_HEPATITIS,data.getHepatitis());
        values.put(parameters.KEY_MEJOR_OPERATION,data.getOperation());
        values.put(parameters.KEY_HIGH_BLOOD_PRESSURE,data.getHigh_blood_pressure());
        values.put(parameters.KEY_VACCINE,data.getVaccine());
        values.put(parameters.KEY_LAST_DONATION_DATE,data.getDonation_date());
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
            String name  = cursor.getString(cursor.getColumnIndexOrThrow(parameters.KEY_NAME));
            String email1 = cursor.getString(cursor.getColumnIndexOrThrow(parameters.KEY_EMAIL));
            String phone = cursor.getString(cursor.getColumnIndexOrThrow(parameters.KEY_PHONE));
            String bloodGroup = cursor.getString(cursor.getColumnIndexOrThrow(parameters.KEY_BLOOD_GROUP));
            String diabetes = cursor.getString(cursor.getColumnIndexOrThrow(parameters.KEY_DIABETES));
            String hepatitis = cursor.getString(cursor.getColumnIndexOrThrow(parameters.KEY_HEPATITIS));
            String vaccine = cursor.getString(cursor.getColumnIndexOrThrow(parameters.KEY_VACCINE));
            String operation = cursor.getString(cursor.getColumnIndexOrThrow(parameters.KEY_MEJOR_OPERATION));
            String bloodPressure = cursor.getString(cursor.getColumnIndexOrThrow(parameters.KEY_HIGH_BLOOD_PRESSURE));
            String donationDate = cursor.getString(cursor.getColumnIndexOrThrow(parameters.KEY_LAST_DONATION_DATE));

            cursor.close();
            return new  userInfo(name, email, phone,bloodGroup,diabetes,hepatitis,operation,vaccine,donationDate,bloodPressure);
        }
        cursor.close();
        return null;


    }
    public ArrayList<userInfo> getDonersByBloodGroup(String bloodGroup) {
        ArrayList<userInfo> doners = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + parameters.USER_INFO_TABLE +
                        " WHERE " + parameters.KEY_BLOOD_GROUP + " = ?",
                new String[]{bloodGroup});

        if (cursor.moveToFirst()) {
            do {
                String name  = cursor.getString(cursor.getColumnIndexOrThrow(parameters.KEY_NAME));
                String email = cursor.getString(cursor.getColumnIndexOrThrow(parameters.KEY_EMAIL));
                String phone = cursor.getString(cursor.getColumnIndexOrThrow(parameters.KEY_PHONE));
                String diabetes = cursor.getString(cursor.getColumnIndexOrThrow(parameters.KEY_DIABETES));
                String hepatitis = cursor.getString(cursor.getColumnIndexOrThrow(parameters.KEY_HEPATITIS));
                String vaccine = cursor.getString(cursor.getColumnIndexOrThrow(parameters.KEY_VACCINE));
                String operation = cursor.getString(cursor.getColumnIndexOrThrow(parameters.KEY_MEJOR_OPERATION));
                String bloodPressure = cursor.getString(cursor.getColumnIndexOrThrow(parameters.KEY_HIGH_BLOOD_PRESSURE));
                String donationDate = cursor.getString(cursor.getColumnIndexOrThrow(parameters.KEY_LAST_DONATION_DATE));
                doners.add(new userInfo(name, email, phone,bloodGroup,diabetes,hepatitis,operation,vaccine,donationDate,bloodPressure));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return doners;
    }


}
