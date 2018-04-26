package nari.app.BianDianYingYong.jinyi.fragment_jinyi;


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
import nari.app.BianDianYingYong.activity.MainActivity;
import nari.app.BianDianYingYong.base.BasePullToRefresh;
import nari.app.BianDianYingYong.bean.ResultBean;
import nari.app.BianDianYingYong.customview.PullToRefreshLayout;
import nari.app.BianDianYingYong.jinyi.activity_jinyi.MainActivity_jinyi;
import nari.app.BianDianYingYong.jinyi.adapter_jinyi.BaoGaoAdapter;
import nari.app.BianDianYingYong.jinyi.bean_jinyi.PingjiabaogaoBean;
import nari.app.BianDianYingYong.utils.DataReadUtil;

import static android.R.attr.data;


/**
 * Created by TQM on 2017/10/17.
 * 已归档界面
 */
public class BaogaoFragment extends android.support.v4.app.Fragment {

    private PullToRefreshLayout pf_fragment_baogao;
    private ListView lv_fragment_baogao;
    /**
     */
    private StringBuilder sb;
    private String mUserName;
    private Integer page = 1;
    private String mObjId;
    private String mMbId;
    private List<PingjiabaogaoBean> baoGaoList = new ArrayList<>();
    private BaoGaoAdapter adapter;
    private String SBLX = "";
    private String DYDJ = "";
    private String DZMC = "";
    private String SBMC = "";
    private String YXBH = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_baogao, null);
        pf_fragment_baogao = v.findViewById(R.id.pf_fragment_baogao);
        lv_fragment_baogao = v.findViewById(R.id.lv_fragment_baogao);
        initData();

        return v;
    }

    /**
     * 处理界面数据
     */
    private void initData() {
        getData(page + "");
        pf_fragment_baogao.setOnStartListener(new BasePullToRefresh.OnStartListener() {
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

        lv_fragment_baogao.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
//                Intent intent = new Intent(getActivity(), ArchivedActivity.class);
//                startActivity(intent);
            }
        });
    }


    /*
                * 请求评价报告界面网络数据*/
    private void getData(String page) {
        if (this.page == 1) {
            baoGaoList.clear();
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
                    .append("<SBLX>").append(SBLX).append("</SBLX>")
                    .append("<YXBH>").append(YXBH).append("</YXBH>")
                    .append("<DYDJ>").append(DYDJ).append("</DYDJ>")
                    .append("<SBMC>").append(SBMC).append("</SBMC>")
                    .append("<DZMC>").append(DZMC).append("</DZMC>")
                    .append("<PJSB_ID>").append("").append("</PJSB_ID>")
                    .append("<ZJRW_ID>").append("").append("</ZJRW_ID>")
                    .append("<BGZT>").append("01").append("</BGZT>")
                    .append("<PAGE_INDEX>").append(page).append("</PAGE_INDEX>")
                    .append("<PAGE_SIZE>").append("5").append("</PAGE_SIZE>")
                    .append("</params>").append("\n")
                    .append("</list>");
            String serviceName = "bdjyh"; //   服务名
            String interfaceName = "GetJYHPJLB";//   接口名
            Object[] params = new Object[]{sb};
            String data = DataReadUtil.getDataFromDb(serviceName, interfaceName, params);
            Log.e("lala", "BaogaoFragment   data======" + data);
            if (this.page == 1) { //下拉刷新
                baoGaoList.clear();//   清空数组
                parseTicketInfo(data);
                pf_fragment_baogao.refreshSuccess();

            } else {
                parseTicketInfo(data);
                pf_fragment_baogao.loadmoreSuccess();

            }
            if (baoGaoList.size() < 5) {
                pf_fragment_baogao.setLoadmoreable(false);
            } else {
                pf_fragment_baogao.setLoadmoreable(true);
            }

            freshData();
        }
    }

    public void fillterData(String sblx, String dydj, String dz, String sbmc, String yxbh) {
        SBLX = sblx;
        DYDJ = dydj;
        DZMC = dz;
        SBMC = sbmc;
        YXBH = yxbh;
        getData("1");
    }

    private void freshData() {

        if (adapter == null) {
            adapter = new BaoGaoAdapter(getActivity(), baoGaoList);
            lv_fragment_baogao.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
            //mLv_fragment_archived.setAdapter(mAdapter);
        }
    }

    /**
     * 解析评价报告列表数据
     *
     * @author TQM
     */
    private void parseTicketInfo(String t) {
        if (t == null || "".equals(t)) {
            Toast.makeText(getActivity(), "数据为空", Toast.LENGTH_SHORT).show();
            // TODO: 2018-04-26
            PingjiabaogaoBean bean = new PingjiabaogaoBean();
            PingjiabaogaoBean bean1 = new PingjiabaogaoBean();
            PingjiabaogaoBean bean2 = new PingjiabaogaoBean();
            baoGaoList.add(bean);
            bean1.setPJZ("");
            baoGaoList.add(bean1);
            baoGaoList.add(bean2);
            // TODO: 2018-04-26
        } else {
            ResultBean<PingjiabaogaoBean> result = ResultBean.fromJson(t, PingjiabaogaoBean.class);
            //baoGaoList=result.getRecords();
            baoGaoList.addAll(result.getRecords());
        }
    }
}

