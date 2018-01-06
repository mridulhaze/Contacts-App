package vn.vutran.android.data;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;

import vn.vutran.android.config.Constants;
import vn.vutran.android.model.Contact;

public class DatabaseHandler extends SQLiteOpenHelper {

    private final ArrayList<Contact> contactList = new ArrayList<>();
    public DatabaseHandler(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_CONTACT_TABLE = "CREATE TABLE " + Constants.TABLE_NAME + "("
                + Constants.KEY_ID + " INTEGER PRIMARY KEY, "
                + Constants.CONTACT_NAME + " TEXT, "
                + Constants.CONTACT_PHONE + " TEXT, "
                + Constants.CONTACT_EMAIL + " TEXT, "
                + Constants.CONTACT_ADDRESS + " TEXT, "
                + Constants.CONTACT_AVATAR + " TEXT);";

        db.execSQL(CREATE_CONTACT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS " + Constants.DATABASE_NAME);
        Log.v("ONUPGRADE", "DROPING THE TABLE AND CREATING A NEW ONE!");

        onCreate(db);

    }

    public void deleteContact(int id) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.TABLE_NAME, Constants.KEY_ID + " = ? ",
                new String[]{ String.valueOf(id)});

        db.close();

    }

    public void addContact(Contact contact) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.CONTACT_NAME, contact.getName());
        values.put(Constants.CONTACT_PHONE, contact.getPhone());
        values.put(Constants.CONTACT_EMAIL, contact.getEmail());
        values.put(Constants.CONTACT_ADDRESS, contact.getAddress());
        values.put(Constants.CONTACT_AVATAR, contact.getImageURI().toString());

        db.insert(Constants.TABLE_NAME, null, values);
        Log.v("Add contact", "OK");

        db.close();

    }

    public int updateContact (Contact contact) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Constants.CONTACT_NAME, contact.getName());
        values.put(Constants.CONTACT_PHONE, contact.getPhone());
        values.put(Constants.CONTACT_EMAIL, contact.getEmail());
        values.put(Constants.CONTACT_ADDRESS, contact.getAddress());
        values.put(Constants.CONTACT_AVATAR, contact.getImageURI().toString());

        int rowsAffected = db.update(Constants.TABLE_NAME, values, Constants.KEY_ID + "=?", new String[] { String.valueOf(contact.getId()) });

        db.close();

        return rowsAffected;

    }

    // Get all contacts
    public ArrayList<Contact> getContacts() {

        contactList.clear();

        String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Constants.TABLE_NAME, new String[]{Constants.KEY_ID, Constants.CONTACT_NAME, Constants.CONTACT_PHONE, Constants.CONTACT_EMAIL, Constants.CONTACT_ADDRESS, Constants.CONTACT_AVATAR},null,null, null,null,Constants.KEY_ID+ " DESC");

        //loop through cursor
        if (cursor.moveToFirst()) {

            do {

                Contact contact = new Contact();
                contact.setName(cursor.getString(cursor.getColumnIndex(Constants.CONTACT_NAME)));
                contact.setPhone(cursor.getString(cursor.getColumnIndex(Constants.CONTACT_PHONE)));
                contact.setEmail(cursor.getString(cursor.getColumnIndex(Constants.CONTACT_EMAIL)));
                contact.setAddress(cursor.getString(cursor.getColumnIndex(Constants.CONTACT_ADDRESS)));
                contact.setImageURI(Uri.parse(cursor.getString(cursor.getColumnIndex(Constants.CONTACT_AVATAR))));
                contact.setId(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ID)));

                contactList.add(contact);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return contactList;

    }

    public int getContactsCount() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Constants.TABLE_NAME, null);
        int count = cursor.getCount();
        db.close();
        cursor.close();

        return count;
    }

}
