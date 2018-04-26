package nari.app.BianDianYingYong.utils;

import android.util.Log;
import android.widget.Toast;

import nari.app.BianDianYingYong.base.BaseApplication;
import nari.mip.util.rpc.MobileService;

public class DataReadUtil {
    /**
     * 从接口读取数据
     *
     * @param serviceName   服务名
     * @param interfaceName 接口名
     * @param params        参数
     * @return 从数据库中读取的数据，为String
     */
    public static String getDataFromDb(String serviceName, String interfaceName, Object[] params) {
        String data = "";
        try {
            //data = MobileService.invokeWebService(serviceName, interfaceName, params);
            data = MobileService.invokeWebService(serviceName, interfaceName, 60000, true, true, -1L, params);
        } catch (Exception e) {
            Log.e("lala", "异常===========" + e.toString());
//            Toast.makeText(BaseApplication.mApplicationInstance, e.toString(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        return data;
    }
}
