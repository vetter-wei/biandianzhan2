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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
import nari.app.BianDianYingYong.jinyi.bean_jinyi.JYHCacheBean;
import nari.app.BianDianYingYong.jinyi.bean_jinyi.PJDFXQBean;
import nari.app.BianDianYingYong.jinyi.bean_jinyi.PingjiabaogaoBean;
import nari.app.BianDianYingYong.jinyi.customview_jinyi.BaiduView;
import nari.app.BianDianYingYong.jinyi.customview_jinyi.CircleNumberProgressBar;
import nari.app.BianDianYingYong.utils.DataReadUtil;
import nari.app.BianDianYingYong.utils.SharedPreferencesHelper;
import nari.app.BianDianYingYong.utils.StringUtil;

/**
 * Created by 李元甲 on 2018-04-02.
 */

public class PJBGCacheEditActivity extends BaseActivity implements View.OnClickListener {

    private DrawerLayout drawerLayout;
    private ImageView back;
    private ImageView baidu_view;
    private ImageView ic_filter;
    private TextView tv_title;
    private TextView tv_ZFMC;

    private TextView tv_USER;
    private TextView tv_DATE;
    private TextView tv_FZ;
    private ListView lv_pjdx;
    private ListView lv_filter;

    private LinearLayout ll_btxx;
    private CircleNumberProgressBar pr_fz;
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
        setContentView(R.layout.activity_jyh_cache_edit);
        PJBG_ID = getIntent().getStringExtra("PJBG_ID");//评价报告ID
        PJSB_ID = getIntent().getStringExtra("PJSB_ID");//评价设备ID
        PJMB_ID = getIntent().getStringExtra("PJMB_ID");//评价模板ID
        ZJRW_ID = getIntent().getStringExtra("ZJRW_ID");//专家任务ID
        PJXZ_ID = getIntent().getStringExtra("PJXZ_ID");//评价细则ID
        PJZ = getIntent().getStringExtra("PJZ");//评价值
        init();
        initListener();
        initData();
        // TODO: 2018-04-26
        initFakeData();
        //BaoGaoAdapter.progressDialog.cancelProgressDialog();

    }
    // TODO: 2018-04-26 虚假数据
    private void initFakeData() {


        btxxList = new ArrayList<>();
        BTSXBean btsxBean0 = new BTSXBean();
        btsxBean0.setSXMC("变电站名称");
        btsxBean0.setQZ("朝阳变电站");
        BTSXBean btsxBean1 = new BTSXBean();
        btsxBean1.setSXMC("设备型号");
        btsxBean1.setQZ("朝阳变电站");
        BTSXBean btsxBean2 = new BTSXBean();
        btsxBean2.setSXMC("生产日期");
        btsxBean2.setQZ("朝阳变电站");
        BTSXBean btsxBean3 = new BTSXBean();
        btsxBean3.setSXMC("运行编号");
        btsxBean3.setQZ("朝阳变电站");
        BTSXBean btsxBean4 = new BTSXBean();
        btsxBean4.setSXMC("投运日期");
        btsxBean4.setQZ("朝阳变电站");
//        btzhxxBean = result.getRecords().get(0);
        btxxList.add(btsxBean0);
        btxxList.add(btsxBean1);
        btxxList.add(btsxBean2);
        btxxList.add(btsxBean3);
        btxxList.add(btsxBean4);
        //reSetData();
        pjxzBean = new PJXZBean();
        pjdxBeanList = new ArrayList<>();
        PJDXBean bean0 = new PJDXBean();
        PJDXBean bean1 = new PJDXBean();
        pjdxBeanList.add(bean0);
        pjdxBeanList.add(bean1);
        //reSetData();
        freshUI();

    }
    FinalDb db;

    private void init() {
        back = findViewById(R.id.image_processed_czbz_back);
        drawerLayout = findViewById(R.id.drawerLayout);
        tv_title = findViewById(R.id.tv_title);
        ic_filter = findViewById(R.id.ic_filter);
        tv_ZFMC = findViewById(R.id.tv_ZFMC);
        tv_USER = findViewById(R.id.tv_USER);
        tv_DATE = findViewById(R.id.tv_DATE);
        tv_FZ = findViewById(R.id.tv_FZ);
        pr_fz = findViewById(R.id.pr_fz);
        lv_pjdx = findViewById(R.id.lv_pjdx);
        lv_filter = findViewById(R.id.lv_filter);
        ll_btxx = findViewById(R.id.ll_btxx);
        baidu_view = findViewById(R.id.baidu_view);

    }

    private void initListener() {
        back.setOnClickListener(this);
        baidu_view.setOnClickListener(this);
        ic_filter.setOnClickListener(this);
    }

    private void initData() {
        Gson gson = new Gson();
        List<JYHCacheBean> jyhCacheBeens = db.findAllByWhere(JYHCacheBean.class, "PJBGID = \"" + PJBG_ID + "\"");
        if(jyhCacheBeens == null || jyhCacheBeens.size() ==0)return;
        JYHCacheBean jyhCacheBean = jyhCacheBeens.get(0);
        pjdxBeanList = gson.fromJson(jyhCacheBean.getPjdxgson(),new TypeToken<List<PJDXBean>>(){}.getType());
        btxxList = gson.fromJson(jyhCacheBean.getBtgson(),new TypeToken<List<BTSXBean>>(){}.getType());
        //btxxList = gson.fromJson(jyhCacheBean.getBtgson(),new TypeToken<BTSXBean>(){}.getType());
        pjxzBean = gson.fromJson(jyhCacheBean.getPjxzgson(),PJXZBean.class);
        if (!StringUtil.empty(PJZ) ) {
            score = Double.parseDouble(PJZ);
        } else {
            score = getAllScore();
        }
        //reSetData();
        freshUI();
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
            case R.id.baidu_view:
                //onBackPressed();
                //todo  筛选跳转弹窗逻辑
                //drawerLayout.openDrawer(GravityCompat.END);
                save();

                break;

        }
    }

    private void freshUI() {
        for (BTSXBean btsxBean : btxxList) {
            if ("".equals(btsxBean.getQZ()) || btsxBean.getQZ() == null) {

            } else {
                if ("变电站名称".equals(btsxBean.getSXMC())) {
                    tv_ZFMC.setText(btsxBean.getQZ());
                } else {
                    final LinearLayout view = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.cell_btxx,
                            ll_btxx, false);
                    ((TextView)view.findViewById(R.id.tv_name)).setText(btsxBean.getSXMC());
                    ((TextView)view.findViewById(R.id.tv_value)).setText(btsxBean.getQZ());
                    ll_btxx.addView(view);
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
        tv_FZ.setText(scoreB.add(new BigDecimal(fz)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + "");
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
        Gson gson = new Gson();
        List<JYHCacheBean>jyhCacheBeans = db.findAllByWhere(JYHCacheBean.class,"PJBGID = \"" + PJBG_ID + "\"");
        if(jyhCacheBeans == null || jyhCacheBeans.size() ==0)return;
        JYHCacheBean jyhCacheBean = jyhCacheBeans.get(0);
        PingjiabaogaoBean listitem = gson.fromJson(jyhCacheBean.getListgson(),PingjiabaogaoBean.class);
        listitem.setPJZ(score+"");
        jyhCacheBean.setListgson(gson.toJson(listitem));
        jyhCacheBean.setPjdxgson(gson.toJson(pjdxBeanList));
        db.update(jyhCacheBean);
        Toast.makeText(PJBGCacheEditActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
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


                        imageWay = new CustomImageWay(PJBGCacheEditActivity.this, 1, 2, 3);
                        String path = "";
                        for (BTSXBean btsxBean :
                                btxxList) {
                            if ("变电站名称".equals(btsxBean.getSXMC())) {
                                path = btsxBean.getQZ();
                                break;
                            }
                        }
                        path  = path+ File.separator+pjdx.getPJDXMC();
                        imageWay.cameraJYH(path,pjxxBean);
                        return;
                    }
                }
            }
        }


    }

    public static void anctionStart(Activity activity, String PJBG_ID, String PJSB_ID, String PJMB_ID, String ZJRW_ID, String PJZ, String PJXZ_ID, int resultCode) {

        Intent intent = new Intent(activity, PJBGCacheEditActivity.class);
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


    private double getAllScore() {
        BigDecimal total = new BigDecimal(0);
        for (PJDXBean bean : pjdxBeanList
                ) {
            total = total.add(new BigDecimal(bean.getFZ()));
        }
        return total.setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
    }

}

