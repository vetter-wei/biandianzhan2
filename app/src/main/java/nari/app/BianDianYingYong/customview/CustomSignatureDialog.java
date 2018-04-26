package nari.app.BianDianYingYong.customview;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import nari.app.BianDianYingYong.R;
import nari.app.BianDianYingYong.adapter.UserNameAdapter;
import nari.app.BianDianYingYong.utils.DataReadUtil;
import nari.app.BianDianYingYong.utils.SharedPreferencesHelper;
import nari.app.BianDianYingYong.utils.StringUtil;


/**
 * Created by lx on 2017/10/23.
 */

public class CustomSignatureDialog {
    private Context context;
    private Dialog mDialog;
    private TextView tv_qm_title;
    private String objId;
    private String mbId;
    MyListener myListener;
    private String flag;
    private String userId;
    private List<String> nameList = new ArrayList<String>();
    private String[] mItems1;
    private String[] mItems2;
    private String RYMC;
    private String DEPTNAME;
    private Spinner spinner_club;
    private Spinner spinner_name;
    private String deptid;

    public CustomSignatureDialog(Context context, String objId, String mbId, MyListener myListener) {
        this.context = context;
        this.objId = objId;
        this.mbId = mbId;
        this.myListener = myListener;
        initView();
    }

    private void initView() {
        this.mDialog = new Dialog(context, R.style.dialogSignaStyle);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_signature, null);
        tv_qm_title = view.findViewById(R.id.tv_qm_title);
        spinner_club = view.findViewById(R.id.spinner_club);
        spinner_name = view.findViewById(R.id.spinner_name);
        final EditText et_pw = (EditText) view.findViewById(R.id.et_pw);
        Button bt_qm_sure = (Button) view.findViewById(R.id.bt_qm_sure);
        ImageView iv_qm_close = (ImageView) view.findViewById(R.id.iv_qm_close);
        //获取用户名称和用户部门
        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(context, "PersonalInformation");
        RYMC = sharedPreferencesHelper.getStringValue("RYMC");
        DEPTNAME = sharedPreferencesHelper.getStringValue("DEPTNAME");
        userId = sharedPreferencesHelper.getStringValue("userName");
        deptid = sharedPreferencesHelper.getStringValue("DEPTID");
        mItems1 = new String[]{DEPTNAME};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item, mItems1);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_club.setAdapter(adapter1);
        getSignatureData();
        iv_qm_close.setOnClickListener(new OnClickListener() { // 关闭dialog
            @Override
            public void onClick(View view) {

                mDialog.cancel();
            }
        });
        bt_qm_sure.setOnClickListener(new OnClickListener() { // 确认按钮
            @Override
            public void onClick(View view) {
                String password = et_pw.getText().toString().trim();
                String mClub = (String) spinner_club.getSelectedItem();
                String mName = (String) spinner_name.getSelectedItem();
                if (StringUtil.empty(mClub) || StringUtil.empty(mName) || StringUtil.empty(password)) {
                    Toast.makeText(context, "您的用户部门，名称或密码不能为空！", Toast.LENGTH_LONG).show();
                } else { // 再判断密码是否正确
                    PasswordChecked(userId, password);
                    if (flag.equals("true")) {
                        myListener.getSpinnerText(mName);
                        mDialog.cancel();
                    } else {
                        Toast.makeText(context, "您输入的密码有误，请重新输入", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        this.mDialog.setContentView(view);
        this.mDialog.setCanceledOnTouchOutside(true);
        this.mDialog.show();

    }

    public interface MyListener {
        void getSpinnerText(String userName);
    }

    /*
* 请求网络获取签名人员列表数据*/
    public void getSignatureData() {
        if (objId == null && mbId == null) {
            Toast.makeText(context, "用户id为空，请重新登陆", Toast.LENGTH_SHORT).show();
        } else {
            StringBuilder sb = new StringBuilder();
            //拼接请求体
            sb.append("<list>").append("\n")
                    .append("<params>").append("\n")
                    .append("<PID>").append(objId).append("</PID>").append("\n")
                    .append("<MBID>").append("16").append("</MBID>").append("\n")
                    .append("<QMYJMC>").append("监护人").append("</QMYJMC>").append("\n")
                    .append("<BMID>").append(deptid).append("</BMID>").append("\n")
                    .append("<MKLX>").append("2").append("</MKLX>").append("\n")
                    .append("</params>").append("\n")
                    .append("</list>");
            String serviceName = "sxlp1"; //   服务名
            String interfaceName = "signName";//   接口名
            Object[] params = new Object[]{sb};
            String data = DataReadUtil.getDataFromDb(serviceName, interfaceName, params);
            Log.e("lala", "CustomSignatureDialog   data=====" + data);
            if ("".equals(data) || data == null) {
                // 建立数据源
                mItems2 = new String[]{RYMC};
                // 建立Adapter并且绑定数据源
                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(context,
                        android.R.layout.simple_spinner_item, mItems2);
                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_name.setAdapter(adapter2);
            } else {
                parseSignatureData(data);
                UserNameAdapter nameAdapter = new UserNameAdapter(nameList, context);
                spinner_name.setAdapter(nameAdapter);
            }
        }
    }

    /**
     * 解析签名人员列表数据
     *
     * @author DWQ
     */
    private void parseSignatureData(String t) {
        if (t == null && "".equals(t)) {
            //  Toast.makeText(getActivity(), "没有更多数据了", Toast.LENGTH_SHORT).show();
            Log.e("lala", "走了t == null &&.equals(t)=====" + t);
        } else {
            JSONObject data;
            try {
                data = new JSONObject(t);
                JSONArray records = data.getJSONArray("records");
                if (records.length() == 0) {
                    Toast.makeText(context, "没有更多数据了", Toast.LENGTH_SHORT).show();
                    Log.e("lala", "records.length() =====" + records.length());
                } else {
                    for (int i = 0; i < records.length(); i++) {
                        JSONObject record = records.getJSONObject(i);
                        String objId = record.getString("OBJ_ID");
                        if (objId == null) {
                            objId = "";
                        }
                        String ZHMC = record.getString("ZHMC");
                        if (ZHMC == null) {
                            ZHMC = "";
                        }
                        String DLRMC = record.getString("DLRMC");
                        if (DLRMC == null) {
                            DLRMC = "";
                        }
                        nameList.add(DLRMC);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /*
* 签名密码验证*/
    public void PasswordChecked(String YHMC, String pwd) {
        StringBuilder sb = new StringBuilder();
        //拼接请求体
        sb.append("<list>").append("\n")
                .append("<params>").append("\n")
                .append("<LOGIN_NAME>").append(YHMC).append("</LOGIN_NAME>").append("\n")
                .append("<PASS_WORD>").append(pwd).append("</PASS_WORD>").append("\n")
                .append("</params>").append("\n")
                .append("</list>");
        String serviceName = "sxlp1"; //   服务名
        String interfaceName = "validateUserPass";//   接口名
        Object[] params = new Object[]{sb};
        String data = DataReadUtil.getDataFromDb(serviceName, interfaceName, params);
        Log.e("lala", "mainactivity   data======" + data);
        parsePasswordChecked(data);
    }

    /**
     * 解析签名密码验证数据
     *
     * @author DWQ
     */
    private void parsePasswordChecked(String t) {
        if (t == null && "".equals(t)) {
            //  Toast.makeText(getActivity(), "没有更多数据了", Toast.LENGTH_SHORT).show();
            Log.e("lala", "走了t == null &&.equals(t)=====" + t);
        } else {
            JSONObject data;
            try {
                data = new JSONObject(t);
                JSONArray records = data.getJSONArray("records");
                if (records.length() == 0) {
                    Toast.makeText(context, "没有更多数据了", Toast.LENGTH_SHORT).show();
                    Log.e("lala", "records.length() == 0=====" + records.length());
                } else {
                    for (int i = 0; i < records.length(); i++) {
                        JSONObject record = records.getJSONObject(i);
                        flag = record.getString("FLAG");
                        if ("null".equals(flag)) {
                            flag = "false";
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();

            }
        }
    }


    public void show() {
        this.mDialog.show();
    }

    // 设置标题
    public void setTitle(String title) {
        tv_qm_title.setText(title);
    }
}
