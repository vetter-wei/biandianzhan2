package nari.app.BianDianYingYong.jinyi.adapter_jinyi;

import android.app.Activity;
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
import nari.app.BianDianYingYong.jinyi.activity_jinyi.PJBGEditActivity;
import nari.app.BianDianYingYong.jinyi.activity_jinyi.PJBGXQActivity;
import nari.app.BianDianYingYong.jinyi.bean_jinyi.PJBGDetailsBean;
import nari.app.BianDianYingYong.jinyi.customview_jinyi.CircleNumberProgressBar;

import static android.media.CamcorderProfile.get;

/**
 * Created by ShawDLee on 2018/1/17.
 */

public class PingJiaBaoGaoAdapter extends BaseAdapter {
    private Context mContext;
    private List<PJBGDetailsBean> baoGaoDetailsList;

    public PingJiaBaoGaoAdapter(Context mContext, List<PJBGDetailsBean> baoGaoDetailsList) {
        this.mContext = mContext;
        this.baoGaoDetailsList = baoGaoDetailsList;
    }

    @Override
    public int getCount() {
        if (baoGaoDetailsList == null) {

            return 0;
        } else {

            return baoGaoDetailsList.size();
        }
    }

    @Override
    public Object getItem(int i) {
        if (baoGaoDetailsList != null) {
            return baoGaoDetailsList.get(i);
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
        final PJBGDetailsBean bean = baoGaoDetailsList.get(i);
        view = LayoutInflater.from(mContext).inflate(R.layout.item_activity_pjrw_adapter, null);
        holder = new ViewHolder(view);
        view.setTag(holder);
        holder.tv_pjrwDetails_sbmc.setText(baoGaoDetailsList.get(i).getSBMC());
        holder.tv_pjrwDetails_dzmc.setText(baoGaoDetailsList.get(i).getDZMC());
        String pjsj = baoGaoDetailsList.get(i).getPJSJ();
        if (pjsj.length() > 18) {
            holder.tv_pjrwDetails_pjsj.setText(pjsj.substring(0, pjsj.length() - 2));
        } else {
            holder.tv_pjrwDetails_pjsj.setText(pjsj);
        }
        String bgzt = baoGaoDetailsList.get(i).getBGZT();
        if ("01".equals(bgzt)) {

            holder.tv_pjrwDetails_tj.setImageResource(R.mipmap.ic_state_edit);
        } else if ("02".equals(bgzt)) {

            holder.tv_pjrwDetails_tj.setImageResource(R.mipmap.ic_state_submit);
        }
        if ("".equals(baoGaoDetailsList.get(i).getPJZ().trim())) {

            holder.tv_pjrwDetails_point.setVisibility(View.GONE);
            holder.tv_weipingjia.setVisibility(View.VISIBLE);
            holder.tv_fen.setVisibility(View.GONE);
        } else {
            //holder.pb_pjrwDetails_jdt.setProgress(Integer.parseInt(baoGaoDetailsList.get(i).getPJZ().trim()));

            holder.tv_fen.setVisibility(View.VISIBLE);
            holder.tv_pjrwDetails_point.setVisibility(View.VISIBLE);
            holder.tv_weipingjia.setVisibility(View.GONE);
            holder.tv_pjrwDetails_point.setText(baoGaoDetailsList.get(i).getPJZ());
        }
        holder.lo_whole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (bean.getBGZT()) {
                    case "01":
                        PJBGEditActivity.anctionStart((Activity) mContext, bean.getOBJ_ID(), bean.getPJSB_ID(), bean.getPJMB_ID(), bean.getZJRW_ID(), bean.getPJZ(),bean.getPJXZ_ID(), 2);
                        break;
                    case "02":
                        PJBGXQActivity.anctionStart((Activity) mContext, bean.getOBJ_ID(), bean.getPJSB_ID(), bean.getPJMB_ID(), bean.getZJRW_ID(), bean.getPJZ(),bean.getPJXZ_ID(), 2);
                        break;
                }
            }
        });
        return view;
    }

    public static class ViewHolder {
        @InjectView(R.id.tv_pjrwDetails_sbmc)
        TextView tv_pjrwDetails_sbmc;
        @InjectView(R.id.tv_pjrwDetails_dzmc)
        TextView tv_pjrwDetails_dzmc;
        @InjectView(R.id.tv_pjrwDetails_pjsj)
        TextView tv_pjrwDetails_pjsj;
        @InjectView(R.id.tv_pjrwDetails_point)
        TextView tv_pjrwDetails_point;
        @InjectView(R.id.pb_pjrwDetails_jdt)
        CircleNumberProgressBar pb_pjrwDetails_jdt;
        @InjectView(R.id.tv_pjrwDetails_tj)
        ImageView tv_pjrwDetails_tj;
        @InjectView(R.id.tv_fen)
        TextView tv_fen;
        @InjectView(R.id.tv_weipingjia)
        TextView tv_weipingjia;
        @InjectView(R.id.lo_whole)
        View lo_whole;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
