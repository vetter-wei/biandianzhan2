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
import nari.app.BianDianYingYong.customview.CustomListView;

/**
 * Created by DWQ on 2017/11/28.
 * 调度令界面adapter
 */

public class OrderAdapter extends BaseAdapter {
    private Context mContext;
    private List<OrderTicketListBean> ticketList;
    private OrderContentAdapter orderContentAdapter;

    public OrderAdapter(Context mContext,List<OrderTicketListBean> list) {
        this.mContext = mContext;
        this.ticketList=list;
        orderContentAdapter=new OrderContentAdapter(mContext,ticketList);
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
            view = LayoutInflater.from(mContext).inflate(R.layout.item_activity_order_adapter, null);
            holder.lv_order_content= view.findViewById(R.id.lv_ddl_order_content);
            holder.tv_order_operationUnit = view.findViewById(R.id.tv_ddl_operation_unit);
            holder.tv_order_person = view.findViewById(R.id.tv_ddl_order_person);
            holder.tv_order_time = view.findViewById(R.id.tv_ddl_order_time);
            holder.tv_top_Line= view.findViewById(R.id.tv_topLine);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        //消除第一个item的竖线
        if (i==0){
            holder.tv_top_Line.setBackgroundResource(0x00000000);
        }else {
            holder.tv_top_Line.setBackgroundResource(R.color.colorFill);
        }
        holder.lv_order_content.setAdapter(orderContentAdapter);
        holder.tv_order_operationUnit.setText(ticketList.get(i).getSBMC());
        holder.tv_order_person.setText(ticketList.get(i).getFLR());
        holder.tv_order_time.setText(ticketList.get(i).getFLSJ());
        return view;
    }

    public class ViewHolder {
        CustomListView lv_order_content;// 展示内容的listview
        TextView tv_top_Line;// 顶部的竖线
        TextView tv_order_operationUnit;// 操作单位
        TextView tv_order_person;// 下令人
        TextView tv_order_time;// 下令时间
    }
}
