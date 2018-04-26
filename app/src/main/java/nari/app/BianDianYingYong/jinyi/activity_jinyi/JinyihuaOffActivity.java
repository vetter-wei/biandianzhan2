package nari.app.BianDianYingYong.jinyi.activity_jinyi;

import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.google.gson.Gson;

import net.tsz.afinal.FinalDb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import nari.app.BianDianYingYong.R;
import nari.app.BianDianYingYong.activity.MainActivity;
import nari.app.BianDianYingYong.adapter.MainVPAdapter;
import nari.app.BianDianYingYong.base.BaseActivity;
import nari.app.BianDianYingYong.bean.ResultBean;
import nari.app.BianDianYingYong.customview.DownloadPopupWindow;
import nari.app.BianDianYingYong.jinyi.adapter_jinyi.BaoGaoAdapter;
import nari.app.BianDianYingYong.jinyi.adapter_jinyi.BaoGaoCacheAdapter;
import nari.app.BianDianYingYong.jinyi.bean_jinyi.BTSXBean;
import nari.app.BianDianYingYong.jinyi.bean_jinyi.JYHCacheBean;
import nari.app.BianDianYingYong.jinyi.bean_jinyi.PingjiabaogaoBean;
import nari.app.BianDianYingYong.utils.DataReadUtil;
import nari.app.BianDianYingYong.utils.HttpAsycTask;
import nari.app.BianDianYingYong.utils.ParamBean;
import nari.app.BianDianYingYong.utils.ProgressDialog;
import nari.app.BianDianYingYong.utils.SharedPreferencesHelper;
import nari.mip.core.Platform;


import nari.mip.core.service.sso.SSOInfo;

/**
 * Created by 李元甲 on 2018-04-02.
 */

