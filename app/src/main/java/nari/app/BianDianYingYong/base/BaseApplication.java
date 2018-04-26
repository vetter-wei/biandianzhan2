package nari.app.BianDianYingYong.base;

import android.app.Application;


/**
 * 
 */

public class BaseApplication extends Application {
		//全局变量 是否有网络 默认为true
		public static boolean HAS_INTNET = true;
	    public static BaseApplication mApplicationInstance;
	    @Override
	    public void onCreate() {
	        super.onCreate();
	      
	        mApplicationInstance = this;
	       
	    }
}
