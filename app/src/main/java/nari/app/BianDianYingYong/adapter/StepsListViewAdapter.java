package nari.app.BianDianYingYong.adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nari.app.BianDianYingYong.R;
import nari.app.BianDianYingYong.activity.ExecutionActivity;
import nari.app.BianDianYingYong.activity.MainActivity;
import nari.app.BianDianYingYong.activity.OperationStepsActivity;
import nari.app.BianDianYingYong.customview.CustomImageWay;
import nari.app.BianDianYingYong.customview.CustomListView;
import nari.app.BianDianYingYong.customview.CustomWZXDialog;
import nari.app.BianDianYingYong.utils.SharedPreferencesHelper;
import nari.app.BianDianYingYong.utils.StringUtil;

import static android.R.attr.data;
import static android.media.CamcorderProfile.get;
import static nari.app.BianDianYingYong.R.id.lv_exe_czxm;
import static nari.mip.core.a.a.i;


/**
 * Created by DWQ on 2017/10/18.
 */

public class StepsListViewAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private boolean mIsYes = false;
    private boolean mIsNo = false;
    private String[] mCzbzArray;
    private String[] mOrderArray;
    public HashMap<Integer, String> mStusMap = new HashMap<>();// 用来保存用户选择的状态
    private String objId;
    private String status;
    public CustomImageWay imageWay;
    private String str;
    private String mTimeSub = "";
    public String lastItemTime = "";
    public String firstItemTime = "";
    private String ph;

    public StepsListViewAdapter(Context context, String[] czbzArray, String status, String objId, String ph) {
        this.context = context;
        this.mCzbzArray = czbzArray;
        this.status = status;
        this.objId = objId;
        this.ph = ph;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (mCzbzArray == null) {
            return 0;
        } else {
            return mCzbzArray.length;
        }
    }

    @Override
    public Object getItem(int i) {
        return mCzbzArray[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (view == null) { //解决listview复用的问题
            holder = new ViewHolder();
            view = layoutInflater.inflate(R.layout.operation_steps_listview_item, null);
            holder.image_czxm_take_photo = view.findViewById(R.id.image_czxm_take_photo);
            holder.tv_czxm_yes = view.findViewById(R.id.tv_czxm_item_yes);
            holder.tv_czxm_no = view.findViewById(R.id.tv_czxm_item_no);
            holder.ll_czxm_status = view.findViewById(R.id.ll_czxm_xuanze);
            holder.tv_czxm_bh = view.findViewById(R.id.tv_czxm_listview_number);
            holder.tv_czxm_time = view.findViewById(R.id.tv_czxm_listview_time);
            holder.tv_czxm_nr = view.findViewById(R.id.tv_czxm_listview_cznr);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        String order = mCzbzArray[i];
        mOrderArray = StringUtil.splitString("\\|", order);
        if (mOrderArray[0].contains("√")) {
            holder.ll_czxm_status.setBackgroundResource(R.mipmap.czbz_yes);
            mStusMap.put(i, "√");
        } else if (mOrderArray[0].contains("×")) {
            holder.ll_czxm_status.setBackgroundResource(R.mipmap.czbz_no);
            mStusMap.put(i, "×");
            holder.tv_czxm_time.setText("");
        }
        holder.tv_czxm_bh.setText(mOrderArray[1]);
        holder.tv_czxm_nr.setText(mOrderArray[2]);
        for (int j = 0; j < mOrderArray.length; j++) {
            if (mOrderArray[mOrderArray.length - 1].length() == 19 && mOrderArray[mOrderArray.length - 1].contains(":")) {
                holder.tv_czxm_time.setText(mOrderArray[mOrderArray.length - 1]);
            } else if (mOrderArray[mOrderArray.length - 1].length() == 16 && mOrderArray[mOrderArray.length - 1].contains(":")) {
                holder.tv_czxm_time.setText(mOrderArray[mOrderArray.length - 1]);
            }
        }
        if ("pro".equals(status) || "arc".equals(status)) {

        } else {
            initListener(holder, i);

            holder.image_czxm_take_photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String number = holder.tv_czxm_bh.getText().toString();
                    CustomImageWay imageWay = new CustomImageWay((OperationStepsActivity) context, 1, 2, 3);
                    imageWay.CameraCZP(ph, number);
                }
            });
        }
        return view;

    }

    private void initListener(final ViewHolder holder, final int i) {
        holder.tv_czxm_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mStusMap.get(i - 1) == null && i > 0) {   //mStusMap.get(position -1).equals("√") || mStusMap.get(position -1).equals("×")
                    Toast.makeText(context, "不可隔步操作", Toast.LENGTH_SHORT).show();
                } else {
                    if (mStusMap.get(i) != null) {
                        Toast.makeText(context, "该步骤已操作过，不可再操作", Toast.LENGTH_SHORT).show();
                    } else {
                        mIsYes = true;
                        setFinishStatus(holder, i);
                    }
                }
            }
        });
        holder.tv_czxm_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mStusMap.get(i - 1) == null && i > 0) {   //mStusMap.get(position -1).equals("√") || mStusMap.get(position -1).equals("×")
                    Toast.makeText(context, "不可隔步操作", Toast.LENGTH_SHORT).show();
                } else {
                    if (mStusMap.get(i) != null) {
                        Toast.makeText(context, "该步骤已操作过，不可再操作", Toast.LENGTH_SHORT).show();
                    } else {
                        mIsNo = true;
                        setFinishStatus(holder, i);
                    }
                }
            }
        });
    }

    private void setFinishStatus(final ViewHolder holder, final int i) {
        if (mIsYes) {
            mStusMap.put(i, "√");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            str = simpleDateFormat.format(date);
            holder.ll_czxm_status.setBackgroundResource(R.mipmap.czbz_yes);
            holder.tv_czxm_time.setText(str);
            mIsYes = false;
        } else if (mIsNo) {
            String xuhao = holder.tv_czxm_bh.getText().toString().trim();
            CustomWZXDialog customWZXDialog = new CustomWZXDialog(context, objId, xuhao, new CustomWZXDialog.XuanzeListener() {
                @Override
                public void getXuzeStatus(String status) {
                    mStusMap.put(i, "×");
                    holder.ll_czxm_status.setBackgroundResource(R.mipmap.czbz_no);
                    holder.tv_czxm_time.setText("");
                    if (i == mCzbzArray.length - 1) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date date = new Date(System.currentTimeMillis());
                        lastItemTime = simpleDateFormat.format(date);
                    } else if (i == 0) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date date = new Date(System.currentTimeMillis());
                        firstItemTime = simpleDateFormat.format(date);
                    }
                }
            });
            mIsNo = false;
        }
    }

    public static class ViewHolder {
        public ImageView image_czxm_take_photo;
        public TextView tv_czxm_bh;
        public TextView tv_czxm_time;
        public TextView tv_czxm_nr;
        public TextView tv_czxm_yes;
        public TextView tv_czxm_no;
        public LinearLayout ll_czxm_status;
    }
}
