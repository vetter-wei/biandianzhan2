package nari.app.BianDianYingYong.jinyi.activity_jinyi;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import net.tsz.afinal.FinalDb;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nari.app.BianDianYingYong.R;
import nari.app.BianDianYingYong.adapter.PJDXEditAdapter;
import nari.app.BianDianYingYong.adapter.PJDXFilterAdapter;
import nari.app.BianDianYingYong.base.BaseActivity;
import nari.app.BianDianYingYong.base.BaseApplication;
import nari.app.BianDianYingYong.bean.BASEBean;
import nari.app.BianDianYingYong.bean.BTZHXXBean;
import nari.app.BianDianYingYong.bean.JCXBean;
import nari.app.BianDianYingYong.bean.JYHPMBGLXXBean;
import nari.app.BianDianYingYong.bean.PJDXBean;
import nari.app.BianDianYingYong.bean.PJMBBean;
import nari.app.BianDianYingYong.bean.PJXMBean;
import nari.app.BianDianYingYong.bean.PJXXBean;

import nari.app.BianDianYingYong.bean.PJXZBean;
import nari.app.BianDianYingYong.bean.ResultBean;
import nari.app.BianDianYingYong.customview.CustomImageWay;
import nari.app.BianDianYingYong.jinyi.adapter_jinyi.BaoGaoAdapter;
import nari.app.BianDianYingYong.jinyi.bean_jinyi.BTSXBean;
import nari.app.BianDianYingYong.jinyi.bean_jinyi.FLAGBean;
import nari.app.BianDianYingYong.jinyi.bean_jinyi.PJDFXQBean;
import nari.app.BianDianYingYong.jinyi.customview_jinyi.BaiduView;
import nari.app.BianDianYingYong.jinyi.customview_jinyi.CircleNumberProgressBar;
import nari.app.BianDianYingYong.utils.DataReadUtil;
import nari.app.BianDianYingYong.utils.SharedPreferencesHelper;
import nari.app.BianDianYingYong.utils.StringUtil;


/**
 * Created by wubch on 2018-01-15.
 * 精益化评价
 */

public class PJBGEditActivity extends BaseActivity implements View.OnClickListener {

