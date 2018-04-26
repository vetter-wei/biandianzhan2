package nari.app.BianDianYingYong.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import nari.app.BianDianYingYong.R;
import nari.app.BianDianYingYong.jinyi.activity_jinyi.PJBGCacheEditActivity;
import nari.app.BianDianYingYong.jinyi.activity_jinyi.PJBGEditActivity;
import nari.app.BianDianYingYong.bean.JCXBean;
import nari.app.BianDianYingYong.bean.PJXXBean;
import nari.app.BianDianYingYong.customview.KFYZDialog;
import nari.app.BianDianYingYong.customview.MSJYEditDialog;
import nari.app.BianDianYingYong.utils.StringUtil;

/**
 * Created by wubch on 2018-01-18.
 */

public class PJXXEditAdapter extends ArrayAdapter<PJXXBean> {

    public PJXXEditAdapter(Context context, List<PJXXBean> vos){
        super(context, R.layout.item_pjxx,vos);
        this.mContext =context;
    }

    private Context mContext;

    public static class ViewHold {


        @InjectView(R.id.tv_title)
        TextView tv_title;

        @InjectView(R.id.lo_has)
        View lo_has;

        @InjectView(R.id.tv_has)
        TextView tv_has;

        @InjectView(R.id.ic_has)
        ImageView ic_has;
        @InjectView(R.id.ic_none)
        ImageView ic_none;

        @InjectView(R.id.tv_none)
        TextView tv_none;

        @InjectView(R.id.ic_plus)
        View ic_plus;

        @InjectView(R.id.ic_minus)
        View ic_minus;

        @InjectView(R.id.tv_num)
        TextView tv_num;

        @InjectView(R.id.lo_num)
        View lo_num;

        @InjectView(R.id.lo_camera)
        View lo_camera;


        @InjectView(R.id.lo_suggestion)
        View lo_suggestion;

        @InjectView(R.id.lo_kfyz)
        View lo_kfyz;
        @InjectView(R.id.tv_kdfz)
        TextView tv_kdfz;




        @InjectView(R.id.tv_jcfs)
        TextView tv_jcfs;




        public ViewHold(View view) {
            ButterKnife.inject(this, view);
        }
    }

    @Override
    public View getView(final int position,  View convertView, ViewGroup parent) {
        final PJXXEditAdapter.ViewHold vh;

        convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_pjxx, null);
        vh = new PJXXEditAdapter.ViewHold(convertView);
        convertView.setTag(vh);
        final PJXXBean bean = getItem(position);
        final JCXBean jcxBean = bean.getJcxBean();
        if(StringUtil.isNull(jcxBean.getKFZ()))jcxBean.setKFZ("0");
        //vh.tv_title.setText(StringUtil.toChinese(String.valueOf(position+1))+"、"+bean.getPJDXMC());
        vh.tv_title.setText(bean.getPJXXMS());
        //vh.tv_jcfs.setText(bean.getJCFS());
        vh.tv_jcfs.setText(bean.getJCFS());
        vh.tv_kdfz.setText(jcxBean.getKDFZ()+"");

