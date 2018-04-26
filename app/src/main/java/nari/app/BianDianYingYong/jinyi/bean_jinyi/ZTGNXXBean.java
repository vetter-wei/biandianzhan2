package nari.app.BianDianYingYong.jinyi.bean_jinyi;

/**
 * Created by wubch on 2018-01-22.
 * 粘贴功能信息
 */

public class ZTGNXXBean {
    private String PJSB_ID = "";//评价设备ID
    private String PJBG_ID = "";//评价报告ID
    private String SBMC = "";//设备名称
    private String SBLX = "";//设备类型
    private String ZTZT = "";//粘贴状态
    private String DYDJ = "";//电压等级
    private String RESULT = "";//
    private String DESCRIPTION = "";//
    private boolean isChecked;

    public String getPJBG_ID() {
        return PJBG_ID;
    }

    public void setPJBG_ID(String PJBG_ID) {
        this.PJBG_ID = PJBG_ID;
    }

    public String getPJSB_ID() {
        return PJSB_ID;
    }

    public void setPJSB_ID(String PJSB_ID) {
        this.PJSB_ID = PJSB_ID;
    }

    public String getSBMC() {
        return SBMC;
    }

    public void setSBMC(String SBMC) {
        this.SBMC = SBMC;
    }

    public String getSBLX() {
        return SBLX;
    }

    public void setSBLX(String SBLX) {
        this.SBLX = SBLX;
    }

    public String getZTZT() {
        return ZTZT;
    }

    public void setZTZT(String ZTZT) {
        this.ZTZT = ZTZT;
    }

    public String getDYDJ() {
        return DYDJ;
    }

    public void setDYDJ(String DYDJ) {
        this.DYDJ = DYDJ;
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

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
