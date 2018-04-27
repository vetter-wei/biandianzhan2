package nari.app.BianDianYingYong.bean;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;


/**
 * Created by lx on 2017/11/14.
 */
@Table(name = "proActCache2")
public class CheckTicketActivityBean {
    //// TODO: 2018-04-27
    @Id
    private String OBJ_ID = "OBJ_ID";
    private String DYCS = "DYCS";
    private String DYSJ = "DYSJ";
    private String DYRYID = "DYRYID";
    private String DYRYMC ="DYRYMC";
    private String ZPBMMC = "ZPBMMC";
    private String ZPBMID = "ZPBMID";
    private String GZDDMC = "GZDDMC";
    private String GZDDMS = "GZDDMS";
    private String PH = "PH";
    private String FLSJ = "FLSJ";
    private String SLSJ = "SLSJ";
    private String DLBH = "DLBH";
    private String YJWB = "YJWB";
    private String FLRMC = "FLRMC";
    private String FLRID = "FLRID";
    private String SLRMC = "SLRMC";
    private String SLRID = "SLRID";
    private String CZKGSJ = "CZKGSJ";
    private String CZJSSJ ="CZJSSJ";
    private String CZRW = "CZRW";
    private String BZ = "BZ";
    private String CZRID = "CZRID";
    private String CZRMC = "CZRMC";
    private String JHRID = "JHRID";
    private String JHRMC = "JHRMC";
    private String ZBFZRID = "ZBFZRID";
    private String ZBFZRMC = "ZBFZRMC";
    private String SSDSMC ="SSDSMC";
    private String SSDSID = "SSDSID";
    private String CZBZ = "CZBZ";
    private String JHXCZ = "JHXCZ";
    private String DRCZ = "DRCZ";
    private String JXRYCZ = "JXRYCZ";
    private String HWB = "HWB";
    /*@Id
    private String OBJ_ID;
    private String DYCS;
    private String DYSJ;
    private String DYRYID;
    private String DYRYMC;
    private String ZPBMMC;
    private String ZPBMID;
    private String GZDDMC;
    private String GZDDMS;
    private String PH;
    private String FLSJ;
    private String SLSJ;
    private String DLBH;
    private String YJWB;
    private String FLRMC;
    private String FLRID;
    private String SLRMC;
    private String SLRID;
    private String CZKGSJ;
    private String CZJSSJ;
    private String CZRW;
    private String BZ;
    private String CZRID;
    private String CZRMC;
    private String JHRID;
    private String JHRMC;
    private String ZBFZRID;
    private String ZBFZRMC;
    private String SSDSMC;
    private String SSDSID;
    private String CZBZ;
    private String JHXCZ;
    private String DRCZ;
    private String JXRYCZ;
    private String HWB;*/

    public String getOBJ_ID() {
        return OBJ_ID;
    }

    public void setOBJ_ID(String OBJ_ID) {
        this.OBJ_ID = OBJ_ID;
    }

    public String getJHXCZ() {
        return JHXCZ;
    }

    public String getHWB() {
        return HWB;
    }

    public void setHWB(String HWB) {
        this.HWB = HWB;
    }

    public void setJHXCZ(String JHXCZ) {
        this.JHXCZ = JHXCZ;
    }

    public String getDRCZ() {
        return DRCZ;
    }

    public void setDRCZ(String DRCZ) {
        this.DRCZ = DRCZ;
    }

    public String getJXRYCZ() {
        return JXRYCZ;
    }

    public void setJXRYCZ(String JXRYCZ) {
        this.JXRYCZ = JXRYCZ;
    }

    private String getDYCS() {
        return DYCS;
    }

    public void setDYCS(String DYCS) {
        this.DYCS = DYCS;
    }

    public String getDYSJ() {
        return DYSJ;
    }

    public void setDYSJ(String DYSJ) {
        this.DYSJ = DYSJ;
    }

    public String getDYRYID() {
        return DYRYID;
    }

    public void setDYRYID(String DYRYID) {
        this.DYRYID = DYRYID;
    }

    public String getDYRYMC() {
        return DYRYMC;
    }