    private DrawerLayout drawerLayout;
    private ImageView back;
    private ImageView ic_filter;
    private TextView tv_title;
    private TextView tv_ZFMC;
    private TextView tv_YXBH;
    private TextView tv_SCCJ;
    private TextView tv_SBXH;
    private TextView tv_SCRQ;
    private TextView tv_TYRQ;
    private TextView tv_USER;
    private TextView tv_DATE;
    private TextView tv_FZ;
    private ListView lv_pjdx;
    private ListView lv_filter;
    private BaiduView baidu_view;
    private LinearLayout ll_btxx;
    private CircleNumberProgressBar pr_fz;
    private BTZHXXBean btzhxxBean;
    public CustomImageWay imageWay;
    String PJMB_ID;
    String PJBG_ID;
    String PJSB_ID;
    String ZJRW_ID;
    String PJXZ_ID;
    String PJZ;
    List<PJDXBean> pjdxBeanList = new ArrayList<>();
    private List<BTSXBean> btxxList = new ArrayList<>();
    PJXZBean pjxzBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        db = FinalDb.create(BaseApplication.mApplicationInstance);
        setContentView(R.layout.activity_evaluate);
        PJBG_ID = getIntent().getStringExtra("PJBG_ID");//评价报告ID
        PJSB_ID = getIntent().getStringExtra("PJSB_ID");//评价设备ID
        PJMB_ID = getIntent().getStringExtra("PJMB_ID");//评价模板ID
        ZJRW_ID = getIntent().getStringExtra("ZJRW_ID");//专家任务ID
        PJXZ_ID = getIntent().getStringExtra("PJXZ_ID");//评价细则ID
        PJZ = getIntent().getStringExtra("PJZ");//评价值
        init();
        initListener();
        initData();
        BaoGaoAdapter.progressDialog.cancelProgressDialog();

    }

    FinalDb db;

    private void init() {
        back = findViewById(R.id.image_processed_czbz_back);
        drawerLayout = findViewById(R.id.drawerLayout);
        tv_title = findViewById(R.id.tv_title);
        baidu_view = findViewById(R.id.baidu_view);
        ic_filter = findViewById(R.id.ic_filter);
        tv_ZFMC = findViewById(R.id.tv_ZFMC);
        tv_USER = findViewById(R.id.tv_USER);
        tv_DATE = findViewById(R.id.tv_DATE);
        tv_FZ = findViewById(R.id.tv_FZ);
        pr_fz = findViewById(R.id.pr_fz);
        lv_pjdx = findViewById(R.id.lv_pjdx);
        lv_filter = findViewById(R.id.lv_filter);
        ll_btxx = findViewById(R.id.ll_btxx);

    }

    private void initListener() {
        back.setOnClickListener(this);
        ic_filter.setOnClickListener(this);
        baidu_view.setOnTabClickListener(new BaiduView.OnTabClickListener() {
            @Override
            public void onTabClick(int currentTab) {
                switch (currentTab) {
                    case 1:
                        Toast.makeText(PJBGEditActivity.this, "保存操作", Toast.LENGTH_SHORT).show();
                        save();
                        break;
                    case 2:
                        Toast.makeText(PJBGEditActivity.this, "提交操作", Toast.LENGTH_SHORT).show();
                        submit();
                        break;
                    case 3:
                        Toast.makeText(PJBGEditActivity.this, "粘贴操作", Toast.LENGTH_SHORT).show();
                        paste();
                        break;
                }
            }
        });
    }

    private void initData() {
        StringBuilder sb = new StringBuilder();
        //拼接请求体
        sb.append("<list>").append("\n")
                .append("<params>").append("\n")
                .append("<PJXZ_ID>").append(PJXZ_ID).append("</PJXZ_ID>").append("\n")
                .append("<PJSB_ID>").append(PJSB_ID).append("</PJSB_ID>").append("\n")
                .append("</params>").append("\n")
                .append("</list>");
        String serviceName = "bdjyh"; //   服务名
        Object[] params = new Object[]{sb};

        String data = DataReadUtil.getDataFromDb(serviceName, "GetBTZSXX", params);
        Log.e("lala", "GetBTZSXX   data======" + data);
        ResultBean<BTSXBean> result = ResultBean.fromJson(data, BTSXBean.class);
        if (result == null || result.getRecords() == null || result.getRecords().size() == 0)
            return;
        btxxList = result.getRecords();
//        btzhxxBean = result.getRecords().get(0);

        List<PJDFXQBean> pjdfxqBeenList = null;
        if (!StringUtil.isNull(PJZ)) {
            sb = new StringBuilder();
            //拼接请求体
            sb.append("<list>").append("\n")
                    .append("<params>").append("\n")
                    .append("<PJBG_ID>").append(PJBG_ID).append("</PJBG_ID>").append("\n")
                    .append("<PJZ>").append(PJZ).append("</PJZ>").append("\n")
                    .append("</params>").append("\n")
                    .append("</list>");

            params = new Object[]{sb};
            data = DataReadUtil.getDataFromDb(serviceName, "GetPJDFXQ", params);
            ResultBean<PJDFXQBean> result1 = ResultBean.fromJson(data, PJDFXQBean.class);
            if (!(result1 == null || result1.getRecords() == null || result1.getRecords().size() == 0)) {
                pjdfxqBeenList = result1.getRecords();
            }
        }

        sb = new StringBuilder();
        //拼接请求体
        sb.append("<list>").append("\n")
                .append("<params>").append("\n")
                .append("<PJMB_ID>").append(PJMB_ID).append("</PJMB_ID>").append("\n")
                .append("</params>").append("\n")
                .append("</list>");

        params = new Object[]{sb};
        Log.e("-------->", "走了");
        data = DataReadUtil.getDataFromDb(serviceName, "GetJYHPMBGLXX", params);
        ResultBean<JYHPMBGLXXBean> result1 = ResultBean.fromJson(data, JYHPMBGLXXBean.class);
        if (result1 == null || result1.getRecords() == null || result1.getRecords().size() == 0)
            return;
        List<JYHPMBGLXXBean> jyhpmbglxxBeanList = result1.getRecords();
        for (JYHPMBGLXXBean bean :
                jyhpmbglxxBeanList) {
            List<PJDXBean> dxs = db.findAllByWhere(PJDXBean.class, "OBJ_ID = \"" + bean.getPJDX_ID() + "\"");
            if (dxs == null || dxs.size() == 0) return;
            if (dxs != null || dxs.size() != 0) {
                PJDXBean pjdxBean = dxs.get(0);
                pjdxBeanList.add(pjdxBean);
                List<PJXMBean> pjxmBeanList = db.findAllByWhere(PJXMBean.class, "PJDX_ID = \"" + pjdxBean.getOBJ_ID() + "\"");
                if (pjxmBeanList == null || pjxmBeanList.size() == 0) return;
                pjdxBean.setPjxmBeanList(pjxmBeanList);
                for (PJXMBean pjxmBean :
                        pjxmBeanList) {
                    List<PJXXBean> pjxxBeanList = db.findAllByWhere(PJXXBean.class, "PJXM_ID = \"" + pjxmBean.getOBJ_ID() + "\"");
//                    if (pjxxBeanList == null || pjxxBeanList.size() == 0) return;
                    pjxmBean.setPjxxBeanList(pjxxBeanList);
                    for (PJXXBean pjxxBean :
                            pjxxBeanList) {
                        List<JCXBean> jcxBeanList = db.findAllByWhere(JCXBean.class, "PJXX_ID  = \"" + pjxxBean.getOBJ_ID() + "\"");
//                        if (jcxBeanList == null || jcxBeanList.size() == 0) return;
                        pjxxBean.setJcxBean(jcxBeanList.get(0));
                    }

                }

            }

        }
        List<PJMBBean> pjmbBeanList = db.findAllByWhere(PJMBBean.class, "OBJ_ID = \"" + PJMB_ID + "\"");
        if (pjmbBeanList == null || pjmbBeanList.size() == 0) return;
        List<PJXZBean> pjxzBeanList = db.findAllByWhere(PJXZBean.class, "OBJ_ID = \"" + pjmbBeanList.get(0).getPJXZ_ID() + "\"");
        if (pjxzBeanList == null || pjxzBeanList.size() == 0) return;
        pjxzBean = pjxzBeanList.get(0);

        if (pjdfxqBeenList != null) {
            setCopy(pjdfxqBeenList);
            score = Double.parseDouble(PJZ);
        } else {
            score = getAllScore();
        }
        reSetData();
        freshUI();
    }

    //提交
    private void submit() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String PJSJ = sdf.format(new Date());
        StringBuilder sb = new StringBuilder("");
        sb.append("<list>").append("\n")
                .append("<params>").append("\n")
                .append("<PJBG id=\'").append(PJBG_ID).append("\'>").append("\n");
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

                    sb.append("<PJSJ>").append(PJSJ).append("</PJSJ>").append("\n");

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

        String data = DataReadUtil.getDataFromDb("bdjyh", "PostJYHPJZJRWLB", params);

        ResultBean<FLAGBean> result = ResultBean.fromJson(data, FLAGBean.class);
        if (result == null || result.getRecords() == null || result.getRecords().size() == 0) {
            Toast.makeText(PJBGEditActivity.this, "提交失败", Toast.LENGTH_SHORT).show();

            return;
        } else {
            if ("true".equals(result.getRecords().get(0).getFLAG())) {

                sb = new StringBuilder("");
                sb.append("<list>").append("\n")
                        .append("<params>").append("\n")
                        .append("<PJBG_ID>").append(PJBG_ID).append("</PJBG_ID>").append("\n")
                        .append("<BGZT>").append("02").append("</BGZT>").append("\n")
                        .append("<TJSJ>").append(PJSJ).append("</TJSJ>").append("\n")
                        .append("</params>").append("\n")
                        .append("</list>");
                String serviceName = "bdjyh"; //   服务名
                params = new Object[]{sb};

                data = DataReadUtil.getDataFromDb(serviceName, "PostBGZTXX", params);
                ResultBean<BASEBean> r = ResultBean.fromJson(data, BASEBean.class);
                if (r == null || r.getRecords().size() == 0 || "0".equals(r.getRecords().get(0).getRESLUT())) {
                    Toast.makeText(PJBGEditActivity.this, "提交失败", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Toast.makeText(PJBGEditActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                    finish();
                }

            } else {
                Toast.makeText(PJBGEditActivity.this, "提交失败", Toast.LENGTH_SHORT).show();
                return;
            }
            return;
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_processed_czbz_back:
                onBackPressed();
                break;
            case R.id.ic_filter:
                //onBackPressed();
                //todo  筛选跳转弹窗逻辑
                drawerLayout.openDrawer(GravityCompat.END);

                break;

        }
    }

    private void freshUI() {
        for (BTSXBean btsxBean : btxxList) {
            if ("".equals(btsxBean.getQZ()) || btsxBean.getQZ() == null) {

            } else {
                if ("变电站名称".equals(btsxBean.getSXMC())) {
                    tv_ZFMC.setText(btsxBean.getSXMC() + ":" + btsxBean.getQZ());
                } else {
                    TextView tv = new TextView(this);
                    tv.setText(btsxBean.getSXMC() + ":" + btsxBean.getQZ());
                    tv.setTextSize(16);
                    ll_btxx.addView(tv);
                }
            }
        }
//        if (btzhxxBean == null) return;
//        tv_title.setText(StringUtil.nullToStr(btzhxxBean.getSBMC()));
//        tv_ZFMC.setText("变电站名称：" + StringUtil.nullToStr(btzhxxBean.getZFMC()));
//        tv_YXBH.setText(StringUtil.nullToStr(btzhxxBean.getYXBH()));
//        tv_SCCJ.setText(StringUtil.nullToStr(btzhxxBean.getSCCJ()));
//        tv_SBXH.setText(StringUtil.nullToStr(btzhxxBean.getSBXH()));
//        if (!StringUtil.isNull(btzhxxBean.getSCRQ())) {
//            tv_SCRQ.setText(StringUtil.nullToStr(btzhxxBean.getSCRQ().substring(0, 10)));
//        } else {
//            tv_SCRQ.setText(StringUtil.nullToStr(btzhxxBean.getSCRQ()));
//        }
//        if (!StringUtil.isNull(btzhxxBean.getTYRQ())) {
//            tv_SCRQ.setText(StringUtil.nullToStr(btzhxxBean.getTYRQ().substring(0, 10)));
//        } else {
//            tv_SCRQ.setText(StringUtil.nullToStr(btzhxxBean.getTYRQ()));
//        }

        //tv_TYRQ.setText(StringUtil.nullToStr(btzhxxBean.getTYRQ()));
        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(getApplicationContext(), "PersonalInformation");
        tv_USER.setText("签字人：" + StringUtil.nullToStr(sharedPreferencesHelper.getStringValue("RYMC")));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        tv_DATE.setText("得分日期：" + sdf.format(new Date()));
        setScore(0);
        adapter = new PJDXEditAdapter(this, pjdxBeanList);
        lv_pjdx.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter2 = new PJDXFilterAdapter(this, pjdxBeanList);
        lv_filter.setAdapter(adapter2);
        adapter2.notifyDataSetChanged();
    }

    PJDXEditAdapter adapter;
    PJDXFilterAdapter adapter2;
    double score = -1;

    //实时显示总得分
    public void setScore(double fz) {
        Double totalscore = Double.parseDouble(pjxzBean.getDF());
        if (score == -1) {
            score = getAllScore();
        }

        BigDecimal scoreB = new BigDecimal(score);
        tv_FZ.setText(scoreB.add(new BigDecimal(fz)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + "分");
        score = scoreB.add(new BigDecimal(fz)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        int progress = (int) (((score + fz) / totalscore) * 100);
        pr_fz.setProgress(progress);
    }

    //选择显示项目
    public void showXM(PJXMBean pjxmBean) {
        drawerLayout.closeDrawer(GravityCompat.END);
        for (PJDXBean pjdxBean : pjdxBeanList) {
            pjdxBean.setChecked(false);
            for (PJXMBean pjxmBean1 : pjdxBean.getPjxmBeanList()) {

                pjxmBean1.setChecked(false);
            }
        }
        for (PJDXBean pjdxBean : pjdxBeanList) {
            for (PJXMBean pjxmBean1 : pjdxBean.getPjxmBeanList()) {
                if (pjxmBean.getPJDX_ID().equals(pjdxBean.getOBJ_ID()) && pjxmBean.getOBJ_ID().equals(pjxmBean1.getOBJ_ID())) {
                    pjdxBean.setChecked(true);
                    pjxmBean1.setChecked(true);
                    adapter.notifyDataSetChanged();
                    return;
                }
            }
        }
    }

    //保存操作
    public void save() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String PJSJ = sdf.format(new Date());
        StringBuilder sb = new StringBuilder("");
        sb.append("<list>").append("\n")
                .append("<params>").append("\n")
                .append("<PJBG id=\'").append(PJBG_ID).append("\'>").append("\n");
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
            Toast.makeText(PJBGEditActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
            //Toast.makeText(PJBGEditActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
            //finish();
            return;
        } else {
            if ("true".equals(result.getRecords().get(0).getFLAG())) {


                Toast.makeText(PJBGEditActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                //finish();
                return;


            } else {
                Toast.makeText(PJBGEditActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
                return;
            }

        }

    }

    //粘贴操作
    public void paste() {
        ResultBean<PJDXBean> resultBean = new ResultBean<PJDXBean>();
        resultBean.setRecords(pjdxBeanList);
        PasteActivity.antionStart(PJBGEditActivity.this, ZJRW_ID, PJBG_ID, new Gson().toJson(resultBean));
    }

    //同步网络端数据
    public void setCopy(List<PJDFXQBean> pjdfxqBeenList) {
        for (PJDFXQBean pjdfxqBean : pjdfxqBeenList
                ) {
            for (PJDXBean pjdxBen : pjdxBeanList
                    ) {
                if (pjdfxqBean.getPJDX_ID().equals(pjdxBen.getOBJ_ID())) {
                    for (PJXMBean pjxmBean : pjdxBen.getPjxmBeanList()
                            ) {
                        if (pjdfxqBean.getPJXM_ID().equals(pjxmBean.getOBJ_ID())) {
                            for (PJXXBean pjxxBean : pjxmBean.getPjxxBeanList()
                                    ) {
                                if (pjdfxqBean.getPJXX_ID().equals(pjxxBean.getOBJ_ID())) {
                                    JCXBean jcxBean = pjxxBean.getJcxBean();
                                    jcxBean.setWTMS(pjdfxqBean.getWTMS());
                                    jcxBean.setCLJY(pjdfxqBean.getCLJY());
                                    jcxBean.setKDFZ(pjdfxqBean.getPJJCXJG_KFZ());
                                    if (StringUtil.isNull(jcxBean.getKFZ())) jcxBean.setKFZ("0");
                                    if (!("发现次数".equals(jcxBean.getJCXMS()))) {
                                        if (0 != jcxBean.getKDFZ()) {
                                            jcxBean.setJCXJG("F");
                                        } else {
                                            jcxBean.setJCXJG("T");
                                        }
                                    } else {

                                        BigDecimal kfz = new BigDecimal(jcxBean.getKFZ());
                                        jcxBean.setJCXJG(new BigDecimal(jcxBean.getKDFZ()).divide(kfz).setScale(0, BigDecimal.ROUND_HALF_UP).intValue() + "");
                                    }
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    break;
                }

            }
        }
    }

    //拍照操作
    String picPath;

    public void takePhoto(PJXXBean pjxxBean) {
        PJDXBean pjdxbean  = null;

        for (PJDXBean pjdx : pjdxBeanList) {
            for (PJXMBean pjxm : pjdx.getPjxmBeanList()) {
                for (PJXXBean pjxx : pjxm.getPjxxBeanList()) {
                    if (pjxxBean.equals(pjxx)) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        String PJSJ = sdf.format(new Date());
                        StringBuilder sb = new StringBuilder("");
                        sb.append("<list>").append("\n")
                                .append("<params>").append("\n")
                                .append("<PJBG id=\'").append(PJBG_ID).append("\'>").append("\n");

                        sb.append("<PJDX id=\'").append(pjdx.getOBJ_ID()).append("\'>").append("\n");

                        sb.append("<PJXM id=\'").append(pjxm.getOBJ_ID()).append("\'>").append("\n");

                        JCXBean jcxBean = pjxxBean.getJcxBean();
                        sb.append("<PJXX id=\'").append(pjxxBean.getOBJ_ID()).append("\'>").append("\n");
                        sb.append("<JCX id=\'").append(jcxBean.getOBJ_ID()).append("\'>").append("\n");
                        sb.append("<JCXJG>").append(jcxBean.getJCXJG()).append("</JCXJG>").append("\n")
                                .append("<KFZ>").append(jcxBean.getKDFZ()).append("</KFZ>").append("\n")
                                .append("<WTMS>").append(jcxBean.getWTMS()).append("</WTMS>").append("\n")
                                .append("<CLJY>").append(jcxBean.getCLJY()).append("</CLJY>").append("\n");

                        sb.append("<PJSJ>").append(PJSJ).append("</PJSJ>").append("\n");

                        sb.append("<ZTZT>").append(jcxBean.getZTZT()).append("</ZTZT>").append("\n");

                        sb.append("</JCX>").append("\n");
                        sb.append("</PJXX>").append("\n");


                        sb.append("</PJXM>").append("\n");


                        sb.append("</PJDX>").append("\n");


                        sb.append("</PJBG>").append("\n")
                                .append("</params>").append("\n")
                                .append("</list>");
                        Object[] params = new Object[]{sb};
                        int i = 21213213;
                        String data = DataReadUtil.getDataFromDb("bdjyh", "PostJYHPJZJRWLB", params);

                        ResultBean<BASEBean> result = ResultBean.fromJson(data, BASEBean.class);
                        if (result == null || result.getRecords().size() == 0 || "0".equals(result.getRecords().get(0).getRESLUT())) {
                            Toast.makeText(PJBGEditActivity.this, "同步数据失败", Toast.LENGTH_SHORT).show();
                            return;
                        }


                        imageWay = new CustomImageWay(PJBGEditActivity.this, 1, 2, 3);
                        String path = "";
                        for (BTSXBean btsxBean :
                             btxxList) {
                            if ("变电站名称".equals(btsxBean.getSXMC())) {
                                path = btsxBean.getQZ();
                                break;
                            }
                        }
                        path  = path+File.separator+pjdx.getPJDXMC();
                        imageWay.cameraJYH(path,pjxxBean);
                        return;
                    }
                }
            }
        }


    }

    public static void anctionStart(Activity activity, String PJBG_ID, String PJSB_ID, String PJMB_ID, String ZJRW_ID, String PJZ, String PJXZ_ID,int resultCode) {
        /*PJBG_ID =  getIntent().getStringExtra("PJBG_ID");//评价报告ID
        PJSB_ID =  getIntent().getStringExtra("PJSB_ID");//评价设备ID
        PJMB_ID =  getIntent().getStringExtra("PJMB_ID");//评价模板ID
        ZJRW_ID =  getIntent().getStringExtra("ZJRW_ID");//专家任务ID*/
        Intent intent = new Intent(activity, PJBGEditActivity.class);
        intent.putExtra("PJBG_ID", PJBG_ID);//评价报告ID
        intent.putExtra("PJSB_ID", PJSB_ID);//评价设备ID
        intent.putExtra("PJMB_ID", PJMB_ID);//评价模板ID
        intent.putExtra("ZJRW_ID", ZJRW_ID);//专家任务ID
        intent.putExtra("PJXZ_ID", PJXZ_ID);//评价细则ID
        intent.putExtra("PJZ", PJZ);//评价值
        activity.startActivityForResult(intent, resultCode);
    }

    /**
     * 选择成功回调
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    try {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            int i = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                            if (i != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                            } else {
                                MediaStore.Images.Media.insertImage(getContentResolver(), CustomImageWay.outputImage.getAbsolutePath(), "title", "description");//图片插入到系统图库
                                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, CustomImageWay.getCameraImageSavePath()));
                            }
                        }
                        Log.e("lala", "activity中照片路径======" + CustomImageWay.getCameraImageSavePath());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                super.onActivityResult(requestCode, resultCode, data);
                break;
            default:
                break;
        }
    }

    //给所有检查项赋予正确状态
    private void reSetData() {
        for (PJDXBean pjdxBen : pjdxBeanList
                ) {

            for (PJXMBean pjxmBean : pjdxBen.getPjxmBeanList()
                    ) {

                for (PJXXBean pjxxBean : pjxmBean.getPjxxBeanList()
                        ) {
                    JCXBean jcxBean = pjxxBean.getJcxBean();
                    if (!("发现次数".equals(jcxBean.getJCXMS()))) {
                        if (0 != jcxBean.getKDFZ()) {
                            jcxBean.setJCXJG("F");
                        } else {
                            jcxBean.setJCXJG("T");
                        }
                    } else {

                        BigDecimal kfz = new BigDecimal(jcxBean.getKFZ());
                        jcxBean.setJCXJG(new BigDecimal(jcxBean.getKDFZ()).divide(kfz).setScale(0, BigDecimal.ROUND_HALF_UP).intValue() + "");
                    }


                }


            }


        }
    }

    private double getAllScore() {
        BigDecimal total = new BigDecimal(0);
        for (PJDXBean bean : pjdxBeanList
                ) {
            total = total.add(new BigDecimal(bean.getFZ()));
        }
        return total.setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
    }

}

