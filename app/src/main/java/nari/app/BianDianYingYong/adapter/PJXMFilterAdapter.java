package nari.app.BianDianYingYong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import nari.app.BianDianYingYong.R;
import nari.app.BianDianYingYong.jinyi.activity_jinyi.PJBGEditActivity;
import nari.app.BianDianYingYong.bean.PJXMBean;
import nari.app.BianDianYingYong.jinyi.activity_jinyi.PJBGXQActivity;

/**
 * Created by wubch on 2018-01-19.
 */

public class PJXMFilterAdapter extends ArrayAdapter<PJXMBean> {


    public PJXMFilterAdapter(Context context, List<PJXMBean> vos){
        super(context, R.layout.item_pjxm_filter,vos);
        this.mContext =context;
    }

    private Context mContext;

    public static class ViewHold {


        @InjectView(R.id.tv_title)
        TextView tv_title;
        @InjectView(R.id.lo_whole_title)
        View lo_whole_title;




        public ViewHold(View view) {
            ButterKnife.inject(this, view);
        }
    }

    @Override
    public View getView(final int position,  View convertView, ViewGroup parent) {
        final PJXMFilterAdapter.ViewHold vh;

        convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_pjxm_filter, null);
        vh = new PJXMFilterAdapter.ViewHold(convertView);
        convertView.setTag(vh);
        final PJXMBean bean = getItem(position);
        vh.tv_title.setText(bean.getPJXMMC());



        vh.lo_whole_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(mContext instanceof PJBGEditActivity){

                ((PJBGEditActivity)mContext).showXM(bean);
                return;
            }
            if(mContext instanceof PJBGXQActivity){

                ((PJBGXQActivity)mContext).showXM(bean);
                return;
            }
            }
        });


        return convertView;
    }


}

