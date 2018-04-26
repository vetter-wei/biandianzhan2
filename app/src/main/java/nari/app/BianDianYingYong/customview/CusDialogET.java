package nari.app.BianDianYingYong.customview;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import nari.app.BianDianYingYong.R;
import nari.app.BianDianYingYong.activity.ExecutionActivity;
import nari.app.BianDianYingYong.utils.DataReadUtil;


public class CusDialogET {
    private Context context;
    private Dialog mDialog;
    private TextView mTv_re_title; //   标题
    private String mObjId;   //   票id
    private String mRYMC;   //  人员名称
    private String mUserId;   //  用户id
    private String style;   //  按钮类型  “1”：未执行  ；“2”：作废
    public CusDialogET(Context context, String mRYMC, String mUserId , String mObjId, String style) {
        this.context = context;
        this.mRYMC = mRYMC;
        this.mObjId = mObjId;
        this.mUserId = mUserId;
        this.style = style;
        initView();
    }

    private void initView() {
        this.mDialog = new Dialog(context, R.style.dialogSignaStyle);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_et, null);
        final ScrollEditText set_content = view.findViewById(R.id.set_content);
        mTv_re_title = view.findViewById(R.id.tv_re_title);
        Button bt_dx_sure = view.findViewById(R.id.bt_re_sure);
        Button bt_dx_close = view.findViewById(R.id.bt_re_close);
        bt_dx_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = set_content.getText().toString().trim();
                if("".equals(content)) {
                    Toast.makeText(context, "编辑内容不能为空", Toast.LENGTH_SHORT).show();
                }else {
                    if ("1".equals(style)) {   //   调用未执行接口
                        notExecute(content);
                    }else if ("2".equals(style)) {    //   调用作废接口
                        toObsolete(content);
                    }
                }

            }
        });
        bt_dx_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.cancel();
            }
        });
        this.mDialog.setContentView(view);
        this.mDialog.setCanceledOnTouchOutside(true);
        this.mDialog.show();
    }

    public void show() {
        this.mDialog.show();
    }

    public void setTitle(String title) {
        mTv_re_title.setText(title);
    }
    /*
       * 操作票未执行接口*/
    private void notExecute(String content) {
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
                .append(content)
                .append("</YY>").append("\n")
                .append("</params>").append("\n")
                .append("</list>");
        String serviceName = "sxlp1"; //   服务名
        String interfaceName = "notExecute";//   接口名
        Object[] params = new Object[]{sb};
        String data = DataReadUtil.getDataFromDb(serviceName, interfaceName, params);
        Log.e("lala", "未执行接口   data======" + data);
        if (data == null && "".equals(data)) {
            Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
        }else {
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
                        mDialog.cancel();
                        Toast.makeText(context, "未执行成功", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context, "未执行失败", Toast.LENGTH_SHORT).show();
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();

            }
        }
    }

    /*
* 请求操作票作废接口*/
    private void toObsolete(String content) {
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
                .append(content)
                .append("</YY>").append("\n")
                .append("</params>").append("\n")
                .append("</list>");
        String serviceName = "sxlp1"; //   服务名
        String interfaceName = "toObsolete";//   接口名
        Object[] params = new Object[]{sb};
        String data = DataReadUtil.getDataFromDb(serviceName, interfaceName, params);
        Log.e("lala","点击作废按钮后的数据===============" + data);
        if (data == null && "".equals(data)) {
            Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
        }else {
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
                        mDialog.cancel();
                        Toast.makeText(context, "作废成功", Toast.LENGTH_SHORT).show();
                        ((ExecutionActivity)context).finish();
                    }else {
                        Toast.makeText(context, "作废失败", Toast.LENGTH_SHORT).show();
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();

            }
        }
    }

}
