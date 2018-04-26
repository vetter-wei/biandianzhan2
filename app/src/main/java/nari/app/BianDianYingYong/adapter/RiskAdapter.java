package nari.app.BianDianYingYong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import nari.app.BianDianYingYong.R;

/**
 * Created by lx on 2017/11/28.
 */

public class RiskAdapter extends BaseAdapter {
    private Context mContext;
    public RiskAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return 2;

    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder;;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(
                    R.layout.item_risk_pop_adapter, null);
            holder.tv_pop_num = view.findViewById(R.id.tv_pop_num);
            holder.tv_pop_shr = view.findViewById(R.id.tv_pop_shr);
            holder.tv_pop_wxdfx = view.findViewById(R.id.tv_pop_wxdfx);
            holder.tv_pop_aqkz = view.findViewById(R.id.tv_pop_aqkz);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        return view;
    }

    public static class ViewHolder {
        TextView tv_pop_num; //   序号
        TextView tv_pop_shr;//   审核人
        TextView tv_pop_wxdfx;//   危险点分析
        TextView tv_pop_aqkz;//   安全控制措施

    }
}
