package nari.app.BianDianYingYong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import nari.app.BianDianYingYong.R;
import nari.app.BianDianYingYong.bean.ArchivedTicketListBean;

/**
 * Created by DWQ on 2017/11/1.
 * 已归档界面adapter
 */

public class ArchivedAdapter extends BaseAdapter {
    private Context mContext;
    private List<ArchivedTicketListBean> ticketList; //   票列表集合
    public ArchivedAdapter(Context mContext,List<ArchivedTicketListBean> ticketList) {
        this.mContext = mContext;
        this.ticketList = ticketList;
    }
    @Override
    public int getCount() {
        if (ticketList == null) {
            return 0;
        }else {
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
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_fragment_archived_adapter, null);
            holder.tv_archived_fragment_bh = convertView.findViewById(R.id.tv_archived_fragment_bh);
            holder.tv_archived_fragment_cznr =  convertView.findViewById(R.id.tv_archived_fragment_cznr);
            holder.tv_archived_fragment_czr =  convertView.findViewById(R.id.tv_archived_fragment_czr);
            holder.tv_archived_fragment_time =  convertView.findViewById(R.id.tv_archived_fragment_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

//        String status = ticketList.get(position).getPZT();
//        if("21".equals(status)) { //   带审核
//            holder.iv_ygd_status.setImageResource(R.mipmap.daishenhe);
//        }else if ("31".equals(status)) { //   待执行
//            holder.iv_ygd_status.setImageResource(R.mipmap.daizhixing);
//        }else if ("41".equals(status)) { //   执行中
//            holder.iv_ygd_status.setImageResource(R.mipmap.zhixing);
//        }else if ("51".equals(status)) { //   已归档
//
//        }
        holder.tv_archived_fragment_bh.setText(ticketList.get(position).getPH());
        holder.tv_archived_fragment_cznr.setText(ticketList.get(position).getCZRW());
        holder.tv_archived_fragment_czr.setText(ticketList.get(position).getZPBMMC());
        String time = ticketList.get(position).getZPSJ();
        if (time.length() >= 18) {
            String timeSub = time.substring(0, time.length() - 2);
            holder.tv_archived_fragment_time.setText(timeSub);
        } else {
            holder.tv_archived_fragment_time.setText(time);
        }
        return convertView;
    }

    public static class ViewHolder {
        TextView tv_archived_fragment_bh;
        TextView tv_archived_fragment_cznr;
        TextView tv_archived_fragment_time;
        TextView tv_archived_fragment_czr;
    }
}
