package nari.app.BianDianYingYong.jinyi.adapter_jinyi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import nari.app.BianDianYingYong.R;
import nari.app.BianDianYingYong.jinyi.bean_jinyi.ZTGNXXBean;

import static nari.mip.core.a.a.s;

/**
 * Created by ShawDLee on 2018/1/22.
 */

public class PasteAdapter extends BaseAdapter {
    private Context mContext;
    private List<ZTGNXXBean> list;

    public PasteAdapter(Context mContext, List<ZTGNXXBean> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public int getCount() {
        if (list == null) {
            return 0;
        } else {
            return list.size();
        }
    }

    @Override
    public Object getItem(int i) {
        if (list != null) {
            return list.get(i);
        }
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        view = LayoutInflater.from(mContext).inflate(
                R.layout.item_activity_paste_adapter, null);
        holder = new ViewHolder(view);
        view.setTag(holder);
        holder.tv_paste_sbmc.setText(list.get(i).getSBMC());
        String ztzt = list.get(i).getZTZT().toString();
        if ("01".equals(ztzt)) {
            holder.tv_paste_ztzt.setText("未粘贴");
        } else if ("02".equals(ztzt)) {
            holder.tv_paste_ztzt.setText("已粘贴");
        }
        String dydj = list.get(i).getDYDJ();
        if ("22".equals(dydj)) {
            holder.tv_paste_dydj.setText("交流10kV");
        } else if ("23".equals(dydj)) {
            holder.tv_paste_dydj.setText("交流15.75kV");
        } else if ("24".equals(dydj)) {
            holder.tv_paste_dydj.setText("交流20kV");
        } else if ("25".equals(dydj)) {
            holder.tv_paste_dydj.setText("交流35kV");
        } else if ("30".equals(dydj)) {
            holder.tv_paste_dydj.setText("交流66kV");
        } else if ("31".equals(dydj)) {
            holder.tv_paste_dydj.setText("交流72.5kV");
        } else if ("32".equals(dydj)) {
            holder.tv_paste_dydj.setText("交流110kV");
        } else if ("33".equals(dydj)) {
            holder.tv_paste_dydj.setText("交流220kV");
        } else if ("34".equals(dydj)) {
            holder.tv_paste_dydj.setText("交流330kV");
        } else if ("35".equals(dydj)) {
            holder.tv_paste_dydj.setText("交流500kV");
        } else if ("36".equals(dydj)) {
            holder.tv_paste_dydj.setText("交流750kV");
        } else if ("37".equals(dydj)) {
            holder.tv_paste_dydj.setText("交流1000kV");
        }
        if (list.get(i).isChecked()) {
            holder.image_paste_check.setImageResource(R.mipmap.paste_check);
        } else {
            holder.image_paste_check.setImageResource(R.mipmap.paste_uncheck);
        }
        holder.image_paste_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list.get(i).isChecked()) {
                    list.get(i).setChecked(false);
                    holder.image_paste_check.setImageResource(R.mipmap.paste_uncheck);
                } else {
                    list.get(i).setChecked(true);
                    holder.image_paste_check.setImageResource(R.mipmap.paste_check);
                }
            }
        });
        return view;
    }

    public static class ViewHolder {
        @InjectView(R.id.image_paste_check)
        ImageView image_paste_check;
        @InjectView(R.id.tv_paste_sbmc)
        TextView tv_paste_sbmc;
        @InjectView(R.id.tv_paste_dydj)
        TextView tv_paste_dydj;
        @InjectView(R.id.tv_paste_ztzt)
        TextView tv_paste_ztzt;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
