package nari.app.BianDianYingYong.customview;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import nari.app.BianDianYingYong.R;
import nari.app.BianDianYingYong.activity.ExecutionActivity;
import nari.app.BianDianYingYong.utils.DataReadUtil;
import nari.app.BianDianYingYong.utils.SharedPreferencesHelper;


/**
 * Created by ShawDLee on 2017/12/13.
 */

public class CustomJSZLDialog implements View.OnClickListener{
    private Context context;
    private Dialog mDialog;
    private TextView tv_jszl_title; //dialog标题
    private EditText et_jszl_flr; //发令人
    private TextView tv_jszl_flsj; //发令时间
    private EditText et_jszl_jlr; //接令人
    private TextView tv_jszl_bdz; //变电站
    private TextView tv_jszl_cznr; //操作内容
    private Button cancel; //取消
    private Button queren; //确认
    private String mObjId;
    private String mMbId;
    private String mRYMC; //人员名称
    private String mUserId; // 用户id
    private String flr; //发令人
    private String jlr; //接令人
    private ImageView image_jszl_close;
    private TimePickerView pvCustomTime;
    private String flsj;

    public CustomJSZLDialog(Context context, String mObjId, String mMbId) {
        this.context = context;
        this.mObjId = mObjId;
        this.mMbId = mMbId;
        initView();
        getZlqrData();
    }

