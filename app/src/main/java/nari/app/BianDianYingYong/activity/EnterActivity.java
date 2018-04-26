package nari.app.BianDianYingYong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import net.tsz.afinal.FinalDb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import nari.app.BianDianYingYong.R;
import nari.app.BianDianYingYong.base.BaseActivity;
import nari.app.BianDianYingYong.base.BaseApplication;
import nari.app.BianDianYingYong.bean.JCXBean;
import nari.app.BianDianYingYong.bean.PJDXBean;
import nari.app.BianDianYingYong.bean.PJMBBean;
import nari.app.BianDianYingYong.bean.PJXMBean;
import nari.app.BianDianYingYong.bean.PJXXBean;
import nari.app.BianDianYingYong.bean.PJXZBean;
import nari.app.BianDianYingYong.bean.ResultBean;
import nari.app.BianDianYingYong.customview.LXHUTSDialog;
import nari.app.BianDianYingYong.customview.MSJYEditDialog;
import nari.app.BianDianYingYong.jinyi.activity_jinyi.JinyihuaOffActivity;
import nari.app.BianDianYingYong.jinyi.activity_jinyi.MainActivity_jinyi;
import nari.app.BianDianYingYong.utils.DataReadUtil;
import nari.app.BianDianYingYong.utils.SharedPreferencesHelper;
import nari.mip.core.Platform;
import nari.mip.core.service.sso.SSOInfo;

import static nari.app.BianDianYingYong.R.id.tv_enter_cache;
import static nari.mip.core.a.a.p;
import static nari.mip.core.c.e;


/**
 * Created by ShawDLee on 2017/12/26.
 *
 */

