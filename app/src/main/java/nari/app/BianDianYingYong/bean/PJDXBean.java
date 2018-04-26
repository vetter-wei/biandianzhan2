package nari.app.BianDianYingYong.bean;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wubch on 2018-01-16.
 * 评价大项
 */
@Table(name="PJDX")
public class PJDXBean  {
    @Id
    private String OBJ_ID = "";//主键
    private String RESULT = "";//操作是否成功 1：成功，0：失败
    private String DESCRIPTION = "";//异常信息
    private String PJXZ_ID = "";//评价细则_主键
    private String PJDXBM ="";//评价大项编码
    private String PJDXMC = "";//评价大项名称
    private String FZ = "";//分值
    private boolean isChecked = false;//是否选中
    List<PJXMBean> pjxmBeanList;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public List<PJXMBean> getPjxmBeanList() {
        return pjxmBeanList;
    }

    public void setPjxmBeanList(List<PJXMBean> pjxmBeanList) {
        this.pjxmBeanList = pjxmBeanList;
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

    public String getPJXZ_ID() {
        return PJXZ_ID;
    }

    public void setPJXZ_ID(String PJXZ_ID) {
        this.PJXZ_ID = PJXZ_ID;
    }

    public String getPJDXBM() {
        return PJDXBM;
    }

    public void setPJDXBM(String PJDXBM) {
        this.PJDXBM = PJDXBM;
    }

    public String getPJDXMC() {
        return PJDXMC;
    }

    public void setPJDXMC(String PJDXMC) {
        this.PJDXMC = PJDXMC;
    }

    public String getFZ() {
        return FZ;
    }

    public void setFZ(String FZ) {
        this.FZ = FZ;
    }
    public void reverseCheck(){
        isChecked = !isChecked;
        /*if(pjxmBeanList == null || pjxmBeanList.size() == 0)return;
        for (PJXMBean bean:
                pjxmBeanList) {
            bean.reverseCheck();
        }*/
    }
}
