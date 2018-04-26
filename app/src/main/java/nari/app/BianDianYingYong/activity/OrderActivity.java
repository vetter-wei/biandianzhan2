package nari.app.BianDianYingYong.activity;

import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import nari.app.BianDianYingYong.R;
import nari.app.BianDianYingYong.adapter.OrderAdapter;
import nari.app.BianDianYingYong.base.BaseActivity;
import nari.app.BianDianYingYong.bean.OrderTicketActivityBean;
import nari.app.BianDianYingYong.bean.OrderTicketListBean;

/**
 * Created by DWQ on 2017/11/28.
 * 关联调度令
 */

public class OrderActivity extends BaseActivity implements View.OnClickListener {

    private ListView lvOrder;
    private ImageView back;
    private OrderTicketActivityBean orderBean;
    private TextView mTV_order_czmd; // 操作目的
    private TextView mTV_order_bh; // 编号
    private TextView mTV_order_nxr; // 拟写人
    private TextView mTV_order_shr; // 审核人
    private TextView mTV_order_ydcz_time; // 预定操作时间
    private TextView mTV_order_yfr; // 预发人
    private TextView mTV_order_jsr; // 接收人
    private TextView mTV_order_csr; // 抄送人
    private TextView mTV_order_czflr; // 操作发令人
    private TextView mTV_order_czjhr; // 操作监护人
    private List<OrderTicketListBean> list = new ArrayList<>();
    private OrderTicketListBean orderListBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_ticket);
        initView();
        initListener();
        String data=getIntent().getStringExtra("data");
        parseDispatchOrderData(data);
        parseXMLWithPull(orderBean.getDATA());
        OrderAdapter orderAdapter = new OrderAdapter(OrderActivity.this,list);
        lvOrder.setAdapter(orderAdapter);
    }

    private void initView() {
        lvOrder = findViewById(R.id.lv_activity_order_all);
        back = findViewById(R.id.image_order_activity_back);
        mTV_order_czmd = findViewById(R.id.tv_order_activity_czmd);
        mTV_order_bh = findViewById(R.id.tv_order_activity_bh);
        mTV_order_nxr = findViewById(R.id.tv_order_activity_nxr);
        mTV_order_shr = findViewById(R.id.tv_order_activity_shr);
        mTV_order_ydcz_time = findViewById(R.id.tv_order_activity_ydcz_time);
        mTV_order_yfr = findViewById(R.id.tv_order_activity_yfr);
        mTV_order_jsr = findViewById(R.id.tv_order_activity_jsr);
        mTV_order_csr = findViewById(R.id.tv_order_activity_csr);
        mTV_order_czflr = findViewById(R.id.tv_order_activity_czflr);
        mTV_order_czjhr = findViewById(R.id.tv_order_activity_czjhr);
    }

    private void initListener() {
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_order_activity_back:
                finish();
                break;
        }
    }

    /**
     * 解析关联调度令数据
     *
     * @author DWQ
     */
    private void parseDispatchOrderData(String t) {
        if (t == null && "" .equals(t)) {
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
                        //操作目的
                        String CZMD = record.getString("CZMD");
                        if ("null" .equals(CZMD)) {
                            CZMD = "";
                        }
                        //调度编号
                        String BH = record.getString("BH");
                        if ("null" .equals(BH)) {
                            BH = "";
                        }
                        String ZRWBH_ID = record.getString("ZRWBH_ID");    // 主任务id
                        if ("null" .equals(ZRWBH_ID)) {
                            ZRWBH_ID = "";
                        }

                        String NPR = record.getString("NPR");// 拟票人
                        if ("null" .equals(NPR)) {
                            NPR = "";
                        }
                        String SHR = record.getString("SHR");// 审核人
                        if ("null" .equals(SHR)) {
                            SHR = "";
                        }
                        String YDZXRQ = record.getString("YDZXRQ");// 预计执行时间
                        if ("null" .equals(YDZXRQ)) {
                            YDZXRQ = "";
                        }
                        String YFLR = record.getString("YFLR");// 预发令人
                        if ("null" .equals(YFLR)) {
                            YFLR = "";
                        }
                        String YSLR = record.getString("YSLR");// 预受令人
                        if ("null" .equals(YSLR)) {
                            YSLR = "";
                        }
                        String DDY = record.getString("DDY");// 调度员
                        if ("null" .equals(DDY)) {
                            DDY = "";
                        }

                        String JHR = record.getString("JHR");// 监护人
                        if ("null" .equals(JHR)) {
                            JHR = "";
                        }
                        String JHRMC = record.getString("JHRMC");// 监护名称
                        if ("null" .equals(JHRMC)) {
                            JHRMC = "";
                        }
                        String DATA = record.getString("DATA");
                        if ("null" .equals(DATA)) {
                            DATA = "";
                        }
                        orderBean = new OrderTicketActivityBean();
                        orderBean.setCZMD(CZMD);
                        orderBean.setBH(BH);
                        orderBean.setZRWBH_ID(ZRWBH_ID);
                        orderBean.setNPR(NPR);
                        orderBean.setSHR(SHR);
                        orderBean.setYDZXRQ(YDZXRQ);
                        orderBean.setYFLR(YFLR);
                        orderBean.setYSLR(YSLR);
                        orderBean.setDDY(DDY);
                        orderBean.setJHR(JHR);
                        orderBean.setJHRMC(JHRMC);
                        orderBean.setDATA(DATA);
                    }
                }
                setData();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void parseXMLWithPull(String xmlData) {
        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setInput(new StringReader(xmlData));

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String name = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if ("LIST_CZBZ" .equals(name)) {
                            orderListBean = new OrderTicketListBean();
                        } else if ("FXH" .equals(name)) {
                            String FXH = parser.nextText();
                            if ("null" .equals(FXH)) {
                                FXH = "";
                            }
                            orderListBean.setFXH(FXH);
                        } else if ("SBMC" .equals(name)) {
                            String SBMC = parser.nextText();
                            if ("null" .equals(SBMC)) {
                                SBMC = "";
                            }
                            orderListBean.setSBMC(SBMC);
                        } else if ("CZNR" .equals(name)) {
                            String CZNR = parser.nextText();
                            if ("null" .equals(CZNR)) {
                                CZNR = "";
                            }
                            orderListBean.setCZNR(CZNR);
                        } else if ("FLR" .equals(name)) {
                            String FLR = parser.nextText();
                            if ("null" .equals(FLR)) {
                                FLR = "";
                            }
                            orderListBean.setFLR(FLR);
                        } else if ("FLSJ" .equals(name)) {
                            String FLSJ = parser.nextText();
                            if ("null" .equals(FLSJ)) {
                                FLSJ = "";
                            }
                            orderListBean.setFLSJ(FLSJ);
                        } else if ("CZSJ" .equals(name)) {
                            String CZSJ = parser.nextText();
                            if ("null" .equals(CZSJ)) {
                                CZSJ = "";
                            }
                            orderListBean.setCZSJ(CZSJ);
                        } else if ("CZR" .equals(name)) {
                            String CZR = parser.nextText();
                            if ("null" .equals(CZR)) {
                                CZR = "";
                            }
                            orderListBean.setCZR(CZR);
                        } else if ("YJSJ" .equals(name)) {
                            String YJSJ = parser.nextText();
                            if ("null" .equals(YJSJ)) {
                                YJSJ = "";
                            }
                            orderListBean.setYJSJ(YJSJ);
                        } else if ("SLRMC" .equals(name)) {
                            String SLRMC = parser.nextText();
                            if ("null" .equals(SLRMC)) {
                                SLRMC = "";
                            }
                            orderListBean.setSLRMC(SLRMC);
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if ("LIST_CZBZ" .equals(parser.getName())) {
                            list.add(orderListBean);
                        }
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setData() {
        mTV_order_czmd.setText(orderBean.getCZMD());
        mTV_order_bh.setText(orderBean.getBH());
        mTV_order_nxr.setText(orderBean.getNPR());
        mTV_order_shr.setText(orderBean.getSHR());
        mTV_order_ydcz_time.setText(orderBean.getYDZXRQ());
        mTV_order_yfr.setText(orderBean.getYFLR());
        mTV_order_jsr.setText(orderBean.getYSLR());
        mTV_order_czflr.setText(orderBean.getDDY());
        mTV_order_czjhr.setText(orderBean.getJHR());
    }

}
