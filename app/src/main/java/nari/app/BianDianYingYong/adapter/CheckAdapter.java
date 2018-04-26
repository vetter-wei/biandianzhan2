package nari.app.BianDianYingYong.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import net.tsz.afinal.FinalDb;

import java.util.List;

import nari.app.BianDianYingYong.R;
import nari.app.BianDianYingYong.base.BaseApplication;
import nari.app.BianDianYingYong.bean.TicketListBean;
import nari.app.BianDianYingYong.utils.SharedPreferencesHelper;

import static android.R.attr.data;
import static android.media.CamcorderProfile.get;

/*
 * 待处理界面adapter
 * */
public class CheckAdapter extends BaseAdapter {

    private Context mContext;
    private List<TicketListBean> ticketList; //   票列表集合
    FinalDb db;
    private String status;

    public CheckAdapter(Context mContext, List<TicketListBean> ticketList) {
        this.mContext = mContext;
        this.ticketList = ticketList;
    }

    @Override
    public int getCount() {
        if (ticketList == null) {

            return 0;
        } else {

            return ticketList.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if (ticketList != null) {
            return ticketList.get(position);
        }
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_fragment_check_adapter, null);
            db = FinalDb.create(BaseApplication.mApplicationInstance);
            holder.tv_processed_fragment_bh = convertView.findViewById(R.id.tv_processed_fragment_bh);
            holder.tv_processed_fragment_cznr = convertView.findViewById(R.id.tv_processed_fragment_cznr);
            holder.tv_processed_fragment_czr = convertView.findViewById(R.id.tv_processed_fragment_czr);
            holder.tv_processed_fragment_time = convertView.findViewById(R.id.tv_processed_fragment_time);
            holder.tv_status = convertView.findViewById(R.id.tv_ygd_status);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//        String status = ticketList.get(position).getPZT();
//        if("21".equals(status)) { //   待审核
//            holder.iv_dcl_status.setImageResource(R.mipmap.daishenhe);
//        }else if ("31".equals(status)) { //   待执行
//            holder.iv_dcl_status.setImageResource(R.mipmap.daizhixing);
//        }else if ("41".equals(status)) { //   执行中
//            holder.iv_dcl_status.setImageResource(R.mipmap.zhixing);
//        }else if ("51".equals(status)) { //   已归档
//
//        }
        SpannableString span = new SpannableString("详情："+ticketList.get(position).getCZRW());
        span.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.color_999999)), 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        span.setSpan(new ForegroundColorSpan(Color.rgb(66,66,66)), 3, span.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(mContext, "PersonalInformation");
        status = sharedPreferencesHelper.getStringValue("status");
        holder.tv_processed_fragment_bh.setText(ticketList.get(position).getPH());
        //holder.tv_processed_fragment_cznr.setText(ticketList.get(position).getCZRW());
        holder.tv_processed_fragment_cznr.setText(span);
        holder.tv_processed_fragment_czr.setText(ticketList.get(position).getZPBMMC());
        if (status.equals("2")) {
            List<TicketListBean> list = db.findAllByWhere(TicketListBean.class, "OBJ_ID  = \"" + ticketList.get(position).getOBJ_ID() + "\"");
            if (list == null || list.size() == 0) {

            } else {
                TicketListBean bean1 = list.get(0);
                if ("00".equals(bean1.getPState())) {
                    holder.tv_status.setText("已作废");
                }
            }
        }
        String time = ticketList.get(position).getZPSJ();
        if (time.length() >= 18) {
            String timeSub = time.substring(0, time.length() - 2);
            holder.tv_processed_fragment_time.setText(timeSub);
        } else {
            holder.tv_processed_fragment_time.setText(time);
        }
        return convertView;
    }

    public static class ViewHolder {
        TextView tv_processed_fragment_bh;
        TextView tv_processed_fragment_cznr;
        TextView tv_processed_fragment_time;
        TextView tv_processed_fragment_czr;
        TextView tv_status;
    }

    public void setDatas(List<TicketListBean> ticketList) {
        this.ticketList = ticketList;
        notifyDataSetChanged();
    }
}
