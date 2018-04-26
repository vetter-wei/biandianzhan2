package nari.app.BianDianYingYong.customview;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import net.tsz.afinal.FinalDb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import nari.app.BianDianYingYong.R;
import nari.app.BianDianYingYong.activity.ExecutionActivity;
import nari.app.BianDianYingYong.base.BaseApplication;
import nari.app.BianDianYingYong.bean.CheckTicketActivityBean;
import nari.app.BianDianYingYong.bean.ExecutionTicketActivityBean;
import nari.app.BianDianYingYong.bean.ExecutionTicketListBean;
import nari.app.BianDianYingYong.bean.ResultBean;
import nari.app.BianDianYingYong.bean.TicketListBean;
import nari.app.BianDianYingYong.bean.TicketListBean_Cache;
import nari.app.BianDianYingYong.fragment.ExecutionFragment;
import nari.app.BianDianYingYong.fragment.ProcessedFragment;
import nari.app.BianDianYingYong.utils.DataReadUtil;
import nari.app.BianDianYingYong.utils.ProgressDialog;
import nari.app.BianDianYingYong.utils.SharedPreferencesHelper;

import static android.R.attr.data;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static nari.app.BianDianYingYong.R.style.dialog;

/**
 * Created by ShawDLee on 2018/3/29.
 */

public class DownloadPopupWindow implements View.OnClickListener {
    private Context context;
    private LinearLayout download;
    private LinearLayout upload;
    private View view;
    private ProcessedFragment processedFragment;
    private ExecutionFragment executionFragment;
    FinalDb db;
    private String mRYMC;
    private String mUserId;
    private String status;
    private int count = 0;

    public DownloadPopupWindow(Context context, View view, ProcessedFragment processedFragment, ExecutionFragment executionFragment) {
        this.context = context;
        this.view = view;
        this.processedFragment = processedFragment;
        this.executionFragment = executionFragment;
        initView();
    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View popupView = inflater.inflate(R.layout.popupwindow_download, null);
        popupView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);//强置绘制试图
        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(context, "PersonalInformation");
        mRYMC = sharedPreferencesHelper.getStringValue("userName");
        mUserId = sharedPreferencesHelper.getStringValue("userId");
        status = sharedPreferencesHelper.getStringValue("status");
        db = FinalDb.create(BaseApplication.mApplicationInstance);
        download = popupView.findViewById(R.id.ll_download);
        upload = popupView.findViewById(R.id.ll_upload);
        PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setOutsideTouchable(true);//设置点击外部区域关闭popupwindow
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));//设置背景透明
        //设置popupwindow的显示位置
        popupWindow.showAsDropDown(view, -popupWindow.getContentView().getMeasuredWidth() + view.getWidth(), 0);
        download.setOnClickListener(this);
        upload.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_upload:
                List<ExecutionTicketActivityBean> executList = db.findAll(ExecutionTicketActivityBean.class);
                if (executList == null || executList.size() == 0) {
                    Toast.makeText(context, "无缓存数据上传", Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = 0; i < executList.size(); i++) {
                        ExecutionTicketActivityBean executbean = executList.get(i);
                        saveCZPInfo(executbean);
                    }
                    if (count==executList.size()){
                        Toast.makeText(context,"上传成功",Toast.LENGTH_SHORT).show();
                        db.deleteAll(TicketListBean.class);
                        db.deleteAll(CheckTicketActivityBean.class);
                        db.deleteAll(ExecutionTicketActivityBean.class);
                        db.deleteAll(ExecutionTicketListBean.class);
                        count=0;
                    }else {
                        Toast.makeText(context,"上传异常",Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.ll_download:
                ProgressDialog progressDialog = new ProgressDialog();
                progressDialog.showProgressDialog(context, "下载中..", false);
                db.deleteAll(TicketListBean.class);
                db.deleteAll(CheckTicketActivityBean.class);
                db.deleteAll(ExecutionTicketActivityBean.class);
                db.deleteAll(ExecutionTicketListBean.class);
                processedFragment.cacheProData();
                executionFragment.cacheExeData();
                progressDialog.cancelProgressDialog();
                Toast.makeText(context, "下载完成", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    private void saveCZPInfo(ExecutionTicketActivityBean bean) {
        StringBuilder sb = new StringBuilder();
        //拼接请求体
        sb.append("<config>")
                .append("<list>")
                .append("<params>")
                .append("<USER_NAME>").append(mRYMC).append("</USER_NAME>")
                .append("<USER_ID>").append(mUserId).append("</USER_ID>")
                .append("<PID>").append(bean.getOBJ_ID()).append("</PID>")
                .append("<MKLX>").append("2").append("</MKLX>")
                .append("<MBID>").append(bean.getMBID()).append("</MBID>")
                .append("<DYMC>").append("HB").append("</DYMC>")
                .append("</params>")
                .append("</list>")
                .append("<patterns>")
                .append("<pattern>")

                .append("<item key=\"操作开工时间\">")
                .append(bean.getCZKGSJ())
                .append("</item>")

                .append("<item key=\"操作结束时间\">")
                .append(bean.getCZJSSJ())
                .append("</item>")

                .append("<item key=\"监护下操作\">")
                .append(bean.getJHXCZ())
                .append("</item>")

                .append("<item key=\"单人操作\">")
                .append(bean.getDRCZ())
                .append("</item>")

                .append("<item key=\"检修人员操作\">")
                .append(bean.getJXRYCZ())
                .append("</item>")

                .append("<item key=\"操作步骤\">")
                .append(bean.getHWB())
                .append("</item>")

                .append("<item key=\"备注\">")
                .append(bean.getBZ())
                .append("</item>")

                .append("<item key=\"操作人名称\">")
                .append(bean.getCZRMC())
                .append("</item>")

                .append("<item key=\"监护人名称\">")
                .append(bean.getJHRMC())
                .append("</item>")

                .append("<item key=\"值班负责人名称\">")
                .append(bean.getZBFZRMC())
                .append("</item>")

                .append("</pattern>")
                .append("</patterns>")
                .append("</config>");
        String serviceName = "sxlp1"; //   服务名
        String interfaceName = "saveCZPInfo";//   接口名
        Object[] params = new Object[]{sb};
        String data = DataReadUtil.getDataFromDb(serviceName, interfaceName, params);
        Log.e("lala", "saveCZPInfo   data======" + data);
        if (data == null && "".equals(data)) {
            Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
        } else {
            JSONObject datas;
            try {
                datas = new JSONObject(data);
                JSONArray records = datas.getJSONArray("records");
                if (records.length() == 0) {
                    Toast.makeText(context, "数据为空", Toast.LENGTH_SHORT).show();
                } else {
                    JSONObject record = records.getJSONObject(0);
                    //工作票主键
                    String flag = record.getString("FLAG");
                    if ("true".equals(flag)) {
                        count++;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
