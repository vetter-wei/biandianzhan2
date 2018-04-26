package nari.app.BianDianYingYong.bean;

import com.google.gson.Gson;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by wubch on 2018-01-16.
 * 返回结果处理Bean
 */

public class ResultBean <T> implements Serializable {

    /**
     * 数据内容
     */
    private List<T> records;



    public static ResultBean fromJson(String json, Class clazz) {
        Gson gson = new Gson();
        Type objectType = type(ResultBean.class, clazz);
        return gson.fromJson(json, objectType);
    }

    static ParameterizedType type(final Class raw, final Type... args) {
        return new ParameterizedType() {
            public Type getRawType() {
                return raw;
            }

            public Type[] getActualTypeArguments() {
                return args;
            }

            public Type getOwnerType() {
                return null;
            }
        };
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }
}
