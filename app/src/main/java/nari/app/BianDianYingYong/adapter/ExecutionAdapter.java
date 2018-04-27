package nari.app.BianDianYingYong.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import nari.app.BianDianYingYong.R;
import nari.app.BianDianYingYong.bean.ExecutionTicketListBean;

/*
 * 执行中界面adapter
 * */
public class ExecutionAdapter extends BaseAdapter {

    private Context mContext;
    private List<ExecutionTicketListBean> ticketList; //   票列表集合
    public ExecutionAdapter(Context mContext,List<ExecutionTicketListBean> ticketList) {//
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
                    R.layout.item_fragment_execution_adapter, null);
            holder.tv_execution_fragment_bh = convertView.findViewById(R.id.tv_execution_fragment_bh);
            holder.tv_execution_fragment_cznr =  convertView.findViewById(R.id.tv_execution_fragment_cznr);
            holder.tv_execution_fragment_czr =  convertView.findViewById(R.id.tv_execution_fragment_czr);
            holder.tv_execution_fragment_time =  convertView.findViewById(R.id.tv_execution_fragment_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//        String status = ticketList.get(position).getPZT();
//        if("21".equals(status)) { //   带审核
//            holder.iv_zxz_status.setImageResource(R.mipmap.daishenhe);
//        }else if ("31".equals(status)) { //   待执行
//            holder.iv_zxz_status.setImageResource(R.mipmap.daizhixing);
//        }else if ("41".equals(status)) { //   执行中
//            holder.iv_zxz_status.setImageResource(R.mipmap.zhixing);
//        }else if ("51".equals(status)) { //   已归档
//
//        }
        SpannableString span = new SpannableString("详情："+ticketList.get(position).getCZRW());
        span.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.color_999999)), 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        span.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.font_66)), 3, span.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        holder.tv_execution_fragment_bh.setText(ticketList.get(position).getPH());
        holder.tv_execution_fragment_cznr.setText(span);
        holder.tv_execution_fragment_czr.setText(ticketList.get(position).getZPBMMC());
        String time = ticketList.get(position).getZPSJ();
        if (time.length() >= 18) {
            String timeSub = time.substring(0, time.length() - 2);
            holder.tv_execution_fragment_time.setText(timeSub);
        } else {
            holder.tv_execution_fragment_time.setText(time);
        }
        return convertView;
    }

    public static class ViewHolder {
        TextView tv_execution_fragment_bh;
        TextView tv_execution_fragment_cznr;
        TextView tv_execution_fragment_time;
        TextView tv_execution_fragment_czr;
    }
/*
* 设置数据并刷新adapter*/
    public void setDatas(List<ExecutionTicketListBean> ticketList) {
        this.ticketList = ticketList;
        notifyDataSetChanged();

    }
}
