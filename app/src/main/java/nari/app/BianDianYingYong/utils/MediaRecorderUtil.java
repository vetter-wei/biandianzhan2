package nari.app.BianDianYingYong.utils;

import android.media.MediaRecorder;

import java.io.IOException;


/**
 * Created by ShawDLee on 2017/12/19.
 */

public class MediaRecorderUtil {
    private MediaRecorder recorder;

    public void startMediaRecorder(String path) {
    if (recorder != null) {
            recorder.reset();
        } else {
            recorder = new MediaRecorder();
        }
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC); // 设置音频来源（MIC表示麦克风）
        recorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB); // 设置音频输出格式（默认的输出格式）
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB); // 设置音频编码方式（默认的编码方式）
        recorder.setOutputFile(path); // 指定音频输出文件
        try {
            recorder.prepare(); // 调用prepare方法
        } catch (IOException e) {
            e.printStackTrace();
        }
        recorder.start();
    }

    public void stopMediaRecorder() {
        if (recorder != null) {
            recorder.stop();
            recorder.release();
            recorder = null;
        }
    }
}
