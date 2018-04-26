package nari.app.BianDianYingYong.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * SharedPreferences存数据到本地沙盒
 *
 * @author TQM
 * @version 创建时间：2013-3-9 上午10:13:25
 */
public class SharedPreferencesHelper {
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    Context context;

    public SharedPreferencesHelper(Context c, String name) {
        context = c;
        if (name != null && !"".equals(name)) {
            sp = c.getSharedPreferences(name, 0);
        } else {
            sp = PreferenceManager.getDefaultSharedPreferences(c);
        }
        editor = sp.edit();
    }

    /**
     * 往沙盒中写字符串数据
     *
     * @param key
     * @param value
     * @Title: putStringValue
     */
    public void putStringValue(String key, String value) {
        editor = sp.edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * 通过键值获取沙盒中的数据
     *
     * @param key
     * @return String
     * @Title: getStringValue
     */
    public String getStringValue(String key) {
        return sp.getString(key, null);
    }

    /**
     * 往沙盒中写布尔类型数据
     *
     * @param key
     * @param value
     * @Title: putBooleanValue
     */
    public void putBooleanValue(String key, Boolean value) {
        editor = sp.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * 通过键值获取沙盒中布尔类型数据
     *
     * @param key
     * @return Boolean
     * @Title: getBooleanValue
     */
    public Boolean getBooleanValue(String key) {
        return sp.getBoolean(key, false);
    }
    /**
     * 通过键值获取沙盒中布尔类型数据(可以自己设置默认值)
     *
     * @param key
     * @return Boolean
     * @Title: getBooleanValue
     */
    public Boolean getBooleanValue(String key , boolean defaultValue) {
        return sp.getBoolean(key, defaultValue);
    }

    /**
     * 往沙盒中写浮点类型数据
     *
     * @param key
     * @param value
     * @Title: putFloatValue
     */
    public void putFloatValue(String key, Float value) {
        editor = sp.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    /**
     * 通过键值获取浮点型数据
     *
     * @param key
     * @return Float
     * @Title: getFloatValue
     */
    public Float getFloatValue(String key) {
        return sp.getFloat(key, 0);
    }

    /**
     * 往沙盒中写整形数据
     *
     * @param key
     * @param value
     * @Title: putIntValue
     */
    public void putIntValue(String key, int value) {
        editor = sp.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * 通过键值获取沙盒中整形数据
     *
     * @param key
     * @return int
     * @Title: getIntValue
     * @Description: TODO
     */
    public int getIntValue(String key) {
        return sp.getInt(key, 0);
    }

    /**
     * 通过键值获取沙盒中的整形数据（可以自定义默认的值）
     *
     * @param key
     * @param defValue
     * @return
     */
    public int getIntValue(String key, int defValue) {
        return sp.getInt(key, defValue);
    }

    /**
     * 往沙盒中写长整形数据
     *
     * @param key
     * @param value
     * @Title: putLongValue
     */
    public void putLongValue(String key, Long value) {
        editor = sp.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    /**
     * 通过键值获取沙盒中长整形数据
     *
     * @param key
     * @return Long
     * @Title: getLongValue
     */
    public Long getLongValue(String key) {
        return sp.getLong(key, 0);
    }

    /**
     * 删除
     *
     * @param key
     */
    public void remove(String key) {
        editor = sp.edit();
        editor.remove(key);
        editor.apply();
    }
}
