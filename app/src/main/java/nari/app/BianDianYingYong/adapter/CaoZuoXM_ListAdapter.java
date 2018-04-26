package nari.app.BianDianYingYong.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import nari.app.BianDianYingYong.R;
import nari.app.BianDianYingYong.bean.OperationStepBean;


/**
 * 详情中操作项目列表（最多只展示三个）
 * Created by xieshibao on 2017/12/20.
 */

public class CaoZuoXM_ListAdapter extends BaseAdapter {
    private Context context;
    List<Object> czxm_List = new ArrayList<Object>();
    int offset = 0;

    public CaoZuoXM_ListAdapter(Context context, List<Object> czxm_List) {//List<Object> czxm_List为后台取回的操作项目的值的集合，用于展示，
        this.context = context;
        this.czxm_List = czxm_List;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_czxm_adapter, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

           /**
                * description ：设置数据
                * author : xxxxcl
                * Update: 2017/12/22 0022 10:28
                */

        for(int i = 0,count = czxm_List.size();i<count && position + offset < count;i++){
            OperationStepBean operationStepBean = ((OperationStepBean)(czxm_List.get(position + offset)));
            String isDone = operationStepBean.getIsDone();
            if("√".equalsIgnoreCase(isDone)){
                holder.tv_exe_order_one.setText(operationStepBean.getOperationOrder());
                holder.tv_exe_czxm_one.setText(operationStepBean.getOperationDescription());
                holder.tv_exe_zxsj_one.setText(operationStepBean.getOperationTime());
                break;
            }else{
                Log.e("Operation","无已操作的步骤可以显示");
            }

        }
        return view;
    }

    static class ViewHolder {
        public TextView tv_exe_order_one;
        public TextView tv_exe_czxm_one;
        public TextView tv_exe_zxsj_one;

        private ViewHolder(View view) {
            tv_exe_order_one = view.findViewById(R.id.tv_exe_order_one);
            tv_exe_czxm_one = view.findViewById(R.id.tv_exe_czxm_one);
            tv_exe_zxsj_one = view.findViewById(R.id.tv_exe_zxsj_one);
        }
    }

}
