package colors.com.example.firstplace.coloreyeze;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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
        values.put("groupID",device.getGroupID());
        db.insert("Device",null,values);
    }
    public void insertDeviceGroup(DeviceGroup deviceGroup){
        ContentValues values = new ContentValues();

        values.put("name",deviceGroup.getGroupName());
        values.put("color",deviceGroup.getGroupColor());
        db.insert("DeviceGroup",null,values);
    }

    public void insertStrip(Strip strip){
        ContentValues values = new ContentValues();
        values.put("_id",strip.getId());
        values.put("name",strip.getName());
        values.put("color",strip.getColor());
        values.put("pixels",strip.getPixels());
        values.put("deviceID",strip.getDeviceID());

        db.insert("Strip",null,values);
    }


    public void update(Device device){
        ContentValues values = new ContentValues();

        values.put("name",device.getDeviceName());
        values.put("ip",device.getDeviceIP());
        values.put("color",device.getDeviceColor());
        values.put("groupID",device.getGroupID());

        db.update("Device",values,"_id= ?",new String[]{"" + device.getDeviceId()});
    }
    public void updateDeviceGroup(DeviceGroup deviceGroup){
        ContentValues values = new ContentValues();

        values.put("name",deviceGroup.getGroupName());
        values.put("color",deviceGroup.getGroupColor());
        db.update("DeviceGroup",values,"_id= ?",new String[]{"" + deviceGroup.getGroupID()});
    }


    public void updateStrip(Strip strip){
        ContentValues values = new ContentValues();
        values.put("_id",strip.getId());
        values.put("name",strip.getName());
        values.put("color",strip.getColor());
        values.put("pixels",strip.getPixels());
        values.put("deviceID",strip.getPixels());

        db.update("Strip",values,"_id= ?",new String[]{"" + strip.getId()});
    }

    public void delete(Device device){
        db.delete("Device","_id= ?",new String[]{"" + device.getDeviceId()});
    }
    public void deleteDeviceGroup(DeviceGroup deviceGroup){
        db.delete("DeviceGroup","_id= ?",new String[]{"" + deviceGroup.getGroupID()});
    }
    public void deleteStrip(Strip strip){
        db.delete("Strip","_id= ? and deviceID=?",new String[]{"" + strip.getId(),"" + strip.getDeviceID()});
    }

    public List<Device> searchAllDevices(){
        List <Device> listDevices = new ArrayList<Device>();
        String[] columns = {"_id","name","ip","color","groupID"};

        Cursor cursor = db.query("Device",columns,null,null,null,null,"name ASC");

        if(cursor.getCount()>0){
            cursor.moveToFirst();

            do{

                Device device = new Device();
                device.setDeviceId(cursor.getLong(0));
                device.setDeviceName(cursor.getString(1));
                device.setDeviceIP(cursor.getString(2));
                device.setDeviceColor(cursor.getString(3));
                device.setGroupID(cursor.getLong(4));
                listDevices.add(device);
            }while(cursor.moveToNext());

        }
        cursor.close();
        return listDevices;
    }

    public List<DeviceGroup> searchAllDeviceGroups(){
        List <DeviceGroup> listDeviceGroups = new ArrayList<DeviceGroup>();
        String[] columns = {"_id","name","color"};

        Cursor cursor = db.query("DeviceGroup",columns,null,null,null,null,"name ASC");

        if(cursor.getCount()>0){
            cursor.moveToFirst();

            do{

                DeviceGroup deviceGroup = new DeviceGroup();
                deviceGroup.setGroupID(cursor.getLong(0));
                deviceGroup.setGroupName(cursor.getString(1));
                deviceGroup.setGroupColor(cursor.getString(2));

                listDeviceGroups.add(deviceGroup);
            }while(cursor.moveToNext());

        }
        cursor.close();
        return listDeviceGroups;
    }

    public List<Strip> searchAllStrips(int deviceID){
        List <Strip> listStrips = new ArrayList<Strip>();
        String[] columns = {"_id","name","color","pixels","deviceID"};
        Cursor cursor = db.query("Strip",columns,"deviceID=" + deviceID,null,null,null,null);
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do{

                Strip strip = new Strip();
                strip.setId(cursor.getInt(0));
                strip.setName(cursor.getString(1));
                strip.setColor(cursor.getString(2));
                strip.setPixels(cursor.getInt(3));
                strip.setDeviceID(cursor.getInt(4));
                listStrips.add(strip);

            }while(cursor.moveToNext());

        }
        else{

        }
        cursor.close();
        return listStrips;
    }

}
