package nari.app.BianDianYingYong.jinyi.fragment_jinyi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import nari.app.BianDianYingYong.R;
import nari.app.BianDianYingYong.activity.ArchivedActivity;
import nari.app.BianDianYingYong.activity.MainActivity;
import nari.app.BianDianYingYong.base.BasePullToRefresh;
import nari.app.BianDianYingYong.bean.ResultBean;
import nari.app.BianDianYingYong.customview.PullToRefreshLayout;
import nari.app.BianDianYingYong.jinyi.activity_jinyi.MainActivity_jinyi;
import nari.app.BianDianYingYong.jinyi.activity_jinyi.PJRenWuActivity;
import nari.app.BianDianYingYong.jinyi.adapter_jinyi.BaoGaoAdapter;
import nari.app.BianDianYingYong.jinyi.adapter_jinyi.RenWuAdapter;
import nari.app.BianDianYingYong.jinyi.bean_jinyi.PJBGDetailsBean;
import nari.app.BianDianYingYong.jinyi.bean_jinyi.PingJiaRenWuBean;
import nari.app.BianDianYingYong.jinyi.bean_jinyi.PingjiabaogaoBean;
import nari.app.BianDianYingYong.utils.DataReadUtil;
import nari.app.BianDianYingYong.utils.ProgressDialog;

import static android.media.CamcorderProfile.get;
import static nari.app.BianDianYingYong.jinyi.adapter_jinyi.BaoGaoAdapter.progressDialog;


/**
 * Created by Administrator on 2018/1/11 0011.
 */
public class RenwuFragment extends android.support.v4.app.Fragment {
    private PullToRefreshLayout pf_fragment_renwu;
    private ListView lv_fragment_renwu;
    /**
     * 操作票列表信息集合
     */
    private StringBuilder sb;
    private String mUserName;
    private Integer page = 1;
    private String mObjId;
    private String mMbId;
    private List<PingJiaRenWuBean> renWuList = new ArrayList<>();
    private RenWuAdapter adapter;
    private String KSSJ = "";
    private String JSSJ = "";
    public static ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_renwu, null);
        pf_fragment_renwu = v.findViewById(R.id.pf_fragment_renwu);
        lv_fragment_renwu = v.findViewById(R.id.lv_fragment_renwu);
        initData();

        return v;
    }


    /**
     * 处理界面数据
     */
    private void initData() {
        getData(page + "");
        pf_fragment_renwu.setOnStartListener(new BasePullToRefresh.OnStartListener() {
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

        lv_fragment_renwu.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                progressDialog = new ProgressDialog();
                progressDialog.showProgressDialog(getActivity(), "加载中..", false);
                Intent intent = new Intent(getActivity(), PJRenWuActivity.class);
                intent.putExtra("obj_id", renWuList.get(position).getOBJ_ID());
                intent.putExtra("userName", mUserName);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        if (PJRenWuActivity.stepToPJRW == true) {
            getData("1");
            PJRenWuActivity.stepToPJRW = false;
        }
        super.onResume();
    }

    /*
                    * 请求评价任务界面网络数据*/
    private void getData(String page) {
        if (this.page == 1) {
            renWuList.clear();
        } else {

        }
        if (getActivity() instanceof MainActivity)
            mUserName = ((MainActivity) getActivity()).getUserName();
        if (getActivity() instanceof MainActivity_jinyi)
            mUserName = ((MainActivity_jinyi) getActivity()).getUserName();
        if (mUserName == null) {
            Toast.makeText(getActivity(), "MIP登录失败，请重新登陆", Toast.LENGTH_SHORT).show();
        } else {
            sb = new StringBuilder();
            //拼接请求体
            sb.append("<list>").append("\n")
                    .append("<params>").append("\n")
                    .append("<USERMC>").append(mUserName).append("</USERMC>")
                    .append("<KSSJ>").append(KSSJ).append("</KSSJ>")
                    .append("<JSSJ>").append(JSSJ).append("</JSSJ>")
                    .append("<PAGE_INDEX>").append(page).append("</PAGE_INDEX>")
                    .append("<PAGE_SIZE>").append("8").append("</PAGE_SIZE>")
                    .append("</params>").append("\n")
                    .append("</list>");
            String serviceName = "bdjyh"; //   服务名
            String interfaceName = "GetJYHPJZJRWLB";//   接口名
            Object[] params = new Object[]{sb};
            String data = DataReadUtil.getDataFromDb(serviceName, interfaceName, params);
            Log.e("lala", "RenwuFragment   data======" + data);

            if (this.page == 1) { //下拉刷新
                renWuList.clear();//   清空数组
                parseTicketInfo(data);
                pf_fragment_renwu.refreshSuccess();

            } else {
                parseTicketInfo(data);
                pf_fragment_renwu.loadmoreSuccess();

            }
            if (renWuList.size() < 8) {
                pf_fragment_renwu.setLoadmoreable(false);
            } else {
                pf_fragment_renwu.setLoadmoreable(true);
            }

            freshData();
        }


    }

    public void fillterData(String startTime, String endTime) {
        KSSJ = startTime;
        JSSJ = endTime;
        getData("1");
    }


    private void freshData() {

        if (adapter == null) {
            adapter = new RenWuAdapter(getActivity(), renWuList);
            lv_fragment_renwu.setAdapter(adapter);

        } else {
            adapter.notifyDataSetChanged();
            //mLv_fragment_archived.setAdapter(mAdapter);
        }
    }

    /**
     * 解析评价任务列表数据
     *
     * @author TQM
     */
    private void parseTicketInfo(String t) {
        if (t == null || "".equals(t)) {
            Toast.makeText(getActivity(), "数据为空", Toast.LENGTH_SHORT).show();
        } else {
            ResultBean<PingJiaRenWuBean> result = ResultBean.fromJson(t, PingJiaRenWuBean.class);
            //baoGaoList=result.getRecords();
            renWuList.addAll(result.getRecords());
        }
    }
}
