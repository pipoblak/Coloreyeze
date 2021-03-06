package colors.com.example.firstplace.coloreyeze;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import uz.shift.colorpicker.LineColorPicker;
import uz.shift.colorpicker.OnColorChangedListener;

/**
 * Created by FirstPlace on 12/07/2016.
 */
public class AddDeviceActivity extends AppCompatActivity implements View.OnClickListener{
    LineColorPicker colorPicker;
    Device device= new Device() ;
    EditText txtname,txtip;
    ImageButton btndelete;
    String name,ip;
    int color;
    Boolean edit = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_device_dialog);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbarAddDevice);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ((ImageButton) findViewById(R.id.btnSave_DeviceAct)).setOnClickListener(this);
        ((ImageButton) findViewById(R.id.btnDeleteDevice)).setOnClickListener(this);
        GridView gridViewDevices = (GridView) findViewById(R.id.gridViewDevices);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                colorPicker = (LineColorPicker) findViewById(R.id.addDeviceColorPicker);
        // set on change listener
        colorPicker.setOnColorChangedListener(new OnColorChangedListener() {
            @Override
            public void onColorChanged(int c) {
                Log.d("Cor Selecionada", "Selected color " + Integer.toHexString(c));
            }
        });

        //Initializing EditTexts
        txtname = (EditText) findViewById(R.id.txtNameDevice);
        txtip = (EditText)findViewById(R.id.txtDeviceIp);
        btndelete=(ImageButton) findViewById(R.id.btnDeleteDevice);
        btndelete.setVisibility(View.GONE);


        Intent intent = getIntent();
        if(intent != null){
            Bundle bundle = intent.getExtras();
            if(bundle != null){
                ((TextView) this.findViewById(R.id.action_add_device)).setText(getString(R.string.action_edit_device));
                device.setDeviceId(bundle.getLong("deviceId"));
                device.setDeviceName(bundle.getString("deviceName"));
                device.setDeviceIP(bundle.getString("deviceIP"));
                device.setDeviceColor(bundle.getString("deviceColor"));
                edit=true;
                txtname.setText(device.getDeviceName());
                txtip.setText(device.getDeviceIP());
                colorPicker.setSelectedColor(Integer.parseInt(device.getDeviceColor()));
                btndelete.setVisibility(View.VISIBLE);
            }
        }


    }

    @Override
    public void onClick(View v) {
        final DB db = new DB(this);
        final Activity activity = this;
        final Intent intent = getSupportParentActivityIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        switch (v.getId()){

            case R.id.btnSave_DeviceAct:
                name = txtname.getText().toString();
                ip = txtip.getText().toString();
                color = colorPicker.getColor();


                if(name.isEmpty() || ip.isEmpty()){
                    Toast.makeText(this, getString(R.string.toast_empty_fields), Toast.LENGTH_SHORT).show();
                    break;
                }


                device.setDeviceName(name);
                device.setDeviceIP(ip);
                device.setDeviceColor(color+"");


                if (edit==true){

                    db.update(device);
                    Toast.makeText(this, getString(R.string.sucess_save) , Toast.LENGTH_SHORT).show();
                    }

                else{
                    db.insert(device);
                    Toast.makeText(this, getString(R.string.sucess_update), Toast.LENGTH_SHORT).show();

                }

                startActivity(intent);

                break;

            case R.id.btnDeleteDevice:
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle(getString(R.string.confirmation_are_you_sure))
                        .setMessage(getString(R.string.confirmation_are_you_sure_delete))
                        .setPositiveButton(getString(R.string.confirmation_yes), new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                db.delete(device);
                                Toast.makeText(activity,getString(R.string.sucess_delete), Toast.LENGTH_SHORT).show();
                                startActivity(intent);

                            }

                        })
                        .setNegativeButton(getString(R.string.confirmation_no), null)
                        .show();
                break;
            default:
                break;
        }
    }
}