    private void initView() {
        this.mDialog = new Dialog(context, R.style.dialogJszlStyle);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_jszl, null);
        tv_jszl_title = view.findViewById(R.id.tv_jszl_title);
        cancel = view.findViewById(R.id.button_jszl_cancel);
        queren = view.findViewById(R.id.button_jszl_queren);
        et_jszl_flr = view.findViewById(R.id.et_jszl_flr);
        tv_jszl_flsj = view.findViewById(R.id.tv_jszl_flsj);
        et_jszl_jlr = view.findViewById(R.id.et_jszl_jlr);
        tv_jszl_bdz = view.findViewById(R.id.tv_jszl_bdz);
        tv_jszl_cznr = view.findViewById(R.id.tv_jszl_cznr);
        image_jszl_close=view.findViewById(R.id.image_jszl_close);
        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(context, "PersonalInformation");
        mRYMC = sharedPreferencesHelper.getStringValue("RYMC");
        mUserId = sharedPreferencesHelper.getStringValue("userId");
        this.mDialog.setContentView(view);
//        this.mDialog.setCanceledOnTouchOutside(true);
        this.mDialog.show();
        initListener();
    }

    private void initListener() {
        initCustomTimePicker();
        cancel.setOnClickListener(this);
        queren.setOnClickListener(this);
        tv_jszl_flsj.setOnClickListener(this);
        image_jszl_close.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_jszl_queren:
                flsj = tv_jszl_flsj.getText().toString().trim();
                flr = et_jszl_flr.getText().toString().trim();
                jlr = et_jszl_jlr.getText().toString().trim();
                if (flr == null || "".equals(flr) || jlr == null || "".equals(jlr) || flsj == null || "".equals(flsj)) {
                    Toast.makeText(context, "正令确认中选项值不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    toExecuted();
                }
                break;
            case R.id.tv_jszl_flsj:
                pvCustomTime.show();
                break;
            case R.id.image_jszl_close:
                mDialog.cancel();
                break;
            case R.id.button_jszl_cancel:
                mDialog.cancel();
                break;
        }
    }
    /*
* 请求网络获取接受正令数据*/
    public void getZlqrData() {
        StringBuilder sb = new StringBuilder();
        //拼接请求体
        sb.append("<list>").append("\n")
                .append("<params>").append("\n")
                .append("<PID>").append(mObjId).append("</PID>").append("\n")
                .append("</params>").append("\n")
                .append("</list>");
        String serviceName = "sxlp1"; //   服务名
        String interfaceName = "getManagerBaseInfo";//   接口名
        Object[] params = new Object[]{sb};
        String data = DataReadUtil.getDataFromDb(serviceName, interfaceName, params);
        Log.e("lala", "CustomJSZLDialog   data======" + data);
        parseJszlData(data);
    }

    /**
     * 解析接受正令数据
     *
     * @author DWQ
     */
    private void parseJszlData(String t) {
        if (t == null && "".equals(t)) {
            //  Toast.makeText(getActivity(), "没有更多数据了", Toast.LENGTH_SHORT).show();
            Log.e("lala", "走了t == null &&.equals(t)=====" + t);
        } else {
            JSONObject data;
            try {
                data = new JSONObject(t);
                JSONArray records = data.getJSONArray("records");
                if (records.length() == 0) {
                    Toast.makeText(context, "没有更多数据了", Toast.LENGTH_SHORT).show();
                    Log.e("lala", "records.length() == 0=====" + records.length());
                } else {
                    for (int i = 0; i < records.length(); i++) {
                        JSONObject record = records.getJSONObject(i);
                    }
                }
                setData();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void setData() {
        et_jszl_flr.setText("");
        tv_jszl_flsj.setText("");
        et_jszl_jlr.setText("");
        tv_jszl_bdz.setText("");
        tv_jszl_cznr.setText("");
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
            Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
        } else {
            JSONObject datas;
            try {
                datas = new JSONObject(data);
                JSONArray records = datas.getJSONArray("records");
                if (records.length() == 0) {
                    Toast.makeText(context, "数据为空", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("lala", "records=======" + records.toString());
                    JSONObject record = records.getJSONObject(0);
                    //工作票主键
                    String flag = record.getString("FLAG");
                    if ("true".equals(flag)) {
                        Intent intent = new Intent(context, ExecutionActivity.class);
                        intent.putExtra("mbId", mMbId);
                        intent.putExtra("obj_id", mObjId);
                        intent.putExtra("flr", flr);
                        intent.putExtra("jlr", jlr);
                        intent.putExtra("flsj", flsj);
                        context.startActivity(intent);
                        ((Activity) context).finish();
                    } else {
                        Toast.makeText(context, "执行失败", Toast.LENGTH_SHORT).show();
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();

            }
        }
    }

    // 设置标题
    public void setTitle(String title) {
        tv_jszl_title.setText(title);
    }

    private void initCustomTimePicker() {

        /**
         * @description
         *
         * 注意事项：
         * 1.自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针.
         * 具体可参考demo 里面的两个自定义layout布局。
         * 2.因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
         * setRangDate方法控制起始终止时间(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
         */
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(2014, 1, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2027, 2, 28);
        //时间选择器 ，自定义布局
        pvCustomTime = new TimePickerView.Builder(context, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                tv_jszl_flsj.setText(getTime(date));
            }
        })
                /*.setType(TimePickerView.Type.ALL)//default is all
                .setCancelText("Cancel")
                .setSubmitText("Sure")
                .setContentSize(18)
                .setTitleSize(20)
                .setTitleText("Title")
                .setTitleColor(Color.BLACK)
               /*.setDividerColor(Color.WHITE)//设置分割线的颜色
                .setTextColorCenter(Color.LTGRAY)//设置选中项的颜色
                .setLineSpacingMultiplier(1.6f)//设置两横线之间的间隔倍数
                .setTitleBgColor(Color.DKGRAY)//标题背景颜色 Night mode
                .setBgColor(Color.BLACK)//滚轮背景颜色 Night mode
                .setSubmitColor(Color.WHITE)
                .setCancelColor(Color.WHITE)*/
               /*.gravity(Gravity.RIGHT)// default is center*/
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {

                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.returnData();
                                pvCustomTime.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.dismiss();
                            }
                        });
                    }
                })
                .setContentSize(18)
                .setType(new boolean[]{true, true, true, true, true, false})
                // .setType(new boolean[]{false, false, false, true, true, true})
                .setLabel("年", "月", "日", "时", "分", "秒")
                .setLineSpacingMultiplier(1.2f)
                .setTextXOffset(0, 0, 0, 0, 0, 0) // (0, 0, 0, 40, 0, -40)  设置滚轮样式
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(0xFF24AD9D)
                .build();

    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm"); ///    "yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }


}

