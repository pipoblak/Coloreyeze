package colors.com.example.firstplace.coloreyeze;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import uz.shift.colorpicker.LineColorPicker;
import uz.shift.colorpicker.OnColorChangedListener;

/**
 * Created by FirstPlace on 18/08/2016.
 */
public class AddStripActivity extends AppCompatActivity implements View.OnClickListener {
    LineColorPicker colorPicker;
    Strip strip= new Strip() ;
    EditText txtname,txtpixels,txtid;
    ImageButton btndelete;
    String name,ip;
    int color;
    Boolean edit = false;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_device_dialog);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbarAddDevice);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ((ImageButton) findViewById(R.id.btnSave_DeviceAct)).setOnClickListener(this);
        ((ImageButton) findViewById(R.id.btnDeleteDevice)).setOnClickListener(this);
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
        txtname = (EditText) findViewById(R.id.txtNameStrip);
        txtid = (EditText)findViewById(R.id.txtStripID);
        btndelete=(ImageButton) findViewById(R.id.btnDeleteDevice);
        btndelete.setVisibility(View.GONE);


        Intent intent = getIntent();
        if(intent != null){
            Bundle bundle = intent.getExtras();
            if(bundle != null){
                ((TextView) this.findViewById(R.id.action_add_device)).setText(getString(R.string.action_edit_strip));

                strip.setId(bundle.getInt("stripId"));
                strip.setName(bundle.getString("stripName"));
                strip.setPixels(bundle.getInt("stripPixels"));
                strip.setColor(bundle.getString("stripColor"));
                strip.setDeviceID(bundle.getInt("deviceID"));

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

    }

}
