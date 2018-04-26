package nari.app.BianDianYingYong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import nari.app.BianDianYingYong.R;
import nari.app.BianDianYingYong.customview.CustomListView;
import nari.app.BianDianYingYong.utils.StringUtil;


/**
 * Created by lx on 2017/11/22.
 */

public class ExecutionActivityAdapter extends BaseAdapter {
    private String[] mCzbzArray;
    private Context context;
    private LayoutInflater layoutInflater;


    private String mTimeSub = "";
    private int count = 100;
    private int mCuttonP;
    private CustomListView lv_exe_czxm;
    private String[] mOrderArray;
    private int status = 1;

    public ExecutionActivityAdapter(Context context, String[] czbzArray) {
        this.context = context;
        this.mCzbzArray = czbzArray;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (mCzbzArray == null || "".equals(mCzbzArray[0])) {
            return 0;
        } else if (mCzbzArray.length < 3) {
            return mCzbzArray.length;
        } else {
            return 3;
        }
    }

    @Override
    public Object getItem(int i) {
        return mCzbzArray[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = layoutInflater.inflate(R.layout.item_exe_activity, null);
            holder.tv_czxm_number = view.findViewById(R.id.tv_czxm_number);
            holder.tv_czxm_cznr = view.findViewById(R.id.tv_czxm_cznr);
            holder.tv_czxm_czsj = view.findViewById(R.id.tv_czxm_czsj);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        int e = mCzbzArray.length;
        if (e <= i) {

        } else {
            String order = mCzbzArray[i];
            //   切割字符串
            mOrderArray = StringUtil.splitString("\\|", order);
            holder.tv_czxm_number.setText(mOrderArray[1]);
            holder.tv_czxm_cznr.setText(mOrderArray[2]);
            for (int j = 0; j < mOrderArray.length; j++) {
                if (mOrderArray[j].length() == 19 && mOrderArray[j].contains(":")) {
                    holder.tv_czxm_czsj.setText(mOrderArray[j].substring(10, mOrderArray[j].length() - 3));
                }
            }
        }

        return view;
    }


    public static class ViewHolder {
        public TextView tv_czxm_number;
        public TextView tv_czxm_cznr;
        public TextView tv_czxm_czsj;
    }

}
