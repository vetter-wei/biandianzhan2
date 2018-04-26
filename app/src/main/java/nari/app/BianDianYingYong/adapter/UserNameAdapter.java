package nari.app.BianDianYingYong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import nari.app.BianDianYingYong.R;

/**
 * Created by ShawDLee on 2018/1/5.
 */

public class UserNameAdapter extends BaseAdapter {
    private List<String> nameList;
    private Context context;

    public UserNameAdapter(List<String> nameList, Context context) {
        this.nameList = nameList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return nameList.size();
    }

    @Override
    public Object getItem(int i) {
        return nameList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        view=layoutInflater.inflate(R.layout.spinner_name, null);
        TextView name=view.findViewById(R.id.tv_spinner_name);
        name.setText(nameList.get(i));
        return view;
    }
}
