package nari.app.BianDianYingYong.bean;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;

import java.io.Serializable;

/**
 * Created by wubch on 2018-01-16.
 * 评价模板Bean
 */
@Table(name="PJMB1")
public class PJMBBean  {
    @Id
    private String OBJ_ID;//主键
    private String PJXZ_ID;//评价细则_主键
    private String BTXX_ID;//表头信息_主键
    private String MBMC;//模板名称
    private String GLCS;//过滤参数
    private String GLMS;//过滤 描述
    private String SFQY;//是否启用 包括:01是 02否
    private String BBH;//版本号
    private String RESULT;//操作是否成功 1：成功，0：失败
    private String DESCRIPTION;//异常信息

    public String getOBJ_ID() {
        return OBJ_ID;
    }

    public void setOBJ_ID(String OBJ_ID) {
        this.OBJ_ID = OBJ_ID;
    }

    public String getPJXZ_ID() {
        return PJXZ_ID;
    }

    public void setPJXZ_ID(String PJXZ_ID) {
        this.PJXZ_ID = PJXZ_ID;
    }

    public String getBTXX_ID() {
        return BTXX_ID;
    }

    public void setBTXX_ID(String BTXX_ID) {
        this.BTXX_ID = BTXX_ID;
    }

    public String getMBMC() {
        return MBMC;
    }

    public void setMBMC(String MBMC) {
        this.MBMC = MBMC;
    }

    public String getGLCS() {
        return GLCS;
    }

    public void setGLCS(String GLCS) {
        this.GLCS = GLCS;
    }

    public String getGLMS() {
        return GLMS;
    }

    public void setGLMS(String GLMS) {
        this.GLMS = GLMS;
    }

    public String getSFQY() {
        return SFQY;
    }

    public void setSFQY(String SFQY) {
        this.SFQY = SFQY;
    }

    public String getBBH() {
        return BBH;
    }

    public void setBBH(String BBH) {
        this.BBH = BBH;
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
}
