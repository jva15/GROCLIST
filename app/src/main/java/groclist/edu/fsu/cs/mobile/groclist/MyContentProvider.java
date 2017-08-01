package groclist.edu.fsu.cs.mobile.groclist;


        import android.content.Context;
        import android.database.sqlite.SQLiteDatabase;

        import java.io.File;
        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.OutputStream;
        import java.text.SimpleDateFormat;
        import java.util.Calendar;
        import java.util.Date;
        import java.util.Locale;

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
                    "PLU TEXT , " +
                    "UPC TEXT , " +
                    "PRICE FLOAT, " +
                    "TIMESTAMP DATE, " +
                    "LOCATION TEXT, " +
                    "DESCRIPTION TEXT,"+
                    "LISTSTATUS INTEGER, " +
                    "TOTALPRICE FLOAT) ";
    public static final String PLUcollumn = "PLU";
    public static final String UPCcollumn = "UPC";
    public static final String PRICEcollumn = "PRICE";
    public static final String TIMESTAMPcollumn = "TIMESTAMP";
    public static final String LOCATIONcollumn = "LOCATION";
    public static final String DESCRIPTIONcollumn = "DESCRIPTION";

    public static final Uri CONTENT_URI =
            Uri.parse("content://groclist.edu.fsu.cs.mobile.groclist.MyContentProvider");


    protected static final class dbhelper extends SQLiteOpenHelper {

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
        String UPC = values.getAsString("UPC");
        Calendar c = Calendar.getInstance();

        //CONVERT calender variables to a date for the database
        String SS = (c.get(Calendar.SECOND)>9 ?
                "0"+c.get(Calendar.SECOND): ""+c.get(Calendar.SECOND));
        String MM = (c.get(Calendar.MINUTE)>9 ?
                "0"+c.get(Calendar.MINUTE): ""+c.get(Calendar.MINUTE));
        String HH = (c.get(Calendar.HOUR_OF_DAY)>9 ?
                "0"+c.get(Calendar.HOUR_OF_DAY): ""+c.get(Calendar.HOUR_OF_DAY));
        String DD = (c.get(Calendar.DAY_OF_MONTH)>9 ?
                "0"+c.get(Calendar.DAY_OF_MONTH): ""+c.get(Calendar.DAY_OF_MONTH));
        String MO = (c.get(Calendar.MONTH)>9 ?
                "0"+c.get(Calendar.MONTH): ""+c.get(Calendar.MONTH));
        String YEAR = ""+(c.get(Calendar.YEAR));

        SimpleDateFormat sdf = new SimpleDateFormat(YEAR+"-"+MM+"-"+DD+" "+HH+":"+MM+":"+SS, Locale.getDefault());
        String date = sdf.format(new Date());
        values.put("TIMESTAMP",date);
        values.put("LOCATION","0,0");
        dbhelper help = new dbhelper(getContext());

        Long id = help.getWritableDatabase()
                .insert("UserTable", null, values);

        //Here, implicitly assume that an item has *EITHER* a UPC *OR* a PLU but *NOT BOTH*
        // must set other to null.

        if(UPC!=null)
            return Uri.withAppendedPath(CONTENT_URI, PLU);
        else
            return Uri.withAppendedPath(CONTENT_URI, UPC);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        dbhelper help = new dbhelper(getContext());

        return help.getWritableDatabase().update("UserTable", values, selection,selectionArgs);
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
