package nari.app.BianDianYingYong.bean;

import java.io.Serializable;

/**
 * Created by wubch on 2018-01-15.
 * 表头展示信息Bean
 */

public class BTZHXXBean {

    private String SSZF;//所属站房
    private String ZFMC;//站房名称
    private String SBXH;//设备型号
    private String YXBH;//运行编号
    private String SBMC;//设备名称
    private String SCRQ;//生产日期
    private String SCCJ;//生产厂家
    private String TYRQ;//投运日期
    private String RESULT;//操作是否成功1：成功，0：失败

    private String RYID;//



    public String getRYID() {
        return RYID;
    }

    public void setRYID(String RYID) {
        this.RYID = RYID;
    }


    public String getSSZF() {
        return SSZF;
    }

    public void setSSZF(String SSZF) {
        this.SSZF = SSZF;
    }

    public String getZFMC() {
        return ZFMC;
    }

    public void setZFMC(String ZFMC) {
        this.ZFMC = ZFMC;
    }

    public String getSBXH() {
        return SBXH;
    }

    public void setSBXH(String SBXH) {
        this.SBXH = SBXH;
    }

    public String getYXBH() {
        return YXBH;
    }

    public void setYXBH(String YXBH) {
        this.YXBH = YXBH;
    }

    public String getSBMC() {
        return SBMC;
    }

    public void setSBMC(String SBMC) {
        this.SBMC = SBMC;
    }

    public String getSCRQ() {
        return SCRQ;
    }

    public void setSCRQ(String SCRQ) {
        this.SCRQ = SCRQ;
    }

    public String getSCCJ() {
        return SCCJ;
    }

    public void setSCCJ(String SCCJ) {
        this.SCCJ = SCCJ;
    }

    public String getTYRQ() {
        return TYRQ;
    }

    public void setTYRQ(String TYRQ) {
        this.TYRQ = TYRQ;
    }

    public String getRESULT() {
        return RESULT;
    }

    public void setRESULT(String RESULT) {
        this.RESULT = RESULT;
    }
}
