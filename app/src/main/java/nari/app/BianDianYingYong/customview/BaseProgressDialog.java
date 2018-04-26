package nari.app.BianDianYingYong.customview;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;

import nari.app.BianDianYingYong.R;


public class BaseProgressDialog {
    private Dialog mDialog;
    private TextView mTextView;

    private Runnable cancelRunnable = new Runnable() {

        @Override
        public void run() {
            if (mDialog.isShowing())
                mDialog.cancel();
        }
    };

    public BaseProgressDialog(Context context) {
        mDialog = new Dialog(context, R.style.dialog);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_progress, null);
        mTextView = (TextView) view.findViewById(R.id.textview);
        setCancelable(false);
        mDialog.setContentView(view);
        mDialog.setOnCancelListener(new OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                mTextView.removeCallbacks(cancelRunnable);
            }
        });
        mDialog.show();
    }

    public void setCancelable(boolean cancelable){
        mDialog.setCancelable(cancelable);
    }

    public void setText(String text) {
        mTextView.setText(text);
    }

    public void setText(int textID) {
        mTextView.setText(textID);
    }

    public void show() {
        mTextView.removeCallbacks(cancelRunnable);
        if (!mDialog.isShowing())
            mDialog.show();
    }

    public void cancelImmediately() {
        mDialog.cancel();
    }

    public void cancel() {
        mTextView.postDelayed(cancelRunnable, 500);
    }
}

