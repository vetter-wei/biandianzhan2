package nari.app.BianDianYingYong.bean;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wubch on 2018-01-16.
 * 评价小项
 */
@Table(name="PJXX1")
public class PJXXBean {
    //// TODO: 2018-04-26
    @Id
    private String OBJ_ID = "1";//主键
    private String RESULT = "";//操作是否成功 1：成功，0：失败
    private String DESCRIPTION = "";//异常信息
    private String PJXM_ID = "";//评价项目_主键
    private String PJXXBM = "";//评价小项编码
    private String PJXXMS = "评价小项描述";//评价小项描述
    private String JCFS = "检查方式";//检查方式
    private String KFFS ="扣分方式";//扣分方式
    private String KFZH = "扣分组合";//扣分组合
    private String ZHFS = "组合方式";//组合方式
    private String KFYZ = "扣分原则";//扣分原则
    private boolean isChecked = false;//是否选中
    JCXBean jcxBean;

    public PJXXBean() {
        super();
        jcxBean = new JCXBean();
    }
    /*@Id
    private String OBJ_ID = "";//主键
    private String RESULT = "";//操作是否成功 1：成功，0：失败
    private String DESCRIPTION = "";//异常信息
    private String PJXM_ID = "";//评价项目_主键
    private String PJXXBM = "";//评价小项编码
    private String PJXXMS = "";//评价小项描述
    private String JCFS = "";//检查方式
    private String KFFS ="";//扣分方式
    private String KFZH = "";//扣分组合
    private String ZHFS = "";//组合方式
    private String KFYZ = "";//扣分原则
    private boolean isChecked = false;//是否选中
    JCXBean jcxBean;*/

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public JCXBean getJcxBean() {
        return jcxBean;
    }

    public void setJcxBean(JCXBean jcxBean) {
        this.jcxBean = jcxBean;
    }

    public String getOBJ_ID() {
        return OBJ_ID;
    }

    public void setOBJ_ID(String OBJ_ID) {
        this.OBJ_ID = OBJ_ID;
    }

    public String getRESULT() {
        return RESULT;
    }

    public void setRESULT(String RESULT) {
        this.RESULT = RESULT;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getPJXM_ID() {
        return PJXM_ID;
    }

    public void setPJXM_ID(String PJXM_ID) {
        this.PJXM_ID = PJXM_ID;
    }

    public String getPJXXBM() {
        return PJXXBM;
    }

    public void setPJXXBM(String PJXXBM) {
        this.PJXXBM = PJXXBM;
    }

    public String getPJXXMS() {
        return PJXXMS;
    }

    public void setPJXXMS(String PJXXMS) {
        this.PJXXMS = PJXXMS;
    }

    public String getJCFS() {
        return JCFS;
    }

    public void setJCFS(String JCFS) {
        this.JCFS = JCFS;
    }

    public String getKFFS() {
        return KFFS;
    }

    public void setKFFS(String KFFS) {
        this.KFFS = KFFS;
    }

    public String getKFZH() {
        return KFZH;
    }

    public void setKFZH(String KFZH) {
        this.KFZH = KFZH;
    }

    public String getZHFS() {
        return ZHFS;
    }

    public void setZHFS(String ZHFS) {
        this.ZHFS = ZHFS;
    }

    public String getKFYZ() {
        return KFYZ;
    }

    public void setKFYZ(String KFYZ) {
        this.KFYZ = KFYZ;
    }
}
