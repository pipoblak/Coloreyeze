package colors.com.example.firstplace.coloreyeze;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by pipob on 13/07/2016.
 */
public class DeviceAdapter extends BaseAdapter {
    private Context context;
    private List<Device> list;
    TextView txtname,txtip,txtapikey,txtid;


    public DeviceAdapter(Context context, List<Device> list){
        this.context=context;
        this.list=list;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getDeviceId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int auxPosition = position;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.grid_device_holder_layout, null);
        LinearLayout layoutTop = (LinearLayout)layout.findViewById(R.id.layoutTop);
        txtname = (TextView) layout.findViewById(R.id.deviceName);
        txtip = (TextView) layout.findViewById(R.id.deviceIp);

        final Animation animAlpha;
        animAlpha= AnimationUtils.loadAnimation(context,R.anim.anim_alpha);


        txtname.setText(list.get(position).getDeviceName());
        txtip.setText(list.get(position).getDeviceIP());
        layoutTop.setBackgroundColor(Integer.parseInt(list.get(position).getDeviceColor()));

        Button editarBt = (Button) layout.findViewById(R.id.optionsButton);
        editarBt.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                v.startAnimation(animAlpha);
                Intent intent = new Intent(context, AddDeviceActivity.class);
                intent.putExtra("deviceId", list.get(auxPosition).getDeviceId());
                intent.putExtra("deviceName", list.get(auxPosition).getDeviceName());
                intent.putExtra("deviceIP", list.get(auxPosition).getDeviceIP());
                intent.putExtra("deviceApikey", list.get(auxPosition).getApiKey());
                intent.putExtra("deviceColor", list.get(auxPosition).getDeviceColor());
                intent.putExtra("devicePixels", list.get(auxPosition).getDevicePixels());
                context.startActivity(intent);
            }
        });
        Button btnShowColorDialog = (Button) layout.findViewById(R.id.btnShowColor);

        btnShowColorDialog.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                v.startAnimation(animAlpha);
                Intent intent = new Intent(context, ColorPickerActivity.class);
                intent.putExtra("deviceId", list.get(auxPosition).getDeviceId());
                intent.putExtra("deviceName", list.get(auxPosition).getDeviceName());
                intent.putExtra("deviceIP", list.get(auxPosition).getDeviceIP());
                intent.putExtra("deviceApikey", list.get(auxPosition).getApiKey());
                intent.putExtra("deviceColor", list.get(auxPosition).getDeviceColor());
                intent.putExtra("devicePixels", list.get(auxPosition).getDevicePixels());
                context.startActivity(intent);
            }
        });
        Button btnEffects = (Button) layout.findViewById(R.id.btnEffects);
        btnEffects.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                v.startAnimation(animAlpha);
                Intent intent = new Intent(context, ColorPickerActivity.class);
                intent.putExtra("deviceId", list.get(auxPosition).getDeviceId());
                intent.putExtra("deviceName", list.get(auxPosition).getDeviceName());
                intent.putExtra("deviceIP", list.get(auxPosition).getDeviceIP());
                intent.putExtra("deviceApikey", list.get(auxPosition).getApiKey());
                intent.putExtra("deviceColor", list.get(auxPosition).getDeviceColor());
                intent.putExtra("devicePixels", list.get(auxPosition).getDevicePixels());
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Selecione um Efeito")
                        .setItems(R.array.effects, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // The 'which' argument contains the index position
                                // of the selected item
                            }
                        });
                builder.create();
                builder.show();
            }
        });

        txtname.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, ColorPickerActivity.class);
                intent.putExtra("deviceId", list.get(auxPosition).getDeviceId());
                intent.putExtra("deviceName", list.get(auxPosition).getDeviceName());
                intent.putExtra("deviceIP", list.get(auxPosition).getDeviceIP());
                intent.putExtra("deviceApikey", list.get(auxPosition).getApiKey());
                intent.putExtra("deviceColor", list.get(auxPosition).getDeviceColor());
                intent.putExtra("devicePixels", list.get(auxPosition).getDevicePixels());
                context.startActivity(intent);
            }
        });


        /*
        Button deletarBt = (Button) layout.findViewById(R.id.deletar);
        deletarBt.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                BD bd = new BD(context);
                bd.deletar(list.get(auxPosition));

                layout.setVisibility(View.GONE);
            }
        });
*/
        return layout;
    }

}
