package nari.app.BianDianYingYong.bean;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;

import java.util.List;

/**
 * Created by wubch on 2018-01-17.
 * 报告粘贴Bean
 */
@Table(name="COPY1")
public class COPYBean {
    @Id
    private String id;//专家任务ID+评价模板ID+评价设备ID
    String  resultBeanStr;//保存的评价大项bean；
    String score = "0";//目前得分
    String pjxzBeanStr;//评价细则beanStr

    public String getPjxzBeanStr() {
        return pjxzBeanStr;
    }

    public void setPjxzBeanStr(String pjxzBeanStr) {
        this.pjxzBeanStr = pjxzBeanStr;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResultBeanStr() {
        return resultBeanStr;
    }

    public void setResultBeanStr(String resultBeanStr) {
        this.resultBeanStr = resultBeanStr;
    }
}
