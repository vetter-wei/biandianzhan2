package nari.app.BianDianYingYong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import nari.app.BianDianYingYong.R;
import nari.app.BianDianYingYong.bean.PJDXBean;
import nari.app.BianDianYingYong.bean.PJXMBean;
import nari.app.BianDianYingYong.customview.CustomListView;
import nari.app.BianDianYingYong.utils.StringUtil;

/**
 * Created by wubch on 2018-01-18.
 */

public class PJXMEditAdapter extends ArrayAdapter<PJXMBean> {

    public PJXMEditAdapter(Context context, List<PJXMBean> vos){
        super(context, R.layout.item_pjxm,vos);
        this.mContext =context;
    }

    private Context mContext;

    public static class ViewHold {


        @InjectView(R.id.tv_num)
        TextView tv_num;

        @InjectView(R.id.lo_whole_title)
        View lo_whole_title;

        @InjectView(R.id.tv_title)
        TextView tv_title;
        @InjectView(R.id.iv_show)
        ImageView iv_show;

        @InjectView(R.id.lv_content)
        CustomListView lv_content;



        public ViewHold(View view) {
            ButterKnife.inject(this, view);
        }
    }

    @Override
    public View getView(final int position,  View convertView, ViewGroup parent) {
        final PJXMEditAdapter.ViewHold vh;

        convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_pjxm, null);
        vh = new PJXMEditAdapter.ViewHold(convertView);
        convertView.setTag(vh);
        final PJXMBean bean = getItem(position);
        /*if((position+1)<=9){
            vh.tv_num.setText(" "+(position+1)+" ");
        }else{
            vh.tv_num.setText((position+1)+"");
        }*/

        vh.tv_title.setText(bean.getPJXMMC());
        if(bean.getPjxxBeanList() == null || bean.getPjxxBeanList().size() == 0){
            vh.lv_content.setVisibility(View.GONE);
        }else{

            if(bean.isChecked()){
                PJXXEditAdapter adapter = new PJXXEditAdapter(mContext,bean.getPjxxBeanList());
                vh.tv_title.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
                vh.lv_content.setAdapter(adapter);
                vh.lv_content.setVisibility(View.VISIBLE);
                vh.iv_show.setImageResource(R.drawable.ic_checked2);
                adapter.notifyDataSetChanged();

            }else{
                vh.lv_content.setVisibility(View.GONE);
                vh.tv_title.setTextColor(mContext.getResources().getColor(R.color.font_66));
                vh.iv_show.setImageResource(R.drawable.ic_unchecked2);
            }
            vh.lo_whole_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bean.reverseCheck();
                    notifyDataSetChanged();
                }
            });
        }
        return convertView;
    }


}