package nari.app.BianDianYingYong.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    final static String PLEASE_SELECT = "请选择...";

    public static boolean empty(Object o) {
        return o == null || "".equals(o.toString().trim()) || "null".equalsIgnoreCase(o.toString().trim()) || "undefined".equalsIgnoreCase(o.toString().trim())
                || PLEASE_SELECT.equals(o.toString().trim());
    }

    /**
     * 给JID返回用户名
     *
     * @param Jid
     * @return
     */
    public static String getUserNameByJid(String Jid) {
        if (empty(Jid)) {
            return null;
        }
        if (!Jid.contains("@")) {
            return Jid;
        }
        return Jid.split("@")[0];
    }

    /**
     * 给JID返回不带设备号的用户名
     *
     * @param Jid
     * @return
     */
    public static String getUserJidByJID(String Jid) {
        if (empty(Jid)) {
            return null;
        }
        if (!Jid.contains("/")) {
            return Jid;
        }
        return Jid.split("/")[0];
    }

    /**
     * 验证手机号码格式
     */
    public static boolean isMobileNO(String mobiles) {
        /*
         * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
		 * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		 */
        String telRegex = "[1][3578]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles))
            return false;
        else
            return mobiles.matches(telRegex);
    }

    /**
     * 验证电话号码
     *
     * @param telNum
     * @return
     */
    public static boolean isTelNum(String telNum) {
        String str = "^[0-9]{2,8}[-| ]{0,1}[0-9]{0,8}[-| ]{0,1}[0-9]{0,8}$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(telNum);
        boolean b = m.matches();
        return b;
    }


    public static boolean isNull(String str) {
        return str == null || "".equals(str.trim())||"null".equals(str.trim());
    }
    /**
     * 切割字符串
     * @param rule :切割规则
     * @param s ：要切割的字符串
     * @return
     */
    public static String[] splitString(String rule,String s) {
        String[] array = s.split(rule);
        return array;
    }
    /**
     * 将null转换为"" 防止空指针异常
     *
     * @param str
     * @return
     */
    public static String nullToStr(String str) {
        if (str == null || "".equals(str)||"null".equals(str)) {
            return "";
        }

        return str;
    }
    /**
     * 将阿拉伯数字转换为"中文防止空指针异常

     * @return
     */
    public static String toChinese(String string) {
        String[] s1 = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
        String[] s2 = { "十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千" };

        String result = "";

        int n = string.length();
        for (int i = 0; i < n; i++) {

            int num = string.charAt(i) - '0';

            if (i != n - 1 && num != 0) {
                result += s1[num] + s2[n - 2 - i];
            } else {
                result += s1[num];
            }
            System.out.println("  "+result);
        }

        System.out.println("----------------");
        System.out.println(result);
        return result;

    }

}