    public void setDYRYMC(String DYRYMC) {
        this.DYRYMC = DYRYMC;
    }

    public String getZPBMMC() {
        return ZPBMMC;
    }

    public void setZPBMMC(String ZPBMMC) {
        this.ZPBMMC = ZPBMMC;
    }

    public String getZPBMID() {
        return ZPBMID;
    }

    public void setZPBMID(String ZPBMID) {
        this.ZPBMID = ZPBMID;
    }

    public String getGZDDMC() {
        return GZDDMC;
    }

    public void setGZDDMC(String GZDDMC) {
        this.GZDDMC = GZDDMC;
    }

    public String getGZDDMS() {
        return GZDDMS;
    }

    public void setGZDDMS(String GZDDMS) {
        this.GZDDMS = GZDDMS;
    }

    public String getPH() {
        return PH;
    }

    public void setPH(String PH) {
        this.PH = PH;
    }

    public String getFLSJ() {
        return FLSJ;
    }

    public void setFLSJ(String FLSJ) {
        this.FLSJ = FLSJ;
    }

    public String getSLSJ() {
        return SLSJ;
    }

    public void setSLSJ(String SLSJ) {
        this.SLSJ = SLSJ;
    }

    public String getDLBH() {
        return DLBH;
    }

    public void setDLBH(String DLBH) {
        this.DLBH = DLBH;
    }

    public String getYJWB() {
        return YJWB;
    }

    public void setYJWB(String YJWB) {
        this.YJWB = YJWB;
    }

    public String getFLRMC() {
        return FLRMC;
    }

    public void setFLRMC(String FLRMC) {
        this.FLRMC = FLRMC;
    }

    public String getFLRID() {
        return FLRID;
    }

    public void setFLRID(String FLRID) {
        this.FLRID = FLRID;
    }

    public String getSLRMC() {
        return SLRMC;
    }

    public void setSLRMC(String SLRMC) {
        this.SLRMC = SLRMC;
    }

    public String getSLRID() {
        return SLRID;
    }

    public void setSLRID(String SLRID) {
        this.SLRID = SLRID;
    }

    public String getCZKGSJ() {
        return CZKGSJ;
    }

    public void setCZKGSJ(String CZKGSJ) {
        this.CZKGSJ = CZKGSJ;
    }

    public String getCZJSSJ() {
        return CZJSSJ;
    }

    public void setCZJSSJ(String CZJSSJ) {
        this.CZJSSJ = CZJSSJ;
    }

    public String getCZRW() {
        return CZRW;
    }

    public void setCZRW(String CZRW) {
        this.CZRW = CZRW;
    }

    public String getBZ() {
        return BZ;
    }

    public void setBZ(String BZ) {
        this.BZ = BZ;
    }

    public String getCZRID() {
        return CZRID;
    }

    public void setCZRID(String CZRID) {
        this.CZRID = CZRID;
    }

    public String getCZRMC() {
        return CZRMC;
    }

    public void setCZRMC(String CZRMC) {
        this.CZRMC = CZRMC;
    }

    public String getJHRID() {
        return JHRID;
    }

    public void setJHRID(String JHRID) {
        this.JHRID = JHRID;
    }

    public String getJHRMC() {
        return JHRMC;
    }

    public void setJHRMC(String JHRMC) {
        this.JHRMC = JHRMC;
    }

    public String getZBFZRID() {
        return ZBFZRID;
    }

    public void setZBFZRID(String ZBFZRID) {
        this.ZBFZRID = ZBFZRID;
    }

    public String getZBFZRMC() {
        return ZBFZRMC;
    }

    public void setZBFZRMC(String ZBFZRMC) {
        this.ZBFZRMC = ZBFZRMC;
    }

    public String getSSDSMC() {
        return SSDSMC;
    }

    public void setSSDSMC(String SSDSMC) {
        this.SSDSMC = SSDSMC;
    }

    public String getSSDSID() {
        return SSDSID;
    }

    public void setSSDSID(String SSDSID) {
        this.SSDSID = SSDSID;
    }

    public String getCZBZ() {
        return CZBZ;
    }

    public void setCZBZ(String CZBZ) {
        this.CZBZ = CZBZ;
    }


}
