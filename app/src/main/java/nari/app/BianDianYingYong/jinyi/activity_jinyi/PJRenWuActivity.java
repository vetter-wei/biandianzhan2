package nari.app.BianDianYingYong.jinyi.activity_jinyi;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import nari.app.BianDianYingYong.R;
import nari.app.BianDianYingYong.activity.ExecutionActivity;
import nari.app.BianDianYingYong.activity.MainActivity;
import nari.app.BianDianYingYong.base.BaseActivity;
import nari.app.BianDianYingYong.base.BasePullToRefresh;
import nari.app.BianDianYingYong.bean.ResultBean;
import nari.app.BianDianYingYong.customview.BaseButtonDialog;
import nari.app.BianDianYingYong.customview.CustomListView;
import nari.app.BianDianYingYong.customview.PullToRefreshLayout;
import nari.app.BianDianYingYong.jinyi.adapter_jinyi.BaoGaoAdapter;
import nari.app.BianDianYingYong.jinyi.adapter_jinyi.PingJiaBaoGaoAdapter;
import nari.app.BianDianYingYong.jinyi.bean_jinyi.PJBGDetailsBean;
import nari.app.BianDianYingYong.jinyi.bean_jinyi.PingJiaRenWuBean;
import nari.app.BianDianYingYong.jinyi.bean_jinyi.PingjiabaogaoBean;
import nari.app.BianDianYingYong.jinyi.fragment_jinyi.RenwuFragment;
import nari.app.BianDianYingYong.utils.DataReadUtil;

import static java.util.Collections.addAll;
import static nari.app.BianDianYingYong.R.id.lv_fragment_baogao;
import static nari.app.BianDianYingYong.R.id.pf_fragment_baogao;
import static nari.app.BianDianYingYong.R.style.dialog;
import static nari.app.BianDianYingYong.activity.ExecutionActivity.stepToArc;
import static nari.app.BianDianYingYong.activity.ExecutionActivity.stepToExe;
import static nari.mip.core.a.a.s;

/**
 * Created by ShawDLee on 2018/1/17.
 */