public class JinyihuaOffActivity extends BaseActivity {
    FinalDb db;
    private String SBLX = "";
    private String DYDJ = "";
    private String DZMC = "";
    private String SBMC = "";
    private String YXBH = "";
    private String userName;
    private ImageView cache;
    private ListView lv_fragment_baogao;
    private List<PingjiabaogaoBean> baoGaoList = new ArrayList<>();
    List<JYHCacheBean>  jyhCacheBeanList = new ArrayList<>();
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
        setContentView(R.layout.activity_jinyihuaoff);
        userName = MainActivity.getUserNamestatic();
        initView();
        initListener();
        db = FinalDb.create(this);
    }


    /**
     * 初始化页面控件
     */
    private void initView() {

        cache = findViewById(R.id.popupwindow_cache);
        lv_fragment_baogao = findViewById(R.id.lv_fragment_baogao);

    }

    private void initListener() {
        cache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = LayoutInflater.from(getBaseContext());
                View popupView = inflater.inflate(R.layout.popupwindow_download, null);
                popupView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);//强置绘制试图
                SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(getBaseContext(), "PersonalInformation");

                PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT, true);
                popupWindow.setOutsideTouchable(true);//设置点击外部区域关闭popupwindow
                popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));//设置背景透明
                //设置popupwindow的显示位置
                popupWindow.showAsDropDown(view, -popupWindow.getContentView().getMeasuredWidth() + view.getWidth(), 0);
                popupView.findViewById(R.id.ll_upload).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        submitData();
                    }
                });
                popupView.findViewById(R.id.ll_download).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getdata();
                    }
                });
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

    @Override
    protected void onResume() {
        super.onResume();
        //getdata();
        getDbData();
    }

    void getDbData(){
        List<JYHCacheBean> jyhCacheBeens = db.findAll(JYHCacheBean.class) ;
        if(jyhCacheBeens == null || jyhCacheBeens.size() ==0){
            Toast.makeText(this,"无缓存数据",Toast.LENGTH_SHORT).show();
            //return;
        }
        baoGaoList.clear();
        for(JYHCacheBean jyhCacheBean :jyhCacheBeens){
            baoGaoList.add(new Gson().fromJson(jyhCacheBean.getListgson(),PingjiabaogaoBean.class));
        }
        freshData();
    }

    private BaoGaoCacheAdapter adapter;
    private void freshData() {

        if (adapter == null) {
            adapter = new BaoGaoCacheAdapter(this, baoGaoList);
            lv_fragment_baogao.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {

            adapter.notifyDataSetChanged();
            //mLv_fragment_archived.setAdapter(mAdapter);
        }
    }
    ProgressDialog progressDialog = new ProgressDialog();
    void getdata(){
        progressDialog = new ProgressDialog();
        progressDialog.showProgressDialog(this, "加载中..", false);
        StringBuilder sb = new StringBuilder();
        //拼接请求体
        sb.append("<list>").append("\n")
                .append("<params>").append("\n")
                .append("<USERMC>").append(userName).append("</USERMC>")
                .append("<SBLX>").append(SBLX).append("</SBLX>")
                .append("<YXBH>").append(YXBH).append("</YXBH>")
                .append("<DYDJ>").append(DYDJ).append("</DYDJ>")
                .append("<SBMC>").append(SBMC).append("</SBMC>")
                .append("<DZMC>").append(DZMC).append("</DZMC>")
                .append("<PJSB_ID>").append("").append("</PJSB_ID>")
                .append("<ZJRW_ID>").append("").append("</ZJRW_ID>")
                .append("<BGZT>").append("01").append("</BGZT>")
                .append("<PAGE_INDEX>").append("1").append("</PAGE_INDEX>")
                .append("<PAGE_SIZE>").append("200").append("</PAGE_SIZE>")
                .append("</params>").append("\n")
                .append("</list>");
        String serviceName = "bdjyh"; //   服务名
        String interfaceName = "GetJYHPJLB";//   接口名
        Object[] params = new Object[]{sb};
        JYHCACHEAsycTask asycTask = new JYHCACHEAsycTask();
        JYHCACHEAsycTask.DataChuli chili = new JYHCACHEAsycTask.DataChuli() {
            @Override
            public void chuli(String data) {
                //Toast.makeText(MainActivity_jinyi.this,data,Toast.LENGTH_SHORT).show();
                progressDialog.cancelProgressDialog();
                ResultBean<PingjiabaogaoBean> result = ResultBean.fromJson(data, PingjiabaogaoBean.class);
                if (result == null || result.getRecords() == null || result.getRecords().size() == 0)
                    return;
                baoGaoList.clear();
                baoGaoList.addAll(result.getRecords());
                Toast.makeText(JinyihuaOffActivity.this,"缓存成功",Toast.LENGTH_SHORT).show();
                freshData();
            }
        };
        asycTask.setDataChuli(chili);
        ParamBean paramBean = new ParamBean();
        paramBean.setIntfacre(interfaceName);
        paramBean.setService(serviceName);
        paramBean.setParams(sb.toString());
        asycTask.setContext(this);
        asycTask.execute(paramBean);
    }
    void submitData(){
        progressDialog = new ProgressDialog();
        progressDialog.showProgressDialog(this, "上传中..", false);
        JYHUpdateAsycTask asycTask = new JYHUpdateAsycTask();
        JYHUpdateAsycTask.DataChuli chili = new JYHUpdateAsycTask.DataChuli() {
            @Override
            public void chuli(Boolean data) {
                progressDialog.cancelProgressDialog();
                if(data){
                    Toast.makeText(JinyihuaOffActivity.this,"上传成功",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(JinyihuaOffActivity.this,"上传失败",Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(MainActivity_jinyi.this,data,Toast.LENGTH_SHORT).show();
                progressDialog.cancelProgressDialog();
                getDbData();
            }
        };
        asycTask.setDataChuli(chili);
        ParamBean paramBean = new ParamBean();
        asycTask.setContext(this);
        asycTask.execute("");
    }

}
