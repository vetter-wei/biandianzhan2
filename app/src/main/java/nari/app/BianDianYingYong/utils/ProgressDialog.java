package nari.app.BianDianYingYong.utils;

import android.content.Context;
import nari.app.BianDianYingYong.customview.BaseProgressDialog;

/**
 * Created by ShawDLee on 2018/3/29.
 */

public class ProgressDialog {
    /**
     * 展示进度条和提示框
     */
    public void showProgressDialog(Context context,String text, boolean cancelable) {
        if (this.progressDialog == null) {
            this.progressDialog = new BaseProgressDialog(context);
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
}
