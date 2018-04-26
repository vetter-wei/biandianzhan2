package nari.app.BianDianYingYong.jinyi.activity_jinyi;


import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import nari.app.BianDianYingYong.R;
import nari.app.BianDianYingYong.base.BaseActivity;
import nari.app.BianDianYingYong.customview.CustomDatePicker;
import nari.app.BianDianYingYong.jinyi.adapter_jinyi.MainVPAdapter_jinyi;
import nari.app.BianDianYingYong.jinyi.customview_jinyi.MyRadioGroup;
import nari.app.BianDianYingYong.jinyi.fragment_jinyi.BaogaoFragment;
import nari.app.BianDianYingYong.jinyi.fragment_jinyi.RenwuFragment;
import nari.app.BianDianYingYong.utils.DataReadUtil;
import nari.app.BianDianYingYong.utils.HttpAsycTask;
import nari.app.BianDianYingYong.utils.ParamBean;
import nari.app.BianDianYingYong.utils.SharedPreferencesHelper;
import nari.mip.core.Platform;
import nari.mip.core.service.sso.SSOInfo;

import static android.R.attr.data;


public class MainActivity_jinyi extends BaseActivity implements View.OnClickListener {

    private String userName; // 用户名
    private DrawerLayout drawerLayout;
    private LinearLayout ll_report_screen; // 报告筛选
    private LinearLayout ll_renwu_screen; // 任务筛选
    private ImageView search_menu;
    private TextView tv_pjrw_screen_kssj; // 开始时间
    private TextView tv_pjrw_screen_jssj; // 结束时间
    private TimePickerView pvCustomTime; // 时间选择器
    private int mTimeStyle;
    private TextView tv_screen_reset; // 重置
    private TextView tv_screen_sure; // 确认
    private MainVPAdapter_jinyi adapter;
    private ViewPager vp;
    private EditText et_pjbg_screen_dz;
    private EditText et_pjbg_screen_sbmc;
    private EditText et_pjbg_screen_yxbh;
    private MyRadioGroup radioGroup_sblx;
    private MyRadioGroup radioGroup_dydj;
    private String sblx = "";
    private String dydj = "";
    // 筛选按钮
    private RadioButton rb_zbyq;
    private RadioButton rb_dlq;
    private RadioButton rb_glkg;
    private RadioButton rb_kgg;
    private RadioButton rb_blz;
    private RadioButton rb_blq;
    private RadioButton rb_mx;
    private RadioButton rb_dkq;
    private RadioButton rb_dlhgq;
    private RadioButton rb_dyhgq;
    private RadioButton rb_dldrq;
    private RadioButton rb_syb;
    private RadioButton rb_jdw;
    private RadioButton rb_zbq;
    private RadioButton rb_jdq;
    private RadioButton rb_zydxt;
    private RadioButton rb_pbdkq;
    private RadioButton rb_jllbq;
    private RadioButton rb_hlbyq;
    private RadioButton rb_zndl;
    private RadioButton rb_hlz;
    private RadioButton rb_jzghl;
    private RadioButton rb_hlzbh;
    private RadioButton rb_wgbcq;
    private RadioButton rb_hlzkz;
    private RadioButton rb_clbc;
    private RadioButton rb_wgfsqs;
    private RadioButton rb_zhdq;
    private RadioButton rb_bg_110;
    private RadioButton rb_bg_220;
    private RadioButton rb_bg_1000;
    private RadioButton rb_bg_10;
    private RadioButton rb_bg_15_75;
    private RadioButton rb_bg_20;
    private RadioButton rb_bg_35;
    private RadioButton rb_bg_66;
    private RadioButton rb_bg_72_5;
    private RadioButton rb_bg_330;
    private RadioButton rb_bg_500;
    private RadioButton rb_bg_750;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_main_jinyi);
//        showProgressDialog("加载中..",false);
        getUserName();
        getPersonalData();
        getScreenData();
        initView();
