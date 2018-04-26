package nari.app.BianDianYingYong.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import net.tsz.afinal.FinalDb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import nari.app.BianDianYingYong.R;
import nari.app.BianDianYingYong.activity.MainActivity;
import nari.app.BianDianYingYong.activity.ProcessedActivity;
import nari.app.BianDianYingYong.adapter.CheckAdapter;
import nari.app.BianDianYingYong.base.BaseApplication;
import nari.app.BianDianYingYong.base.BasePullToRefresh;
import nari.app.BianDianYingYong.bean.CheckTicketActivityBean;
import nari.app.BianDianYingYong.bean.ResultBean;
import nari.app.BianDianYingYong.bean.TicketListBean;
import nari.app.BianDianYingYong.bean.TicketListBean_Cache;
import nari.app.BianDianYingYong.customview.PullToRefreshLayout;
import nari.app.BianDianYingYong.jinyi.bean_jinyi.PingJiaRenWuBean;
import nari.app.BianDianYingYong.utils.DataReadUtil;
import nari.app.BianDianYingYong.utils.SharedPreferencesHelper;

/**
 * Created by TQM on 2017/10/17.
 * 待处理界面
 */
public class ProcessedFragment extends android.support.v4.app.Fragment {
    /**
     * 上拉下拉刷新控件
     */
    private PullToRefreshLayout pf_fragment_check;
    private ListView lv_fragment_check;
    /**
     * 操作票列表信息集合
     */
    private List<TicketListBean> ticketList = new ArrayList<TicketListBean>();
    private StringBuilder sb;
    private String mUserName;   //   mip用户名
    private Integer page = 1;   //   页数
    private String mObjId;   //   obj_Id
    private String mMbId;  //   票模板ID
    private CheckAdapter mAdapter;   //   待处理适配器
    private String status;
    FinalDb db;
    private ResultBean<TicketListBean> result;
    private List<TicketListBean> list_cache;
    private List<CheckTicketActivityBean> pro_list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_check, null);
        pf_fragment_check = (PullToRefreshLayout) v.findViewById(R.id.pf_fragment_check);
        lv_fragment_check = (ListView) v.findViewById(R.id.lv_fragment_check);
        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(getActivity(), "PersonalInformation");
        status = sharedPreferencesHelper.getStringValue("status");
        db = FinalDb.create(BaseApplication.mApplicationInstance);
        lv_fragment_check.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {  //   跳转到详情页
                Intent intent = new Intent(getActivity(), ProcessedActivity.class);
                if (status.equals("1")) {
                    intent.putExtra("mbId", ticketList.get(position).getMBID());
                    intent.putExtra("obj_id", ticketList.get(position).getOBJ_ID());
                } else {
                    intent.putExtra("obj_id", list_cache.get(position).getOBJ_ID());
                }
                startActivity(intent);

            }
        });
        if (status.equals("1")) {
            initData();
        } else {
            freshData();
        }
        return v;
    }

    /**
     * 处理界面数据
     */
    private void initData() {
        getData(page + "");
        pf_fragment_check.setOnStartListener(new BasePullToRefresh.OnStartListener() {
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
    }

    @Override
    public void onResume() {

        super.onResume();
        if (ProcessedActivity.stepToPro == true) {
            getData("1");
            ProcessedActivity.stepToPro = false;
        }
        if (status.equals("2")) {
            freshData();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // initData();
    }

    /*
            * 请求待处理界面网络数据*/
    private void getData(String page) {

        mUserName = ((MainActivity) getActivity()).getUserName();
        if (mUserName == null) {
            Toast.makeText(getActivity(), "MIP登录失败，请重新登陆", Toast.LENGTH_SHORT).show();
        } else {
            sb = new StringBuilder();
            //拼接请求体
            sb.append("<list>").append("\n").append("<params>").append("\n")
                    .append("<LOGIN_NAME>").append(mUserName)
                    .append("</LOGIN_NAME>").append("\n").append("<PAGE_INDEX>")    //   页数
                    .append(page).append("</PAGE_INDEX>").append("\n")
                    .append("<PZT>").append("31").append("</PZT>")  //   票状态
                    .append("\n").append("<PAGE_SIZE>").append("10").append("</PAGE_SIZE>")  //   每页数据
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
                pf_fragment_check.refreshSuccess();

            } else {
                parseTicketInfo(data);
                pf_fragment_check.loadmoreSuccess();

            }


            if (ticketList.size() < 10) {
                pf_fragment_check.setLoadmoreable(false);
            } else {
                pf_fragment_check.setLoadmoreable(true);
            }
            freshData();
        }


    }

    /*
    * 设置及刷新adapter
    * */
    private void freshData() {

        if (mAdapter == null) {
            if (status.equals("1")) {
                mAdapter = new CheckAdapter(getActivity(), ticketList);
                lv_fragment_check.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            } else {
                list_cache = db.findAll(TicketListBean.class);
                if (list_cache == null || list_cache.size() == 0) {

                } else {
                    mAdapter = new CheckAdapter(getActivity(), list_cache);
                    lv_fragment_check.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
            }

        } else {
            list_cache = db.findAll(TicketListBean.class);
            if (list_cache == null || list_cache.size() == 0) {

            } else {
                mAdapter = new CheckAdapter(getActivity(), list_cache);
                lv_fragment_check.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    public void cacheProData() {
        FinalDb db = FinalDb.create(getActivity());
        for (int i = 0; i < ticketList.size(); i++) {
            String objid = ticketList.get(i).getOBJ_ID();
            String mbid = ticketList.get(i).getMBID();
            StringBuilder sb = new StringBuilder();
            //拼接请求体
            sb.append("<list>").append("\n")
                    .append("<params>").append("\n")
                    .append("<PID>")
                    .append(objid)  //   BCFE0584AB2C905DC45FBCD3EF015FBCFE0552004A
                    .append("</PID>").append("\n")
                    .append("<MBID>")
                    .append(mbid)   //   16
                    .append("</MBID>").append("\n")
                    .append("</params>").append("\n")
                    .append("</list>");
            String serviceName = "sxlp1"; //   服务名
            String interfaceName = "getCZPInfo";//   接口名
            Object[] params = new Object[]{sb};
            String data = DataReadUtil.getDataFromDb(serviceName, interfaceName, params);
            String a = jsonString(data);
            ResultBean<CheckTicketActivityBean> resultBean = ResultBean.fromJson(a, CheckTicketActivityBean.class);
            CheckTicketActivityBean checkTicketActivityBean = resultBean.getRecords().get(0);
            checkTicketActivityBean.setOBJ_ID(objid);
            pro_list.add(checkTicketActivityBean);
        }
        List<TicketListBean> proCacheList = db.findAll(TicketListBean.class);
        if (proCacheList == null || proCacheList.size() == 0) {
            for (TicketListBean bean : result.getRecords()) {
                db.save(bean);
            }
        }
        List<CheckTicketActivityBean> checkList = db.findAll(CheckTicketActivityBean.class);
        if (checkList == null || checkList.size() == 0) {
            for (CheckTicketActivityBean bean : pro_list) {
                db.save(bean);
            }
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

    /**
     * 解析操作票列表数据
     *
     * @author TQM
     */
    private void parseTicketInfo(String t) {
        /*if (t == null || "".equals(t)) {
            Toast.makeText(getActivity(), "数据为空", Toast.LENGTH_SHORT).show();
        } else {
            result = ResultBean.fromJson(t, TicketListBean.class);
            //baoGaoList=result.getRecords();
            ticketList.addAll(result.getRecords());
        }*/

        //TODO 获取数据（假数据）改完样式删除
        getFALSEDate();
    }

    /*TODO 假数据，改完样式删除*/
    private void getFALSEDate(){

        for ( int i = 1;i<9;i++ ) {
            TicketListBean ticket = new TicketListBean();
            ticket.setOBJ_ID(""+i);
            ticket.setPH("江发路变电站"+i+i+i);
            ticket.setCZRW("220kv普鸠2D53、普鸠2D54、普鸠2D59线路及普庆变220kVIA 母线启动送电");
            ticket.setZPBMMC("张耀文");
            ticket.setZPSJ("2016-11-0"+i+"  19:4"+i);
            ticketList.add(ticket);
        }
    }
}
