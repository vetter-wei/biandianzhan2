package nari.app.BianDianYingYong.bean;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;

import java.util.List;

/**
 * Created by lx on 2017/11/14.
 */
@Table(name = "exeActCache5")
public class ExecutionTicketActivityBean {
    @Id
    private String OBJ_ID;
    public String DYCS;
    public String DYSJ;
    public String DYRYID;
    public String DYRYMC;
    public String ZPBMMC;
    public String ZPBMID;
    public String GZDDMC;
    public String GZDDMS;
    public String PH;
    public String FLSJ;
    public String SLSJ;
    public String DLBH;
    public String YJWB;
    public String FLRMC;
    public String FLRID;
    public String SLRMC;
    public String SLRID;
    public String CZKGSJ;
    public String CZJSSJ;
    public String CZRW;
    public String BZ;
    public String CZRID;
    public String CZRMC;
    public String JHRID;
    public String JHRMC;
    public String ZBFZRID;
    public String ZBFZRMC;
    public String SSDSMC;
    public String SSDSID;
    public String CZBZ;
    private String JHXCZ;
    private String DRCZ;
    private String JXRYCZ;
    private String HWB;
    private String MBID;

    public String getMBID() {
        return MBID;
    }

    public void setMBID(String MBID) {
        this.MBID = MBID;
    }

    public String getOBJ_ID() {
        return OBJ_ID;
    }

    public void setOBJ_ID(String OBJ_ID) {
        this.OBJ_ID = OBJ_ID;
    }

    public String getHWB() {
        return HWB;
    }

    public void setHWB(String HWB) {
        this.HWB = HWB;
    }

    public String getJHXCZ() {
        return JHXCZ;
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

    public String getDYCS() {
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
