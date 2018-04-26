package nari.app.BianDianYingYong.customview;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import net.tsz.afinal.FinalDb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import nari.app.BianDianYingYong.R;
import nari.app.BianDianYingYong.activity.ProcessedActivity;
import nari.app.BianDianYingYong.base.BaseApplication;
import nari.app.BianDianYingYong.bean.TicketListBean;
import nari.app.BianDianYingYong.bean.WzxCacheBean;
import nari.app.BianDianYingYong.utils.DataReadUtil;
import nari.app.BianDianYingYong.utils.SharedPreferencesHelper;

/**
 * Created by ShawDLee on 2017/12/26.
 */

public class CustomZuoFeiDialog implements View.OnClickListener {
    private Context context;
    private Dialog mDialog;
    private ImageView image_zuofei_cancel;
    private EditText et_zuofei_reason;
    private Button button_zuofei_reset;
    private Button button_zuofei_queren;
    private String mObjId;
    private String mRYMC;
    private String mUserId;
    private String status;
    FinalDb db;

    public CustomZuoFeiDialog(Context context, String mObjId) {
        this.context = context;
        this.mObjId = mObjId;
        initView();
    }

    private void initView() {
        this.mDialog = new Dialog(context, R.style.dialogJszlStyle);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_zuofei, null);
        image_zuofei_cancel = view.findViewById(R.id.image_zuofei_cancel);
        et_zuofei_reason = view.findViewById(R.id.et_zuofei_reason);
        button_zuofei_reset = view.findViewById(R.id.button_zuofei_reset);
        button_zuofei_queren = view.findViewById(R.id.button_zuofei_queren);
        db = FinalDb.create(BaseApplication.mApplicationInstance);
        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(context, "PersonalInformation");
        mRYMC = sharedPreferencesHelper.getStringValue("RYMC");
        mUserId = sharedPreferencesHelper.getStringValue("userId");
        status = sharedPreferencesHelper.getStringValue("status");
        this.mDialog.setContentView(view);
        this.mDialog.setCanceledOnTouchOutside(true);
        this.mDialog.show();
        initListener();
    }

    private void initListener() {
        image_zuofei_cancel.setOnClickListener(this);
        button_zuofei_reset.setOnClickListener(this);
        button_zuofei_queren.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_zuofei_cancel:
                mDialog.cancel();
                break;
            case R.id.button_zuofei_reset:
                et_zuofei_reason.getText().clear();
                break;
            case R.id.button_zuofei_queren:
                if ("".equals(et_zuofei_reason.getText().toString().trim())) {
                    Toast.makeText(context, "作废原因不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    if (status.equals("1")) {
                        toObsolete();
                    } else {
                        String a = et_zuofei_reason.getText().toString().trim();
//                        WzxCacheBean wzxCacheBean = new WzxCacheBean();
//                        wzxCacheBean.setObjId(mObjId);
//                        wzxCacheBean.setpState("00");
//                        wzxCacheBean.setWzxReson(a);
//                        db.save(wzxCacheBean);
                        TicketListBean ticketListBean=db.findAllByWhere(TicketListBean.class,"OBJ_ID  = \"" + mObjId + "\"").get(0);
                        ticketListBean.setPState("00");
                        db.update(ticketListBean);
                        Toast.makeText(context, "作废成功", Toast.LENGTH_SHORT).show();
                        ((ProcessedActivity) context).finish();

                    }
                }
                break;
            default:
                break;
        }
    }

    /*
    * 请求操作票作废接口*/
    private void toObsolete() {
        StringBuilder sb = new StringBuilder();
        //拼接请求体
        sb.append("<list>").append("\n")
                .append("<params>").append("\n")
                .append("<USER_NAME>>")  //人员名称
                .append(mRYMC)
                .append("</USER_NAME>>").append("\n")
                .append("<USER_ID>")  //用户id
                .append(mUserId)
                .append("</USER_ID>").append("\n")
                .append("<PID>")  //票id
                .append(mObjId)
                .append("</PID>").append("\n")
                .append("<MKLX>")
                .append("2")
                .append("</MKLX>").append("\n")
                .append("<YY>")
                .append("因为风险分析不到位所以在该处作废")
                .append("</YY>").append("\n")
                .append("</params>").append("\n")
                .append("</list>");
        String serviceName = "sxlp1"; //   服务名
        String interfaceName = "toObsolete";//   接口名
        Object[] params = new Object[]{sb};
        String data = DataReadUtil.getDataFromDb(serviceName, interfaceName, params);
        Log.e("lala", "点击作废按钮后的数据===============" + data);
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
                    Log.e("lala", "records=======" + records.toString());
                    JSONObject record = records.getJSONObject(0);
                    //工作票主键
                    String flag = record.getString("FLAG");
                    if ("true".equals(flag)) {
                        Toast.makeText(context, "作废成功", Toast.LENGTH_SHORT).show();
                        ((ProcessedActivity) context).finish();
                    } else {
                        Toast.makeText(context, "作废失败", Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
