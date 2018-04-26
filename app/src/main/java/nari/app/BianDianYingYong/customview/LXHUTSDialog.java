package nari.app.BianDianYingYong.customview;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import nari.app.BianDianYingYong.R;
import nari.app.BianDianYingYong.bean.JCXBean;
import nari.app.BianDianYingYong.utils.StringUtil;

/**
 * 离线缓存提示Dialog
 * Created by 李元甲 on 2018-04-02.
 */

public class LXHUTSDialog extends Dialog {
    public LXHUTSDialog(Context context) {
        super(context);
    }


    /**
     * 回调函数，用于在Dialog的监听事件触发后刷新Activity的UI显示
     */

    public interface InfoGetListener {


        public void Choose(String  position);

    }

    public LXHUTSDialog(Context context, int theme) {
        super(context, theme);
    }
    /**
     * builder 模式
     */
    public static class Builder {

        private Context context;
        private String msg;

        /**
         * 关闭按钮
         */
        private DialogInterface.OnClickListener positiveButtonClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        };

        private LXHUTSDialog.InfoGetListener infoGetListener;
        /**
         * 设置回调借口
         */
        public LXHUTSDialog.Builder setInfoGetListener(LXHUTSDialog.InfoGetListener infoGetListener){
            this.infoGetListener = infoGetListener;
            return  this;
        }
        public LXHUTSDialog.Builder setMsg(String msg){
            this.msg = msg;
            return  this;
        }


        public Builder(Context context) {
            this.context = context;

        }

        /**
         * 创建对话框
         */
        public LXHUTSDialog create() {
            final LXHUTSDialog dialog = new LXHUTSDialog(context, R.style.dialog);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            final View layout = inflater.inflate(R.layout.dialog_lixianpanduan, null);
            ((layout.findViewById(R.id.image_close))).setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            positiveButtonClickListener.onClick(dialog,
                                    DialogInterface.BUTTON_POSITIVE);
                        }
                    }
            );
            ((TextView)(layout.findViewById(R.id.tv_msg))).setText(msg);
            ((layout.findViewById(R.id.button_cancel))).setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            positiveButtonClickListener.onClick(dialog,
                                    DialogInterface.BUTTON_POSITIVE);

                        }
                    }
            );
            ((layout.findViewById(R.id.button_confirm))).setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            positiveButtonClickListener.onClick(dialog,
                                    DialogInterface.BUTTON_POSITIVE);
                            if(infoGetListener != null){
                                infoGetListener.Choose(msg);
                            }

                        }
                    }
            );
            dialog.setContentView(layout);
            return dialog;
        }
    }


}

