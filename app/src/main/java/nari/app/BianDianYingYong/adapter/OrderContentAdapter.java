package nari.app.BianDianYingYong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import nari.app.BianDianYingYong.R;
import nari.app.BianDianYingYong.bean.OrderTicketListBean;

/**
 * Created by DWQ on 2017/11/28.
 * 调度令内容adapter
 */

public class OrderContentAdapter extends BaseAdapter {
    private Context mContext;
    private List<OrderTicketListBean> ticketList;
    public OrderContentAdapter(Context mContext,List<OrderTicketListBean> list) {
        this.mContext = mContext;
        this.ticketList=list;
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_activity_order_content_adapter, null);
            holder.tv_accept_person = view.findViewById(R.id.tv_ddl_accpect_person);
            holder.tv_accept_time = view.findViewById(R.id.tv_ddl_accpect_time);
            holder.tv_exe_person = view.findViewById(R.id.tv_ddl_exe_person);
            holder.tv_exe_time = view.findViewById(R.id.tv_ddl_exe_time);
            holder.tv_content_title = view.findViewById(R.id.tv_ddl_content_title);
            holder.tv_item_id=view.findViewById(R.id.tv_ddl_itemId);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.tv_content_title.setText(ticketList.get(i).getCZNR());
        holder.tv_item_id.setText(ticketList.get(i).getFXH());
        holder.tv_accept_person.setText(ticketList.get(i).getSLIRMC());
        holder.tv_accept_time.setText(ticketList.get(i).getYJSJ());
        holder.tv_exe_person.setText(ticketList.get(i).getCZR());
        holder.tv_exe_time.setText(ticketList.get(i).getCZSJ());
        return view;
    }

    public class ViewHolder {
        TextView tv_content_title;// 内容标题
        TextView tv_accept_person;// 接收人
        TextView tv_accept_time;// 接收时间
        TextView tv_exe_person;// 执行人
        TextView tv_exe_time;// 执行时间
        TextView tv_item_id;
    }
}
