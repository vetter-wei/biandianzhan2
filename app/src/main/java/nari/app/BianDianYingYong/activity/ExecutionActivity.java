package nari.app.BianDianYingYong.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;

import net.tsz.afinal.FinalDb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import nari.app.BianDianYingYong.R;
import nari.app.BianDianYingYong.adapter.ExecutionActivityAdapter;
import nari.app.BianDianYingYong.adapter.StepsListViewAdapter;
import nari.app.BianDianYingYong.base.BaseActivity;
import nari.app.BianDianYingYong.base.BaseApplication;
import nari.app.BianDianYingYong.bean.CheckTicketActivityBean;
import nari.app.BianDianYingYong.bean.ExecutionTicketActivityBean;
import nari.app.BianDianYingYong.bean.ResultBean;
import nari.app.BianDianYingYong.customview.BaseButtonDialog;
import nari.app.BianDianYingYong.customview.CusDialogET;
import nari.app.BianDianYingYong.customview.CustomDatePicker;
import nari.app.BianDianYingYong.customview.CustomListView;
import nari.app.BianDianYingYong.customview.CustomSignatureDialog;
import nari.app.BianDianYingYong.jinyi.activity_jinyi.MainActivity_jinyi;
import nari.app.BianDianYingYong.utils.DataReadUtil;
import nari.app.BianDianYingYong.utils.SharedPreferencesHelper;
import nari.app.BianDianYingYong.utils.StringUtil;

import static nari.app.BianDianYingYong.R.mipmap.beizhu;
import static nari.mip.core.a.a.t;
import static nari.mip.core.c.i;
import static nari.mip.core.c.j;


/**
 * 执行中界面
 */

