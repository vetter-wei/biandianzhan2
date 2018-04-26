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
import nari.app.BianDianYingYong.utils.DataReadUtil;

/**
 * Created by ShawDLee on 2017/12/29.
 */

public class CustomWZXDialog {
    private Context context;
    private Dialog mDialog;
    private TextView tv_wzx_title; //   标题
    private String objId;
    private String xuhao;
    XuanzeListener listener;

    public CustomWZXDialog(Context context, String objId, String xuhao,XuanzeListener listener) {
        this.context = context;
        this.objId = objId;
        this.xuhao = xuhao;
        this.listener=listener;
        initView();
    }

    private void initView() {
        this.mDialog = new Dialog(context, R.style.dialogSignaStyle);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_wzx, null);
        final ScrollEditText set_content = view.findViewById(R.id.se_wzx_nr);
        tv_wzx_title = view.findViewById(R.id.tv_wzx_title);
        Button bt_wzx_sure = view.findViewById(R.id.bt_wzx_sure);
        Button bt_wzx_close = view.findViewById(R.id.bt_wzx_close);
        bt_wzx_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = set_content.getText().toString().trim();
                if ("".equals(content)) {
                    Toast.makeText(context, "编辑内容不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    listener.getXuzeStatus("sure");
                    saveCzbzNotExecuteReson(content);
                }

            }
        });
        bt_wzx_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.cancel();
            }
        });
        this.mDialog.setContentView(view);
        this.mDialog.setCanceledOnTouchOutside(true);
        this.mDialog.show();
    }
    public interface XuanzeListener{
        void getXuzeStatus(String status);
    }

    public void show() {
        this.mDialog.show();
    }

    public void setTitle(String title) {
        tv_wzx_title.setText(title);
    }

    /*
       * 操作票未执行接口*/
    private void saveCzbzNotExecuteReson(String content) {
        StringBuilder sb = new StringBuilder();
        //拼接请求体
        sb.append("<list>").append("\n")
                .append("<params>").append("\n")
                .append("<PID>").append(objId).append("</PID>").append("\n")
                .append("<YY>").append(content).append("</YY>").append("\n")
                .append("<CZBZXH>").append(xuhao).append("</CZBZXH>").append("\n")
                .append("</params>").append("\n")
                .append("</list>");
        String serviceName = "sxlp1"; //   服务名
        String interfaceName = "saveCzbzNotExecuteReson";//   接口名
        Object[] params = new Object[]{sb};
        String data = DataReadUtil.getDataFromDb(serviceName, interfaceName, params);
        Log.e("lala", "未执行接口   data======" + data);
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
                        mDialog.cancel();
                        Toast.makeText(context, "不执行成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "不执行失败", Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();

            }
        }
    }

}
