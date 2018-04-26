package nari.app.BianDianYingYong.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;


import java.io.FileNotFoundException;

import nari.app.BianDianYingYong.R;
import nari.app.BianDianYingYong.adapter.StepsListViewAdapter;
import nari.app.BianDianYingYong.base.BaseActivity;
import nari.app.BianDianYingYong.customview.CustomImageWay;
import nari.app.BianDianYingYong.customview.CustomListView;
import nari.app.BianDianYingYong.utils.StringUtil;

/**
 * Created by ShawDLee on 2017/11/23.
 */

public class OperationStepsActivity extends BaseActivity implements View.OnClickListener {

    private ImageView back;
    private Context context;
    private StepsListViewAdapter stepsListViewAdapter;
    private String[] mCzbzArray;
    private String objId;
    private CustomListView lv_czxm_content;
    private String strSub = "";
    private MediaScannerConnection mMediaonnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.operation_steps);
        init();
        String status = getIntent().getStringExtra("status");
        String allCzbz = getIntent().getStringExtra("allCzbz");
        String ph=getIntent().getStringExtra("ph");
        objId = getIntent().getStringExtra("objId");
        mCzbzArray = StringUtil.splitString("@@", allCzbz);
        stepsListViewAdapter = new StepsListViewAdapter(OperationStepsActivity.this, mCzbzArray, status, objId,ph);
        lv_czxm_content.setAdapter(stepsListViewAdapter);
        stepsListViewAdapter.notifyDataSetChanged();
    }

    private void init() {
        back = findViewById(R.id.image_processed_czbz_back);
        lv_czxm_content = findViewById(R.id.lv_processed_czbz_content);
        initListener();
    }

    private void initListener() {
        back.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_processed_czbz_back:
                saveCzxmInfo();
                Intent intent = new Intent();
                intent.putExtra("czxm", strSub);
                setResult(RESULT_OK, intent);
                finish();
                break;

        }
    }

    private void saveCzxmInfo() {
        String str = "";
        String xuhao = "";
        String content = "";
        String time = "";
        String status = "";
        for (int i = 0; i < lv_czxm_content.getChildCount(); i++) {
            View view = lv_czxm_content.getChildAt(i);
            StepsListViewAdapter.ViewHolder holder = (StepsListViewAdapter.ViewHolder) view.getTag();
            xuhao = holder.tv_czxm_bh.getText().toString().trim();
            content = holder.tv_czxm_nr.getText().toString().trim();
            if ("".equals(holder.tv_czxm_time.getText().toString().trim())) {
                if (i == lv_czxm_content.getChildCount() - 1) {
                    time = stepsListViewAdapter.lastItemTime;
                } else if (i == 0) {
                    time = stepsListViewAdapter.firstItemTime;
                } else {
                    time = "";
                }
            } else {
                time = holder.tv_czxm_time.getText().toString().trim();
            }
            if (stepsListViewAdapter.mStusMap.size() > i) {
                status = stepsListViewAdapter.mStusMap.get(i);
            } else {
                status = "";
            }
            str += status + "|" + xuhao + "|" + content + "|" + time + "@@";
        }
        strSub = str.substring(0, str.length() - 2);
    }


    @Override
    public void onBackPressed() {
        saveCzxmInfo();
        Intent intent = new Intent();
        intent.putExtra("czxm", strSub);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    try {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            int i = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                            if (i != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                            } else {
                                MediaStore.Images.Media.insertImage(getContentResolver(), CustomImageWay.outputImage.getAbsolutePath(), "title", "description");//图片插入到系统图库
//                                Uri localUri = Uri.fromFile(CustomImageWay.outputImage);
//                                Intent localIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//                                localIntent.setData(Uri.fromFile(Environment.getExternalStorageDirectory()));
//                                sendBroadcast(localIntent);

//                                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://"+CustomImageWay.outputImage.getAbsolutePath())));//通知图库刷新
                                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, CustomImageWay.getCameraImageSavePath()));
                            }
                        }
                        Log.e("lala", "activity中照片路径======" + CustomImageWay.getCameraImageSavePath());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                super.onActivityResult(requestCode, resultCode, data);
                break;
            default:
                break;
        }
    }
}

