package nari.app.BianDianYingYong.customview;


import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import nari.app.BianDianYingYong.R;


public class BaseButtonDialog {
    private Dialog mDialog;
    private ViewGroup mContent;
    private TextView mTextView;
    private Button leftButton;
    private Button rightButton;
    private OnButtonListener buttonListener;

    public BaseButtonDialog(Context context) {
        mDialog = new Dialog(context, R.style.dialogSignaStyle);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_with_botton, null);
        mContent = (ViewGroup) view.findViewById(R.id.content);
        mTextView = (TextView) view.findViewById(R.id.textview);
        leftButton = (Button) view.findViewById(R.id.left);
        leftButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (buttonListener != null)
                    buttonListener.onLeftButtonClick(BaseButtonDialog.this);
            }
        });
        rightButton = (Button) view.findViewById(R.id.right);
        rightButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (buttonListener != null)
                    buttonListener.onRightButtonClick(BaseButtonDialog.this);
            }
        });
        mDialog.setCancelable(false);
        mDialog.setContentView(view);
        mDialog.show();
    }

    /**
     * 给弹框添加自定义View
     *
     * @param v 自定义View
     */
    public void setView(View v) {
        mContent.removeAllViews();
        mContent.addView(v);
    }

    public void setText(String text) {
        mTextView.setText(text);
    }

    public void setText(int textID) {
        mTextView.setText(textID);
    }

    public void setLeftButtonText(String text) {
        leftButton.setText(text);
    }

    public void setLeftButtonText(int textID) {
        leftButton.setText(textID);
    }

    public void setRightButtonText(String text) {
        rightButton.setText(text);
    }

    public void setRightButtonText(int textID) {
        rightButton.setText(textID);
    }

    public void setRightButtonTextColor(int color) {
        rightButton.setTextColor(color);
    }

    public void show() {
        mDialog.show();
    }

    public void cancel() {
        mDialog.cancel();
    }

    public void dismiss() {
        if (mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    public OnButtonListener getButtonListener() {
        return buttonListener;
    }

    public void setButtonListener(OnButtonListener buttonListener) {
        this.buttonListener = buttonListener;
    }

    public interface OnButtonListener {
        public void onLeftButtonClick(BaseButtonDialog dialog);

        public void onRightButtonClick(BaseButtonDialog dialog);
    }

}
