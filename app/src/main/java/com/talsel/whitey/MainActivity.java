package com.talsel.whitey;

import android.annotation.SuppressLint;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private final Handler handler = new Handler();

    private SoundPool soundPool;
    private int tractor;
    private int train;
    private int streamId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView stopView = findViewById(R.id.button_stop);

        stopView.setOnClickListener(view -> fadeOut(1.0f));

        ImageView tractorView = findViewById(R.id.button_tractor);

        tractorView.setOnClickListener(view -> playSound(view.getId()));

        ImageView trainView = findViewById(R.id.button_train);

        trainView.setOnClickListener(view -> playSound(view.getId()));

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(2)
                .setAudioAttributes(audioAttributes)
                .build();

        tractor = soundPool.load(this, R.raw.tractor, 1);
        train = soundPool.load(this, R.raw.train, 1);
    }

    @SuppressLint("NonConstantResourceId")
    public void playSound(int id) {
        soundPool.autoPause();
        switch (id) {
            case R.id.button_tractor:
                streamId = soundPool.play(tractor, 1, 1, 0, -1, 1);
                break;
            case R.id.button_train:
                streamId = soundPool.play(train, 1, 1, 0, -1, 1);
                break;
        }
    }

    private void fadeOut(float fadeOutVol) {
        handler.postDelayed(() -> {
            if (fadeOutVol > 0.0f) {
                soundPool.setVolume(streamId, fadeOutVol, fadeOutVol);

                fadeOut(fadeOutVol - 0.05f);
            } else {
                soundPool.autoPause();
            }
        }, 150);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundPool.release();
        soundPool = null;
    }
}