package nari.app.BianDianYingYong.jinyi.activity_jinyi;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import net.tsz.afinal.FinalDb;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import nari.app.BianDianYingYong.base.BaseApplication;
import nari.app.BianDianYingYong.bean.JCXBean;
import nari.app.BianDianYingYong.bean.JYHPMBGLXXBean;
import nari.app.BianDianYingYong.bean.PJDXBean;
import nari.app.BianDianYingYong.bean.PJMBBean;
import nari.app.BianDianYingYong.bean.PJXMBean;
import nari.app.BianDianYingYong.bean.PJXXBean;
import nari.app.BianDianYingYong.bean.PJXZBean;
import nari.app.BianDianYingYong.bean.ResultBean;
import nari.app.BianDianYingYong.jinyi.bean_jinyi.BTSXBean;
import nari.app.BianDianYingYong.jinyi.bean_jinyi.JYHCacheBean;
import nari.app.BianDianYingYong.jinyi.bean_jinyi.PJDFXQBean;
import nari.app.BianDianYingYong.jinyi.bean_jinyi.PingjiabaogaoBean;
import nari.app.BianDianYingYong.utils.DataReadUtil;
import nari.app.BianDianYingYong.utils.HttpAsycTask;
import nari.app.BianDianYingYong.utils.ParamBean;
import nari.app.BianDianYingYong.utils.StringUtil;

/**
 * Created by 李元甲 on 2018-04-02.
 */

public class JYHCACHEAsycTask extends AsyncTask<ParamBean,Integer,String> {
    FinalDb  db ;
    List<JYHCacheBean> jyhbeans;
    Context context;
    public interface DataChuli {
        void chuli(String data);
    }
    JYHCACHEAsycTask.DataChuli dataChuli;

    public void setContext(Context context) {
        this.context = context;
        db = FinalDb.create(context);
    }

    public void setDataChuli(JYHCACHEAsycTask.DataChuli dataChuli) {
        this.dataChuli = dataChuli;
    }

