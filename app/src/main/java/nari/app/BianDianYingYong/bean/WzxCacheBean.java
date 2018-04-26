package nari.app.BianDianYingYong.bean;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;

/**
 * Created by ShawDLee on 2018/4/2.
 */
@Table(name = "wzxCache")
public class WzxCacheBean {
    @Id
    private String ObjId;
    private String pState;
    private String wzxReson;

    public String getObjId() {
        return ObjId;
    }

    public void setObjId(String objId) {
        ObjId = objId;
    }

    public String getpState() {
        return pState;
    }

    public void setpState(String pState) {
        this.pState = pState;
    }

    public String getWzxReson() {
        return wzxReson;
    }

    public void setWzxReson(String wzxReson) {
        this.wzxReson = wzxReson;
    }
}
