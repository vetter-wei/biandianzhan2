package nari.app.BianDianYingYong.bean;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wubch on 2018-01-16.
 * 评价项目
 */
@Table(name="PJXM1")
public class PJXMBean {
    @Id
    private String OBJ_ID = "";//主键
    private String RESULT = "";//操作是否成功 1：成功，0：失败
    private String DESCRIPTION = "";//异常信息
    private String PJDX_ID  = "";//评价大项_主键
    private String PJXMBM ="";//评价项目编码
    private String PJXMMC = "";//评价项目名称
    private boolean isChecked = false;//是否选中
    private List<PJXXBean> pjxxBeanList;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public List<PJXXBean> getPjxxBeanList() {
        return pjxxBeanList;
    }

    public void setPjxxBeanList(List<PJXXBean> pjxxBeanList) {
        this.pjxxBeanList = pjxxBeanList;
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

    public String getPJDX_ID() {
        return PJDX_ID;
    }

    public void setPJDX_ID(String PJDX_ID) {
        this.PJDX_ID = PJDX_ID;
    }

    public String getPJXMBM() {
        return PJXMBM;
    }

    public void setPJXMBM(String PJXMBM) {
        this.PJXMBM = PJXMBM;
    }

    public String getPJXMMC() {
        return PJXMMC;
    }

    public void setPJXMMC(String PJXMMC) {
        this.PJXMMC = PJXMMC;
    }
    public void reverseCheck(){
        isChecked = !isChecked;
        /*if(pjxxBeanList == null || pjxxBeanList.size() == 0)return;
        for (PJXXBean bean:
                pjxxBeanList) {
            bean.setChecked(!bean.isChecked());
        }*/
    }
}
