package nari.app.BianDianYingYong.jinyi.adapter_jinyi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import nari.app.BianDianYingYong.R;
import nari.app.BianDianYingYong.jinyi.bean_jinyi.PingJiaRenWuBean;
import nari.app.BianDianYingYong.jinyi.customview_jinyi.CircleNumberProgressBar;
import nari.app.BianDianYingYong.utils.ProgressDialog;

/**
 * Created by ShawDLee on 2018/1/17.
 */

public class RenWuAdapter extends BaseAdapter {
    private Context mContext;
    private List<PingJiaRenWuBean> renWuList;

    public RenWuAdapter(Context mContext, List<PingJiaRenWuBean> renWuList) {
        this.mContext = mContext;
        this.renWuList = renWuList;
    }

    @Override
    public int getCount() {
        if (renWuList == null) {

            return 0;
        } else {

            return renWuList.size();
        }
    }

    @Override
    public Object getItem(int i) {
        if (renWuList != null) {
            return renWuList.get(i);
        }
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        view = LayoutInflater.from(mContext).inflate(
                R.layout.item_fragment_renwu_adapter, null);
        holder = new ViewHolder(view);
        view.setTag(holder);
        holder.tv_pjrw_content.setText(renWuList.get(i).getPJRWMC());
        String stratTime = renWuList.get(i).getKSSJ();
        String endTime = renWuList.get(i).getJSSJ();
        if (stratTime.length() > 18) {
            holder.tv_pjrw_startTime.setText(stratTime.substring(0, stratTime.length() - 2));
        } else {
            holder.tv_pjrw_startTime.setText(stratTime);
        }
        if (endTime.length() > 18) {
            holder.tv_pjrw_endTime.setText(endTime.substring(0, endTime.length() - 2));
        } else {
            holder.tv_pjrw_endTime.setText(endTime);
        }
        return view;
    }

    public static class ViewHolder {
        @InjectView(R.id.tv_pjrw_content)
        TextView tv_pjrw_content;
        @InjectView(R.id.tv_pjrw_startTime)
        TextView tv_pjrw_startTime;
        @InjectView(R.id.tv_pjrw_endTime)
        TextView tv_pjrw_endTime;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
