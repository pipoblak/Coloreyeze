package colors.com.example.firstplace.coloreyeze;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FirstPlace on 18/08/2016.
 */
public class StripAdapter extends BaseAdapter  implements View.OnClickListener{
    List<Strip> stripList = new ArrayList<Strip>();
    Context context;
    Animation alpha;
    String deviceIP;

    public StripAdapter(List<Strip> stripList,Context context,String deviceIP){
        this.context = context;
        this.stripList = stripList;
        this.deviceIP=deviceIP;
    }

    @Override
    public int getCount() {
        return stripList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.listview_strip_holder, null);
        LinearLayout iconLayout = (LinearLayout) layout.findViewById(R.id.iconLinearLayout);
        LinearLayout btnsLayout = (LinearLayout) layout.findViewById(R.id.btnsLayout);


        iconLayout.getBackground().setColorFilter(Integer.parseInt(stripList.get(position).getColor()), PorterDuff.Mode.SRC_IN);
        TextView txtIcon,txtStripName;
        txtIcon = (TextView) layout.findViewById(R.id.txtIcon);
        txtStripName = (TextView) layout.findViewById(R.id.txtStripName);
        txtIcon.setText(stripList.get(position).getName().toUpperCase());
        txtStripName.setText(stripList.get(position).getName());
        alpha = AnimationUtils.loadAnimation(context,R.anim.anim_alpha);
        layout.setOnClickListener(this);
        btnsLayout.setOnClickListener(this);
        final int pos = position;
        ImageButton infoBtn = (ImageButton) layout.findViewById(R.id.btnInfo);
        infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddStripActivity.class);
                intent.putExtra("stripId", stripList.get(pos).getId());
                intent.putExtra("stripName",  stripList.get(pos).getName());
                intent.putExtra("stripPixels", stripList.get(pos).getPixels());
                intent.putExtra("stripColor",  stripList.get(pos).getColor());
                intent.putExtra("deviceID",  stripList.get(pos).getDeviceID());
                context.startActivity(intent);
            }
        });

        return layout;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.LayoutHolderStrip :
                v.startAnimation(alpha);
                break;
            case R.id.btnsLayout:
                break;

        }
    }
}
