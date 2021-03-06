package colors.com.example.firstplace.coloreyeze;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import uz.shift.colorpicker.LineColorPicker;
import uz.shift.colorpicker.OnColorChangedListener;

/**
 * Created by FirstPlace on 18/08/2016.
 */
public class AddStripActivity extends AppCompatActivity implements View.OnClickListener {
    LineColorPicker colorPicker;
    Strip strip= new Strip() ;
    String name,id,pixels;
    EditText txtname,txtpixels,txtid;
    ImageButton btndelete;
    int deviceId;
    Context context;


    int color;
    Boolean edit = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_strip);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbarAddDevice);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ((ImageButton) findViewById(R.id.btnSave_DeviceAct)).setOnClickListener(this);
        ((ImageButton) findViewById(R.id.btnDeleteDevice)).setOnClickListener(this);
        context=this;
        colorPicker = (LineColorPicker) findViewById(R.id.addStripColorPicker);
        // set on change listener
        colorPicker.setOnColorChangedListener(new OnColorChangedListener() {
            @Override
            public void onColorChanged(int c) {
                Log.d("Cor Selecionada", "Selected color " + Integer.toHexString(c));
                color=c;
            }
        });
        color = colorPicker.getColor();
        //Initializing EditTexts
        txtname = (EditText) findViewById(R.id.txtNameStrip);
        txtid = (EditText)findViewById(R.id.txtStripID);
        txtpixels = (EditText)findViewById(R.id.txtPixels);
        btndelete=(ImageButton) findViewById(R.id.btnDeleteDevice);
        btndelete.setVisibility(View.GONE);


        Intent intent = getIntent();
        if(intent != null){
            Bundle bundle = intent.getExtras();
            if(bundle != null){
                if(bundle.getString("stripName")!= null){
                ((TextView) findViewById(R.id.action_add_device)).setText(getString(R.string.action_edit_strip));

                    strip.setId(bundle.getInt("stripId"));
                    strip.setName(bundle.getString("stripName"));
                    strip.setPixels(bundle.getInt("stripPixels"));
                    strip.setColor(bundle.getString("stripColor"));
                    strip.setDeviceID(bundle.getInt("deviceID"));
                    Log.v("String", strip.getId() + "");
                    edit = true;
                    txtname.setText(strip.getName());
                    txtid.setText("" + strip.getId());
                    colorPicker.setSelectedColor(Integer.parseInt(strip.getColor()));
                    txtpixels.setText("" + strip.getPixels());
                    btndelete.setVisibility(View.VISIBLE);
                } else {
                    deviceId = Integer.parseInt(bundle.getLong("deviceID") + "");


                }

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
                id = txtid.getText().toString();
                color = colorPicker.getColor();
                pixels = txtpixels.getText().toString();


                if(name.isEmpty() || id.isEmpty()   || pixels.isEmpty()){
                    Toast.makeText(this, getString(R.string.toast_empty_fields), Toast.LENGTH_SHORT).show();
                    break;
                }

                try{
                    strip.setName(name);
                    strip.setId(Integer.parseInt(id));
                    strip.setColor(color+"");
                    strip.setPixels(Integer.parseInt(pixels));
                    strip.setDeviceID(deviceId);
                }catch(Exception e){


                }



                if (edit==true){

                    db.updateStrip(strip);
                    Toast.makeText(this, getString(R.string.sucess_save) , Toast.LENGTH_SHORT).show();
                }

                else{

                    db.insertStrip(strip);
                    Toast.makeText(this, getString(R.string.sucess_update), Toast.LENGTH_SHORT).show();

                }
                finish();
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
                                DB db = new DB(context);
                                db.deleteStrip(strip);
                                Toast.makeText(activity,getString(R.string.sucess_delete), Toast.LENGTH_SHORT).show();
                                finish();

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
