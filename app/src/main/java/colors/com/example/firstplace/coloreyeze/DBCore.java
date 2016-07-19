package colors.com.example.firstplace.coloreyeze;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by FirstPlace on 12/07/2016.
 */
public class DBCore extends SQLiteOpenHelper {
    private static final String NAME_DB="Coloreyeze";
    private static final int VERSION_DB=2;
    public DBCore(Context context){
        super(context,NAME_DB,null,VERSION_DB);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Device(_id integer primary key autoincrement, name text not null, ip text not null, color text not null, apikey text not null,pixels integer not null);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE Device;");
        onCreate(db);
    }

    @Override
    protected void finalize() throws Throwable {
        this.close();
        super.finalize();
    }
}
