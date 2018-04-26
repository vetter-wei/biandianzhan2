package nari.app.BianDianYingYong.bean;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;

/**
 * Created by wubch on 2018-01-16.
 * 评价细则Bean
 */
@Table(name="PJXZ")
public class PJXZBean {
    @Id
    private String OBJ_ID;//主键
    private String RESULT;//操作是否成功 1：成功，0：失败
    private String DESCRIPTION;//异常信息
    private String XZBM;//细则编码
    private String XZMC;//细则名称
    private String XZFL;//细则分类 包括:01设备管理 02电站管理 来源:变电精益化公共代码表,代码类别为001
    private String DZXZ;//电站性质
    private String DF;//得分
    private String XH;//序号
    private String ZY;//专业
    private String SSBB;//所属版本

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

    public String getXZBM() {
        return XZBM;
    }

    public void setXZBM(String XZBM) {
        this.XZBM = XZBM;
    }

    public String getXZMC() {
        return XZMC;
    }

    public void setXZMC(String XZMC) {
        this.XZMC = XZMC;
    }

    public String getXZFL() {
        return XZFL;
    }

    public void setXZFL(String XZFL) {
        this.XZFL = XZFL;
    }

    public String getDZXZ() {
        return DZXZ;
    }

    public void setDZXZ(String DZXZ) {
        this.DZXZ = DZXZ;
    }

    public String getDF() {
        return DF;
    }

    public void setDF(String DF) {
        this.DF = DF;
    }

    public String getXH() {
        return XH;
    }

    public void setXH(String XH) {
        this.XH = XH;
    }

    public String getZY() {
        return ZY;
    }

    public void setZY(String ZY) {
        this.ZY = ZY;
    }

    public String getSSBB() {
        return SSBB;
    }

    public void setSSBB(String SSBB) {
        this.SSBB = SSBB;
    }
}
