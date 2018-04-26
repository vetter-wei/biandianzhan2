package nari.app.BianDianYingYong.customview;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import nari.app.BianDianYingYong.R;
import nari.app.BianDianYingYong.bean.JCXBean;
import nari.app.BianDianYingYong.bean.PJXXBean;
import nari.app.BianDianYingYong.utils.StringUtil;

/**
 * Created by wubch on 2018-01-18.
 */

public class KFYZDialog extends Dialog

    {
        public KFYZDialog(Context context) {
        super(context);
    }


        /**
         * 回调函数，用于在Dialog的监听事件触发后刷新Activity的UI显示
         */

        public interface InfoGetListener {


            public void Choose(String  position);

        }

        public KFYZDialog(Context context, int theme) {
        super(context, theme);
    }
        /**
         * builder 模式
         */
        public static class Builder {

            private Context context;


            /**
             * 关闭按钮
             */
            private DialogInterface.OnClickListener positiveButtonClickListener = new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            };

            private KFYZDialog.InfoGetListener infoGetListener;
            /**
             * 设置回调借口
             */
            public KFYZDialog.Builder setInfoGetListener(KFYZDialog.InfoGetListener infoGetListener){
                this.infoGetListener = infoGetListener;
                return  this;
            }
            /**
             * 设置回调借口
             */
            public KFYZDialog.Builder setPJXXBean(PJXXBean bean){
                this.bean = bean;
                return  this;
            }

            public Builder(Context context) {
                this.context = context;
            }

            private PJXXBean bean;
            /**
             * 创建对话框
             */
            public KFYZDialog create() {

                final KFYZDialog dialog = new KFYZDialog(context, R.style.dialog);
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                final View layout = inflater.inflate(R.layout.dialog_kfyz, null);
                //
                ((layout.findViewById(R.id.btn_close))).setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                positiveButtonClickListener.onClick(dialog,
                                        DialogInterface.BUTTON_POSITIVE);

                            }
                        }
                );
                ((TextView)layout.findViewById(R.id.tv_kfyz)).setText(bean.getKFYZ());
                dialog.setContentView(layout);
                return dialog;
            }
        }

    }