    @Override
    protected String doInBackground(ParamBean... paramBeen) {
        db.deleteAll(JYHCacheBean.class);
        ParamBean p = paramBeen[0];
        String data = DataReadUtil.getDataFromDb(p.getService(), p.getIntfacre(), new Object[]{p.getParams()} );
        ResultBean<PingjiabaogaoBean> result = ResultBean.fromJson(data, PingjiabaogaoBean.class);
        if (result == null || result.getRecords() == null || result.getRecords().size() == 0){
            return data;
        }
        List<PingjiabaogaoBean>  pjbgbeans= result.getRecords();
        jyhbeans = new ArrayList<>(pjbgbeans.size());
        for (PingjiabaogaoBean pjbg:pjbgbeans
             ) {
            initil(pjbg);
        }
        return data;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(dataChuli != null){
            dataChuli.chuli(s);
        }
    }
    void initil(PingjiabaogaoBean pjbg){
        List<PJDXBean> pjdxBeanList = new ArrayList<>();
        List<BTSXBean> btxxList = new ArrayList<>();
        PJXZBean pjxzBean;
        String PJMB_ID = pjbg.getPJMB_ID();
        String PJBG_ID = pjbg.getOBJ_ID();
        String PJSB_ID = pjbg.getPJSB_ID();
        String ZJRW_ID = pjbg.getZJRW_ID();
        String PJXZ_ID = pjbg.getPJXZ_ID();
        String PJZ = pjbg.getPJZ();




        StringBuilder sb = new StringBuilder();
        //拼接请求体
        sb.append("<list>").append("\n")
                .append("<params>").append("\n")
                .append("<PJXZ_ID>").append(PJXZ_ID).append("</PJXZ_ID>").append("\n")
                .append("<PJSB_ID>").append(PJSB_ID).append("</PJSB_ID>").append("\n")
                .append("</params>").append("\n")
                .append("</list>");
        String serviceName = "bdjyh"; //   服务名
        Object[] params = new Object[]{sb};

        String data = DataReadUtil.getDataFromDb(serviceName, "GetBTZSXX", params);
        Log.e("lala", "GetBTZSXX   data======" + data);
        ResultBean<BTSXBean> result = ResultBean.fromJson(data, BTSXBean.class);
        if (result == null || result.getRecords() == null || result.getRecords().size() == 0)
            return;
        btxxList = result.getRecords();
//        btzhxxBean = result.getRecords().get(0);

        List<PJDFXQBean> pjdfxqBeenList = null;
        if (!StringUtil.isNull(PJZ)) {
            sb = new StringBuilder();
            //拼接请求体
            sb.append("<list>").append("\n")
                    .append("<params>").append("\n")
                    .append("<PJBG_ID>").append(PJBG_ID).append("</PJBG_ID>").append("\n")
                    .append("<PJZ>").append(PJZ).append("</PJZ>").append("\n")
                    .append("</params>").append("\n")
                    .append("</list>");

            params = new Object[]{sb};
            data = DataReadUtil.getDataFromDb(serviceName, "GetPJDFXQ", params);
            ResultBean<PJDFXQBean> result1 = ResultBean.fromJson(data, PJDFXQBean.class);
            if (!(result1 == null || result1.getRecords() == null || result1.getRecords().size() == 0)) {
                pjdfxqBeenList = result1.getRecords();
            }
        }

        sb = new StringBuilder();
        //拼接请求体
        sb.append("<list>").append("\n")
                .append("<params>").append("\n")
                .append("<PJMB_ID>").append(PJMB_ID).append("</PJMB_ID>").append("\n")
                .append("</params>").append("\n")
                .append("</list>");

        params = new Object[]{sb};
        Log.e("-------->", "走了");
        data = DataReadUtil.getDataFromDb(serviceName, "GetJYHPMBGLXX", params);
        ResultBean<JYHPMBGLXXBean> result1 = ResultBean.fromJson(data, JYHPMBGLXXBean.class);
        if (result1 == null || result1.getRecords() == null || result1.getRecords().size() == 0)
            return;
        List<JYHPMBGLXXBean> jyhpmbglxxBeanList = result1.getRecords();
        for (JYHPMBGLXXBean bean :
                jyhpmbglxxBeanList) {
            List<PJDXBean> dxs = db.findAllByWhere(PJDXBean.class, "OBJ_ID = \"" + bean.getPJDX_ID() + "\"");
            if (dxs == null || dxs.size() == 0) return;
            if (dxs != null || dxs.size() != 0) {
                PJDXBean pjdxBean = dxs.get(0);
                pjdxBeanList.add(pjdxBean);
                List<PJXMBean> pjxmBeanList = db.findAllByWhere(PJXMBean.class, "PJDX_ID = \"" + pjdxBean.getOBJ_ID() + "\"");
                if (pjxmBeanList == null || pjxmBeanList.size() == 0) return;
                pjdxBean.setPjxmBeanList(pjxmBeanList);
                for (PJXMBean pjxmBean :
                        pjxmBeanList) {
                    List<PJXXBean> pjxxBeanList = db.findAllByWhere(PJXXBean.class, "PJXM_ID = \"" + pjxmBean.getOBJ_ID() + "\"");
//                    if (pjxxBeanList == null || pjxxBeanList.size() == 0) return;
                    pjxmBean.setPjxxBeanList(pjxxBeanList);
                    for (PJXXBean pjxxBean :
                            pjxxBeanList) {
                        List<JCXBean> jcxBeanList = db.findAllByWhere(JCXBean.class, "PJXX_ID  = \"" + pjxxBean.getOBJ_ID() + "\"");
//                        if (jcxBeanList == null || jcxBeanList.size() == 0) return;
                        pjxxBean.setJcxBean(jcxBeanList.get(0));
                    }

                }

            }

        }
        List<PJMBBean> pjmbBeanList = db.findAllByWhere(PJMBBean.class, "OBJ_ID = \"" + PJMB_ID + "\"");
        if (pjmbBeanList == null || pjmbBeanList.size() == 0) return;
        List<PJXZBean> pjxzBeanList = db.findAllByWhere(PJXZBean.class, "OBJ_ID = \"" + pjmbBeanList.get(0).getPJXZ_ID() + "\"");
        if (pjxzBeanList == null || pjxzBeanList.size() == 0) return;
        pjxzBean = pjxzBeanList.get(0);

        if (pjdfxqBeenList != null) {
            //同步网络端数据

                for (PJDFXQBean pjdfxqBean : pjdfxqBeenList
                        ) {
                    for (PJDXBean pjdxBen : pjdxBeanList
                            ) {
                        if (pjdfxqBean.getPJDX_ID().equals(pjdxBen.getOBJ_ID())) {
                            for (PJXMBean pjxmBean : pjdxBen.getPjxmBeanList()
                                    ) {
                                if (pjdfxqBean.getPJXM_ID().equals(pjxmBean.getOBJ_ID())) {
                                    for (PJXXBean pjxxBean : pjxmBean.getPjxxBeanList()
                                            ) {
                                        if (pjdfxqBean.getPJXX_ID().equals(pjxxBean.getOBJ_ID())) {
                                            JCXBean jcxBean = pjxxBean.getJcxBean();
                                            jcxBean.setWTMS(pjdfxqBean.getWTMS());
                                            jcxBean.setCLJY(pjdfxqBean.getCLJY());
                                            jcxBean.setKDFZ(pjdfxqBean.getPJJCXJG_KFZ());
                                            if (StringUtil.isNull(jcxBean.getKFZ())) jcxBean.setKFZ("0");
                                            if (!("发现次数".equals(jcxBean.getJCXMS()))) {
                                                if (0 != jcxBean.getKDFZ()) {
                                                    jcxBean.setJCXJG("F");
                                                } else {
                                                    jcxBean.setJCXJG("T");
                                                }
                                            } else {

                                                BigDecimal kfz = new BigDecimal(jcxBean.getKFZ());
                                                jcxBean.setJCXJG(new BigDecimal(jcxBean.getKDFZ()).divide(kfz).setScale(0, BigDecimal.ROUND_HALF_UP).intValue() + "");
                                            }
                                            break;
                                        }
                                    }
                                    break;
                                }
                            }
                            break;
                        }

                    }
                }

            //score = Double.parseDouble(PJZ);
        } else {
            //score = getAllScore();
        }

        JYHCacheBean jyh = new JYHCacheBean();
        jyh.setPJBGID(pjbg.getOBJ_ID());
        jyh.setBtgson(gson.toJson(btxxList));
        jyh.setPjdxgson(gson.toJson(pjdxBeanList));
        jyh.setPjxzgson(gson.toJson(pjxzBean));
        jyh.setListgson(gson.toJson(pjbg));
        db.save(jyh);


    }
    Gson gson= new Gson();
}