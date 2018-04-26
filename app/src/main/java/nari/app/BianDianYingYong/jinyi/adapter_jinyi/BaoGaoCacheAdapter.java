package nari.app.BianDianYingYong.jinyi.adapter_jinyi;

import android.app.Activity;
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
import nari.app.BianDianYingYong.jinyi.activity_jinyi.PJBGCacheEditActivity;
import nari.app.BianDianYingYong.jinyi.activity_jinyi.PJBGEditActivity;
import nari.app.BianDianYingYong.jinyi.activity_jinyi.PJBGXQActivity;
import nari.app.BianDianYingYong.jinyi.bean_jinyi.PingjiabaogaoBean;
import nari.app.BianDianYingYong.jinyi.customview_jinyi.CircleNumberProgressBar;
import nari.app.BianDianYingYong.utils.ProgressDialog;

/**
 * Created by 李元甲 on 2018-04-02.
 */

public class BaoGaoCacheAdapter extends BaseAdapter {
    private Context mContext;
    private List<PingjiabaogaoBean> baoGaoList;
    public static ProgressDialog progressDialog;

    public BaoGaoCacheAdapter(Context context, List<PingjiabaogaoBean> baoGaoList) {
        this.mContext = context;
        this.baoGaoList = baoGaoList;
    }

    @Override
    public int getCount() {
        if (baoGaoList == null) {

            return 0;
        } else {

            return baoGaoList.size();
        }
    }

    @Override
    public Object getItem(int i) {
        if (baoGaoList != null) {
            return baoGaoList.get(i);
        }
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        BaoGaoAdapter.ViewHolder holder;
        final PingjiabaogaoBean bean = baoGaoList.get(i);
        view = LayoutInflater.from(mContext).inflate(
                R.layout.item_fragment_baogao_adapter, null);
        holder = new BaoGaoAdapter.ViewHolder(view);
        view.setTag(holder);
        holder.tv_pjbg_sbmc.setText(baoGaoList.get(i).getSBMC());
        holder.tv_pjbg_sblx.setText(baoGaoList.get(i).getSBLXMC());
        holder.tv_pjbg_dzmc.setText(baoGaoList.get(i).getDZMC());
        holder.tv_pjbg_yxbh.setText(baoGaoList.get(i).getYXBH());
        holder.tv_pjbg_pjsj.setText(baoGaoList.get(i).getPJSJ());
        String dydj = baoGaoList.get(i).getDYDJ();
        if ("22".equals(dydj)) {
            holder.tv_pjbg_dydj.setText("交流10kV");
        } else if ("23".equals(dydj)) {
            holder.tv_pjbg_dydj.setText("交流15.75kV");
        } else if ("24".equals(dydj)) {
            holder.tv_pjbg_dydj.setText("交流20kV");
        } else if ("25".equals(dydj)) {
            holder.tv_pjbg_dydj.setText("交流35kV");
        } else if ("30".equals(dydj)) {
            holder.tv_pjbg_dydj.setText("交流66kV");
        } else if ("31".equals(dydj)) {
            holder.tv_pjbg_dydj.setText("交流72.5kV");
        } else if ("32".equals(dydj)) {
            holder.tv_pjbg_dydj.setText("交流110kV");
        } else if ("33".equals(dydj)) {
            holder.tv_pjbg_dydj.setText("交流220kV");
        } else if ("34".equals(dydj)) {
            holder.tv_pjbg_dydj.setText("交流330kV");
        } else if ("35".equals(dydj)) {
            holder.tv_pjbg_dydj.setText("交流500kV");
        } else if ("36".equals(dydj)) {
            holder.tv_pjbg_dydj.setText("交流750kV");
        } else if ("37".equals(dydj)) {
            holder.tv_pjbg_dydj.setText("交流1000kV");
        }
        if ("".equals(baoGaoList.get(i).getPJZ().trim())) {
            holder.pb_pjbg_jdt.setProgress(0);
            holder.tv_pjbg_point.setText("未评价");
        } else {

            holder.tv_pjbg_point.setText(baoGaoList.get(i).getPJZ() + "分");
        }
        holder.lo_whole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (bean.getBGZT()) {
                    case "01":
                        //progressDialog = new ProgressDialog();
                       // progressDialog.showProgressDialog(mContext, "加载中..", false);
                        PJBGCacheEditActivity.anctionStart((Activity) mContext, bean.getOBJ_ID(), bean.getPJSB_ID(), bean.getPJMB_ID(), bean.getZJRW_ID(), bean.getPJZ(), bean.getPJXZ_ID(), 2);
                        break;
                    case "02":
                        PJBGXQActivity.anctionStart((Activity) mContext, bean.getOBJ_ID(), bean.getPJSB_ID(), bean.getPJMB_ID(), bean.getZJRW_ID(), bean.getPJZ(), bean.getPJXZ_ID(), 2);
                        break;
                }
            }
        });
        return view;
    }

    public static class ViewHolder {
        @InjectView(R.id.tv_pjbg_sbmc)
        TextView tv_pjbg_sbmc;
        @InjectView(R.id.tv_pjbg_sblx)
        TextView tv_pjbg_sblx;
        @InjectView(R.id.tv_pjbg_dzmc)
        TextView tv_pjbg_dzmc;
        @InjectView(R.id.tv_pjbg_yxbh)
        TextView tv_pjbg_yxbh;
        @InjectView(R.id.tv_pjbg_pjsj)
        TextView tv_pjbg_pjsj;
        @InjectView(R.id.pb_pjbg_jdt)
        CircleNumberProgressBar pb_pjbg_jdt;
        @InjectView(R.id.tv_pjbg_point)
        TextView tv_pjbg_point;

        @InjectView(R.id.lo_whole)
        View lo_whole;

        @InjectView(R.id.tv_pjbg_dydj)
        TextView tv_pjbg_dydj;


        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}

