package nari.app.BianDianYingYong.activity;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import nari.app.BianDianYingYong.R;
import nari.app.BianDianYingYong.adapter.MainVPAdapter;
import nari.app.BianDianYingYong.base.BaseApplication;
import nari.app.BianDianYingYong.bean.BTZHXXBean;
import nari.app.BianDianYingYong.bean.ResultBean;
import nari.app.BianDianYingYong.customview.DownloadPopupWindow;
import nari.app.BianDianYingYong.customview.LXHUTSDialog;
import nari.app.BianDianYingYong.utils.DataReadUtil;
import nari.app.BianDianYingYong.utils.SharedPreferencesHelper;
import nari.mip.core.Platform;
import nari.mip.core.service.sso.SSOInfo;

import static nari.mip.core.a.a.n;
import static nari.mip.core.a.a.p;
import static nari.mip.core.a.a.s;
import static nari.mip.core.c.g;

public class MainActivity extends FragmentActivity {

    private String userName;
    private ImageView cache;
    private String status;
    private SharedPreferencesHelper sharedPreferencesHelper;
    private MainVPAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_main);
        status = getIntent().getStringExtra("status");
        sharedPreferencesHelper = new SharedPreferencesHelper(getApplicationContext(), "PersonalInformation");
        sharedPreferencesHelper.putStringValue("status", status);
        initView();
        initListener();
        if (status.equals("1")) {
            getUserName();
            getPersonalData();
        }
    }


    /**
     * 初始化页面控件
     */
    private void initView() {
        TabLayout tab_bar = (TabLayout) findViewById(R.id.tab_bar);
        ViewPager vp_main_activity = (ViewPager) findViewById(R.id.vp_main_activity);
        cache = findViewById(R.id.popupwindow_cache);
        //  vp_main_activity.setOffscreenPageLimit(3);//设置预加载的fragment个数，防止调取动态数据时，listview会显示空白，原因：就是onCreateView每次都调用导致的，这样fragment每次都会设置新的view，而调试发现，之前的view并没有被回收……这就导致了，新的view覆盖了之前设置的view，
//        navigationView = (NavigationView) findViewById(R.id.nav_view);
        adapter = new MainVPAdapter(getSupportFragmentManager(), getApplicationContext(), status);
        vp_main_activity.setAdapter(adapter);
        // tab_bar.setTabMode(TabLayout.MODE_SCROLLABLE);
        tab_bar.setupWithViewPager(vp_main_activity);
//        navigationView.setItemIconTintList(null);//设置图标为原来的颜色
//        navigationView.setNavigationItemSelectedListener(this);
        if (status.equals("1")){
            cache.setVisibility(View.VISIBLE);
        }else {
            cache.setVisibility(View.GONE);
        }
    }

    private void initListener() {
        cache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DownloadPopupWindow download = new DownloadPopupWindow(MainActivity.this, cache, adapter.getProFragment(),adapter.getExeFragment());
            }
        });
    }

    /**
     * 获取用户名称
     *
     * @return
     */
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
    public static String getUserNamestatic() {
        try {
            SSOInfo ssoInfo = Platform.getCurrent().getSSOService()
                    .getSSOInfo();// 获得MIP提供的公共信息(有用户名、密码、设备id等信息)
            // 获得用户名称
            String userName = ssoInfo.getUserName();
            Log.e("lala:", "mainactivithy mUserName==============" + userName);
            return userName;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /*
* 请求网络获取个人信息数据*/
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



                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();

            }
        }
    }

}
