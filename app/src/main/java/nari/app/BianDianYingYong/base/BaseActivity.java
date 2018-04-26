package nari.app.BianDianYingYong.base;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import nari.app.BianDianYingYong.customview.BaseProgressDialog;

/**
 * Created by TQM on 2017/10/17.
 * activity基类，用于处理子activity通用功能
 */
public class BaseActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        //设置浸透式状态栏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }


    }

    /**
     * 展示进度条和提示框
     */


    public void showProgressDialog(String text, boolean cancelable) {
        if (this.progressDialog == null) {
            this.progressDialog = new BaseProgressDialog(this);
        }

        this.progressDialog.setText(text);
        this.progressDialog.setCancelable(cancelable);
        this.progressDialog.show();
    }

    public void cancelProgressDialog() {
        if (this.progressDialog != null) {
            this.progressDialog.setCancelable(false);
            this.progressDialog.cancelImmediately();
        }
    }

    private BaseProgressDialog progressDialog;

    protected void onDestroy() {
        cancelProgressDialog();

        super.onDestroy();
    }

}
