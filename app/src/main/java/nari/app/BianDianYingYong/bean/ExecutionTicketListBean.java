package nari.app.BianDianYingYong.bean;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;

/**
 * Created by lx on 2017/11/8.
 */
@Table(name="exeCache2")
public class ExecutionTicketListBean {
    @Id
    private String OBJ_ID;
    private String MC;   //   票名称：
    private String PLX;   //   票类型：
    private String PZT;   //   票状态：
    private String GZDDMC;   //   工作地点：
    private String GZDDID;   //   工作ID：
    private String CZRW;   //   操作内容任务：
    private String ZPSJ;   //   制票时间：
    private String ZPBMMC;   //   制票部门名称：
    private String PZL;   //   票种类：
    private String MBID;   //   票模板ID：
    private String PH;   //   票名称：
    private String FLRMC;   //   发令人名称：
    private String SLRMC;   //   受令人名称：
    private String KPRID;
    private String DLBH;   //   调度编号：
    public String getDLBH() {
        return DLBH;
    }

    public void setDLBH(String DLBH) {
        this.DLBH = DLBH;
    }
    public String getOBJ_ID() {
        return OBJ_ID;
    }

    public void setOBJ_ID(String OBJ_ID) {
        this.OBJ_ID = OBJ_ID;
    }

    public String getMC() {
        return MC;
    }

    public void setMC(String MC) {
        this.MC = MC;
    }

    public String getPLX() {
        return PLX;
    }

    public void setPLX(String PLX) {
        this.PLX = PLX;
    }

    public String getPZT() {
        return PZT;
    }

    public void setPZT(String PZT) {
        this.PZT = PZT;
    }

    public String getGZDDMC() {
        return GZDDMC;
    }

    public void setGZDDMC(String GZDDMC) {
        this.GZDDMC = GZDDMC;
    }

    public String getGZDDID() {
        return GZDDID;
    }

    public void setGZDDID(String GZDDID) {
        this.GZDDID = GZDDID;
    }

    public String getCZRW() {
        return CZRW;
    }

    public void setCZRW(String CZRW) {
        this.CZRW = CZRW;
    }

    public String getZPSJ() {
        return ZPSJ;
    }

    public void setZPSJ(String ZPSJ) {
        this.ZPSJ = ZPSJ;
    }

    public String getZPBMMC() {
        return ZPBMMC;
    }

    public void setZPBMMC(String ZPBMMC) {
        this.ZPBMMC = ZPBMMC;
    }

    public String getPZL() {
        return PZL;
    }

    public void setPZL(String PZL) {
        this.PZL = PZL;
    }

    public String getMBID() {
        return MBID;
    }

    public void setMBID(String MBID) {
        this.MBID = MBID;
    }

    public String getPH() {
        return PH;
    }

    public void setPH(String PH) {
        this.PH = PH;
    }

    public String getFLRMC() {
        return FLRMC;
    }

    public void setFLRMC(String FLRMC) {
        this.FLRMC = FLRMC;
    }

    public String getSLRMC() {
        return SLRMC;
    }

    public void setSLRMC(String SLRMC) {
        this.SLRMC = SLRMC;
    }

    public String getKPRID() {
        return KPRID;
    }

    public void setKPRID(String KPRID) {
        this.KPRID = KPRID;
    }
  }
