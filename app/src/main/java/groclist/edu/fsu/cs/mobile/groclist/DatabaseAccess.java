package groclist.edu.fsu.cs.mobile.groclist;

/**
 * Created by Kris on 7/25/2017.
 */
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /**
     * Read all quotes from the database.
     *
     * @return a List of quotes
     */
    public Cursor getQuotes(String WhereStatement ) {


        this.open();
        String WS = " WHERE ";
        if(WhereStatement.equals(""))
        {WS="";}
        else WS = WS + WhereStatement;
        //openHelper.getReadableDatabase().query("UserTable", null, null, null,null,null,null);

        return database.rawQuery("SELECT * FROM PLU_Table"+WS, null);

    }
    public Cursor getQuotes(){
        return getQuotes("");
    }
}