//        cancelProgressDialog();
    }

    /*
  * 请求网络获取筛选列表数据*/
    public void getScreenData() {

        StringBuilder sb = new StringBuilder();
        //拼接请求体
        sb.append("<list>").append("\n")
                .append("<params>").append("\n")
                .append("<USERMC>").append(userName).append("</USERMC>").append("\n")
                .append("</params>").append("\n")
                .append("</list>");
        String serviceName = "bdjyh"; //   服务名
        String interfaceName = "GetSXTJ";//   接口名
        Object[] params = new Object[]{sb};
        String data = DataReadUtil.getDataFromDb(serviceName, interfaceName, params);
        Log.e("lala", "getScreenData()   data======" + data);
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
            //ResultBean<BTZHXXBean> dto = ResultBean.fromJson(data, BTZHXXBean.class);
            parsePersonalInformation(data);

        } catch (Exception e) {
            e.printStackTrace();
        }
        /*showProgressDialog("",false);
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
        HttpAsycTask asycTask = new HttpAsycTask();
        HttpAsycTask.DataChuli chili = new HttpAsycTask.DataChuli() {
            @Override
            public void chuli(String data) {
                Toast.makeText(MainActivity_jinyi.this,data,Toast.LENGTH_SHORT).show();
                cancelProgressDialog();
            }
        };
        asycTask.setDataChuli(chili);
        ParamBean paramBean = new ParamBean();
        paramBean.setIntfacre("getLoginInfo");
        paramBean.setService("sxlp1");
        paramBean.setParams(sb.toString());
        asycTask.execute(paramBean);*/
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
                        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(getApplicationContext(), "PersonalInformation");
                        sharedPreferencesHelper.putStringValue("userName", userName);
                        sharedPreferencesHelper.putStringValue("DEPTNAME", DEPTNAME);
                        sharedPreferencesHelper.putStringValue("RYMC", RYMC);
                        sharedPreferencesHelper.putStringValue("userId", RYID);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();

            }
        }
    }

    /**
     * 初始化页面控件
     */
    private void initView() {

        TabLayout tab_bar = (TabLayout) findViewById(R.id.tab_bar_jinyi);
        vp = (ViewPager) findViewById(R.id.vp_jinyi_main_activity);
        search_menu = findViewById(R.id.image_search_menu);
        drawerLayout = findViewById(R.id.drawerLayout_jinyi);
        ll_report_screen = findViewById(R.id.ll_report_screen);
        ll_renwu_screen = findViewById(R.id.ll_renwu_screen);
        tv_pjrw_screen_kssj = findViewById(R.id.tv_pjrw_screen_kssj);
        tv_pjrw_screen_jssj = findViewById(R.id.tv_pjrw_screen_jssj);
        et_pjbg_screen_dz = findViewById(R.id.et_pjbg_screen_dz);
        et_pjbg_screen_sbmc = findViewById(R.id.et_pjbg_screen_sbmc);
        et_pjbg_screen_yxbh = findViewById(R.id.et_pjbg_screen_yxbh);
        tv_screen_reset = findViewById(R.id.tv_screen_reset);
        tv_screen_sure = findViewById(R.id.tv_screen_sure);
        radioGroup_sblx = findViewById(R.id.rg_pjbg_sblx);
        radioGroup_dydj = findViewById(R.id.rg_pjbg_dydj);
        rb_bg_110 = findViewById(R.id.rb_bg_110);
        rb_bg_220 = findViewById(R.id.rb_bg_220);
        rb_bg_1000 = findViewById(R.id.rb_bg_1000);
        rb_bg_10 = findViewById(R.id.rb_bg_10);
        rb_bg_15_75 = findViewById(R.id.rb_bg_15_75);
        rb_bg_20 = findViewById(R.id.rb_bg_20);
        rb_bg_35 = findViewById(R.id.rb_bg_35);
        rb_bg_66 = findViewById(R.id.rb_bg_66);
        rb_bg_72_5 = findViewById(R.id.rb_bg_72_5);
        rb_bg_330 = findViewById(R.id.rb_bg_330);
        rb_bg_500 = findViewById(R.id.rb_bg_500);
        rb_bg_750 = findViewById(R.id.rb_bg_750);
        rb_zbyq = findViewById(R.id.rb_zbyq);
        rb_dlq = findViewById(R.id.rb_dlq);
        rb_glkg = findViewById(R.id.rb_glkg);
        rb_kgg = findViewById(R.id.rb_kgg);
        rb_blz = findViewById(R.id.rb_blz);
        rb_blq = findViewById(R.id.rb_blq);
        rb_mx = findViewById(R.id.rb_mx);
        rb_dkq = findViewById(R.id.rb_dkq);
        rb_dlhgq = findViewById(R.id.rb_dlhgq);
        rb_dyhgq = findViewById(R.id.rb_dyhgq);
        rb_dldrq = findViewById(R.id.rb_dldrq);
        rb_zhdq = findViewById(R.id.rb_zhdq);
        rb_syb = findViewById(R.id.rb_syb);
        rb_jdw = findViewById(R.id.rb_jdw);
        rb_zbq = findViewById(R.id.rb_zbq);
        rb_jdq = findViewById(R.id.rb_jdq);
        rb_zydxt = findViewById(R.id.rb_zydxt);
        rb_pbdkq = findViewById(R.id.rb_pbdkq);
        rb_jllbq = findViewById(R.id.rb_jllbq);
        rb_hlbyq = findViewById(R.id.rb_hlbyq);
        rb_zndl = findViewById(R.id.rb_zndl);
        rb_hlz = findViewById(R.id.rb_hlz);
        rb_jzghl = findViewById(R.id.rb_jzghl);
        rb_hlzbh = findViewById(R.id.rb_hlzbh);
        rb_wgbcq = findViewById(R.id.rb_wgbcq);
        rb_hlzkz = findViewById(R.id.rb_hlzkz);
        rb_clbc = findViewById(R.id.rb_clbc);
        rb_wgfsqs = findViewById(R.id.rb_wgfsqs);
        //  vp_main_activity.setOffscreenPageLimit(3);//设置预加载的fragment个数，防止调取动态数据时，listview会显示空白，原因：就是onCreateView每次都调用导致的，这样fragment每次都会设置新的view，而调试发现，之前的view并没有被回收……这就导致了，新的view覆盖了之前设置的view，
//        navigationView = (NavigationView) findViewById(R.id.nav_view);
        adapter = new MainVPAdapter_jinyi(getSupportFragmentManager(), getApplicationContext());
        vp.setAdapter(adapter);
        // tab_bar.setTabMode(TabLayout.MODE_SCROLLABLE);
        tab_bar.setupWithViewPager(vp);
        String[] titles = {"评价报告", "评价任务"};
        for (int i = 0; i < tab_bar.getTabCount(); i++) {

            TabLayout.Tab tab = tab_bar.getTabAt(i);
            View view = null;
            if (tab != null) {
                if(i==0){
                    view = LayoutInflater.from(this).inflate(R.layout.tab_item_left, null);

                    ((TextView)view.findViewById(R.id.tv_title)).setText(titles[i]);

                }
                if(i==1){
                    view = LayoutInflater.from(this).inflate(R.layout.tab_item_right, null);

                    ((TextView)view.findViewById(R.id.tv_title)).setText(titles[i]);
                    tab.setCustomView(view);
                }
            }
        }
        //reflex(tab_bar);
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    ll_renwu_screen.setVisibility(View.GONE);
                    ll_report_screen.setVisibility(View.VISIBLE);
                } else if (position == 1) {
                    ll_renwu_screen.setVisibility(View.VISIBLE);
                    ll_report_screen.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        initListener();
        initCustomTimePicker();
    }

    private void initListener() {
        search_menu.setOnClickListener(this);
        tv_pjrw_screen_kssj.setOnClickListener(this);
        tv_pjrw_screen_jssj.setOnClickListener(this);
        tv_screen_reset.setOnClickListener(this);
        tv_screen_sure.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        switch (view.getId()) {
            case R.id.image_search_menu:
                drawerLayout.openDrawer(GravityCompat.END);
                break;
            case R.id.tv_pjrw_screen_kssj:
                mTimeStyle = 1;
                customDatePicker1.show(now);
                break;
            case R.id.tv_pjrw_screen_jssj:
                mTimeStyle = 2;
                customDatePicker1.show(now);
                break;
            case R.id.tv_screen_reset:
                int index1 = vp.getCurrentItem();
                Fragment fragment1 = adapter.getItem(index1);
                if (fragment1 instanceof RenwuFragment) {
                    tv_pjrw_screen_kssj.setText("");
                    tv_pjrw_screen_jssj.setText("");
                } else if (fragment1 instanceof BaogaoFragment) {
                    radioGroup_sblx.clearCheck2();
                    radioGroup_dydj.clearCheck2();
                    sblx = "";
                    dydj = "";
                    et_pjbg_screen_dz.setText("");
                    et_pjbg_screen_sbmc.setText("");
                    et_pjbg_screen_yxbh.setText("");
                }
                break;
            case R.id.tv_screen_sure:
                int index = vp.getCurrentItem();
                Fragment fragment = adapter.getItem(index);
                if (fragment instanceof RenwuFragment) {
                    String kssj = tv_pjrw_screen_kssj.getText().toString().trim();
                    String jssj = tv_pjrw_screen_jssj.getText().toString().trim();
                    ((RenwuFragment) fragment).fillterData(kssj, jssj);
                    drawerLayout.closeDrawer(GravityCompat.END);
                } else if (fragment instanceof BaogaoFragment) {
                    baoGaoScreen();
                    String dz = et_pjbg_screen_dz.getText().toString().trim();
                    String sbmc = et_pjbg_screen_sbmc.getText().toString().trim();
                    String yxbh = et_pjbg_screen_yxbh.getText().toString().trim();
                    ((BaogaoFragment) fragment).fillterData(sblx, dydj, dz, sbmc, yxbh);
                    drawerLayout.closeDrawer(GravityCompat.END);
                }
                break;
            default:
                break;
        }
    }

    private void baoGaoScreen() {
        if (rb_bg_10.isChecked()) {
            dydj = "22";
        } else if (rb_bg_15_75.isChecked()) {
            dydj = "23";
        } else if (rb_bg_20.isChecked()) {
            dydj = "24";
        } else if (rb_bg_35.isChecked()) {
            dydj = "25";
        } else if (rb_bg_66.isChecked()) {
            dydj = "30";
        } else if (rb_bg_72_5.isChecked()) {
            dydj = "31";
        } else if (rb_bg_110.isChecked()) {
            dydj = "32";
        } else if (rb_bg_220.isChecked()) {
            dydj = "33";
        } else if (rb_bg_330.isChecked()) {
            dydj = "34";
        } else if (rb_bg_500.isChecked()) {
            dydj = "35";
        } else if (rb_bg_750.isChecked()) {
            dydj = "36";
        } else if (rb_bg_1000.isChecked()) {
            dydj = "37";
        }
        if (rb_zbyq.isChecked()) {
            sblx = "0301";
        } else if (rb_dlq.isChecked()) {
            sblx = "0305";
        } else if (rb_glkg.isChecked()) {
            sblx = "0306";
        } else if (rb_kgg.isChecked()) {
            sblx = "0322";
        } else if (rb_blz.isChecked()) {
            sblx = "0326";
        } else if (rb_blq.isChecked()) {
            sblx = "0318";
        } else if (rb_mx.isChecked()) {
            sblx = "0311";
        } else if (rb_dkq.isChecked()) {
            sblx = "0312";
        } else if (rb_dlhgq.isChecked()) {
            sblx = "0313";
        } else if (rb_dyhgq.isChecked()) {
            sblx = "0314";
        } else if (rb_dldrq.isChecked()) {
            sblx = "0316";
        } else if (rb_zhdq.isChecked()) {
            sblx = "0321";
        } else if (rb_syb.isChecked()) {
            sblx = "0303";
        } else if (rb_jdw.isChecked()) {
            sblx = "0332";
        } else if (rb_zbq.isChecked()) {
            sblx = "0330";
        } else if (rb_jdq.isChecked()) {
            sblx = "0497";
        } else if (rb_zydxt.isChecked()) {
            sblx = "0502";
        } else if (rb_pbdkq.isChecked()) {
            sblx = "2103";
        } else if (rb_jllbq.isChecked()) {
            sblx = "0345";
        } else if (rb_hlbyq.isChecked()) {
            sblx = "2102";
        } else if (rb_zndl.isChecked()) {
            sblx = "0336";
        } else if (rb_hlz.isChecked()) {
            sblx = "zf02";
        } else if (rb_jzghl.isChecked()) {
            sblx = "2101";
        } else if (rb_hlzbh.isChecked()) {
            sblx = "0402";
        } else if (rb_wgbcq.isChecked()) {
            sblx = "0341";
        } else if (rb_hlzkz.isChecked()) {
            sblx = "0403";
        } else if (rb_clbc.isChecked()) {
            sblx = "0343";
        } else if (rb_wgfsqs.isChecked()) {
            sblx = "0342";
        }
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

    /**
     * 点击返回键处理
     */
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();

        }
    }




    CustomDatePicker customDatePicker1;
    private void initCustomTimePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        customDatePicker1 = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(Calendar time) { // 回调接口，获得选中的时间
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
                String s = sdf.format(time.getTime());
                Date date = time.getTime();
                if (mTimeStyle == 1) { //   2：操作开始时间 ；
                    String czjsTime = tv_pjrw_screen_jssj.getText().toString().trim();
                    if (czjsTime.equals("") || czjsTime == null) {
                        tv_pjrw_screen_kssj.setText(getTime(date));
                    } else {
                        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
                        Date d = null;
                        try {
                            d = fmt.parse(czjsTime);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if (date.before(d)) {
                            tv_pjrw_screen_kssj.setText(getTime(date));
                        } else {
                            Toast.makeText(MainActivity_jinyi.this, "操作开始时间必须早于操作结束时间！", Toast.LENGTH_SHORT).show();
                        }
                    }

                } else if (mTimeStyle == 2) {   // 3： 操作结束时间；
                    String czkssj = tv_pjrw_screen_kssj.getText().toString().trim();
                    //把string转化为date
                    DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
                    Date d = null;
                    try {
                        d = fmt.parse(czkssj);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (czkssj == null || "".equals(czkssj)) {
                        Toast.makeText(MainActivity_jinyi.this, "请先选择操作开始时间！", Toast.LENGTH_LONG).show();
                    } else {
                        if (date.after(d)) {
                            tv_pjrw_screen_jssj.setText(getTime(date));
                        } else {
                            Toast.makeText(MainActivity_jinyi.this, "操作结束时间必须晚于操作开始时间！", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        }, "2010-01-01 00:00", "2025-01-01 00:00");
        customDatePicker1.showSpecificTime(false);
        customDatePicker1.setIsLoop(true);



    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); ///    "yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }


}
