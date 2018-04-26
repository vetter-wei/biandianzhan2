package nari.app.BianDianYingYong.utils;

import android.app.Activity;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FileUtils {
    /**
     * @return
     */
    public static boolean mkdirs(String file) {

        String path = file.substring(0, file.lastIndexOf("/") + 1);

        File fi = new File(path);

        if (!fi.exists()) {

            return fi.mkdir();

        } else {

            return true;
        }

    }

    public static void writeFileByByteArray(byte[] array, String path) throws IOException {

        File file = new File(path);

        File p = new File(path.substring(0, path.lastIndexOf("/") + 1));

        if (!p.exists()) {

            System.out.println(p.mkdir());

        }
        FileOutputStream fos = new FileOutputStream(file);

        fos.write(array, 0, array.length);

        fos.close();

    }

    public static String getPictureSelectedPath(Intent intent, Activity activity) {
        Uri uri = intent.getData();
        Cursor cursor = activity.managedQuery(uri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToNext();
        return cursor.getString(index);
    }

    public static byte[] compressAndWriteFile(Bitmap bitmap, Context context, String path) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 40, baos);
        FileUtils.writeFileByByteArray(baos.toByteArray(), path);
        return baos.toByteArray();
    }

    /**
     * 获得指定文件的byte数组
     */
    public static byte[] getBytes(String filePath) {
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    /**
     * 根据byte数组，生成文件
     */
    public static void getFile(byte[] bfile, String filePath, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if (!dir.exists() && dir.isDirectory()) {// 判断文件目录是否存在
                dir.mkdirs();
            }
            file = new File(filePath + "/" + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bfile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 把uri转为File对象
     */
    public static String uri2File(Activity aty, Uri uri) {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            if (android.os.Build.VERSION.SDK_INT < 11) {
                // 在API11以下可以使用：managedQuery
                String[] proj = {MediaStore.Images.Media.DATA};
                @SuppressWarnings("deprecation")
                Cursor actualimagecursor = aty.managedQuery(uri, proj, null, null, null);
                int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                actualimagecursor.moveToFirst();
                return actualimagecursor.getString(actual_image_column_index);
            } else {
                // 在API11以上：要转为使用CursorLoader,并使用loadInBackground来返回
                String[] projection = {MediaStore.Images.Media.DATA};
                CursorLoader loader = new CursorLoader(aty, uri, projection, null, null, null);
                Cursor cursor = loader.loadInBackground();
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                return cursor.getString(column_index);
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    /**
     * Try to return the absolute file path from the given Uri
     *
     * @param context
     * @param uri
     * @return the file path or null
     */
    public static String getPath(Context context, Uri uri) {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {"_data"};
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver().query(uri, null, null, null, null);
                while (cursor.moveToNext()) {
                    Log.i("ContentTest", "personid=" + cursor.getInt(0) + ",name=" + cursor.getString(1));
                }
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                Toast.makeText(context, "未找到路径", Toast.LENGTH_LONG).show();
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    //设置文件名字
    public static final String getFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault());
        return dateFormat.format(date);// + ".jpg";
    }

    /**
     * 获取文件地址
     *
     * @param context
     * @return
     */
    public static final String getTempFileDir(Context context) {
        String path = getFileDir(context);
        File file = new File(path);
        if (!file.exists())
            file.mkdirs();
        return path;
    }

    public static final String getFileDir(Context context) {
        String path = isExternalMemoryAvailable() ? context
                .getExternalFilesDir(null).getPath() + "/" : context
                .getFilesDir().getPath() + "/";
        File file = new File(path);
        if (!file.exists())
            file.mkdirs();
        return path;
    }

    public static final boolean isExternalMemoryAvailable() {
        return Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())
                && (Environment.getExternalStorageState() != null)
                && (Environment.MEDIA_MOUNTED_READ_ONLY != Environment
                .getExternalStorageState());
    }

}