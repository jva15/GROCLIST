package groclist.edu.fsu.cs.mobile.groclist;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class MyContentProvider extends ContentProvider {
    public static final int DBVERSION = 1;
    public final static String DBNAME = "PLU_DB";
    public final static String TABLE_NAMESTABLE = "PLUTable";
    private static final String SQL_CREATE_MAIN =
            "CREATE TABLE PLUTable ( " +
                    "PLU INTEGER PRIMARY KEY, " +
                    "COMMODITY TEXT, " +
                    "VARIETY TEXT, " +
                    "EXPIRATION)";

    public static final Uri CONTENT_URI =
            Uri.parse("content://edu.fsu.cs.mobile.proj2.groclist");


    protected static final class dbhelper extends SQLiteOpenHelper {//hacked from slides

        dbhelper(Context context) {
            super(context, DBNAME, null, DBVERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_MAIN);
        }

        @Override
        public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        }
    }


        @Override
    public boolean onCreate() {
        // TODO: Create Database

        dbhelper helper = new dbhelper(getContext());

        return true;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: insert Employee information
        String mEmployeeID = values.getAsString("mEmployeeID");
        //String mName = values.getAsString("mName");
        //String mEmail = values.getAsString("mEmail");
        //String mGender = values.getAsString("mGender");
        //String mPassword = values.getAsString("mPassword");
        //String mDepartment = values.getAsString("mDepartment");


        dbhelper help = new dbhelper(getContext());

        Long id = help.getWritableDatabase()
                .insert("PLUTable", null, values);
        return Uri.withAppendedPath(CONTENT_URI, mEmployeeID);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: update Employee information

        dbhelper help = new dbhelper(getContext());

        return help.getWritableDatabase().update("EmployeeTable", values, selection,selectionArgs);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // TODO: delete Employee information


        dbhelper help = new dbhelper(getContext());

        return help.getWritableDatabase().delete(TABLE_NAMESTABLE, selection,selectionArgs);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: query Employee by selection


        dbhelper help = new dbhelper(getContext());

        return help.getReadableDatabase().query("EmployeeTable", projection, selection, selectionArgs,null,null,sortOrder);
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }
}
