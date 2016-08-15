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
import android.widget.ImageButton;
import android.widget.ImageView;
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
        ((ImageButton) findViewById(R.id.btnAddDevice)).setOnClickListener(this);
        GridView gridViewDevices = (GridView) findViewById(R.id.gridViewDevices);
        DB db = new DB(this);
        ((ImageView) findViewById(R.id.logo)).setOnClickListener(this);
        List<Device> list = db.searchAllDevices();
        gridViewDevices.setAdapter(new DeviceAdapter(this, list));



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAddDevice :


                Intent intent = new Intent(this,AddDeviceActivity.class);
                startActivity(intent);

                break;

            case R.id.logo:
                final Animation animRotate;
                animRotate= AnimationUtils.loadAnimation(this,R.anim.anim_rotate);
                v.startAnimation(animRotate);

                break;
            default:
                break;

        }
    }
}