public class EnterActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_enter_caozuopiao;
    private TextView tv_enter_jingyihua;
    private TextView tv_enter_cache;
    private TextView tv_jyh_enter_cache;
    private SharedPreferencesHelper sharedPreferencesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.program_entrance);
        sharedPreferencesHelper = new SharedPreferencesHelper(getApplicationContext(), "PersonalInformation");
        initView();
        getUserName();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initialDataBase();
        new Thread(new Runnable() {
            @Override
            public void run() {
                getPersonalData();
            }
        }).start();

    }

    private void initView() {
        tv_enter_caozuopiao = findViewById(R.id.tv_enter_caozuopiao);
        tv_enter_jingyihua = findViewById(R.id.tv_enter_jingyihua);
        tv_enter_cache = (TextView) findViewById(R.id.tv_enter_cache);
        tv_jyh_enter_cache = (TextView) findViewById(R.id.tv_jyh_enter_cache);
        initListener();
    }

    private void initListener() {
        tv_enter_caozuopiao.setOnClickListener(this);
        tv_enter_jingyihua.setOnClickListener(this);
        tv_enter_cache.setOnClickListener(this);
        tv_jyh_enter_cache.setOnClickListener(this);
    }

    //初始化本地数据库
    private void initialDataBase() {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        FinalDb db = FinalDb.create(EnterActivity.this);

                        StringBuilder sb = new StringBuilder();
                        //拼接请求体
                        sb.append("<list>").append("\n")
                                .append("<params>").append("\n")
                                .append("<USERMC>").append(userName).append("</USERMC>").append("\n")
                                .append("</params>").append("\n")
                                .append("</list>");
                        String serviceName = "bdjyh"; //   服务名
                        Object[] params = new Object[]{sb};
                        //获取评价模板并保存
                        List<PJMBBean> pjmbBeanList = db.findAll(PJMBBean.class);
                        //Log.e("lyj",new Gson().toJson(pjmbBeanList));
                        if (pjmbBeanList == null || pjmbBeanList.size() == 0) {
                            String data = DataReadUtil.getDataFromDb(serviceName, "GetPJMB", params);
                            ResultBean<PJMBBean> resultBean = ResultBean.fromJson(data, PJMBBean.class);
                            if (resultBean == null || resultBean.getRecords() == null || resultBean.getRecords().size() == 0)
                                return;
                            for (PJMBBean bean : resultBean.getRecords()
                                    ) {
                                db.save(bean);
                            }
                        }
                        //获取评价细则并保存
                        List<PJXZBean> pjxzBeanList = db.findAll(PJXZBean.class);
                        //Log.e("lyj",new Gson().toJson(pjxzBeanList));
                        if (pjxzBeanList == null || pjxzBeanList.size() == 0) {
                            String data = DataReadUtil.getDataFromDb(serviceName, "GetPJXZ", params);
                            ResultBean<PJXZBean> resultBean = ResultBean.fromJson(data, PJXZBean.class);
                            if (resultBean == null || resultBean.getRecords() == null || resultBean.getRecords().size() == 0)
                                return;
                            for (PJXZBean bean : resultBean.getRecords()
                                    ) {
                                db.save(bean);
                            }
                        }
                        //获取评价大项并保存
                        List<PJDXBean> pjdxBeanList = db.findAll(PJDXBean.class);
                        if (pjdxBeanList == null || pjdxBeanList.size() == 0) {
                            String data = DataReadUtil.getDataFromDb(serviceName, "GetPJDX", params);
                            ResultBean<PJDXBean> resultBean = ResultBean.fromJson(data, PJDXBean.class);
                            if (resultBean == null || resultBean.getRecords() == null || resultBean.getRecords().size() == 0)
                                return;
                            for (PJDXBean bean : resultBean.getRecords()
                                    ) {
                                db.save(bean);
                            }
                        }
                        //获取评价项目并保存
                        List<PJXMBean> pjxmBeanList = db.findAll(PJXMBean.class);
                        if (pjxmBeanList == null || pjxmBeanList.size() == 0) {
                            String data = DataReadUtil.getDataFromDb(serviceName, "GetPJXM", params);
                            ResultBean<PJXMBean> resultBean = ResultBean.fromJson(data, PJXMBean.class);
                            if (resultBean == null || resultBean.getRecords() == null || resultBean.getRecords().size() == 0)
                                return;
                            for (PJXMBean bean : resultBean.getRecords()
                                    ) {
                                db.save(bean);
                            }
                        }
                        //获取评价小项并保存
                        List<PJXXBean> pjxxBeanList = db.findAll(PJXXBean.class);
                        if (pjxxBeanList == null || pjxxBeanList.size() == 0) {
                            String data = DataReadUtil.getDataFromDb(serviceName, "GetPJXX", params);
                            ResultBean<PJXXBean> resultBean = ResultBean.fromJson(data, PJXXBean.class);
                            if (resultBean == null || resultBean.getRecords() == null || resultBean.getRecords().size() == 0)
                                return;
                            for (PJXXBean bean : resultBean.getRecords()
                                    ) {
                                db.save(bean);
                            }
                        }
                        //获取检查项并保存
                        List<JCXBean> jcxBeanList = db.findAll(JCXBean.class);
                        if (jcxBeanList == null || jcxBeanList.size() == 0) {
                            String data = DataReadUtil.getDataFromDb(serviceName, "GetJCX", params);
                            ResultBean<JCXBean> resultBean = ResultBean.fromJson(data, JCXBean.class);
                            if (resultBean == null || resultBean.getRecords() == null || resultBean.getRecords().size() == 0)
                                return;
                            for (JCXBean bean : resultBean.getRecords()
                                    ) {
                                db.save(bean);
                            }
                        }


                    }
                }

        ).start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_enter_caozuopiao:
                Intent intent = new Intent(EnterActivity.this, MainActivity.class);
                intent.putExtra("status","1");
                startActivity(intent);
                break;
            case R.id.tv_enter_jingyihua:
                Intent intent1 = new Intent(EnterActivity.this, MainActivity_jinyi.class);
                startActivity(intent1);
                break;
            case R.id.tv_enter_cache:
                Intent intent2 = new Intent(EnterActivity.this, MainActivity.class);
                intent2.putExtra("status","2");
                startActivity(intent2);
                break;
            case R.id.tv_jyh_enter_cache:
                Intent intent3 = new Intent(EnterActivity.this, JinyihuaOffActivity.class);
                startActivity(intent3);
                break;
        }
    }

    /**
     * 获取用户名称
     *
     * @return
     */
    String userName;

    public String getUserName() {
        try {
            SSOInfo ssoInfo = Platform.getCurrent().getSSOService()
                    .getSSOInfo();// 获得MIP提供的公共信息(有用户名、密码、设备id等信息)
            // 获得用户名称
            userName = ssoInfo.getUserName();
            Log.e("lala:", "mainactivithy mUserName==============" + userName);
            return userName;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public void getPersonalData() {

        try {
            StringBuilder sb = new StringBuilder();
            //拼接请求体
            sb.append("<list>").append("\n")
                    .append("<params>").append("\n")
                    .append("<LOGIN_NAME>").append(userName).append("</LOGIN_NAME>").append("\n")
                    .append("</params>").append("\n")
                    .append("</list>");
            String serviceName = "sxlp1"; //   服务名
            String interfaceName = "getLoginInfo";//   接口名
            Object[] params = new Object[]{sb};
            Log.e("-------->", "走了");
            String data = DataReadUtil.getDataFromDb(serviceName, interfaceName, params);
            Log.e("lala", "mainactivity   data======" + data);
            //ResultBean<BTZHXXBean> dto = ResultBean.fromJson(data, BTZHXXBean.class);
            parsePersonalInformation(data);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析个人信息数据
     *
     * @author DWQ
     */
    private void parsePersonalInformation(String t) {
        if (t == null && "".equals(t)) {
            //  Toast.makeText(getActivity(), "没有更多数据了", Toast.LENGTH_SHORT).show();
            Log.e("lala", "走了t == null &&.equals(t)=====" + t);
            BaseApplication.HAS_INTNET = false;

        } else {
            JSONObject data;
            try {
                data = new JSONObject(t);
                JSONArray records = data.getJSONArray("records");
                if (records.length() == 0) {
                    Toast.makeText(getApplicationContext(), "没有更多数据了", Toast.LENGTH_SHORT).show();
                    Log.e("lala", "records.length() == 0=====" + records.length());
                } else {
                    for (int i = 0; i < records.length(); i++) {
                        JSONObject record = records.getJSONObject(i);
                        //人员id
                        String RYID = record.getString("RYID");
                        if ("null".equals(RYID)) {
                            RYID = "";
                        }
                        //人员名称
                        String RYMC = record.getString("RYMC");
                        if ("null".equals(RYMC)) {
                            RYMC = "";
                        }
                        String BMBM = record.getString("BMBM");    // 部门编码
                        if ("null".equals(BMBM)) {
                            BMBM = "";
                        }

                        String DEPTID = record.getString("DEPTID");// 所属部门名称id
                        if ("null".equals(DEPTID)) {
                            DEPTID = "";
                        }
                        String DEPTNAME = record.getString("DEPTNAME");// 所属部门名称
                        if ("null".equals(DEPTNAME)) {
                            DEPTNAME = "";
                        }
                        String SSDWID = record.getString("SSDWID");// 所属单位名称id
                        if ("null".equals(SSDWID)) {
                            SSDWID = "";
                        }
                        String SSDWMC = record.getString("SSDWMC");// 所属单位名称
                        if ("null".equals(SSDWMC)) {
                            SSDWMC = "";
                        }
                        String SSDSID = record.getString("SSDSID");// 所属地市名称id
                        if ("null".equals(SSDSID)) {
                            SSDSID = "";
                        }
                        String SSDSMC = record.getString("SSDSMC");// 所属地市名称
                        if ("null".equals(SSDSMC)) {
                            SSDSMC = "";
                        }

                        String YWDWID = record.getString("YWDWID");// 运维单位名称id
                        if ("null".equals(YWDWID)) {
                            YWDWID = "";
                        }
                        String YWDWMC = record.getString("YWDWMC");// 运维单位名称
                        if ("null".equals(YWDWMC)) {
                            YWDWMC = "";
                        }
                        sharedPreferencesHelper.putStringValue("userName", userName);
                        sharedPreferencesHelper.putStringValue("DEPTNAME", DEPTNAME);
                        sharedPreferencesHelper.putStringValue("DEPTID", DEPTID);
                        sharedPreferencesHelper.putStringValue("RYMC", RYMC);
                        sharedPreferencesHelper.putStringValue("userId", RYID);

                        BaseApplication.HAS_INTNET = true;

                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();

            }
        }
    }

}
