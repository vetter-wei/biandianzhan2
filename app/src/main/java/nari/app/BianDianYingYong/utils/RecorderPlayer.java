package nari.app.BianDianYingYong.utils;


import android.media.MediaPlayer;
import android.util.Log;

import java.io.IOException;


/**
 * Created by ShawDLee on 2017/12/20.
 */


public class RecorderPlayer {

    private  MediaPlayer mediaPlayer;


    // 播放录音文件
    public void startPlayer(String path) {
        if (mediaPlayer != null){
            mediaPlayer.reset();
        }else {
            mediaPlayer=new MediaPlayer();
        }
        try {
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();


    }

    // 暂停播放录音
    public void pausePalyer() {
//        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            Log.e("TAG", "暂停播放");
//        }

    }

    // 停止播放录音
    public void stopPalyer() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            mediaPlayer.seekTo(0);
            Log.e("TAG", "停止播放");
        }
    }
}
