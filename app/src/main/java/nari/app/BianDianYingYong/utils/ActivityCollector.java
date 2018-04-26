package nari.app.BianDianYingYong.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 *Created by TQM on 2017/10/17.
 *activity集合，处理activity任务栈问题
 */
public class ActivityCollector {
    public static List<Activity> activities=new ArrayList<Activity>();
    public static void addActivity(Activity activity){
        activities.add(activity);
    }
    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }
    public static void finishAll(){
        for (Activity activity:activities){
            if (!activity.isFinishing())
                activity.finish();
        }
    }
    public static void finishAllNotThis(Activity thisActivity){
        for (Activity activity:activities){
            if (!activity.isFinishing()&&!thisActivity.equals(activity))
                activity.finish();
        }
    }
    public static void finishActivity(Activity activity){
        if (!activity.isFinishing())
            activity.finish();
    }

    //获取上一个activity
    public static String getLastActivity(){
         if(activities!=null && activities.size()>1){
            return activities.get(activities.size()-2).getClass().getSimpleName();
         }
        return "";
    }
}