public class ExecutionActivity extends BaseActivity implements View.OnClickListener {
    boolean jianhu, danren, jianxiu;// checkbox选择状态
    private ImageView mIv_exe_back;
    private TextView mTv_exe_look_all1;
    private TextView mTv_exe_czr;
    private TextView mTv_exe_jhr;
    private TextView mTv_exe_ywfzr;
    private CustomListView mLv_exe_czxm;
    private String[] mCzbzArray;
    private TextView mTv_exe_top_name;
    private TextView mTv_exe_czdw;
    private TextView mTv_exe_bh;
    private TextView mTv_exe_start_time;
    private TextView mTv_exe_finsh_time;
    private EditText et_exe_czrw;
    private TextView et_exe_bz;
    private String objId;
    private String mbId;
    private String mRYMC;
    private String mUserId;
    private ExecutionActivityAdapter mExecutionActivityAdapter;//   步骤适配器
    private BaseButtonDialog mBaseButtonDialog;
    private ImageView iv_exe_jianhu;
    private ImageView iv_exe_danren;
    private ImageView iv_exe_jianxiu;
    private TextView tv_exe_jianhu;
    private TextView tv_exe_danren;
    private TextView tv_exe_jianxiu;
    private LinearLayout ll_exe_jianhu;
    private LinearLayout ll_exe_danren;
    private LinearLayout ll_exe_jianxiu;
    private RelativeLayout rl_exe_czr;
    private RelativeLayout rl_exe_jhr;
    private RelativeLayout rl_exe_fzr;
    private LinearLayout mLl_exe_wzx;
    private LinearLayout mLl_exe_finsh;
    private String allCzbz;
    private String[] array1;
    private String mTimeSub = "";
    private String result;
    private String[] array;
    private TimePickerView pvCustomTime;
    private int mTimeStyle;
    public static Handler handler = new Handler();
    public static boolean stepToExe;
    public static boolean stepToArc;
    private StepsListViewAdapter stepsListViewAdapter;
    private String lastData = "";
    FinalDb db;
    private String status;
    private ResultBean<ExecutionTicketActivityBean> resultBean;
    private ExecutionTicketActivityBean exeTicketBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_exection_ticket);
        db = FinalDb.create(BaseApplication.mApplicationInstance);
        init();
        if (status.equals("1")) {
            getTicketDetailsData();
        } else {
            List<ExecutionTicketActivityBean> list1 = db.findAllByWhere(ExecutionTicketActivityBean.class, "OBJ_ID  = \"" + objId + "\"");
            if (list1 == null || list1.size() == 0) {

            } else {
                exeTicketBean = list1.get(0);
                setData(exeTicketBean);
            }
        }

    }


    /**
     * 初始化控件
     */

    private void init() {
        initCustomTimePicker();
        mTv_exe_top_name = findViewById(R.id.tv_exe_top_name);
        mTv_exe_czdw = findViewById(R.id.tv_exe_czdw);
        mTv_exe_bh = findViewById(R.id.tv_exe_bh);
        mTv_exe_start_time = findViewById(R.id.tv_exe_start_time);
        mTv_exe_finsh_time = findViewById(R.id.tv_exe_end_time);
        et_exe_czrw = findViewById(R.id.et_exe_czrw);
        mLv_exe_czxm = findViewById(R.id.lv_exe_czxm);
        mTv_exe_look_all1 = findViewById(R.id.tv_exe_look_all);
        mTv_exe_czr = findViewById(R.id.tv_exe_czr);
        mTv_exe_jhr = findViewById(R.id.tv_exe_jhr);
        mTv_exe_ywfzr = findViewById(R.id.tv_exe_ywfzr);
        et_exe_bz = findViewById(R.id.et_exe_beizhu);
        mIv_exe_back = findViewById(R.id.iv_exe_back);
        iv_exe_jianhu = findViewById(R.id.iv_exe_jianhu);
        iv_exe_danren = findViewById(R.id.iv_exe_danren);
        iv_exe_jianxiu = findViewById(R.id.iv_exe_jianxiu);
        tv_exe_jianhu = findViewById(R.id.tv_exe_jianhu);
        tv_exe_danren = findViewById(R.id.tv_exe_danren);
        tv_exe_jianxiu = findViewById(R.id.tv_exe_jianxiu);
        mLl_exe_wzx = findViewById(R.id.ll_exe_wzx);
        mLl_exe_finsh = findViewById(R.id.ll_exe_finsh);
        rl_exe_czr = findViewById(R.id.rl_exe_czr);
        rl_exe_jhr = findViewById(R.id.rl_exe_jhr);
        rl_exe_fzr = findViewById(R.id.rl_exe_fzr);
        ll_exe_jianhu = findViewById(R.id.ll_exe_jianhu);
        ll_exe_danren = findViewById(R.id.ll_exe_danren);
        ll_exe_jianxiu = findViewById(R.id.ll_exe_jianxiu);
        objId = getIntent().getStringExtra("obj_id");
        mbId = getIntent().getStringExtra("mbId");
        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(getApplicationContext(), "PersonalInformation");
        mRYMC = sharedPreferencesHelper.getStringValue("userName");
        mUserId = sharedPreferencesHelper.getStringValue("userId");
        status = sharedPreferencesHelper.getStringValue("status");
        initListener();
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
                    String czjsTime = mTv_exe_finsh_time.getText().toString().trim();
                    if (czjsTime.equals("") || czjsTime == null) {
                        mTv_exe_start_time.setText(getTime(date));
                    } else {
                        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        Date d = null;
                        try {
                            d = fmt.parse(czjsTime);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if (date.before(d)) {
                            mTv_exe_start_time.setText(getTime(date));
                        } else {
                            Toast.makeText(ExecutionActivity.this, "操作开始时间必须早于操作结束时间！", Toast.LENGTH_SHORT).show();
                        }
                    }

                } else if (mTimeStyle == 2) {   // 3： 操作结束时间；
                    String czksT = mTv_exe_start_time.getText().toString().trim();
                    //把string转化为date
                    DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    Date d = null;
                    try {
                        d = fmt.parse(czksT);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (czksT == null || "".equals(czksT)) {
                        Toast.makeText(ExecutionActivity.this, "请先选择操作开始时间！", Toast.LENGTH_LONG).show();
                    } else {
                        if (date.after(d)) {
                            mTv_exe_finsh_time.setText(getTime(date));
                        } else {
                            Toast.makeText(ExecutionActivity.this, "操作结束时间必须晚于操作开始时间！", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        }, "2010-01-01 00:00", "2025-01-01 00:00");
        customDatePicker1.showSpecificTime(false);
        customDatePicker1.setIsLoop(true);



    }
    private void initListener() {
        mTv_exe_look_all1.setOnClickListener(this);
        mIv_exe_back.setOnClickListener(this);
        rl_exe_czr.setOnClickListener(this);
        rl_exe_jhr.setOnClickListener(this);
        rl_exe_fzr.setOnClickListener(this);
        ll_exe_jianhu.setOnClickListener(this);
        ll_exe_danren.setOnClickListener(this);
        ll_exe_jianxiu.setOnClickListener(this);
        mLl_exe_wzx.setOnClickListener(this);
        mLl_exe_finsh.setOnClickListener(this);
        mTv_exe_start_time.setOnClickListener(this);
        mTv_exe_finsh_time.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        switch (view.getId()) {
            case R.id.tv_exe_start_time:
                mTimeStyle = 1;
                customDatePicker1.show(now);
                break;
            case R.id.tv_exe_end_time:
                mTimeStyle = 2;
                customDatePicker1.show(now);
                break;
            case R.id.tv_exe_look_all:
                if ("".equals(allCzbz)) {
                    Toast.makeText(getApplicationContext(), "没有操作项目", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(ExecutionActivity.this, OperationStepsActivity.class);
                    intent.putExtra("status", "exe");
                    intent.putExtra("objId", objId);
                    intent.putExtra("ph", mTv_exe_bh.getText().toString());
                    if ("".equals(result) || result == null) {
                        intent.putExtra("allCzbz", allCzbz);
                    } else {
                        intent.putExtra("allCzbz", result);
                    }
                    startActivityForResult(intent, 0);
                }
                break;
            case R.id.ll_exe_wzx: //   未执行
                if (mRYMC == null || "".equals(mRYMC) || mUserId == null || "".equals(mUserId)) {
                    Toast.makeText(getApplicationContext(), "您的用户名或用户id为空", Toast.LENGTH_SHORT).show();
                } else {
                    //toObsolete();
                    CusDialogET cusDialogET = new CusDialogET(ExecutionActivity.this, mRYMC, mUserId, objId, "1");
                    cusDialogET.setTitle("未执行原因");
                    cusDialogET.show();
                }
                break;
            case R.id.ll_exe_finsh://   终结
                if (mRYMC == null || "".equals(mRYMC) || mUserId == null || "".equals(mUserId)) {
                    Toast.makeText(getApplicationContext(), "您的用户名或用户id为空", Toast.LENGTH_SHORT).show();
                } else {
                    if (mCzbzArray[mCzbzArray.length - 1].contains("√") || mCzbzArray[mCzbzArray.length - 1].contains("×")
                            || lastData.contains("√") || lastData.contains("×")) {
                        if (danren == false && jianhu == true && jianxiu == false) {
                            if ("".equals(mTv_exe_czr.getText().toString().trim())) {
                                Toast.makeText(getApplicationContext(), "请填写操作人", Toast.LENGTH_SHORT).show();
                            } else {
                                if ("".equals(mTv_exe_jhr.getText().toString().trim())) {
                                    Toast.makeText(getApplicationContext(), "请填写监护人", Toast.LENGTH_SHORT).show();
                                } else {
                                    outBaseButtonDialog();
                                }
                            }
                        } else {
                            outBaseButtonDialog();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "操作项目未全部完成", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.ll_exe_jianhu:
                tv_exe_jianhu.setTextColor(getResources().getColor(R.color.colorPrimary));
                iv_exe_jianhu.setImageResource(R.mipmap.pro_checked);
                tv_exe_danren.setTextColor(0xff4d4d4d);
                iv_exe_danren.setImageResource(R.mipmap.pro_unchecked);
                tv_exe_jianxiu.setTextColor(0xff4d4d4d);
                iv_exe_jianxiu.setImageResource(R.mipmap.pro_unchecked);
                jianhu = true;
                danren = false;
                jianxiu = false;
                mTv_exe_jhr.setText("");
                rl_exe_jhr.setClickable(true);
                break;

            case R.id.ll_exe_danren:
                tv_exe_jianhu.setTextColor(0xff4d4d4d);
                iv_exe_jianhu.setImageResource(R.mipmap.pro_unchecked);
                tv_exe_danren.setTextColor(getResources().getColor(R.color.colorPrimary));
                iv_exe_danren.setImageResource(R.mipmap.pro_checked);
                tv_exe_jianxiu.setTextColor(0xff4d4d4d);
                iv_exe_jianxiu.setImageResource(R.mipmap.pro_unchecked);
                danren = true;
                jianxiu = false;
                jianhu = false;
                mTv_exe_jhr.setText("(空)");
                rl_exe_jhr.setClickable(false);
                break;

            case R.id.ll_exe_jianxiu:
                tv_exe_jianhu.setTextColor(0xff4d4d4d);
                iv_exe_jianhu.setImageResource(R.mipmap.pro_unchecked);
                tv_exe_danren.setTextColor(0xff4d4d4d);
                iv_exe_danren.setImageResource(R.mipmap.pro_unchecked);
                tv_exe_jianxiu.setTextColor(getResources().getColor(R.color.colorPrimary));
                iv_exe_jianxiu.setImageResource(R.mipmap.pro_checked);
                jianxiu = true;
                jianhu = false;
                danren = false;
                mTv_exe_jhr.setText("");
                rl_exe_jhr.setClickable(true);
                break;
            case R.id.iv_exe_back:
                String kssj = mTv_exe_start_time.getText().toString().trim();
                String jssj = mTv_exe_finsh_time.getText().toString().trim();
                if ("".equals(kssj) || "".equals(jssj)) {
                    Toast.makeText(getApplicationContext(), "请选择操作开始和结束时间", Toast.LENGTH_SHORT).show();
                } else {
                    backSaveCZPInfo();
                    finish();
                }
                break;
            case R.id.rl_exe_czr:
                if (danren == false && jianhu == false && jianxiu == false) {
                    Toast.makeText(getApplicationContext(), "必须选择一种操作模式", Toast.LENGTH_SHORT).show();
                } else {
                    CustomSignatureDialog dialog1 = new CustomSignatureDialog(ExecutionActivity.this, objId, mbId, new CustomSignatureDialog.MyListener() {
                        @Override
                        public void getSpinnerText(String userName) {
                            String czr = userName;
                            mTv_exe_czr.setText(czr);
                        }
                    });
                    dialog1.setTitle("操作人签名");
                }
                break;
            case R.id.rl_exe_jhr:
                if (danren == false && jianhu == false && jianxiu == false) {
                    Toast.makeText(getApplicationContext(), "必须选择一种操作模式", Toast.LENGTH_SHORT).show();
                } else if (jianhu == true || jianxiu == true) {
                    CustomSignatureDialog dialog2 = new CustomSignatureDialog(ExecutionActivity.this, objId, mbId, new CustomSignatureDialog.MyListener() {
                        @Override
                        public void getSpinnerText(String userName) {
                            String jhr = userName;
                            mTv_exe_jhr.setText(jhr);
                        }
                    });
                    dialog2.setTitle("监护人签名");
                }
                break;
            case R.id.rl_exe_fzr:
                if (danren == false && jianhu == false && jianxiu == false) {
                    Toast.makeText(getApplicationContext(), "必须选择一种操作模式", Toast.LENGTH_SHORT).show();
                } else {
                    CustomSignatureDialog dialog3 = new CustomSignatureDialog(ExecutionActivity.this, objId, mbId, new CustomSignatureDialog.MyListener() {
                        @Override
                        public void getSpinnerText(String userName) {
                            String fzr = userName;
                            mTv_exe_ywfzr.setText(fzr);
                        }
                    });
                    dialog3.setTitle("负责人签名");
                }
                break;
        }
    }

    private void saveCZPInfo(BaseButtonDialog dialog) {
        String czkgsj = mTv_exe_start_time.getText().toString().trim();
        String czjssj = mTv_exe_finsh_time.getText().toString().trim();
        String jh, dr, jx;
        if (jianhu) {
            jh = "√";
        } else {
            jh = "";
        }
        if (danren) {
            dr = "√";
        } else {
            dr = "";
        }
        if (jianxiu) {
            jx = "√";
        } else {
            jx = "";
        }
        if (result == null) {
            result = "";
        }
        String bzInfo = et_exe_bz.getText().toString().trim();
        String czrmc = mTv_exe_czr.getText().toString().trim();
        String jhrmc = mTv_exe_jhr.getText().toString().trim();
        String fzrmc = mTv_exe_ywfzr.getText().toString().trim();
        if (mRYMC == null || "".equals(mRYMC) || mUserId == null || "".equals(mUserId) || objId == null || mbId == null) {
            Toast.makeText(ExecutionActivity.this, "用户id为空，请重新登陆", Toast.LENGTH_SHORT).show();
        } else {
            StringBuilder sb = new StringBuilder();
            //拼接请求体
            sb.append("<config>")
                    .append("<list>")
                    .append("<params>")
                    .append("<USER_NAME>").append(mRYMC).append("</USER_NAME>")
                    .append("<USER_ID>").append(mUserId).append("</USER_ID>")
                    .append("<PID>").append(objId).append("</PID>")
                    .append("<MKLX>").append("2").append("</MKLX>")
                    .append("<MBID>").append(mbId).append("</MBID>")
                    .append("</params>")
                    .append("</list>")
                    .append("<patterns>")
                    .append("<pattern>")

                    .append("<item key=\"操作开工时间\">")
                    .append(czkgsj)
                    .append("</item>")

                    .append("<item key=\"操作结束时间\">")
                    .append(czjssj)
                    .append("</item>")

                    .append("<item key=\"监护下操作\">")
                    .append(jh)
                    .append("</item>")

                    .append("<item key=\"单人操作\">")
                    .append(dr)
                    .append("</item>")

                    .append("<item key=\"检修人员操作\">")
                    .append(jx)
                    .append("</item>")

                    .append("<item key=\"操作步骤\">")
                    .append(result)
                    .append("</item>")

                    .append("<item key=\"备注\">")
                    .append(bzInfo)
                    .append("</item>")

                    .append("<item key=\"操作人名称\">")
                    .append(czrmc)
                    .append("</item>")

                    .append("<item key=\"监护人名称\">")
                    .append(jhrmc)
                    .append("</item>")

                    .append("<item key=\"值班负责人名称\">")
                    .append(fzrmc)
                    .append("</item>")

                    .append("</pattern>")
                    .append("</patterns>")
                    .append("</config>");
            String serviceName = "sxlp1"; //   服务名
            String interfaceName = "saveCZPInfo";//   接口名
            Object[] params = new Object[]{sb};
            String data = DataReadUtil.getDataFromDb(serviceName, interfaceName, params);
            Log.e("lala", "saveCZPInfo   data======" + data);
            if (data == null && "".equals(data)) {
                Toast.makeText(ExecutionActivity.this, "请检查网络", Toast.LENGTH_SHORT).show();
            } else {
                JSONObject datas;
                try {
                    datas = new JSONObject(data);
                    JSONArray records = datas.getJSONArray("records");
                    if (records.length() == 0) {
                        Toast.makeText(ExecutionActivity.this, "数据为空", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e("lala", "records=======" + records.toString());
                        JSONObject record = records.getJSONObject(0);
                        //工作票主键
                        String flag = record.getString("FLAG");
                        if ("true".equals(flag)) {
                            toEndCZP(dialog);
                        } else {
                            Toast.makeText(ExecutionActivity.this, "终结失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void backSaveCZPInfo() {
        String czkgsj = mTv_exe_start_time.getText().toString().trim();
        String czjssj = mTv_exe_finsh_time.getText().toString().trim();
        String jh, dr, jx;
        if (jianhu) {
            jh = "√";
        } else {
            jh = "";
        }
        if (danren) {
            dr = "√";
        } else {
            dr = "";
        }
        if (jianxiu) {
            jx = "√";
        } else {
            jx = "";
        }
        if (result == null) {
            result = "";
        }
        String bzInfo = et_exe_bz.getText().toString().trim();
        String czrmc = mTv_exe_czr.getText().toString().trim();
        String jhrmc = mTv_exe_jhr.getText().toString().trim();
        String fzrmc = mTv_exe_ywfzr.getText().toString().trim();
        if (status.equals("1")) {
            if (mRYMC == null || "".equals(mRYMC) || mUserId == null || "".equals(mUserId) || objId == null || mbId == null) {
                Toast.makeText(ExecutionActivity.this, "用户id为空，请重新登陆", Toast.LENGTH_SHORT).show();
            } else {
                StringBuilder sb = new StringBuilder();
                //拼接请求体
                sb.append("<config>")
                        .append("<list>")
                        .append("<params>")
                        .append("<USER_NAME>").append(mRYMC).append("</USER_NAME>")
                        .append("<USER_ID>").append(mUserId).append("</USER_ID>")
                        .append("<PID>").append(objId).append("</PID>")
                        .append("<MKLX>").append("2").append("</MKLX>")
                        .append("<MBID>").append(mbId).append("</MBID>")
                        .append("</params>")
                        .append("</list>")
                        .append("<patterns>")
                        .append("<pattern>")

                        .append("<item key=\"操作开工时间\">")
                        .append(czkgsj)
                        .append("</item>")

                        .append("<item key=\"操作结束时间\">")
                        .append(czjssj)
                        .append("</item>")

                        .append("<item key=\"监护下操作\">")
                        .append(jh)
                        .append("</item>")

                        .append("<item key=\"单人操作\">")
                        .append(dr)
                        .append("</item>")

                        .append("<item key=\"检修人员操作\">")
                        .append(jx)
                        .append("</item>")

                        .append("<item key=\"操作步骤\">")
                        .append(result)
                        .append("</item>")

                        .append("<item key=\"备注\">")
                        .append(bzInfo)
                        .append("</item>")

                        .append("<item key=\"操作人名称\">")
                        .append(czrmc)
                        .append("</item>")

                        .append("<item key=\"监护人名称\">")
                        .append(jhrmc)
                        .append("</item>")

                        .append("<item key=\"值班负责人名称\">")
                        .append(fzrmc)
                        .append("</item>")
                        .append("</pattern>")
                        .append("</patterns>")
                        .append("</config>");
                String serviceName = "sxlp1"; //   服务名
                String interfaceName = "saveCZPInfo";//   接口名
                Object[] params = new Object[]{sb};
                String data = DataReadUtil.getDataFromDb(serviceName, interfaceName, params);
                Log.e("lala", "saveCZPInfo   data======" + data);
                if (data == null && "".equals(data)) {
                    Toast.makeText(ExecutionActivity.this, "请检查网络", Toast.LENGTH_SHORT).show();
                } else {
                    JSONObject datas;
                    try {
                        datas = new JSONObject(data);
                        JSONArray records = datas.getJSONArray("records");
                        if (records.length() == 0) {
                            Toast.makeText(ExecutionActivity.this, "数据为空", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e("lala", "records=======" + records.toString());
                            JSONObject record = records.getJSONObject(0);
                            //工作票主键
                            String flag = record.getString("FLAG");
                            if ("true".equals(flag)) {
                                Toast.makeText(ExecutionActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ExecutionActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            ExecutionTicketActivityBean exeSaveBean = db.findAllByWhere(ExecutionTicketActivityBean.class, "OBJ_ID  = \"" + objId + "\"").get(0);
            exeSaveBean.setCZKGSJ(czkgsj);
            exeSaveBean.setCZJSSJ(czjssj);
            exeSaveBean.setBZ(bzInfo);
            exeSaveBean.setCZRMC(czrmc);
            exeSaveBean.setJHRMC(jhrmc);
            exeSaveBean.setZBFZRMC(fzrmc);
            exeSaveBean.setJHXCZ(jh);
            exeSaveBean.setDRCZ(dr);
            exeSaveBean.setJXRYCZ(jx);
            exeSaveBean.setHWB(result);
            db.update(exeSaveBean);
        }
    }

    private void outBaseButtonDialog() {
        if (mBaseButtonDialog == null) {
            mBaseButtonDialog = new BaseButtonDialog(ExecutionActivity.this);
            mBaseButtonDialog.setText("您确定要终结该票？");
            mBaseButtonDialog.setButtonListener(new BaseButtonDialog.OnButtonListener() {
                @Override
                public void onLeftButtonClick(BaseButtonDialog dialog) {

                    dialog.dismiss();
                }

                @Override
                public void onRightButtonClick(BaseButtonDialog dialog) { //   确定

                    saveCZPInfo(dialog);

                }
            });
        }
        mBaseButtonDialog.show();
    }

    /*
* 请求网络获取票详情页数据*/
    public void getTicketDetailsData() {
        if (objId == null || mbId == null || "".equals(objId) || "".equals(mbId)) {
            Toast.makeText(ExecutionActivity.this, "用户id为空，请重新登陆", Toast.LENGTH_SHORT).show();
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
            resultBean = ResultBean.fromJson(i, ExecutionTicketActivityBean.class);
            exeTicketBean = resultBean.getRecords().get(0);
            setData(exeTicketBean);
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
    private void setData(ExecutionTicketActivityBean recordsBean) {
        allCzbz = recordsBean.getHWB();
        Log.e("lala", "操作步骤============" + allCzbz);
        mCzbzArray = StringUtil.splitString("@@", allCzbz);
        String cb_left = recordsBean.getJHXCZ();   //   监护下操作
        String cb_center = recordsBean.getDRCZ();   //   单人操作
//            String cb_right = recordsBean.getJXRYCZ();   //   检修人员操作
        if ("".equals(cb_left.trim())) { // 监护状态下进行的操作
            if ("".equals(cb_center.trim())) {
                tv_exe_jianxiu.setTextColor(getResources().getColor(R.color.colorPrimary));
                iv_exe_jianxiu.setImageResource(R.mipmap.pro_checked);
                jianxiu = true;
                danren = false;
                jianhu = false;
                mTv_exe_jhr.setText(recordsBean.getJHRMC());
                rl_exe_jhr.setClickable(true);
            } else {
                tv_exe_danren.setTextColor(getResources().getColor(R.color.colorPrimary));
                iv_exe_danren.setImageResource(R.mipmap.pro_checked);
                danren = true;
                jianxiu = false;
                jianhu = false;
                mTv_exe_jhr.setText("(空)");
                rl_exe_jhr.setClickable(false);
            }
        } else {
            tv_exe_jianhu.setTextColor(getResources().getColor(R.color.colorPrimary));
            iv_exe_jianhu.setImageResource(R.mipmap.pro_checked);
            jianhu = true;
            danren = false;
            jianxiu = false;
            mTv_exe_jhr.setText(recordsBean.getJHRMC());
            rl_exe_jhr.setClickable(true);
        }
        mTv_exe_czdw.setText(recordsBean.getGZDDMC());
        mTv_exe_bh.setText(recordsBean.getPH());
        mTv_exe_start_time.setText(recordsBean.getCZKGSJ());
        mTv_exe_finsh_time.setText(recordsBean.getCZJSSJ());
        et_exe_czrw.setText(recordsBean.getCZRW());
        et_exe_bz.setText(recordsBean.getBZ());
        mTv_exe_czr.setText(recordsBean.getCZRMC());
        mTv_exe_ywfzr.setText(recordsBean.getZBFZRMC());
        mExecutionActivityAdapter = new ExecutionActivityAdapter(ExecutionActivity.this, mCzbzArray);
        mLv_exe_czxm.setAdapter(mExecutionActivityAdapter);
    }


    /*
   * 发送操作票终结消息*/
    private void toEndCZP(BaseButtonDialog dialog) {
        String finishName = "终结|终结人：" + mRYMC;
        StringBuilder sb = new StringBuilder();
        //拼接请求体
        sb.append("<list>").append("\n")
                .append("<params>").append("\n")
                .append("<USER_NAME>>")  //人员名称
                .append(mRYMC)
                .append("</USER_NAME>>").append("\n")
                .append("<USER_ID>")  //用户id
                .append(mUserId)
                .append("</USER_ID>").append("\n")
                .append("<PID>")  //票id
                .append(objId)
                .append("</PID>").append("\n")
                .append("<PZT>")   //票状态
                .append("51")
                .append("</PZT>").append("\n")
                .append("<LOG_INFO>")  //
                .append(finishName)
                .append("</LOG_INFO>").append("\n")
                .append("<CZLX>")
                .append("终结")
                .append("</CZLX>").append("\n")
                .append("<MKLX>")
                .append("2")
                .append("</MKLX>").append("\n")
                .append("<MBID>")
                .append(mbId)
                .append("</MBID>").append("\n")
                .append("</params>").append("\n")
                .append("</list>");
        String serviceName = "sxlp1"; //   服务名
        String interfaceName = "toEndCZP";//   接口名
        Object[] params = new Object[]{sb};
        String data = DataReadUtil.getDataFromDb(serviceName, interfaceName, params);
        Log.e("lala", "点击终结按钮后的数据===============" + data);
        if (data == null && "".equals(data)) {
            Toast.makeText(ExecutionActivity.this, "请检查网络", Toast.LENGTH_SHORT).show();
        } else {
            JSONObject datas;
            try {
                datas = new JSONObject(data);
                JSONArray records = datas.getJSONArray("records");
                if (records.length() == 0) {
                    Toast.makeText(ExecutionActivity.this, "数据为空", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("lala", "records=======" + records.toString());
                    JSONObject record = records.getJSONObject(0);
                    //工作票主键
                    String flag = record.getString("FLAG");
                    if ("true".equals(flag)) {
                        dialog.dismiss();
                        Toast.makeText(ExecutionActivity.this, "终结成功", Toast.LENGTH_SHORT).show();
//                        new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Message message = new Message();
//                                message.what =0;
//                                handler.sendMessage(message);
//                            }
//                        }).start();
                        stepToExe = true;
                        stepToArc = true;
                        finish();
                    } else {
                        Toast.makeText(ExecutionActivity.this, "终结失败", Toast.LENGTH_SHORT).show();
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        result = data.getExtras().getString("czxm");
        array = StringUtil.splitString("@@", result);
        lastData = array[array.length - 1];
        String[] array2 = StringUtil.splitString("\\|", lastData);
        if (array2[array2.length - 1].length() == 19 && array2[array2.length - 1].contains(":")) {
            mTv_exe_finsh_time.setText(array2[array2.length - 1].substring(0, array2[array2.length - 1].length() - 3));
        }
        for (int i = 0; i < mLv_exe_czxm.getChildCount(); i++) {
            String order = array[i];
            array1 = StringUtil.splitString("\\|", order);
            View view = mLv_exe_czxm.getChildAt(i);
            ExecutionActivityAdapter.ViewHolder holder = (ExecutionActivityAdapter.ViewHolder) view.getTag();
            holder.tv_czxm_number.setText(array1[1]);
            holder.tv_czxm_cznr.setText(array1[2]);
            for (int j = 0; j < array1.length; j++) {
                if (array1[array1.length - 1].length() == 19 && array1[array1.length - 1].contains(":")) {
                    holder.tv_czxm_czsj.setText(array1[array1.length - 1].substring(10, array1[array1.length - 1].length() - 3));
                    if (i == 0) {
                        mTv_exe_start_time.setText(array1[array1.length - 1].substring(0, array1[array1.length - 1].length() - 3));
                        if (array1[0].contains("×")) {
                            holder.tv_czxm_czsj.setText("");
                        }
                    }
                } else {
                    holder.tv_czxm_czsj.setText("");
                }
            }
        }
        Log.e("lala", "result=======" + result);
    }



    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); ///    "yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }
}
