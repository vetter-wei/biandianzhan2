package nari.app.BianDianYingYong.jinyi.activity_jinyi;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.tsz.afinal.FinalDb;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nari.app.BianDianYingYong.bean.JCXBean;
import nari.app.BianDianYingYong.bean.JYHPMBGLXXBean;
import nari.app.BianDianYingYong.bean.PJDXBean;
import nari.app.BianDianYingYong.bean.PJMBBean;
import nari.app.BianDianYingYong.bean.PJXMBean;
import nari.app.BianDianYingYong.bean.PJXXBean;
import nari.app.BianDianYingYong.bean.PJXZBean;
import nari.app.BianDianYingYong.bean.ResultBean;
import nari.app.BianDianYingYong.jinyi.bean_jinyi.BTSXBean;
import nari.app.BianDianYingYong.jinyi.bean_jinyi.FLAGBean;
import nari.app.BianDianYingYong.jinyi.bean_jinyi.JYHCacheBean;
import nari.app.BianDianYingYong.jinyi.bean_jinyi.PJDFXQBean;
import nari.app.BianDianYingYong.jinyi.bean_jinyi.PingjiabaogaoBean;
import nari.app.BianDianYingYong.utils.DataReadUtil;
import nari.app.BianDianYingYong.utils.ParamBean;
import nari.app.BianDianYingYong.utils.StringUtil;

/**
 * Created by 李元甲 on 2018-04-02.
 */

public class JYHUpdateAsycTask extends AsyncTask<Object,Integer,Boolean> {
    FinalDb db ;
    List<JYHCacheBean> jyhbeans;
    Context context;
    public interface DataChuli {
        void chuli(Boolean data);
    }
    JYHUpdateAsycTask.DataChuli dataChuli;

    public void setContext(Context context) {
        this.context = context;
        db = FinalDb.create(context);
    }

    public void setDataChuli(JYHUpdateAsycTask.DataChuli dataChuli) {
        this.dataChuli = dataChuli;
    }

    @Override
    protected Boolean doInBackground(Object... paramBeen) {
        //db.deleteAll(JYHCacheBean.class);
        Boolean success = true;
        List<JYHCacheBean> list = db.findAll(JYHCacheBean.class);
        if(list == null || list.size() ==0)return success;
        for (JYHCacheBean bean:list
                ) {
            if(false ==initil(bean)){
                success = false;
            }
        }
        return success;
    }

    @Override
    protected void onPostExecute(Boolean s) {
        super.onPostExecute(s);
        if(dataChuli != null){
            dataChuli.chuli(s);
        }
    }
    boolean initil(JYHCacheBean bean){

        List<PJDXBean> pjdxBeanList = gson.fromJson(bean.getPjdxgson(),new TypeToken<List<PJDXBean>>(){}.getType());
        PingjiabaogaoBean pjbg = gson.fromJson(bean.getListgson(),PingjiabaogaoBean.class);
        String PJBG_ID = pjbg.getOBJ_ID();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String PJSJ = sdf.format(new Date());
        StringBuilder sb = new StringBuilder("");
        sb.append("<list>").append("\n")
                .append("<params>").append("\n")
                .append("<PJBG id=\'").append(PJBG_ID).append("\'>").append("\n");
        for (PJDXBean pjdxBen : pjdxBeanList
                ) {
            sb.append("<PJDX id=\'").append(pjdxBen.getOBJ_ID()).append("\'>").append("\n");
            for (PJXMBean pjxmBean : pjdxBen.getPjxmBeanList()
                    ) {
                sb.append("<PJXM id=\'").append(pjxmBean.getOBJ_ID()).append("\'>").append("\n");
                for (PJXXBean pjxxBean : pjxmBean.getPjxxBeanList()
                        ) {
                    JCXBean jcxBean = pjxxBean.getJcxBean();


                    sb.append("<PJXX id=\'").append(pjxxBean.getOBJ_ID()).append("\'>").append("\n");
                    sb.append("<JCX id=\'").append(jcxBean.getOBJ_ID()).append("\'>").append("\n");
                    sb.append("<JCXJG>").append(jcxBean.getJCXJG()).append("</JCXJG>").append("\n")
                            .append("<KFZ>").append(jcxBean.getKDFZ()).append("</KFZ>").append("\n")
                            .append("<WTMS>").append(jcxBean.getWTMS()).append("</WTMS>").append("\n")
                            .append("<CLJY>").append(jcxBean.getCLJY()).append("</CLJY>").append("\n");
                    /*if(StringUtil.isNull(jcxBean.getPJSJ())){
                        sb.append("<PJSJ>").append(jcxBean.getPJSJ()).append("</PJSJ>").append("\n");
                    }else{*/
                    sb.append("<PJSJ>").append(PJSJ).append("</PJSJ>").append("\n");
                    /*}*/
                    sb.append("<ZTZT>").append(jcxBean.getZTZT()).append("</ZTZT>").append("\n");

                    sb.append("</JCX>").append("\n");
                    sb.append("</PJXX>").append("\n");


                }
                sb.append("</PJXM>").append("\n");
            }

            sb.append("</PJDX>").append("\n");
        }

        sb.append("</PJBG>").append("\n")
                .append("</params>").append("\n")
                .append("</list>");
        Object[] params = new Object[]{sb};
        String data = DataReadUtil.getDataFromDb("bdjyh", "PostJYHPJZJRWLB", params);

        ResultBean<FLAGBean> result = ResultBean.fromJson(data, FLAGBean.class);
        if (result == null || result.getRecords() == null || result.getRecords().size() == 0) {
            //Toast.makeText(PJBGEditActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
            //Toast.makeText(PJBGEditActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
            //finish();
            return false;
        } else {
            if ("true".equals(result.getRecords().get(0).getFLAG())) {


                //Toast.makeText(PJBGEditActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                db.delete(bean);
                return true;


            } else {
                //Toast.makeText(PJBGEditActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
                return true;
            }

        }





    }
    Gson gson= new Gson();
}
