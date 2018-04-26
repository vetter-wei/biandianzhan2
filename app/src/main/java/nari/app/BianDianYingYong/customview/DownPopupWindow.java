package nari.app.BianDianYingYong.customview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import nari.app.BianDianYingYong.R;

/**
 * Created by DWQ on 2017/11/9.
 */

public class DownPopupWindow implements View.OnClickListener{
    private Context context;//上下文
    private LinearLayout photo, video;//拍照和视频
    private View view;

    public DownPopupWindow(Context context, View view) {
        this.context = context;
        this.view = view;
        initView();
    }
    /**
     * popupwindow初始化和设置
     */
    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View popupView = inflater.inflate(R.layout.popupwindow_down, null);
        photo = popupView.findViewById(R.id.ll_downPopupWindow_photo);
        video = popupView.findViewById(R.id.ll_downPopupWindow_video);
        PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);//初始化popupwindow
        popupWindow.setOutsideTouchable(true);//设置点击外部区域关闭popupwindow
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000)); //设置背景透明
        popupWindow.showAsDropDown(view, -130, 0); //设置popupwindow的显示位置
        photo.setOnClickListener(this);
        video.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_downPopupWindow_photo://拍照
                Intent intent1=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                ((Activity)context).startActivity(intent1);
                break;
            case R.id.ll_downPopupWindow_video://视频
                Intent intent2=new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                ((Activity)context).startActivity(intent2);
                break;
            default:
                break;
        }
    }
}
