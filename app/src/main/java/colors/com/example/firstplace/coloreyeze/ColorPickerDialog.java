package colors.com.example.firstplace.coloreyeze;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.opengl.Visibility;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.github.danielnilsson9.colorpickerview.view.ColorPanelView;
import com.github.danielnilsson9.colorpickerview.view.ColorPickerView;
import com.github.danielnilsson9.colorpickerview.view.ColorPickerView.OnColorChangedListener;

public class ColorPickerDialog extends Dialog implements OnColorChangedListener, View.OnClickListener {

    Context context;
    Bundle bundle;
    private ColorPickerView			mColorPickerView;
    private ColorPanelView			mOldColorPanelView;
    private ColorPanelView			mNewColorPanelView;
    String id,ip,stripid,sharedIsA,idSet;

    Boolean isDevice=false;
    WebSocketCon mWebSocket;

    Boolean conected = true;
    Boolean firstTime = true;
    long startTime = System.currentTimeMillis();

    public ColorPickerDialog(Context cont, Bundle bund) {
        super(cont);
        context = cont;
        bundle=bund;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(bundle.getString("deviceName")!= null){
            id = bundle.getLong("deviceId") + "";
            ip = bundle.getString("deviceIP");
            stripid="0";
            isDevice=true;
        }
        else{
            isDevice=false;
            stripid=bundle.getString("deviceIP");
            ip= bundle.getString("deviceIP");


        }

        getWindow().setFormat(PixelFormat.RGBA_8888);
        conectWebSocket(ip);
        setContentView(R.layout.activity_color_picker);
        setTitle(context.getString(R.string.action_select_color));
        ((Toolbar) findViewById(R.id.toolbarSelectColor)).setVisibility(View.GONE);
        init();
        mNewColorPanelView.setOnClickListener(this);



    }

    public void  conectWebSocket(String ip){
        mWebSocket = new WebSocketCon(ip);
    }
    private void init() {


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        if (isDevice){
            sharedIsA= "color_Device";
            idSet=stripid;
        }
        else{
            sharedIsA = "color_Strip";
            idSet =stripid;
        }

        int initialColor = prefs.getInt( sharedIsA + idSet, 0xFF000000);

        mColorPickerView = (ColorPickerView) findViewById(R.id.colorpickerview__color_picker_view);
        mOldColorPanelView = (ColorPanelView) findViewById(R.id.colorpickerview__color_panel_old);
        mNewColorPanelView = (ColorPanelView) findViewById(R.id.colorpickerview__color_panel_new);





        ((LinearLayout) mOldColorPanelView.getParent()).setPadding(
                mColorPickerView.getPaddingLeft(), 0,
                mColorPickerView.getPaddingRight(), 0);


        mColorPickerView.setOnColorChangedListener(this);
        mColorPickerView.setColor(initialColor, true);
        mOldColorPanelView.setColor(initialColor);




    }

    @Override
    public void onColorChanged(int newColor) {
        mNewColorPanelView.setColor(mColorPickerView.getColor());
        // LOCAL PARA COLOCAR MÉTODO QUE IRÁ ENVIAR AS CORES
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(context).edit();

        if (isDevice){
            sharedIsA= "color_Device";
            idSet=stripid;
        }
        else{
            sharedIsA = "color_Strip";
            idSet =stripid;
        }

        edit.putInt(sharedIsA + idSet, mColorPickerView.getColor());
        edit.commit();

        if(mWebSocket.getConected() == false && firstTime==false){
            conectWebSocket(ip);
       }
        if (startTime + 300 < System.currentTimeMillis()) {
           String strColor = "#R" + Color.red(newColor) + "G" + Color.green(newColor) + "B"+Color.blue(newColor)+ "S" + idSet;

            mWebSocket.sendMessage(strColor);
            firstTime=false;
            startTime = System.currentTimeMillis();

        }
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()) {
            case R.id.colorpickerview__color_panel_new:
              this.cancel();
                break;

         }

    }


}
