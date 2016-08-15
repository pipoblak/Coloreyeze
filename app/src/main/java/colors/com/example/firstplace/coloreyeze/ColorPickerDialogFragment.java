package colors.com.example.firstplace.coloreyeze;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.github.danielnilsson9.colorpickerview.view.ColorPanelView;
import com.github.danielnilsson9.colorpickerview.view.ColorPickerView;

/**
 * Created by FirstPlace on 15/08/2016.
 */
public class ColorPickerDialogFragment extends DialogFragment implements ColorPickerView.OnColorChangedListener, View.OnClickListener {

    Bundle bundle;
    private ColorPickerView mColorPickerView;
    private ColorPanelView mOldColorPanelView;
    private ColorPanelView			mNewColorPanelView;
    String id,ip,pixels;
    WebSocketCon mWebSocket;


    Boolean conected = true;
    Boolean firstTime = true;
    long startTime = System.currentTimeMillis();

    public ColorPickerDialogFragment() {
        bundle = getArguments();

    }



    @Override
    public void onDestroyView() {
        if (getDialog() != null && getRetainInstance()) {
            getDialog().setDismissMessage(null);
        }
        super.onDestroyView();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
        id=bundle.getLong("deviceId")+"";
        ip= bundle.getString("deviceIP");
        pixels = bundle.getInt("devicePixels") + "";

        getDialog().getWindow().setFormat(PixelFormat.RGBA_8888);
        conectWebSocket(ip);
        getDialog().setContentView(R.layout.activity_color_picker);
        getDialog().setTitle(getActivity().getString(R.string.action_select_color));
        ((Toolbar) getActivity().findViewById(R.id.toolbarSelectColor)).setVisibility(View.GONE);
        init();


    }

    public void  conectWebSocket(String ip){
        mWebSocket = new WebSocketCon(ip);
    }
    private void init() {
        Log.v("Test","ASDA");

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        int initialColor = prefs.getInt("color_"+ id, 0xFF000000);

        mColorPickerView = (ColorPickerView) getActivity().findViewById(R.id.colorpickerview__color_picker_view);
        mOldColorPanelView = (ColorPanelView) getActivity().findViewById(R.id.colorpickerview__color_panel_old);
        mNewColorPanelView = (ColorPanelView) getActivity().findViewById(R.id.colorpickerview__color_panel_new);





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
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();
        edit.putInt("color_"+ id, mColorPickerView.getColor());
        edit.commit();

        if(mWebSocket.getConected() == false && firstTime==false){
            conectWebSocket(ip);
        }
        if (startTime + 150 < System.currentTimeMillis()) {
            //mWebSocket.sendMessage("set_RGBs(" + Color.green(newColor) + "," + Color.red(newColor) + "," + Color.blue(newColor) + "," +  pixels +")");


            String hexColor = "#" + Integer.toHexString(Color.rgb(Color.red(newColor) , Color.green(newColor),Color.blue(newColor)));

            mWebSocket.sendMessage(hexColor);
            firstTime=false;
            // Log.v("a",mColorPickerView.getColor() + "  ALPHA:" + Color.alpha(newColor));
            startTime = System.currentTimeMillis();

        }
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()) {

        }

    }


}
