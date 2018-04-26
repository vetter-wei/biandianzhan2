package nari.app.BianDianYingYong.customview;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import nari.app.BianDianYingYong.R;

/**
 * Created by 李元甲 on 2018-04-02.
 */

public class PhotoNameDialog extends Dialog {
    public PhotoNameDialog(Context context) {
        super(context);
    }


    /**
     * 回调函数，用于在Dialog的监听事件触发后刷新Activity的UI显示
     */

    public interface InfoGetListener {


        public void Choose(String  position);

    }

    public PhotoNameDialog(Context context, int theme) {
        super(context, theme);
    }
    /**
     * builder 模式
     */
    public static class Builder {

        private Context context;
        private String msg = "";

        /**
         * 关闭按钮
         */
        private DialogInterface.OnClickListener positiveButtonClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        };

        private PhotoNameDialog.InfoGetListener infoGetListener;
        /**
         * 设置回调借口
         */
        public PhotoNameDialog.Builder setInfoGetListener(PhotoNameDialog.InfoGetListener infoGetListener){
            this.infoGetListener = infoGetListener;
            return  this;
        }
        public PhotoNameDialog.Builder setMsg(String msg){
            this.msg = msg;
            return  this;
        }


        public Builder(Context context) {
            this.context = context;

        }

        /**
         * 创建对话框
         */
        public PhotoNameDialog create() {
            final PhotoNameDialog dialog = new PhotoNameDialog(context, R.style.dialog);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            final View layout = inflater.inflate(R.layout.dialog_photo_name, null);
            ((layout.findViewById(R.id.image_close))).setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            positiveButtonClickListener.onClick(dialog,
                                    DialogInterface.BUTTON_POSITIVE);
                        }
                    }
            );
            //((TextView)(layout.findViewById(R.id.tv_msg))).setText(msg);
            ((layout.findViewById(R.id.button_cancel))).setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            positiveButtonClickListener.onClick(dialog,
                                    DialogInterface.BUTTON_POSITIVE);

                        }
                    }
            );
            final EditText et_photoname = (EditText) layout.findViewById(R.id.et_photoname) ;
            et_photoname.setText(msg);
            ((layout.findViewById(R.id.button_confirm))).setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(et_photoname.length() ==0){
                                Toast.makeText(context,"请输入图片名称",Toast.LENGTH_SHORT).show();
                                return;
                            }

                            positiveButtonClickListener.onClick(dialog,
                                    DialogInterface.BUTTON_POSITIVE);

                            if(infoGetListener != null){
                                infoGetListener.Choose(et_photoname.getText().toString());
                            }

                        }
                    }
            );
            dialog.setContentView(layout);
            return dialog;
        }
    }


}
