package nari.app.BianDianYingYong.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import net.tsz.afinal.FinalDb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import nari.app.BianDianYingYong.R;
import nari.app.BianDianYingYong.adapter.ExecutionActivityAdapter;
import nari.app.BianDianYingYong.base.BaseActivity;
import nari.app.BianDianYingYong.base.BaseApplication;
import nari.app.BianDianYingYong.bean.CheckTicketActivityBean;
import nari.app.BianDianYingYong.bean.ExecutionTicketActivityBean;
import nari.app.BianDianYingYong.bean.ExecutionTicketListBean;
import nari.app.BianDianYingYong.bean.ResultBean;
import nari.app.BianDianYingYong.bean.TicketListBean;
import nari.app.BianDianYingYong.customview.CustomZuoFeiDialog;
import nari.app.BianDianYingYong.utils.DataReadUtil;
import nari.app.BianDianYingYong.utils.SharedPreferencesHelper;
import nari.app.BianDianYingYong.utils.StringUtil;


/**
 * 待执行界面
 * Created by DWQ on 2017/11/9.
 */

public class ProcessedActivity extends BaseActivity implements View.OnClickListener {
    //返回
    private ImageView back;
    //上下文
    private Context mContext;
    private TextView tv_pro_top_name;   //   名称
    private TextView tv_pro_czdw;            //   操作单位
    private TextView tv_pro_bh;        //   编号
    private ImageView image_pro_jianhu; // 监护下操作
    private TextView tv_pro_jianhu;
    private ImageView image_pro_danren; //单人操作
    private TextView tv_pro_danren;
    private ImageView image_pro_jianxiu; // 检修人员操作
    private TextView tv_pro_jianxiu;
    private TextView tv_pro_startTime;   //   开始时间
    private TextView tv_pro_endTime;     //   结束时间
    private EditText et_pro_czrw;     //   操作任务
    private TextView tv_pro_czr;      //   操作人
    private TextView tv_pro_jhr;     //   监护人
    private TextView tv_pro_fzr;   //   负责人
    private TextView tv_pro_beizhu;          //   备注
    private TextView tv_pro_seeAll;    //   查看全部
    private LinearLayout ll_pro_zuofei; // 作废
    private LinearLayout ll_pro_zhixing; // 执行
    private String[] mCzbzArray;
    private ListView mLv_pro_czxm;
    private String mObjId;
    private String mMbId;
    private String mRYMC;
    private String mUserId;
    private String allCzbz;
    private String status;
    FinalDb db;
    public static boolean proToExe;
    public static boolean stepToPro;
    private ResultBean<CheckTicketActivityBean> resultBean;
    private CheckTicketActivityBean checkBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.processed_ticket);
        db = FinalDb.create(BaseApplication.mApplicationInstance);
        init();
    }


    /**
     * 初始化控件
     */
    private void init() {
        tv_pro_top_name = findViewById(R.id.tv_pro_top_name);
        tv_pro_czdw = findViewById(R.id.tv_pro_czdw);
        tv_pro_bh = findViewById(R.id.tv_pro_bh);
        image_pro_jianhu = findViewById(R.id.image_pro_jianhu);
        tv_pro_jianhu = findViewById(R.id.tv_pro_jianhu);
        image_pro_danren = findViewById(R.id.image_pro_danren);
        tv_pro_danren = findViewById(R.id.tv_pro_danren);
        image_pro_jianxiu = findViewById(R.id.image_pro_jianxiu);
        tv_pro_jianxiu = findViewById(R.id.tv_pro_jianxiu);
        tv_pro_startTime = findViewById(R.id.tv_pro_start_time);
        tv_pro_endTime = findViewById(R.id.tv_pro_finsh_time);
        et_pro_czrw = findViewById(R.id.et_pro_czrw);
        tv_pro_czr = findViewById(R.id.tv_pro_czr);
        tv_pro_jhr = findViewById(R.id.tv_pro_jhr);
        tv_pro_fzr = findViewById(R.id.tv_pro_fzr);
        tv_pro_beizhu = findViewById(R.id.tv_pro_beizhu);
        tv_pro_seeAll = findViewById(R.id.tv_pro_look_all);
        back = findViewById(R.id.image_pro_back);
        ll_pro_zuofei = findViewById(R.id.ll_pro_zuofei);
        ll_pro_zhixing = findViewById(R.id.ll_pro_zhixing);
        mLv_pro_czxm = findViewById(R.id.lv_pro_czxm);
        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(getApplicationContext(), "PersonalInformation");
        mRYMC = sharedPreferencesHelper.getStringValue("RYMC");
        mUserId = sharedPreferencesHelper.getStringValue("userId");
        status = sharedPreferencesHelper.getStringValue("status");
        mObjId = getIntent().getStringExtra("obj_id");
        mMbId = getIntent().getStringExtra("mbId");
        initListener();
        if (status.equals("1")) {
            getTicketDetailsData();
        } else {
            checkBean = db.findAllByWhere(CheckTicketActivityBean.class, "OBJ_ID  = \"" + mObjId + "\"").get(0);
            setData(checkBean);
        }
    }

    /**
     * 监听
     */
    private void initListener() {
        back.setOnClickListener(this);
        tv_pro_seeAll.setOnClickListener(this);
        ll_pro_zuofei.setOnClickListener(this);
        ll_pro_zhixing.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_pro_back:
                finish();
                break;
            case R.id.ll_pro_zuofei:
                CustomZuoFeiDialog customZuoFeiDialog = new CustomZuoFeiDialog(ProcessedActivity.this, mObjId);
                break;
            case R.id.ll_pro_zhixing:
                if (status.equals("1")) {
                    toExecuted();
                } else {
                    CheckTicketActivityBean checkBean = db.findAllByWhere(CheckTicketActivityBean.class, "OBJ_ID  = \"" + mObjId + "\"").get(0);
                    ExecutionTicketActivityBean exeBean = new ExecutionTicketActivityBean();
                    exeBean.setOBJ_ID(checkBean.getOBJ_ID());
                    exeBean.setZPBMMC(checkBean.getZPBMMC());
                    exeBean.setGZDDMC(checkBean.getGZDDMC());
                    exeBean.setGZDDMS(checkBean.getGZDDMS());
                    exeBean.setPH(checkBean.getPH());
                    exeBean.setCZKGSJ(checkBean.getCZKGSJ());
                    exeBean.setCZJSSJ(checkBean.getCZJSSJ());
                    exeBean.setCZRW(checkBean.getCZRW());
                    exeBean.setBZ(checkBean.getBZ());
                    exeBean.setCZRID(checkBean.getCZRID());
                    exeBean.setCZRMC(checkBean.getCZRMC());
                    exeBean.setJHRID(checkBean.getJHRID());
                    exeBean.setJHRMC(checkBean.getJHRMC());
                    exeBean.setZBFZRID(checkBean.getZBFZRID());
                    exeBean.setZBFZRMC(checkBean.getZBFZRMC());
                    exeBean.setSSDSMC(checkBean.getSSDSMC());
                    exeBean.setSSDSID(checkBean.getSSDSID());
                    exeBean.setCZBZ(checkBean.getCZBZ());
                    exeBean.setJHXCZ(checkBean.getJHXCZ());
                    exeBean.setDRCZ(checkBean.getDRCZ());
                    exeBean.setJXRYCZ(checkBean.getJXRYCZ());
                    exeBean.setHWB(checkBean.getHWB());
                    exeBean.setMBID(mMbId);
                    db.save(exeBean);
                    db.delete(checkBean);
                    TicketListBean ticketBean = db.findAllByWhere(TicketListBean.class, "OBJ_ID  = \"" + mObjId + "\"").get(0);
                    ExecutionTicketListBean exeList = new ExecutionTicketListBean();
                    exeList.setOBJ_ID(ticketBean.getOBJ_ID());
                    exeList.setMC(ticketBean.getMC());
                    exeList.setPLX(ticketBean.getPLX());
                    exeList.setPZT("41");
                    exeList.setCZRW(ticketBean.getCZRW());
                    exeList.setZPSJ(ticketBean.getZPSJ());
                    exeList.setZPBMMC(ticketBean.getZPBMMC());
                    exeList.setPZL(ticketBean.getPZL());
                    exeList.setMBID(ticketBean.getMBID());
                    exeList.setPH(ticketBean.getPH());
                    exeList.setFLRMC(ticketBean.getFLRMC());
                    exeList.setSLRMC(ticketBean.getSLRMC());
                    exeList.setDLBH(ticketBean.getDLBH());
                    db.save(exeList);
                    db.delete(ticketBean);
                    Intent intent = new Intent(ProcessedActivity.this, ExecutionActivity.class);
                    intent.putExtra("obj_id", mObjId);
                    startActivity(intent);
                    finish();
                }
                break;
            case R.id.tv_pro_look_all:
                if ("".equals(allCzbz)) {
                    Toast.makeText(getApplicationContext(), "没有操作项目", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent1 = new Intent(ProcessedActivity.this, OperationStepsActivity.class);
                    intent1.putExtra("status", "pro");
                    intent1.putExtra("allCzbz", allCzbz);
                    startActivity(intent1);
                }
                break;
        }
    }

    /*
   * 请求网络获取票详情页数据
   * PID ：票id
   * MBID ：票类型
   * */
    public void getTicketDetailsData() {
        if (mObjId == null && mMbId == null) {
            Toast.makeText(ProcessedActivity.this, "用户id为空，请重新登陆", Toast.LENGTH_SHORT).show();
        } else {
            StringBuilder sb = new StringBuilder();
            //拼接请求体
            sb.append("<list>").append("\n")
                    .append("<params>").append("\n")
                    .append("<PID>")
                    .append(mObjId)  //   BCFE0584AB2C905DC45FBCD3EF015FBCFE0552004A
                    .append("</PID>").append("\n")
                    .append("<MBID>")
                    .append(mMbId)   //   16
                    .append("</MBID>").append("\n")
                    .append("</params>").append("\n")
                    .append("</list>");
            String serviceName = "sxlp1"; //   服务名
            String interfaceName = "getCZPInfo";//   接口名
            Object[] params = new Object[]{sb};
            String data = DataReadUtil.getDataFromDb(serviceName, interfaceName, params);
            Log.e("lala", "checktickactivity   data======" + data);
            String i = jsonString(data);
            resultBean = ResultBean.fromJson(i, CheckTicketActivityBean.class);
            //// TODO: 2018-04-27
            if(resultBean == null)return;
            //// TODO: 2018-04-27
            checkBean = resultBean.getRecords().get(0);

            setData(checkBean);
        }
    }

    private static String jsonString(String s) {
        char[] temp = s.toCharArray();
        int n = temp.length;
        for (int i = 0; i < n; i++) {
            if (temp[i] == ':' && temp[i + 1] == '"') {
                for (int j = i + 2; j < n; j++) {
                    if (temp[j] == '"') {
                        if (temp[j + 1] != ',' && temp[j + 1] != '}') {
                            temp[j] = '”';
                        } else if (temp[j + 1] == ',' || temp[j + 1] == '}') {
                            break;
                        }
                    }
                }
            }
        }
        return new String(temp);
    }

    /*
        * 请求执行按钮接口*/
    private void toExecuted() {
        String htName = "回填|回填人：" + mRYMC;
        StringBuilder sb = new StringBuilder();
        //拼接请求体
        sb.append("<list>").append("\n")
                .append("<params>").append("\n")
                .append("<USER_NAME>>").append(mRYMC).append("</USER_NAME>>").append("\n")
                .append("<USER_ID>").append(mUserId).append("</USER_ID>").append("\n")
                .append("<PID>").append(mObjId).append("</PID>").append("\n")
                .append("<PZT>").append("31").append("</PZT>").append("\n")
                .append("<LOG_INFO>").append(htName).append("</LOG_INFO>").append("\n")
                .append("<CZLX>").append("发送").append("</CZLX>").append("\n")
                .append("<MKLX>").append("2").append("</MKLX>").append("\n")
                .append("<MBID>").append(mMbId).append("</MBID>").append("\n")
                .append("</params>").append("\n")
                .append("</list>");
        String serviceName = "sxlp1"; //   服务名
        String interfaceName = "toExecute";//   接口名
        Object[] params = new Object[]{sb};
        String data = DataReadUtil.getDataFromDb(serviceName, interfaceName, params);
        Log.e("lala", "点击开始按钮后的数据===============" + data);
        if (data == null && "".equals(data)) {
            Toast.makeText(getApplicationContext(), "请检查网络", Toast.LENGTH_SHORT).show();
        } else {
            JSONObject datas;
            try {
                datas = new JSONObject(data);
                JSONArray records = datas.getJSONArray("records");
                if (records.length() == 0) {
                    Toast.makeText(getApplicationContext(), "数据为空", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("lala", "records=======" + records.toString());
                    JSONObject record = records.getJSONObject(0);
                    //工作票主键
                    String flag = record.getString("FLAG");
                    if ("true".equals(flag)) {
                        proToExe = true;
                        stepToPro = true;
                        //  ProcessedActivity.instance.finish();
                        Intent intent = new Intent(ProcessedActivity.this, ExecutionActivity.class);
                        intent.putExtra("mbId", mMbId);
                        intent.putExtra("obj_id", mObjId);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "执行失败", Toast.LENGTH_SHORT).show();
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();

            }
        }
    }

    /*
    * 设置数据*/
    private void setData(CheckTicketActivityBean recordsBean) {
//        CheckTicketActivityBean dataBean = JSON.parseObject(data, CheckTicketActivityBean.class);
//        CheckTicketActivityBean.RecordsBean recordsBean = dataBean.getRecords().get(0);
        allCzbz = recordsBean.getHWB();
        mCzbzArray = allCzbz.split("@@");
        String cb_left = recordsBean.getJHXCZ();   //   监护下操作
        String cb_center = recordsBean.getDRCZ();   //   单人操作
        String cb_right = recordsBean.getJXRYCZ();   //   检修人员操作
        if ("".equals(cb_left.trim())) {
            if ("".equals(cb_center.trim())) {
                tv_pro_jianxiu.setTextColor(getResources().getColor(R.color.colorPrimary));
                image_pro_jianxiu.setImageResource(R.mipmap.pro_checked);
                tv_pro_jhr.setText(recordsBean.getJHRMC());
            } else {
                tv_pro_danren.setTextColor(getResources().getColor(R.color.colorPrimary));
                image_pro_danren.setImageResource(R.mipmap.pro_checked);
                tv_pro_jhr.setText("(空)");
            }
        } else {
            tv_pro_jianhu.setTextColor(getResources().getColor(R.color.colorPrimary));
            image_pro_jianhu.setImageResource(R.mipmap.pro_checked);
            tv_pro_jhr.setText(recordsBean.getJHRMC());
        }
        tv_pro_czdw.setText(recordsBean.getGZDDMC());
        tv_pro_bh.setText(recordsBean.getPH());
        tv_pro_startTime.setText(recordsBean.getCZKGSJ());
        tv_pro_endTime.setText(recordsBean.getCZJSSJ());
        et_pro_czrw.setText(recordsBean.getCZRW());
        tv_pro_beizhu.setText(recordsBean.getBZ());
        tv_pro_fzr.setText(recordsBean.getZBFZRMC());
        tv_pro_czr.setText(recordsBean.getCZRMC());
        ExecutionActivityAdapter executionActivityAdapter = new ExecutionActivityAdapter(ProcessedActivity.this, mCzbzArray);
        mLv_pro_czxm.setAdapter(executionActivityAdapter);
    }
}
