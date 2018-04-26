package nari.app.BianDianYingYong.jinyi.customview_jinyi;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import nari.app.BianDianYingYong.R;
import nari.app.BianDianYingYong.bean.JCXBean;
import nari.app.BianDianYingYong.customview.MSJYEditDialog;
import nari.app.BianDianYingYong.utils.StringUtil;

/**
 * Created by wubch on 2018-01-22.
 */

public class MSJYShowDialog extends Dialog {
    public MSJYShowDialog(Context context) {
        super(context);
    }


    /**
     * 回调函数，用于在Dialog的监听事件触发后刷新Activity的UI显示
     */

    public interface InfoGetListener {


        public void Choose(String  position);

    }

    public MSJYShowDialog(Context context, int theme) {
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

        private MSJYShowDialog.InfoGetListener infoGetListener;
        /**
         * 设置回调借口
         */
        public MSJYShowDialog.Builder setInfoGetListener(MSJYShowDialog.InfoGetListener infoGetListener){
            this.infoGetListener = infoGetListener;
            return  this;
        }
        /**
         * 设置回调借口
         */
        public MSJYShowDialog.Builder setJCXBean(JCXBean jcxBean){
            this.jcxBean = jcxBean;
            return  this;
        }

        public Builder(Context context) {
            this.context = context;
        }

        private JCXBean jcxBean;
        /**
         * 创建对话框
         */
        public MSJYShowDialog create() {

            final MSJYShowDialog dialog = new MSJYShowDialog(context, R.style.dialog);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            final View layout = inflater.inflate(R.layout.dialog_msjy_show, null);
            //确定按钮
            ((layout.findViewById(R.id.btn_close))).setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            positiveButtonClickListener.onClick(dialog,
                                    DialogInterface.BUTTON_POSITIVE);

                        }
                    }
            );
            final TextView et_wtms = (TextView) layout.findViewById(R.id.tv_wtms);
            et_wtms.setText(jcxBean.getWTMS());
            final TextView et_cljy = (TextView) layout.findViewById(R.id.tv_cljy);
            et_cljy.setText(jcxBean.getCLJY());
            ((layout.findViewById(R.id.button_reset))).setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            jcxBean.setWTMS("");
                            jcxBean.setCLJY("");
                            et_wtms.setText("");
                            et_cljy.setText("");
                        }
                    }
            );
            ((layout.findViewById(R.id.button_confirm))).setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            positiveButtonClickListener.onClick(dialog,
                                    DialogInterface.BUTTON_POSITIVE);
                            jcxBean.setWTMS(StringUtil.nullToStr(et_wtms.getText().toString()));
                            jcxBean.setCLJY(StringUtil.nullToStr(et_cljy.getText().toString()));

                        }
                    }
            );
            dialog.setContentView(layout);
            return dialog;
        }
    }

}

