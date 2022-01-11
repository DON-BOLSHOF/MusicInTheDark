package ru.samsung.itschool.hello.music;

import static android.content.Intent.getIntent;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.util.Date;

public class MusicService extends Service {
    MediaPlayer mPlayer;
    long time = 0;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        // сообщение о создании службы
        Toast.makeText(this, "Service created", Toast.LENGTH_SHORT).show();
        mPlayer= MediaPlayer.create(this, R.raw.dot);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Service started", Toast.LENGTH_SHORT).show();
        time =  intent.getLongExtra("time",0);
        mPlayer.seekTo((int) time);
        mPlayer.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        //сообщение об остановке службы
        super.onDestroy();
        mPlayer.pause();
        Toast.makeText(this, "Service stopped", Toast.LENGTH_SHORT).show();
    }


}