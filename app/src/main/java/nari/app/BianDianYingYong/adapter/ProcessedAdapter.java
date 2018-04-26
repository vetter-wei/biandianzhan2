package nari.app.BianDianYingYong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

import nari.app.BianDianYingYong.R;
import nari.app.BianDianYingYong.utils.StringUtil;

/**
 * Created by lx on 2017/11/22.
 */

public class ProcessedAdapter extends BaseAdapter {
    private String[] czbzArray;
    private Context context;
    private HashMap<Integer, View> map = new HashMap<Integer, View>();
    private String mTimeSub;
    private int count = 20;
    public ProcessedAdapter(Context context, String[] czbzArray) {
        this.context = context;
        this.czbzArray = czbzArray;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public int getCount() {
        if (czbzArray == null) {
            return 0;
        }else {
            if (czbzArray.length <= 20) {
                return czbzArray.length;
            }else {
                return count;
            }
        }


    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
     //   if (map.get(i) == null) { //解决listview复用的问题
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_pro_activity,null);
            holder.tv_pro_order = view.findViewById(R.id.tv_pro_order);
            holder.tv_pro_czxm = view.findViewById(R.id.tv_pro_czxm);
            holder.tv_pro_zxsj = view.findViewById(R.id.tv_pro_zxsj);
            holder.iv_pro_cz = view.findViewById(R.id.iv_pro_cz);
            holder.image_processed_activity_downPopup = view.findViewById(R.id.image_processed_activity_downPopup);
            //   map.put(i,view);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        String order = czbzArray[i];
        String[] orderArray = StringUtil.splitString("\\|", order);
        if (orderArray.length >= 3) {
            String time = orderArray[orderArray.length - 1];
            //  切割时间的格式只保留18:16；
            mTimeSub = time.substring(10, time.length() - 3);
        }
        if (orderArray.length == 4) {  //   若切割的字符串长度为4 则有√和×两种状态，不为4 则是中间状态
            if ("√".equals(orderArray[2])) {
                holder.iv_pro_cz.setImageResource(R.mipmap.caozuo_yes);
            } else if ("×".equals(orderArray[2])) {
                holder.iv_pro_cz.setImageResource(R.mipmap.caozuo_no);
            }
        } else {
            holder.iv_pro_cz.setImageResource(R.mipmap.caozuo_moren);
        }
        if (orderArray.length >= 2) {
            holder.tv_pro_order.setText(orderArray[0]);
            holder.tv_pro_czxm.setText(orderArray[1]);
            holder.tv_pro_zxsj.setText(mTimeSub);
        }


      //  }else  {
        //    view = map.get(i);

      //  }


        return view;
    }
    public static class ViewHolder {
        TextView tv_pro_order;
        TextView tv_pro_czxm;
        TextView tv_pro_zxsj;
        ImageView iv_pro_cz;
        ImageView image_processed_activity_downPopup;
    }
}
