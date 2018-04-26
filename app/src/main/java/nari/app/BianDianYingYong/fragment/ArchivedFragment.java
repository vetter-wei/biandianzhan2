package nari.app.BianDianYingYong.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import nari.app.BianDianYingYong.R;
import nari.app.BianDianYingYong.activity.ArchivedActivity;
import nari.app.BianDianYingYong.activity.ExecutionActivity;
import nari.app.BianDianYingYong.activity.MainActivity;
import nari.app.BianDianYingYong.adapter.ArchivedAdapter;
import nari.app.BianDianYingYong.base.BasePullToRefresh;
import nari.app.BianDianYingYong.bean.ArchivedTicketListBean;
import nari.app.BianDianYingYong.customview.PullToRefreshLayout;
import nari.app.BianDianYingYong.utils.DataReadUtil;

/**
 * Created by TQM on 2017/10/17.
 * 已归档界面
 */
public class ArchivedFragment extends android.support.v4.app.Fragment {

    private PullToRefreshLayout mPf_fragment_archived;
    private ListView mLv_fragment_archived;
//    private Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what){
//                case 0:
//                    getData("1");
//                    break;
//            }
//        }
//    };
    /**
     * 操作票列表信息集合
     */
    private List<ArchivedTicketListBean> ticketList = new ArrayList<ArchivedTicketListBean>();
    private StringBuilder sb;
    private String mUserName;
    private Integer page = 1;
    private String mObjId;
    private String mMbId;
    private ArchivedAdapter mAdapter;
    private  View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub

            View v = inflater.inflate(R.layout.fragment_archiving, container, false);
            mPf_fragment_archived = (PullToRefreshLayout) v.findViewById(R.id.pf_fragment_archived);
            mLv_fragment_archived = (ListView) v.findViewById(R.id.lv_fragment_archived);
            initData();

        return v;
    }

    /**
     * 处理界面数据
     */
    private void initData() {
        // TODO Auto-generated method stub
        mPf_fragment_archived.setOnStartListener(new BasePullToRefresh.OnStartListener() {
            @Override
            public void onStartRefresh(BasePullToRefresh var1) {
                //下拉刷新
                page = 1;
                getData(page + "");
            }

            @Override
            public void onStartLoadmore(BasePullToRefresh var1) {
                //上拉加载更多
                page++;
                getData(page + "");
            }
        });
        getData(page + "");
        mLv_fragment_archived.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(getActivity(), ArchivedActivity.class);
                String mbid = ticketList.get(position).getMBID();
                intent.putExtra("mbId", mbid);
                intent.putExtra("obj_id", ticketList.get(position).getOBJ_ID());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        if (ExecutionActivity.stepToArc==true){
            getData("1");
            ExecutionActivity.stepToArc=false;
        }
        super.onResume();
    }

    /*
                * 请求归档界面网络数据*/
    private void getData(String page) {

        mUserName = ((MainActivity) getActivity()).getUserName();
        if (mUserName == null) {
            Toast.makeText(getActivity(), "MIP登录失败，请重新登陆", Toast.LENGTH_SHORT).show();
        } else {
            sb = new StringBuilder();
            //拼接请求体
            sb.append("<list>").append("\n").append("<params>").append("\n")
                    .append("<LOGIN_NAME>").append(mUserName)
                    .append("</LOGIN_NAME>").append("\n").append("<PAGE_INDEX>")
                    .append(page).append("</PAGE_INDEX>").append("\n")
                    .append("<PZT>").append("51").append("</PZT>")
                    .append("\n").append("<PAGE_SIZE>").append("10").append("</PAGE_SIZE>")
                    .append("\n").append("<CZMD>").append("DCL")
                    .append("</CZMD>").append("\n")
                    .append("<GZDD>").append("10")
                    .append("</GZDD>").append("\n").append("</params>")
                    .append("\n").append("</list>");
            String serviceName = "sxlp1"; //   服务名
            String interfaceName = "getCZPListInfo";//   接口名
            Object[] params = new Object[]{sb};
            String data = DataReadUtil.getDataFromDb(serviceName, interfaceName, params);
            if (this.page == 1) { //下拉刷新
                ticketList.clear();//   清空数组
                parseTicketInfo(data);
                mPf_fragment_archived.refreshSuccess();

            } else {
                parseTicketInfo(data);
                mPf_fragment_archived.loadmoreSuccess();

            }


            if (ticketList.size() < 10) {
                mPf_fragment_archived.setLoadmoreable(false);
            } else {
                mPf_fragment_archived.setLoadmoreable(true);
            }

            freshData();
        }


    }

    private void freshData() {

        if (mAdapter == null) {
            mAdapter = new ArchivedAdapter(getActivity(), ticketList);
            mLv_fragment_archived.setAdapter(mAdapter);

        } else {
           mAdapter.notifyDataSetChanged();
            //mLv_fragment_archived.setAdapter(mAdapter);
        }
    }

    /*TODO 假数据，改完样式删除*/
    private void parseTicketInfo(String t){

        for ( int i = 1;i<9;i++ ) {
            ArchivedTicketListBean ticket = new ArchivedTicketListBean();
            ticket.setOBJ_ID(""+i);
            ticket.setPH("江发路变电站"+i+i+i);
            ticket.setCZRW("220kv普鸠2D53、普鸠2D54、普鸠2D59线路及普庆变220kVIA 母线启动送电");
            ticket.setZPBMMC("张耀文");
            ticket.setZPSJ("2016-11-0"+i+"  19:4"+i);
            ticketList.add(ticket);
        }
    }
    /**
     * 解析操作票列表数据 TODO 改完样式放开
     *
     * @author TQM
     */
