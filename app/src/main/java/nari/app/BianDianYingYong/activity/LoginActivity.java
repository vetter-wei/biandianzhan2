package nari.app.BianDianYingYong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;

import nari.app.BianDianYingYong.R;
import nari.app.BianDianYingYong.utils.SharedPreferencesHelper;
import nari.mip.core.AppEntryActivity;
import nari.mip.core.Platform;
//todo fangkai
//public class LoginActivity extends AppEntryActivity {  //   继承jar包类，实现内网连接
public class LoginActivity extends AppEntryActivity {  //   继承jar包类，实现内网连接

    @Override
    protected void onReady(Bundle arg0) {
        startActivity(new Intent(this, EnterActivity.class));
        finish();
        Log.e("lala","走了preOnReady() =======");
    }

    @Override
    protected void preOnReady() {
        //   设置mip平台上下文
        setContentView(R.layout.activity_app_start);
        Platform.getCurrent().setPlatformContext(getApplicationContext());
    }

}
