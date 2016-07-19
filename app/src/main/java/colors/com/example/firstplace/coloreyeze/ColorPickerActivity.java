package colors.com.example.firstplace.coloreyeze;

import com.github.danielnilsson9.colorpickerview.view.ColorPanelView;
import com.github.danielnilsson9.colorpickerview.view.ColorPickerView;
import com.github.danielnilsson9.colorpickerview.view.ColorPickerView.OnColorChangedListener;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;



import java.net.URI;
import java.net.URISyntaxException;

public class ColorPickerActivity extends AppCompatActivity implements OnColorChangedListener, View.OnClickListener {


    private ColorPickerView			mColorPickerView;
    private ColorPanelView			mOldColorPanelView;
    private ColorPanelView			mNewColorPanelView;
    String id,ip,pixels;
    WebSocketCon mWebSocket;
    private Button					btnSaveColor;
    Boolean conected = true;
    Boolean firstTime = true;
    long startTime = System.currentTimeMillis();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        id=bundle.getLong("deviceId")+"";
        ip= bundle.getString("deviceIP");
        pixels = bundle.getInt("devicePixels") + "";
        getWindow().setFormat(PixelFormat.RGBA_8888);
        conectWebSocket(ip);
        setContentView(R.layout.activity_color_picker);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbarSelectColor);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();



    }

    public void  conectWebSocket(String ip){
        mWebSocket = new WebSocketCon(ip);
    }
    private void init() {


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int initialColor = prefs.getInt("color_"+ id, 0xFF000000);

        mColorPickerView = (ColorPickerView) findViewById(R.id.colorpickerview__color_picker_view);
        mOldColorPanelView = (ColorPanelView) findViewById(R.id.colorpickerview__color_panel_old);
        mNewColorPanelView = (ColorPanelView) findViewById(R.id.colorpickerview__color_panel_new);

        btnSaveColor = (Button) findViewById(R.id.btnSaveColor);



        ((LinearLayout) mOldColorPanelView.getParent()).setPadding(
                mColorPickerView.getPaddingLeft(), 0,
                mColorPickerView.getPaddingRight(), 0);


        mColorPickerView.setOnColorChangedListener(this);
        mColorPickerView.setColor(initialColor, true);
        mOldColorPanelView.setColor(initialColor);

        btnSaveColor.setOnClickListener(this);


    }

    @Override
    public void onColorChanged(int newColor) {
        mNewColorPanelView.setColor(mColorPickerView.getColor());
        // LOCAL PARA COLOCAR MÉTODO QUE IRÁ ENVIAR AS CORES
        if(mWebSocket.getConected() == false && firstTime==false){
            conectWebSocket(ip);
       }
        if (startTime + 100 < System.currentTimeMillis()) {
        mWebSocket.sendMessage("set_RGBs(" + Color.green(newColor) + "," + Color.red(newColor) + "," + Color.blue(newColor) + "," +  pixels +")");
        firstTime=false;
       // Log.v("a",mColorPickerView.getColor() + "  ALPHA:" + Color.alpha(newColor));
            startTime = System.currentTimeMillis();
        }
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()) {
            case R.id.btnSaveColor:
                SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(this).edit();
                edit.putInt("color_"+ id, mColorPickerView.getColor());
                edit.commit();

                finish();
                break;
         }

    }


}
