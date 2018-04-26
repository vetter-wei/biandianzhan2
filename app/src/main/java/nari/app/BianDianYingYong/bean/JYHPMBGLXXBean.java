package nari.app.BianDianYingYong.bean;

import java.io.Serializable;

/**
 * Created by wubch on 2018-01-16.
 * 模板关联信息
 */

public class JYHPMBGLXXBean {
    private String OBJ_ID;//模板关联表ID
    private String PJDX_ID;//评价大项ID
    private String RESULT;//操作是否成功 1：成功，0：失败
    private String DESCRIPTION;//异常信息

    public String getOBJ_ID() {
        return OBJ_ID;
    }

    public void setOBJ_ID(String OBJ_ID) {
        this.OBJ_ID = OBJ_ID;
    }

    public String getPJDX_ID() {
        return PJDX_ID;
    }

    public void setPJDX_ID(String PJDX_ID) {
        this.PJDX_ID = PJDX_ID;
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
