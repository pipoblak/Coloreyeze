package colors.com.example.firstplace.coloreyeze;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import android.os.Handler;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by FirstPlace on 15/08/2016.
 */
public class DeviceActivity extends AppCompatActivity {
    TextView txtname,txtip,txtStatus;
    WebSocketCon webSocketCon;
    ProgressBar progressBar;
    private Timer timerAtual = new Timer();
    private TimerTask task;
    private final Handler handler = new Handler();
    Context c;
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
        ((ProgressBar)findViewById(R.id.progressBar))
                .getIndeterminateDrawable()
                .setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);

        Intent intent = getIntent();
        if(intent != null){
            Bundle bundle = intent.getExtras();
            if(bundle != null){
                txtname.setText(bundle.getString("deviceName"));
                txtip.setText(getString(R.string.add_device_dialog_DeviceIP) + " : " + bundle.getString("deviceIP"));
                webSocketCon = new WebSocketCon(bundle.getString("deviceIP"));

                ativaTimerTryConnection();
                c = this;

            }
        }

    }
    public void tryConnection(){
        if (!webSocketCon.conected) {
            Toast.makeText(c, getString(R.string.no_possible_connection_device), Toast.LENGTH_SHORT).show();
            txtStatus.setText(getString(R.string.state_disconnect));
            txtStatus.getBackground().setColorFilter(getResources().getColor(R.color.color_red),PorterDuff.Mode.SRC_ATOP);
        }
        else {
            txtStatus.setText(getString(R.string.state_connected));
            txtStatus.getBackground().setColorFilter(getResources().getColor(R.color.color_green),PorterDuff.Mode.SRC_ATOP);

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

}
