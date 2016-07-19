package colors.com.example.firstplace.coloreyeze;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.util.List;

import uz.shift.colorpicker.LineColorPicker;
import uz.shift.colorpicker.OnColorChangedListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
   Animation animAlpha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ((Button) findViewById(R.id.btnAddDevice)).setOnClickListener(this);
        GridView gridViewDevices = (GridView) findViewById(R.id.gridViewDevices);
        DB db = new DB(this);

        List<Device> list = db.searchAllDevices();
        gridViewDevices.setAdapter(new DeviceAdapter(this, list));
        animAlpha= AnimationUtils.loadAnimation(this,R.anim.anim_alpha);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAddDevice :

                v.startAnimation(animAlpha);
                Intent intent = new Intent(this,AddDeviceActivity.class);
                startActivity(intent);

                break;

            default:
                break;

        }
    }
}
