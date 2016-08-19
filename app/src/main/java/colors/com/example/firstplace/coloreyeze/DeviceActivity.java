package colors.com.example.firstplace.coloreyeze;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import android.os.Handler;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * Created by FirstPlace on 15/08/2016.
 */
public class DeviceActivity extends AppCompatActivity implements View.OnClickListener {
    TextView txtname,txtip,txtStatus;
    FloatingActionButton floatingADD;
    ListView listStrips;
    WebSocketCon webSocketCon;
    ProgressBar progressBar;
    LinearLayout stripTitle;
    ImageButton btnRefresh;
    private Timer timerAtual = new Timer();
    private TimerTask task;
    private final Handler handler = new Handler();
    Device device;
    Context c;
    List<Strip> stripList;
    int contTimer=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_detailed_layout);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbarDeviceDetails);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtname = (TextView) findViewById(R.id.txtDetailedName);
        txtip = (TextView) findViewById(R.id.txtDetailedIP);
        progressBar= (ProgressBar) findViewById(R.id.progressBar);
        txtStatus = (TextView) findViewById(R.id.txtStatus);
        floatingADD = (FloatingActionButton) findViewById(R.id.floatingAdd) ;
        listStrips = (ListView) findViewById(R.id.listViewStrips);
        stripTitle = (LinearLayout) findViewById(R.id.stripTitle);
        btnRefresh = (ImageButton) findViewById(R.id.btnRefresh);
        Intent intent = getIntent();
        if(intent != null){
            Bundle bundle = intent.getExtras();
            if(bundle != null){
                device = new Device();
                device.setDeviceIP(bundle.getString("deviceIP"));
                device.setDeviceId(bundle.getLong("deviceId"));
                device.setDeviceColor(bundle.getString("deviceColor"));
                device.setDeviceName(bundle.getString("deviceName"));
                loadStrips(Integer.parseInt(device.getDeviceId() + ""));
                txtname.setText(device.getDeviceName());
                txtip.setText(getString(R.string.add_device_dialog_DeviceIP) + " : " + device.getDeviceIP());


                c = this;

            }
        }
        floatingADD.setOnClickListener(this);
        btnRefresh.setOnClickListener(this);

        ((ProgressBar)findViewById(R.id.progressBar))
                .getIndeterminateDrawable()
                .setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);




    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        device.setDeviceIP(bundle.getString("deviceIP"));
        contTimer=1;
        webSocketCon = new WebSocketCon(device.getDeviceIP());
        ativaTimerTryConnection();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Intent intent;
        intent = getIntent();
        finish();
        startActivity(intent);
    }

    public void tryConnection(){
        if (!webSocketCon.conected) {
            Toast.makeText(c, getString(R.string.no_possible_connection_device), Toast.LENGTH_SHORT).show();
            txtStatus.setText(getString(R.string.state_disconnect));
            txtStatus.getBackground().setColorFilter(getResources().getColor(R.color.color_red),PorterDuff.Mode.SRC_ATOP);
            btnRefresh.setVisibility(View.VISIBLE);
        }
        else {
            txtStatus.setText(getString(R.string.state_connected));
            txtStatus.getBackground().setColorFilter(getResources().getColor(R.color.color_green),PorterDuff.Mode.SRC_ATOP);

            listStrips.setVisibility(View.VISIBLE);
            stripTitle.setVisibility(View.VISIBLE);
            floatingADD.setVisibility(View.VISIBLE);
            btnRefresh.setVisibility(View.VISIBLE);
        }
        webSocketCon.close();
            progressBar.setVisibility(View.GONE);
    }

    private void ativaTimerTryConnection(){
        task = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {


                        if (contTimer==10){
                            tryConnection();
                            task.cancel();
                        }
                        if (!webSocketCon.conected)
                            contTimer++;
                        else
                            contTimer=10;

                    }
                });
            }};

        timerAtual.schedule(task, 300, 300);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.floatingAdd :
                intent = new Intent(this,AddStripActivity .class);
                intent.putExtra("deviceID",device.getDeviceId());
                intent.putExtra("deviceIP",device.getDeviceIP());
                startActivity(intent);
                break;
            case R.id.btnRefresh :
                intent = getIntent();
                finish();
                startActivity(intent);
                break;

        }
    }

    public void loadStrips(int deviceId){
        DB db = new DB(this);
        stripList = db.searchAllStrips(deviceId);
        StripAdapter stripAdapter = new StripAdapter(stripList,this,device.getDeviceIP());
        listStrips.setAdapter(stripAdapter);

    }
}
