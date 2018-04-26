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
import nari.app.BianDianYingYong.activity.OrderActivity;
import nari.app.BianDianYingYong.bean.PJDXBean;
import nari.app.BianDianYingYong.customview.CustomListView;
import nari.app.BianDianYingYong.utils.StringUtil;

/**
 * Created by wubch on 2018-01-17.
 */

public class PJDXEditAdapter extends ArrayAdapter<PJDXBean> {

    public PJDXEditAdapter(Context context, List<PJDXBean> vos){
        super(context, R.layout.item_pjdx,vos);
        this.mContext =context;
    }

    private Context mContext;

    public static class ViewHold {


        @InjectView(R.id.tv_title)
        TextView tv_title;
        @InjectView(R.id.lo_whole_title)
        View lo_whole_title;

        @InjectView(R.id.lv_content)
        CustomListView lv_content;



        public ViewHold(View view) {
            ButterKnife.inject(this, view);
        }
    }

    @Override
    public View getView(final int position,  View convertView, ViewGroup parent) {
        final ViewHold vh;

        convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_pjdx, null);
        vh = new ViewHold(convertView);
        convertView.setTag(vh);
        final PJDXBean bean = getItem(position);
        vh.tv_title.setText(StringUtil.toChinese(String.valueOf(position+1))+"、"+bean.getPJDXMC());

        if(bean.getPjxmBeanList() == null || bean.getPjxmBeanList().size() == 0){
            vh.lv_content.setVisibility(View.GONE);
        }else{

            if(bean.isChecked()){
                PJXMEditAdapter adapter = new PJXMEditAdapter(mContext,bean.getPjxmBeanList());
                vh.lv_content.setVisibility(View.VISIBLE);
                vh.lv_content.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }else{
                vh.lv_content.setVisibility(View.GONE);
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