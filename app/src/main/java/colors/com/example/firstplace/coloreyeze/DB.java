package colors.com.example.firstplace.coloreyeze;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FirstPlace on 12/07/2016.
 */
public class DB {
    private SQLiteDatabase db;

    public DB(Context context){
        DBCore auxDb= new DBCore(context);
        db = auxDb.getWritableDatabase();
    }

    public void insert(Device device){
        ContentValues values = new ContentValues();

        values.put("name",device.getDeviceName());
        values.put("ip",device.getDeviceIP());
        values.put("color",device.getDeviceColor());
        values.put("apikey",device.getApiKey());
        values.put("pixels",device.getDevicePixels());

        db.insert("Device",null,values);
    }

    public void update(Device device){
        ContentValues values = new ContentValues();

        values.put("name",device.getDeviceName());
        values.put("ip",device.getDeviceIP());
        values.put("color",device.getDeviceColor());
        values.put("apikey",device.getApiKey());
        values.put("pixels",device.getDevicePixels());
        db.update("Device",values,"_id= ?",new String[]{"" + device.getDeviceId()});
    }

    public void delete(Device device){
        db.delete("Device","_id= ?",new String[]{"" + device.getDeviceId()});
    }

    public List<Device> searchAllDevices(){
        List <Device> listDevices = new ArrayList<Device>();
        String[] columns = {"_id","name","ip","color","apikey","pixels"};

        Cursor cursor = db.query("Device",columns,null,null,null,null,"name ASC");

        if(cursor.getCount()>0){
            cursor.moveToFirst();

            do{

                Device device = new Device();
                device.setDeviceId(cursor.getLong(0));
                device.setDeviceName(cursor.getString(1));
                device.setDeviceIP(cursor.getString(2));
                device.setDeviceColor(cursor.getString(3));
                device.setApiKey(cursor.getString(4));
                device.setDevicePixels(cursor.getInt(5));
                listDevices.add(device);
            }while(cursor.moveToNext());

        }
        return listDevices;
    }

}
