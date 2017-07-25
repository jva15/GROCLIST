package groclist.edu.fsu.cs.mobile.groclist;


        import android.content.Context;
        import android.database.sqlite.SQLiteDatabase;

        import java.io.File;
        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.OutputStream;

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
    public final static String DBNAME = "GROC_DB";
    public final static String TABLE_NAME = "UserTable";
    private static final String SQL_CREATE_MAIN =
            "CREATE TABLE UserTable ( " +
                    "PLU INTEGER PRIMARY KEY, " +
                    "UPC INTEGER PRIMARY KEY, " +
                    "PRICE FLOAT, " +
                    "TIMESTAMP DATE, " +
                    "LOCATION TEXT, " +
                    "DESCRIPTION TEXT)";

    public static final Uri CONTENT_URI =
            Uri.parse("content://groclist.edu.fsu.cs.mobile.groclist.MyContentProvider");


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
        //Create Database
        dbhelper helper = new dbhelper(getContext());
        return true;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        //insert information
        String PLU = values.getAsString("PLU");

        dbhelper help = new dbhelper(getContext());

        Long id = help.getWritableDatabase()
                .insert("PLUTable", null, values);
        return Uri.withAppendedPath(CONTENT_URI, PLU);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        dbhelper help = new dbhelper(getContext());

        return help.getWritableDatabase().update("PLUTable", values, selection,selectionArgs);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        //delete information

        dbhelper help = new dbhelper(getContext());

        return help.getWritableDatabase().delete(TABLE_NAME, selection,selectionArgs);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {



        dbhelper help = new dbhelper(getContext());

        return help.getReadableDatabase().query("UserTable", projection, selection, selectionArgs,null,null,sortOrder);
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }
}
