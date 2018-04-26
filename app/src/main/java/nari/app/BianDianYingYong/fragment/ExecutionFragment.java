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

import com.google.gson.Gson;

import net.tsz.afinal.FinalDb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import nari.app.BianDianYingYong.R;
import nari.app.BianDianYingYong.activity.ExecutionActivity;
import nari.app.BianDianYingYong.activity.MainActivity;
import nari.app.BianDianYingYong.activity.ProcessedActivity;
import nari.app.BianDianYingYong.adapter.ExecutionAdapter;
import nari.app.BianDianYingYong.base.BaseApplication;
import nari.app.BianDianYingYong.base.BasePullToRefresh;
import nari.app.BianDianYingYong.bean.CheckTicketActivityBean;
import nari.app.BianDianYingYong.bean.ExecutionTicketActivityBean;
import nari.app.BianDianYingYong.bean.ExecutionTicketListBean;
import nari.app.BianDianYingYong.bean.ResultBean;
import nari.app.BianDianYingYong.bean.TicketListBean;
import nari.app.BianDianYingYong.customview.PullToRefreshLayout;
import nari.app.BianDianYingYong.utils.DataReadUtil;
import nari.app.BianDianYingYong.utils.SharedPreferencesHelper;

/**
 * Created by TQM on 2017/10/17.
 * 执行中界面
 */
public class ExecutionFragment extends android.support.v4.app.Fragment {
    /**
     * 上拉下拉刷新控件
     */
    private PullToRefreshLayout pf_fragment_execution;
    private ListView lv_fragment_execution;
    /**
     * 操作票列表信息集合
     */
    private List<ExecutionTicketListBean> ticketList = new ArrayList<ExecutionTicketListBean>();
    private StringBuilder sb;
    private String mUserName;//   mip用户名
    private Integer page = 1; //   页数
    private String mObjId;//   obj_Id
    private String mMbId;  //   票模板ID
    private ExecutionAdapter mAdapter;
    private String status;
    FinalDb db;
    private ResultBean<ExecutionTicketListBean> result;
    private List<ExecutionTicketListBean> list_cache;
    private List<ExecutionTicketActivityBean> exe_list = new ArrayList<>();
//    private Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case 0:
//                    getData("1");
//                    break;
//            }
//        }
//    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View v = inflater.inflate(R.layout.fragment_execution, null);

        pf_fragment_execution = (PullToRefreshLayout) v.findViewById(R.id.pf_fragment_execution);
        lv_fragment_execution = (ListView) v.findViewById(R.id.lv_fragment_execution);
        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(getActivity(), "PersonalInformation");
        status = sharedPreferencesHelper.getStringValue("status");
        db = FinalDb.create(BaseApplication.mApplicationInstance);
        lv_fragment_execution.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {  //   跳转到详情页
                Intent intent = new Intent(getActivity(), ExecutionActivity.class);
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
        // TODO Auto-generated method stub
        pf_fragment_execution.setOnStartListener(new BasePullToRefresh.OnStartListener() {
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
        freshData();
        getData(page + "");
    }

    @Override
    public void onResume() {
        super.onResume();
        if (ExecutionActivity.stepToExe == true) {
            getData("1");
            ExecutionActivity.stepToExe = false;
        } else if (ProcessedActivity.proToExe == true) {
            getData("1");
            ProcessedActivity.proToExe = false;
        }
        if (status.equals("2")) {
            freshData();
        }
    }

    /*
        * 请求执行中界面网络数据*/
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
                    .append("<PZT>").append("41").append("</PZT>")  //   票状态
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
                pf_fragment_execution.refreshSuccess();

            } else {
                parseTicketInfo(data);
                pf_fragment_execution.loadmoreSuccess();
            }

            if (ticketList.size() < 10) {
                pf_fragment_execution.setLoadmoreable(false);
            } else {
                pf_fragment_execution.setLoadmoreable(true);
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
                mAdapter = new ExecutionAdapter(getActivity(), ticketList);
                lv_fragment_execution.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            } else {
                list_cache = db.findAll(ExecutionTicketListBean.class);
                if (list_cache == null || list_cache.size() == 0) {

                } else {
                    mAdapter = new ExecutionAdapter(getActivity(), list_cache);
                    lv_fragment_execution.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
            }
        } else {
            list_cache = db.findAll(ExecutionTicketListBean.class);
            if (list_cache == null || list_cache.size() == 0) {

            } else {
                mAdapter = new ExecutionAdapter(getActivity(), list_cache);
                lv_fragment_execution.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    public void cacheExeData() {
        FinalDb db = FinalDb.create(getActivity());
        for (int i = 0; i < ticketList.size(); i++) {
            String objid = ticketList.get(i).getOBJ_ID();
            String mbid = ticketList.get(i).getMBID();
            StringBuilder sb = new StringBuilder();
            //拼接请求体
            sb.append("<list>").append("\n")
                    .append("<params>").append("\n")
                    .append("<PID>")
                    .append(objid)
                    .append("</PID>").append("\n")
                    .append("<MBID>")
                    .append(mbid)
                    .append("</MBID>").append("\n")
                    .append("</params>").append("\n")
                    .append("</list>");
            String serviceName = "sxlp1"; //   服务名
            String interfaceName = "getCZPInfo";//   接口名
            Object[] params = new Object[]{sb};
            String data = DataReadUtil.getDataFromDb(serviceName, interfaceName, params);
            ResultBean<ExecutionTicketActivityBean> resultBean = ResultBean.fromJson(data, ExecutionTicketActivityBean.class);
            ExecutionTicketActivityBean executionTicketActivityBean = resultBean.getRecords().get(0);
            executionTicketActivityBean.setOBJ_ID(objid);
            executionTicketActivityBean.setMBID(mbid);
            exe_list.add(executionTicketActivityBean);
        }
        List<ExecutionTicketListBean> exeCacheList = db.findAll(ExecutionTicketListBean.class);
        if (exeCacheList == null || exeCacheList.size() == 0) {
            for (ExecutionTicketListBean bean : result.getRecords()) {
                db.save(bean);
            }
        }
        List<ExecutionTicketActivityBean> exeActList = db.findAll(ExecutionTicketActivityBean.class);
        if (exeActList == null || exeActList.size() == 0) {
            for (ExecutionTicketActivityBean bean : exe_list) {
                db.save(bean);
            }
        }
    }

    /**
     * 解析操作票列表数据
     *
     * @author TQM
     */
    private void parseTicketInfo(String t) {
        if (t == null || "".equals(t)) {
            Toast.makeText(getActivity(), "数据为空", Toast.LENGTH_SHORT).show();
        } else {
            result = ResultBean.fromJson(t, ExecutionTicketListBean.class);
            //baoGaoList=result.getRecords();
            ticketList.addAll(result.getRecords());
        }
    }
}
