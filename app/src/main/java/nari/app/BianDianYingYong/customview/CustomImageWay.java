package nari.app.BianDianYingYong.customview;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.File;
import java.util.Random;

import nari.app.BianDianYingYong.activity.MainActivity;
import nari.app.BianDianYingYong.bean.JCXBean;
import nari.app.BianDianYingYong.bean.PJXXBean;
import nari.app.BianDianYingYong.utils.FileUtils;
import nari.app.BianDianYingYong.utils.SharedPreferencesHelper;


/**
 * Created by TQM on 2017/6/10.
 */

public class CustomImageWay {
    private Activity mContext;// 上下文对象
    private Fragment mFragment;// 上下文对象
    private Builder mBuilder;// 弹出对象
    protected int albumRequestCode;// 相册选择时startActivityForResult方法的requestCode值
    protected int cameraRequestCode;// 拍照选择时startActivityForResult方法的requestCode值
    protected int videoRequestCode;// 视频选择时startActivityForResult方法的requestCode值
    private static final String IMAGE_TYPE = ".jpg";// 图片名后缀
    private static final String VIDEO_TYPE = ".mp4";// 图片名后缀
    private String imagePathByCamera;// 拍照时图片保存路径
    private static Uri mPictureSaveuri;
    public static File mOut; // 图片文件
    public static File videoFile; // 视频文件
    private static Uri mvideouri;
    public static final int TAKE_PHOTO = 1;
    public static File outputImage;
    public static Uri imageUri;
    private String number;


    /**
     * 创建一个选择图片方式实例
     *
     * @param mContext          上下文对象
     * @param albumRequestCode  相册选择时startActivityForResult方法的requestCode值
     * @param cameraRequestCode 拍照选择时startActivityForResult方法的requestCode值
     */
    public static String CZP_PHOTO_ADDR;//操作票存放路径
    public static String JYH_PHOTO_ADDR;//精益化评价存放路径

