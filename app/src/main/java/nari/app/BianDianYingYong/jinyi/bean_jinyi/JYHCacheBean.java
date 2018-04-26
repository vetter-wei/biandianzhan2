package nari.app.BianDianYingYong.jinyi.bean_jinyi;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;

/**
 * Created by 李元甲 on 2018-04-02.
 * 精益化缓存信息
 */
@Table(name="JYHCache1")
public class JYHCacheBean {
    @Id
    private String PJBGID = "";//主键评价报告ID
    private String listgson ="";//列表展示信息
    private String btgson ="";//表头信息
    private String pjdxgson= "";//评价信息
    private String pjxzgson= "";//评价细则

    public String getPJBGID() {
        return PJBGID;
    }

    public void setPJBGID(String PJBGID) {
        this.PJBGID = PJBGID;
    }

    public String getListgson() {
        return listgson;
    }

    public void setListgson(String listgson) {
        this.listgson = listgson;
    }

    public String getBtgson() {
        return btgson;
    }

    public void setBtgson(String btgson) {
        this.btgson = btgson;
    }

    public String getPjdxgson() {
        return pjdxgson;
    }

    public void setPjdxgson(String pjdxgson) {
        this.pjdxgson = pjdxgson;
    }

    public String getPjxzgson() {
        return pjxzgson;
    }

    public void setPjxzgson(String pjxzgson) {
        this.pjxzgson = pjxzgson;
    }
}
