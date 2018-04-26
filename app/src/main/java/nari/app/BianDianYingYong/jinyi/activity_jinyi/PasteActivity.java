package nari.app.BianDianYingYong.jinyi.activity_jinyi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nari.app.BianDianYingYong.R;
import nari.app.BianDianYingYong.base.BaseActivity;
import nari.app.BianDianYingYong.bean.JCXBean;
import nari.app.BianDianYingYong.bean.PJDXBean;
import nari.app.BianDianYingYong.bean.PJXMBean;
import nari.app.BianDianYingYong.bean.PJXXBean;
import nari.app.BianDianYingYong.bean.ResultBean;
import nari.app.BianDianYingYong.jinyi.adapter_jinyi.PasteAdapter;
import nari.app.BianDianYingYong.jinyi.bean_jinyi.FLAGBean;
import nari.app.BianDianYingYong.jinyi.bean_jinyi.ZTGNXXBean;
import nari.app.BianDianYingYong.jinyi.customview_jinyi.MyRadioGroup;
import nari.app.BianDianYingYong.utils.DataReadUtil;


/**
 * Created by ShawDLee on 2018/1/18.
 */

public class PasteActivity extends BaseActivity implements View.OnClickListener {

    private ImageView image_paste_back; //返回
    private ImageView image_paste_menu; // 侧滑菜单
    private MyRadioGroup rg_paste_screen_dydj; //电压等级
    private RadioGroup rg_paste_screen_ztzt; // 粘贴状态
    private EditText et_paste_screen_sbmc; // 筛选设备名称
    private TextView tv_paste_screen_reset; // 筛选重置
    private TextView tv_paste_screen_sure; //筛选确认
    private TextView tv_submit; //确定
    private DrawerLayout drawerLayout_paste;
    private StringBuilder sb;
    private String DYDJ = ""; // 电压等级
    private String SBMC = ""; // 设备名称
    private String ZTZT = ""; // 粘贴状态
    String content;
    private List<ZTGNXXBean> list = new ArrayList<>();
    private PasteAdapter pasteAdapter;
    private ListView lv_paste_content;
    private RadioButton rb_unpaste;
    private RadioButton rb_pasted;
    private RadioButton rb_110;
    private RadioButton rb_220;
    private RadioButton rb_1000;
    private RadioButton rb_10;
    private RadioButton rb_15_75;
    private RadioButton rb_20;
    private RadioButton rb_35;
    private RadioButton rb_66;
    private RadioButton rb_72_5;
    private RadioButton rb_330;
    private RadioButton rb_500;
    private RadioButton rb_750;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        content = getIntent().getStringExtra("content");
        setContentView(R.layout.activity_paste);
        initView();
    }

    private void initView() {
        drawerLayout_paste = findViewById(R.id.drawerLayout_paste);
        image_paste_back = findViewById(R.id.image_paste_back);
        image_paste_menu = findViewById(R.id.image_paste_menu);
        rg_paste_screen_dydj = findViewById(R.id.rg_paste_dydj);
        rg_paste_screen_ztzt = findViewById(R.id.rg_paste_ztzt);
        et_paste_screen_sbmc = findViewById(R.id.et_paste_screen_sbmc);
        tv_paste_screen_reset = findViewById(R.id.tv_paste_screen_reset);
        tv_paste_screen_sure = findViewById(R.id.tv_paste_screen_sure);
        lv_paste_content = findViewById(R.id.lv_paste_content);
        rb_unpaste = findViewById(R.id.rb_unpaste);
        rb_pasted = findViewById(R.id.rb_pasted);
        tv_submit = findViewById(R.id.tv_submit);
        rb_110 = findViewById(R.id.rb_110);
        rb_220 = findViewById(R.id.rb_220);
        rb_1000 = findViewById(R.id.rb_1000);
        rb_10 = findViewById(R.id.rb_10);
        rb_15_75 = findViewById(R.id.rb_15_75);
        rb_20 = findViewById(R.id.rb_20);
        rb_35 = findViewById(R.id.rb_35);
        rb_66 = findViewById(R.id.rb_66);
        rb_72_5 = findViewById(R.id.rb_72_5);
        rb_330 = findViewById(R.id.rb_330);
        rb_500 = findViewById(R.id.rb_500);
        rb_750 = findViewById(R.id.rb_750);
        initListener();
        getZTGNXXData(DYDJ, SBMC, ZTZT);
    }

    private void initListener() {
        image_paste_back.setOnClickListener(this);
        image_paste_menu.setOnClickListener(this);
        tv_paste_screen_reset.setOnClickListener(this);
        tv_paste_screen_sure.setOnClickListener(this);
        tv_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_paste_back:
                finish();
                break;
            case R.id.image_paste_menu:
                drawerLayout_paste.openDrawer(GravityCompat.END);
                break;
            case R.id.tv_paste_screen_reset:
                rg_paste_screen_dydj.clearCheck2();
                rg_paste_screen_ztzt.clearCheck();
                DYDJ = "";
                ZTZT = "";
                et_paste_screen_sbmc.setText("");
                break;
            case R.id.tv_paste_screen_sure:
                if (rb_10.isChecked()) {
                    DYDJ = "22";
                } else if (rb_15_75.isChecked()) {
                    DYDJ = "23";
                } else if (rb_20.isChecked()) {
                    DYDJ = "24";
                } else if (rb_35.isChecked()) {
                    DYDJ = "25";
                } else if (rb_66.isChecked()) {
                    DYDJ = "30";
                } else if (rb_72_5.isChecked()) {
                    DYDJ = "31";
                } else if (rb_110.isChecked()) {
                    DYDJ = "32";
                } else if (rb_220.isChecked()) {
                    DYDJ = "33";
                } else if (rb_330.isChecked()) {
                    DYDJ = "34";
                } else if (rb_500.isChecked()) {
                    DYDJ = "35";
                } else if (rb_750.isChecked()) {
                    DYDJ = "36";
                } else if (rb_1000.isChecked()) {
                    DYDJ = "37";
                }
                if (rb_pasted.isChecked()) {
                    ZTZT = "02";
                } else if (rb_unpaste.isChecked()) {
                    ZTZT = "01";
                }
                SBMC = et_paste_screen_sbmc.getText().toString().trim();
                getZTGNXXData(DYDJ, SBMC, ZTZT);
                drawerLayout_paste.closeDrawer(GravityCompat.END);
                break;
            case R.id.tv_submit:
                if (list == null || list.size() == 0) return;
                List<ZTGNXXBean> ztgnxxBeenList = new ArrayList<>();
                for (ZTGNXXBean bean : list) {
                    if (bean.isChecked()) ztgnxxBeenList.add(bean);
                }
                if (ztgnxxBeenList.size() == 0) {
                    Toast.makeText(PasteActivity.this, "请至少选中一项", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuilder PJBG_ID = new StringBuilder("");
                for (int i = 0; i < ztgnxxBeenList.size(); i++) {
                    PJBG_ID.append(ztgnxxBeenList.get(i).getPJBG_ID());
                    if (i != (ztgnxxBeenList.size() - 1)) {
                        PJBG_ID.append(",");
                    }
                }
                List<PJDXBean> pjdxBeanList = ResultBean.fromJson(content, PJDXBean.class).getRecords();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                String PJSJ = sdf.format(new Date());
                StringBuilder sb = new StringBuilder("");
                sb.append("<list>").append("\n")
                        .append("<params>").append("\n")
                        .append("<PJBG id=\'").append(PJBG_ID.toString()).append("\'>").append("\n");
                for (PJDXBean pjdxBen : pjdxBeanList
                        ) {
                    sb.append("<PJDX id=\'").append(pjdxBen.getOBJ_ID()).append("\'>").append("\n");
                    for (PJXMBean pjxmBean : pjdxBen.getPjxmBeanList()
                            ) {
                        sb.append("<PJXM id=\'").append(pjxmBean.getOBJ_ID()).append("\'>").append("\n");
                        for (PJXXBean pjxxBean : pjxmBean.getPjxxBeanList()
                                ) {
                            JCXBean jcxBean = pjxxBean.getJcxBean();


                            sb.append("<PJXX id=\'").append(pjxxBean.getOBJ_ID()).append("\'>").append("\n");
                            sb.append("<JCX id=\'").append(jcxBean.getOBJ_ID()).append("\'>").append("\n");
                            sb.append("<JCXJG>").append(jcxBean.getJCXJG()).append("</JCXJG>").append("\n")
                                    .append("<KFZ>").append(jcxBean.getKDFZ()).append("</KFZ>").append("\n")
                                    .append("<WTMS>").append(jcxBean.getWTMS()).append("</WTMS>").append("\n")
                                    .append("<CLJY>").append(jcxBean.getCLJY()).append("</CLJY>").append("\n");
                    /*if(StringUtil.isNull(jcxBean.getPJSJ())){
                        sb.append("<PJSJ>").append(jcxBean.getPJSJ()).append("</PJSJ>").append("\n");
                    }else{*/
                            sb.append("<PJSJ>").append(PJSJ).append("</PJSJ>").append("\n");
                    /*}*/
                            sb.append("<ZTZT>").append(jcxBean.getZTZT()).append("</ZTZT>").append("\n");

                            sb.append("</JCX>").append("\n");
                            sb.append("</PJXX>").append("\n");


                        }
                        sb.append("</PJXM>").append("\n");
                    }

                    sb.append("</PJDX>").append("\n");
                }

                sb.append("</PJBG>").append("\n")
                        .append("</params>").append("\n")
                        .append("</list>");
                Object[] params = new Object[]{sb};
                int i = 21213213;
                String data = DataReadUtil.getDataFromDb("bdjyh", "PostJYHPJZJRWLB", params);

                ResultBean<FLAGBean> result = ResultBean.fromJson(data, FLAGBean.class);
                if (result == null || result.getRecords() == null || result.getRecords().size() == 0) {
                    Toast.makeText(PasteActivity.this, "粘贴失败", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if ("true".equals(result.getRecords().get(0).getFLAG())) {


                        Toast.makeText(PasteActivity.this, "粘贴成功", Toast.LENGTH_SHORT).show();
                        finish();
                        return;


                    } else {
                        Toast.makeText(PasteActivity.this, "粘贴失败", Toast.LENGTH_SHORT).show();
                        return;
                    }

                }

            default:
                break;
        }
    }

    /*
                * 请求粘贴界面网络数据*/
    private void getZTGNXXData(String DYDJ, String SBMC, String ZTZT) {
        list.clear();
        String ZJRW_ID = getIntent().getStringExtra("ZJRW_ID");
        String PJBG_ID = getIntent().getStringExtra("PJBG_ID");
        sb = new StringBuilder();
        //拼接请求体
        sb.append("<list>").append("\n")
                .append("<params>").append("\n")
                .append("<ZJRW_ID>").append(ZJRW_ID).append("</ZJRW_ID>")
               /* .append("<PJBG_ID>").append(PJBG_ID).append("</PJBG_ID>")*/
                .append("<DYDJ>").append(DYDJ).append("</DYDJ>")
                .append("<SBMC>").append(SBMC).append("</SBMC>")
                .append("<ZTZT>").append(ZTZT).append("</ZTZT>")
                .append("</params>").append("\n")
                .append("</list>");
        String serviceName = "bdjyh"; //   服务名
        String interfaceName = "GetZTGNXX";//   接口名
        Object[] params = new Object[]{sb};
        String data = DataReadUtil.getDataFromDb(serviceName, interfaceName, params);
        Log.e("lala", "PasteActivity   data======" + data);
        parseZTGNXXDetails(data);
    }

    /**
     * 解析粘贴详情列表数据
     *
     * @author TQM
     */
    private void parseZTGNXXDetails(String t) {
        if (t == null || "".equals(t)) {
            list.clear();
            Toast.makeText(getApplicationContext(), "数据为空", Toast.LENGTH_SHORT).show();
        } else {
            ResultBean<ZTGNXXBean> result = ResultBean.fromJson(t, ZTGNXXBean.class);
            if (result == null || result.getRecords() == null) return;
            list.addAll(result.getRecords());
            pasteAdapter = new PasteAdapter(PasteActivity.this, list);
            lv_paste_content.setAdapter(pasteAdapter);
        }
    }


    /**
     * 点击返回键处理
     */
    @Override
    public void onBackPressed() {
        if (drawerLayout_paste.isDrawerOpen(GravityCompat.END)) {
            drawerLayout_paste.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 跳转
     */
    public static void antionStart(Context context, String ZJRW_ID, String PJBG_ID, String content) {
        Intent intent = new Intent(context, PasteActivity.class);
        intent.putExtra("ZJRW_ID", ZJRW_ID);
        intent.putExtra("PJBG_ID", PJBG_ID);
        intent.putExtra("content", content);
        context.startActivity(intent);
    }
}
