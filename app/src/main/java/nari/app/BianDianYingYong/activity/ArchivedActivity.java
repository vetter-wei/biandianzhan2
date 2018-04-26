package nari.app.BianDianYingYong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import nari.app.BianDianYingYong.R;
import nari.app.BianDianYingYong.adapter.ExecutionActivityAdapter;
import nari.app.BianDianYingYong.base.BaseActivity;
import nari.app.BianDianYingYong.bean.ExecutionTicketActivityBean;
import nari.app.BianDianYingYong.bean.ResultBean;
import nari.app.BianDianYingYong.utils.DataReadUtil;
import nari.app.BianDianYingYong.utils.StringUtil;

import static nari.app.BianDianYingYong.R.id.et_pro_czrw;
import static nari.app.BianDianYingYong.R.id.tv_pro_czr;
import static nari.app.BianDianYingYong.R.id.tv_pro_fzr;

/**
 * 已归档界面
 * Created by DWQ on 2017/11/9.
 */

public class ArchivedActivity extends BaseActivity implements View.OnClickListener {

    int ticketType = 0;//用于区分是哪个状态的操作票的详情0：待执行 1:执行中 2:归档票
    boolean jianhu, danren, jianxiu;// RadioButton选择状态
    private String objId;
    private String mbId;
    private TextView mTv_arc_top_name;
    private TextView mTv_arc_czdw;
    private TextView mTv_arc_bh;
    private TextView mTv_arc_start_time;
    private TextView mTv_arc_finsh_time;
    private EditText et_arc_czrw;
    private TextView mTv_arc_look_all;
    private TextView mTv_arc_czr;
    private TextView mTv_arc_jhr;
    private TextView mTv_arc_fzr;
    private TextView mTv_arc_bz;
    private ImageView mIv_arc_back;
    private ImageView mIv_arc_jianhu;
    private ImageView mIv_arc_danren;
    private ImageView mIv_arc_jianxiu;
    private String[] mCzbzArray;
    private ListView mLv_arc_czxm;
    private TextView mTv_arc_jianhu;
    private TextView mTv_arc_danren;
    private TextView mTv_arc_jianxiu;
    private String allCzbz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.archived_ticket_detail);
        init();
    }

    /**
     * 初始化控件
     */
    private void init() {
        mTv_arc_top_name = findViewById(R.id.tv_arc_top_name);
        mTv_arc_czdw = findViewById(R.id.tv_arc_czdw);
        mTv_arc_bh = findViewById(R.id.tv_arc_bh);
        mTv_arc_start_time = findViewById(R.id.tv_arc_start_time);
        mTv_arc_finsh_time = findViewById(R.id.tv_arc_finsh_time);
        et_arc_czrw = findViewById(R.id.et_arc_czrw);
        mTv_arc_look_all = findViewById(R.id.tv_arc_look_all);
        mTv_arc_jianhu = findViewById(R.id.tv_arc_jianhu);
        mTv_arc_danren = findViewById(R.id.tv_arc_danren);
        mTv_arc_jianxiu = findViewById(R.id.tv_arc_jianxiu);
        mTv_arc_czr = findViewById(R.id.tv_arc_czr);
        mTv_arc_jhr = findViewById(R.id.tv_arc_jhr);
        mTv_arc_fzr = findViewById(R.id.tv_arc_fzr);
        mTv_arc_bz = findViewById(R.id.tv_arc_bz);
        mIv_arc_back = findViewById(R.id.iv_arc_back);
        mIv_arc_jianhu = findViewById(R.id.iv_arc_jianhu);
        mIv_arc_danren = findViewById(R.id.iv_arc_danren);
        mIv_arc_jianxiu = findViewById(R.id.iv_arc_jianxiu);
        mLv_arc_czxm = findViewById(R.id.lv_arc_czxm);
        objId = getIntent().getStringExtra("obj_id");
        mbId = getIntent().getStringExtra("mbId");
        initListener();
        getTicketDetailsData();
    }

    /**
     * 监听
     */
    private void initListener() {
        mTv_arc_look_all.setOnClickListener(this);
        mIv_arc_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_arc_look_all:
                if ("".equals(allCzbz)) {
                    Toast.makeText(getApplicationContext(), "没有操作项目", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(ArchivedActivity.this, OperationStepsActivity.class);
                    intent.putExtra("status", "arc");
                    intent.putExtra("allCzbz", allCzbz);
                    startActivity(intent);
                }
                break;
            case R.id.iv_arc_back:
                finish();
                break;
            default:
                break;
        }
    }

    /*
* 请求网络获取票详情页数据*/
    public void getTicketDetailsData() {
        String objId = getIntent().getStringExtra("obj_id");
        String mbId = getIntent().getStringExtra("mbId");
        if (objId == null && mbId == null) {
            Toast.makeText(ArchivedActivity.this, "用户id为空，请重新登陆", Toast.LENGTH_SHORT).show();
        } else {
            StringBuilder sb = new StringBuilder();
            //拼接请求体
            sb.append("<list>").append("\n")
                    .append("<params>").append("\n")
                    .append("<PID>")
                    .append(objId)
                    .append("</PID>").append("\n")
                    .append("<MBID>")
                    .append(mbId)
                    .append("</MBID>").append("\n")
                    .append("</params>").append("\n")
                    .append("</list>");
            String serviceName = "sxlp1"; //   服务名
            String interfaceName = "getCZPInfo";//   接口名
            Object[] params = new Object[]{sb};
            String data = DataReadUtil.getDataFromDb(serviceName, interfaceName, params);
            Log.e("lala", "checktickactivity   data======" + data);
            String i = jsonString(data);
            setData(i);
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
    * 设置数据*/
    private void setData(String data) {
        if (objId == null || mbId == null || "".equals(objId) || "".equals(mbId)) {
            Toast.makeText(ArchivedActivity.this, "用户id为空，请重新登陆", Toast.LENGTH_SHORT).show();
        } else {
            ResultBean<ExecutionTicketActivityBean> resultBean = ResultBean.fromJson(data, ExecutionTicketActivityBean.class);
            ExecutionTicketActivityBean recordsBean = resultBean.getRecords().get(0);
            String cb_left = recordsBean.getJHXCZ();   //   监护下操作
            String cb_center = recordsBean.getDRCZ();   //   单人操作
            String cb_right = recordsBean.getJXRYCZ();   //   检修人员操作
            if ("".equals(cb_left.trim())) {
                if ("".equals(cb_center.trim())) {
                    mTv_arc_jianxiu.setTextColor(0xff29cccc);
                    mIv_arc_jianxiu.setImageResource(R.mipmap.pro_checked);
                    mTv_arc_jhr.setText(recordsBean.getJHRMC());
                } else {
                    mTv_arc_danren.setTextColor(0xff29cccc);
                    mIv_arc_danren.setImageResource(R.mipmap.pro_checked);
                    mTv_arc_jhr.setText("(空)");
                }
            } else {
                mTv_arc_jianhu.setTextColor(0xff29cccc);
                mIv_arc_jianhu.setImageResource(R.mipmap.pro_checked);
                mTv_arc_jhr.setText(recordsBean.getJHRMC());
            }
            allCzbz = recordsBean.getHWB();
            mCzbzArray = StringUtil.splitString("@@", allCzbz);
            mTv_arc_czdw.setText(recordsBean.getGZDDMC());
            mTv_arc_bh.setText(recordsBean.getPH());
            mTv_arc_start_time.setText(recordsBean.getCZKGSJ());
            mTv_arc_finsh_time.setText(recordsBean.getCZJSSJ());
            et_arc_czrw.setText(recordsBean.getCZRW());
            mTv_arc_bz.setText(recordsBean.getBZ());
            mTv_arc_fzr.setText(recordsBean.getZBFZRMC());
            mTv_arc_czr.setText(recordsBean.getCZRMC());
            ExecutionActivityAdapter mExecutionActivityAdapter = new ExecutionActivityAdapter(ArchivedActivity.this, mCzbzArray);
            mLv_arc_czxm.setAdapter(mExecutionActivityAdapter);
        }
    }
}