public class PJRenWuActivity extends BaseActivity {
    private ListView lv_pjbg;
    private StringBuilder sb;
    private String userName;
    private String obj_id;
    private List<PJBGDetailsBean> baoGaoDetailsList = new ArrayList<>();
    private PullToRefreshLayout pf_activity_pjrw;
    private PingJiaBaoGaoAdapter adapter;
    private Integer page = 1;
    private TextView tv_pjrw_commit;
    private String zjrw_id;
    public static boolean stepToPJRW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pjrw_details);
        lv_pjbg = findViewById(R.id.lv_pjrw_activity);
        tv_pjrw_commit = findViewById(R.id.tv_pjrw_commit);
        pf_activity_pjrw = findViewById(R.id.pf_activity_pjrw);
        userName = getIntent().getStringExtra("userName");
        obj_id = getIntent().getStringExtra("obj_id");
        tv_pjrw_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toCommit();
            }
        });
        initData();
        RenwuFragment.progressDialog.cancelProgressDialog();
    }

    private void initData() {
        getPJRWDetailsData(page + "");
        pf_activity_pjrw.setOnStartListener(new BasePullToRefresh.OnStartListener() {
            @Override
            public void onStartRefresh(BasePullToRefresh var1) {
                //下拉刷新
                page = 1;
                getPJRWDetailsData(page + "");
            }

            @Override
            public void onStartLoadmore(BasePullToRefresh var1) {
                //上滑加载
                page++;
                getPJRWDetailsData(page + "");
            }
        });
    }


    /*
* 提交*/
    private void toCommit() {
        StringBuilder sb = new StringBuilder();
        //拼接请求体
        sb.append("<list>").append("\n")
                .append("<params>").append("\n")
                .append("<ZJRW_ID>>").append(obj_id).append("</ZJRW_ID>>").append("\n")
                .append("<RWZT>").append("03").append("</RWZT>").append("\n")
                .append("</params>").append("\n")
                .append("</list>");
        String serviceName = "bdjyh"; //   服务名
        String interfaceName = "PostZJRWXX";//   接口名
        Object[] params = new Object[]{sb};
        String data = DataReadUtil.getDataFromDb(serviceName, interfaceName, params);
        Log.e("lala", "点击提交按钮后的数据===============" + data);
        if (data == null && "".equals(data)) {
            Toast.makeText(PJRenWuActivity.this, "请检查网络", Toast.LENGTH_SHORT).show();
        } else {
            JSONObject datas;
            try {
                datas = new JSONObject(data);
                JSONArray records = datas.getJSONArray("records");
                if (records.length() == 0) {
                    Toast.makeText(PJRenWuActivity.this, "数据为空", Toast.LENGTH_SHORT).show();
                } else {
                    JSONObject record = records.getJSONObject(0);
                    String flag = record.getString("RESULT");
                    if ("true".equals(flag)) {
                        stepToPJRW = true;
                        finish();
                    } else {
                        Toast.makeText(PJRenWuActivity.this, "有未提交的评价报告", Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    /*
                * 请求评价任务详情界面网络数据*/
    private void getPJRWDetailsData(String page) {
        if (userName == null) {
            Toast.makeText(getApplicationContext(), "MIP登录失败，请重新登陆", Toast.LENGTH_SHORT).show();
        } else {
            sb = new StringBuilder();
            //拼接请求体
            sb.append("<list>").append("\n")
                    .append("<params>").append("\n")
                    .append("<USERMC>").append(userName).append("</USERMC>")
                    .append("<SBLX>").append("").append("</SBLX>")
                    .append("<YXBH>").append("").append("</YXBH>")
                    .append("<DYDJ>").append("").append("</DYDJ>")
                    .append("<SBMC>").append("").append("</SBMC>")
                    .append("<DZMC>").append("").append("</DZMC>")
                    .append("<PJSB_ID>").append("").append("</PJSB_ID>")
                    .append("<ZJRW_ID>").append(obj_id).append("</ZJRW_ID>")
                    .append("<PAGE_INDEX>").append(page).append("</PAGE_INDEX>")
                    .append("<PAGE_SIZE>").append("5").append("</PAGE_SIZE>")
                    .append("</params>").append("\n")
                    .append("</list>");
            String serviceName = "bdjyh"; //   服务名
            String interfaceName = "GetJYHPJLB";//   接口名
            Object[] params = new Object[]{sb};
            String data = DataReadUtil.getDataFromDb(serviceName, interfaceName, params);
            Log.e("lala", "PJRenWuActivity   data======" + data);
            if (this.page == 1) { //下拉刷新
                baoGaoDetailsList.clear();//   清空数组
                parsePJRWDetails(data);
                pf_activity_pjrw.refreshSuccess();

            } else {
                parsePJRWDetails(data);
                pf_activity_pjrw.loadmoreSuccess();

            }
            if (baoGaoDetailsList.size() < 5) {
                pf_activity_pjrw.setLoadmoreable(false);
            } else {
                pf_activity_pjrw.setLoadmoreable(true);
            }
            freshData();
        }
    }


    private void freshData() {
        if (adapter == null) {
            adapter = new PingJiaBaoGaoAdapter(PJRenWuActivity.this, baoGaoDetailsList);
            lv_pjbg.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }


    /**
     * 解析评价任务详情列表数据
     *
     * @author TQM
     */
    private void parsePJRWDetails(String t) {
        if (t == null || "".equals(t)) {
            Toast.makeText(getApplicationContext(), "数据为空", Toast.LENGTH_SHORT).show();
            // TODO: 2018-04-26
            PJBGDetailsBean bean = new PJBGDetailsBean();
            PJBGDetailsBean bean1 = new PJBGDetailsBean();
            PJBGDetailsBean bean2 = new PJBGDetailsBean();
            bean.setPJZ("");
            baoGaoDetailsList.add(bean);
            bean1.setBGZT("02");
            baoGaoDetailsList.add(bean1);
            bean1.setBGZT("02");
            baoGaoDetailsList.add(bean2);
            // TODO: 2018-04-26

        } else {
            ResultBean<PJBGDetailsBean> result = ResultBean.fromJson(t, PJBGDetailsBean.class);
            //baoGaoList=result.getRecords();
            baoGaoDetailsList.addAll(result.getRecords());
        }
    }
}