/*    private void parseTicketInfo(String t) {
        if (t == null && "".equals(t)) {
            Toast.makeText(getActivity(), "数据为空", Toast.LENGTH_SHORT).show();
        } else {
            JSONObject data;
            try {
                data = new JSONObject(t);
                JSONArray records = data.getJSONArray("records");
                if (records.length() == 0) {
                    Toast.makeText(getActivity(), "数据为空", Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = 0; i < records.length(); i++) {
                        JSONObject record = records.getJSONObject(i);
                        //工作票主键
                        mObjId = record.getString("OBJ_ID");
                        if ("null".equals(mObjId)) {
                            mObjId = "";
                        }
                        //票模板id
                        mMbId = record.getString("MBID");
                        if ("null".equals(mObjId)) {
                            mMbId = "";
                        }
                        String workPlace = record.getString("GZDDMC");    //工作地点
                        if ("null".equals(workPlace)) {
                            workPlace = "";
                        }

                        String club = record.getString("ZPBMMC");    //制票部门
                        if ("null".equals(club)) {
                            club = "";
                        }
                        String ticketNum = record.getString("PH");    //票号
                        if ("null".equals(ticketNum)) {
                            ticketNum = "";
                        }
                        String ticketContent = record.getString("CZRW");    //票内容
                        if ("null".equals(ticketContent)) {
                            ticketContent = "";
                        }
                        String receiveDate = record.getString("ZPSJ");    //制票时间
                        if ("null".equals(receiveDate)) {
                            receiveDate = "";
                        }
                        String pzt = record.getString("PZT");//票状态
                        if ("null".equals(pzt)) {
                            pzt = "";
                        }
                        String flrmc = record.getString("FLRMC");//   发令人名称
                        if ("null".equals(flrmc)) {
                            flrmc = "";
                        }

                        String slrmc = record.getString("SLRMC");//   受令人名称
                        if ("null".equals(slrmc)) {
                            slrmc = "";
                        }
                        ArchivedTicketListBean ticketListBean = new ArchivedTicketListBean();
                        ticketListBean.setOBJ_ID(mObjId);
                        ticketListBean.setCZRW(ticketContent);
                        ticketListBean.setPH(ticketNum);
                        ticketListBean.setGZDDMC(workPlace);
                        ticketListBean.setZPBMMC(club);
                        ticketListBean.setZPSJ(receiveDate);
                        ticketListBean.setPZT(pzt);
                        ticketListBean.setFLRMC(flrmc);
                        ticketListBean.setSLRMC(slrmc);
                        ticketListBean.setMBID(mMbId);
                        ticketList.add(ticketListBean);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();

            }
        }


    }*/

}