    public CustomImageWay(Activity mContext, int albumRequestCode,
                          int cameraRequestCode, int videoRequestCode) {
        this.mContext = mContext;
        this.albumRequestCode = albumRequestCode;
        this.cameraRequestCode = cameraRequestCode;
        this.videoRequestCode = videoRequestCode;
        CZP_PHOTO_ADDR = mContext.getExternalCacheDir() + File.separator + "湖北变电应用" + File.separator + "操作票";
        File file = new File(CZP_PHOTO_ADDR);
        if (!file.exists()) {
            file.mkdirs();
        }
        JYH_PHOTO_ADDR = mContext.getExternalCacheDir() + File.separator + "湖北变电应用" + File.separator + "精益化";
        file = new File(JYH_PHOTO_ADDR);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 创建一个选择图片方式实例
     *
     * @param mFragment         上下文对象
     * @param albumRequestCode  相册选择时startActivityForResult方法的requestCode值
     * @param cameraRequestCode 拍照选择时startActivityForResult方法的requestCode值
     */
    public CustomImageWay(Fragment mFragment, int albumRequestCode,
                          int cameraRequestCode) {
        this.mFragment = mFragment;
        this.albumRequestCode = albumRequestCode;
        this.cameraRequestCode = cameraRequestCode;
    }

    /**
     * 显示图片选择对话
     */
    public void show() {
//        if (mBuilder == null) {
//            mBuilder = new Builder(mContext == null ? mFragment.getActivity()
//                    : mContext);
//            mBuilder.setTitle("请选择");
//            mBuilder.setItems(R.array.imgway, new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    click(which);
//                }
//            });
//        }
        mBuilder.show();
    }
    public void cameraJYH(String path,PJXXBean pjxxBean){
        JYH_PHOTO_ADDR = mContext.getExternalCacheDir() + File.separator + "湖北变电应用" + File.separator + "精益化";
        File file = new File(JYH_PHOTO_ADDR,path);
        if(!file.exists()){
            file.mkdirs();
        }
        JYH_PHOTO_ADDR = JYH_PHOTO_ADDR+File.separator+path;
        cameraJYH(pjxxBean);

    }

    private void click(int which) {
        switch (which) {
            case 0:
                album();
                break;
            case 1:
//                camera();
                break;
            case 2:
                break;
        }
    }

    public void album() {
        Intent it1;
        if (Build.VERSION.SDK_INT < 19) {
            it1 = new Intent(Intent.ACTION_GET_CONTENT);
            it1.setType("image/*");
        } else {
            it1 = new Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        if (mContext != null)
            mContext.startActivityForResult(it1, albumRequestCode);
        else
            mFragment.startActivityForResult(it1, albumRequestCode);
    }

    public void cameraJYH(PJXXBean pjxxBean){

        PhotoNameDialog dialog = new PhotoNameDialog.Builder(mContext).setMsg(pjxxBean.getPJXXMS()).setInfoGetListener(new PhotoNameDialog.InfoGetListener() {
            @Override
            public void Choose(String position) {
                cameraJYH(position);
            }
        }).create();
        //WindowManager m = ((Activity)mContext).getWindowManager();
        //Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        //p.width = (int) (d.getWidth()); // 宽度设置为屏幕的1
        dialogWindow.setAttributes(p);
        dialog.show();
    }

    public void cameraCZP() {
        PhotoNameDialog dialog = new PhotoNameDialog.Builder(mContext).setInfoGetListener(new PhotoNameDialog.InfoGetListener() {
            @Override
            public void Choose(String position) {
                cameraCZP(position);
            }
        }).create();
        //WindowManager m = ((Activity)mContext).getWindowManager();
        //Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        //p.width = (int) (d.getWidth()); // 宽度设置为屏幕的1
        dialogWindow.setAttributes(p);
        dialog.show();
    }

    private void cameraJYH(String filename){
        camera(JYH_PHOTO_ADDR,filename);

    }

    public void cameraCZP(String filename) {
        camera(CZP_PHOTO_ADDR, filename);
    }

    public void camera(String ph, String number) {
        this.number = number;
        String imageName = number + IMAGE_TYPE;
        File file = new File(ph, imageName);
        if (file.exists()) {
            Toast.makeText(mContext, "文件已存在", Toast.LENGTH_SHORT).show();
            return;
        }
        outputImage = new File(ph, imageName);
        if (outputImage.exists()) {

        }
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mContext, new String[]{Manifest.permission.CAMERA}, 1);
        } else {
            Intent imageCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (Build.VERSION.SDK_INT >= 24) {
                imageUri = FileProvider.getUriForFile(mContext, "nari.app.biandianyingyong.FileProvider", outputImage);
            } else {
                imageUri = Uri.fromFile(outputImage);

            }
            imageCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);// 设置图片输出路径
            mContext.startActivityForResult(imageCaptureIntent, 1);
        }
    }



    /*
    * 录制视频*/
    public void video() {
        String videoName = FileUtils.getFileName() + VIDEO_TYPE;
        Intent it4 = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        String videoDir = FileUtils
                .getTempFileDir(mContext == null ? mFragment.getActivity()
                        : mContext);
        imagePathByCamera = videoDir + videoName;
        File file = new File(videoDir);
        if (!file.exists())
            file.mkdirs();
        // 设置视频保存路径
        videoFile = new File(file, videoName);
        mvideouri = Uri.fromFile(videoFile);
        it4.putExtra(MediaStore.EXTRA_OUTPUT, mvideouri);
        if (mContext != null)
            mContext.startActivityForResult(it4, videoRequestCode);
        else
            mFragment.startActivityForResult(it4, videoRequestCode);
    }

    /**
     * 获取拍照图片路径
     *
     * @return 图片路径
     */
    public String getCameraImage() {
        return imagePathByCamera;
    }

    /**
     * 获取拍照图片保存路径
     *
     * @return 图片路径
     */
    public static Uri getCameraImageSavePath() {
        Log.e("lala", "自定义界面CustomImageWay.getCameraImageSavePath()============" + imageUri);
        return imageUri;
    }

    /**
     * 获取视频保存路径
     *
     * @return视频路径
     */
    public static Uri getVideoSavePath() {
        Log.e("lala", "自定义界面CustomImageWay.getVideoSavePath()============" + mvideouri);
        return mvideouri;
    }
    /**
     * 操作票拍照保存
     *@param path 保存路径 i+File.separator +2
     *@param name 默认名称
     * @return视频路径
     */
    public void CameraCZP(String path,String name){
        CZP_PHOTO_ADDR = mContext.getExternalCacheDir() + File.separator + "湖北变电应用" + File.separator + "操作票";
        File file = new File(CZP_PHOTO_ADDR,path);
        if(!file.exists()){
            file.mkdirs();
        }
        CZP_PHOTO_ADDR = CZP_PHOTO_ADDR+File.separator+path;
        PhotoNameDialog dialog = new PhotoNameDialog.Builder(mContext).setMsg(name).setInfoGetListener(new PhotoNameDialog.InfoGetListener() {
            @Override
            public void Choose(String position) {
                cameraCZP(position);
            }
        }).create();
        //WindowManager m = ((Activity)mContext).getWindowManager();
        //Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        //p.width = (int) (d.getWidth()); // 宽度设置为屏幕的1
        dialogWindow.setAttributes(p);
        dialog.show();
    }

}
