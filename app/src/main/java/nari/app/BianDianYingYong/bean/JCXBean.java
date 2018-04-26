package nari.app.BianDianYingYong.bean;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;

import java.io.Serializable;

/**
 * Created by wubch on 2018-01-16.
 * 检查项
 */
@Table(name="JCX1")
public class JCXBean  {
    @Id
    private String OBJ_ID="";//主键
    private String RESULT = "";//操作是否成功 1：成功，0：失败
    private String DESCRIPTION = "";//异常信息
    private String PJXX_ID = "";//评价小项_主键
    private String JCXBM = "";//检查项编码
    private String JCXZLX = "";//检查项值类型
    private String JCXMS = "";//检查项描述 发现次数
    private String JCYQXXLJF= "";//检查要求下限逻辑符
    private String JCYQXX = "";//检查要求下限
    private String JCYQSXLJF = "";//检查要求上限逻辑符
    private String JCYQSX = "";//检查要求上限
    private String KFZ = "";//扣分值
    private String SFXS = "";//是否显示：1是、0否
    private String SFQY = "";//是否启用：1是、0否
    private String SFSZ = "";//是否数值：1是、0否,当选择是的时候,表示检查项为描述的值为扣分倍数
    private String JCXJG = "T";//检查项结果,T为未扣分F为已扣分
    private String PJXXJG_ID = "";//评价小项结果ID
    private double KDFZ = 0;//扣得分值
    private String WTMS = "";//问题描述
    private String CLJY = "";//处理建议
    private String PJSJ = "";//评价时间
    private String ZTZT = "02";//粘贴状态

    public String getJCXJG() {
        return JCXJG;
    }

    public void setJCXJG(String JCXJG) {
        this.JCXJG = JCXJG;
    }

    public double getKDFZ() {
        return KDFZ;
    }

    public void setKDFZ(double KDFZ) {
        this.KDFZ = KDFZ;
    }

    public String getWTMS() {
        return WTMS;
    }

    public void setWTMS(String WTMS) {
        this.WTMS = WTMS;
    }

    public String getCLJY() {
        return CLJY;
    }

    public void setCLJY(String CLJY) {
        this.CLJY = CLJY;
    }

    public String getPJSJ() {
        return PJSJ;
    }

    public void setPJSJ(String PJSJ) {
        this.PJSJ = PJSJ;
    }

    public String getZTZT() {
        return ZTZT;
    }

    public void setZTZT(String ZTZT) {
        this.ZTZT = ZTZT;
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

    public String getPJXX_ID() {
        return PJXX_ID;
    }

    public void setPJXX_ID(String PJXX_ID) {
        this.PJXX_ID = PJXX_ID;
    }

    public String getJCXBM() {
        return JCXBM;
    }

    public void setJCXBM(String JCXBM) {
        this.JCXBM = JCXBM;
    }

    public String getJCXZLX() {
        return JCXZLX;
    }

    public void setJCXZLX(String JCXZLX) {
        this.JCXZLX = JCXZLX;
    }

    public String getJCXMS() {
        return JCXMS;
    }

    public void setJCXMS(String JCXMS) {
        this.JCXMS = JCXMS;
    }

    public String getJCYQXXLJF() {
        return JCYQXXLJF;
    }

    public void setJCYQXXLJF(String JCYQXXLJF) {
        this.JCYQXXLJF = JCYQXXLJF;
    }

    public String getJCYQXX() {
        return JCYQXX;
    }

    public void setJCYQXX(String JCYQXX) {
        this.JCYQXX = JCYQXX;
    }

    public String getJCYQSXLJF() {
        return JCYQSXLJF;
    }

    public void setJCYQSXLJF(String JCYQSXLJF) {
        this.JCYQSXLJF = JCYQSXLJF;
    }

    public String getJCYQSX() {
        return JCYQSX;
    }

    public void setJCYQSX(String JCYQSX) {
        this.JCYQSX = JCYQSX;
    }

    public String getKFZ() {
        return KFZ;
    }

    public void setKFZ(String KFZ) {
        this.KFZ = KFZ;
    }

    public String getSFXS() {
        return SFXS;
    }

    public void setSFXS(String SFXS) {
        this.SFXS = SFXS;
    }

    public String getSFQY() {
        return SFQY;
    }

    public void setSFQY(String SFQY) {
        this.SFQY = SFQY;
    }

    public String getSFSZ() {
        return SFSZ;
    }

    public void setSFSZ(String SFSZ) {
        this.SFSZ = SFSZ;
    }
}