        if(!("发现次数".equals(jcxBean.getJCXMS()))){
            if(0!=jcxBean.getKDFZ()){
                jcxBean.setJCXJG("F");
            }else{
                jcxBean.setJCXJG("T");
            }
            vh.lo_has.setVisibility(View.VISIBLE);
            vh.lo_num.setVisibility(View.GONE);
            if("F".equals(jcxBean.getJCXJG())){
                jcxBean.setKDFZ(Double.parseDouble(jcxBean.getKFZ()));
                vh.tv_kdfz.setText(jcxBean.getKDFZ()+"");
                vh.ic_has.setImageResource(R.drawable.ic_check);
                vh.tv_has.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
                vh.ic_none.setImageResource(R.drawable.ic_uncheck);
                vh.tv_none.setTextColor(0xff999999);
            }else{
                jcxBean.setKDFZ(0);
                vh.tv_kdfz.setText(jcxBean.getKDFZ()+"");
                vh.ic_has.setImageResource(R.drawable.ic_uncheck);
                vh.tv_has.setTextColor(0xff999999);
                vh.ic_none.setImageResource(R.drawable.ic_check);
                vh.tv_none.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            }
            //vh.lo_num.setVisibility(View.GONE);
            vh.tv_kdfz.setText(jcxBean.getKDFZ()+"");
            vh.ic_has.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(StringUtil.isNull(jcxBean.getWTMS()) || StringUtil.isNull(jcxBean.getCLJY())){
                        Toast.makeText(mContext,"请先填写描述建议",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(0!=jcxBean.getKDFZ())return;
                    jcxBean.setKDFZ(Double.parseDouble(jcxBean.getKFZ()));
                    if(mContext instanceof PJBGEditActivity){
                        ((PJBGEditActivity)mContext).setScore(-(new BigDecimal(jcxBean.getKFZ()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue()));
                    }
                    if(mContext instanceof PJBGCacheEditActivity){
                        ((PJBGCacheEditActivity)mContext).setScore(-(new BigDecimal(jcxBean.getKFZ()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue()));
                    }

                    vh.tv_kdfz.setText(jcxBean.getKDFZ()+"");
                    vh.ic_has.setImageResource(R.drawable.ic_check);
                    vh.tv_has.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));

                    vh.ic_none.setImageResource(R.drawable.ic_uncheck);
                    vh.tv_none.setTextColor(0xff999999);
                    if(0!=jcxBean.getKDFZ()) {
                        jcxBean.setJCXJG("F");
                    }else{
                        jcxBean.setJCXJG("T");
                    }
                }
            });
            vh.ic_none.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(0 ==jcxBean.getKDFZ())return;
                    jcxBean.setKDFZ(0);
                    vh.tv_kdfz.setText(jcxBean.getKDFZ()+"");
                    if(mContext instanceof PJBGEditActivity){
                        ((PJBGEditActivity)mContext).setScore((new BigDecimal(jcxBean.getKFZ()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue()));
                    }
                    if(mContext instanceof PJBGCacheEditActivity){
                        ((PJBGCacheEditActivity)mContext).setScore((new BigDecimal(jcxBean.getKFZ()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue()));
                    }

                    vh.ic_has.setImageResource(R.drawable.ic_uncheck);
                    vh.tv_has.setTextColor(0xff999999);
                    vh.ic_none.setImageResource(R.drawable.ic_check);
                    vh.tv_none.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
                    if(0!=jcxBean.getKDFZ()) {
                        jcxBean.setJCXJG("F");
                    }else{
                        jcxBean.setJCXJG("T");
                    }
                }
            });
        }else{

            BigDecimal kfz = new BigDecimal(jcxBean.getKFZ());


            jcxBean.setJCXJG(new BigDecimal(jcxBean.getKDFZ()).divide(kfz).setScale(0,BigDecimal.ROUND_HALF_UP).intValue()+"");

            vh.lo_num.setVisibility(View.VISIBLE);
            vh.lo_has.setVisibility(View.GONE);
            vh.tv_num.setText(new BigDecimal(jcxBean.getKDFZ()).divide(kfz).setScale(0,BigDecimal.ROUND_HALF_UP).intValue()+"");
            vh.tv_kdfz.setText(jcxBean.getKDFZ()+"");
            vh.ic_minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BigDecimal kfz = new BigDecimal(jcxBean.getKFZ());
                    BigDecimal kdfz = new BigDecimal(jcxBean.getKDFZ());
                    BigDecimal jcyqsx = new BigDecimal(jcxBean.getJCYQSX());
                    if((kdfz.subtract(kfz).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue()<0)){
                        return;
                    }
                    if(mContext instanceof PJBGEditActivity){
                        ((PJBGEditActivity)mContext).setScore((kfz.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue()));
                    }
                    if(mContext instanceof PJBGCacheEditActivity){
                        ((PJBGCacheEditActivity)mContext).setScore((kfz.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue()));
                    }
                    jcxBean.setKDFZ(kdfz.subtract(kfz).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                    vh.tv_kdfz.setText(jcxBean.getKDFZ()+"");
                    vh.tv_num.setText(new BigDecimal(jcxBean.getKDFZ()).divide(kfz).setScale(0,BigDecimal.ROUND_HALF_UP).intValue()+"");
                    jcxBean.setJCXJG(new BigDecimal(jcxBean.getKDFZ()).divide(kfz).setScale(0,BigDecimal.ROUND_HALF_UP).intValue()+"");

                }
            });
            vh.ic_plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(StringUtil.isNull(jcxBean.getWTMS()) || StringUtil.isNull(jcxBean.getCLJY())){
                        Toast.makeText(mContext,"请先填写描述建议",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    BigDecimal kfz = new BigDecimal(jcxBean.getKFZ());
                    BigDecimal kdfz = new BigDecimal(jcxBean.getKDFZ());
                    BigDecimal jcyqsx = new BigDecimal(jcxBean.getJCYQSX());

                    if((kdfz.add(kfz).subtract(jcyqsx).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue()>0)){
                        Toast.makeText(mContext,"已达到扣分上限",Toast.LENGTH_LONG).show();
                        return;
                    }
                    if(mContext instanceof PJBGEditActivity){
                        ((PJBGEditActivity)mContext).setScore(-(kfz.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue()));
                    }
                    if(mContext instanceof PJBGCacheEditActivity){
                        ((PJBGCacheEditActivity)mContext).setScore(-(kfz.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue()));
                    }
                    jcxBean.setKDFZ(kdfz.add(kfz).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                    vh.tv_kdfz.setText(jcxBean.getKDFZ()+"");
                    vh.tv_num.setText(new BigDecimal(jcxBean.getKDFZ()).divide(kfz).setScale(0,BigDecimal.ROUND_HALF_UP).intValue()+"");
                    jcxBean.setJCXJG(new BigDecimal(jcxBean.getKDFZ()).divide(kfz).setScale(0,BigDecimal.ROUND_HALF_UP).intValue()+"");
                }
            });
        }
        vh.lo_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mContext instanceof PJBGEditActivity) {
                    ((PJBGEditActivity) mContext).takePhoto(bean);
                }
                if(mContext instanceof PJBGCacheEditActivity) {
                    ((PJBGCacheEditActivity) mContext).takePhoto(bean);
                }
            }
        });
        vh.lo_suggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MSJYEditDialog dialog = new MSJYEditDialog.Builder(mContext).setJCXBean(jcxBean).create();
                //WindowManager m = ((Activity)mContext).getWindowManager();
                //Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
                Window dialogWindow = dialog.getWindow();
                dialogWindow.setGravity(Gravity.CENTER);
                WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
                //p.width = (int) (d.getWidth()); // 宽度设置为屏幕的1
                dialogWindow.setAttributes(p);
                dialog.show();
            }
        });
        vh.lo_kfyz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KFYZDialog dialog = new KFYZDialog.Builder(mContext).setPJXXBean(bean).create();
                //WindowManager m = ((Activity)mContext).getWindowManager();
                //Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
                Window dialogWindow = dialog.getWindow();
                dialogWindow.setGravity(Gravity.CENTER);
                WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
                //p.width = (int) (d.getWidth()); // 宽度设置为屏幕的1
                dialogWindow.setAttributes(p);
                dialog.show();
            }
        });
        return convertView;
    }


}
