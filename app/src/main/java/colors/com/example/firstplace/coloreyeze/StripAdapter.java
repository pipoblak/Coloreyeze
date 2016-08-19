package colors.com.example.firstplace.coloreyeze;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FirstPlace on 18/08/2016.
 */
public class StripAdapter extends BaseAdapter {
    List<Strip> stripList = new ArrayList<Strip>();
    Context context;

    public StripAdapter(List<Strip> stripList,Context context){
        this.context = context;
        this.stripList = stripList;

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
        iconLayout.getBackground().setColorFilter(Integer.parseInt(stripList.get(position).getColor()), PorterDuff.Mode.SRC_IN);
        TextView txtIcon,txtStripName;
        txtIcon = (TextView) layout.findViewById(R.id.txtIcon);
        txtStripName = (TextView) layout.findViewById(R.id.txtStripName);
        txtIcon.setText(stripList.get(position).getName());
        txtStripName.setText(stripList.get(position).getName());



        return layout;
    }
}
