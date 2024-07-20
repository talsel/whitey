package se.talsel.whitey;

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
    private int tractor, train, streamId, whitenoise, aeroplane, lakewater, ocean;
    private boolean isFadingOut = false;
    private boolean isFadingIn = false;

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

        ImageView aeroplaneView = findViewById(R.id.button_aeroplane);
        aeroplaneView.setOnClickListener(view -> playSound(view.getId()));

        ImageView whitenoiseView = findViewById(R.id.button_whitenoise);
        whitenoiseView.setOnClickListener(view -> playSound(view.getId()));

        ImageView lakewaterView = findViewById(R.id.button_lakewater);
        lakewaterView.setOnClickListener(view -> playSound(view.getId()));

        ImageView oceanView = findViewById(R.id.button_ocean);
        oceanView.setOnClickListener(view -> playSound(view.getId()));

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(1)
                .setAudioAttributes(audioAttributes)
                .build();

        tractor = soundPool.load(this, R.raw.tractor, 1);
        train = soundPool.load(this, R.raw.train, 1);
        lakewater = soundPool.load(this, R.raw.lakewater, 1);
        aeroplane = soundPool.load(this, R.raw.aeroplane, 1);
        whitenoise = soundPool.load(this, R.raw.whitenoise, 1);
        ocean = soundPool.load(this, R.raw.ocean, 1);
    }

    @SuppressLint("NonConstantResourceId")
    public void playSound(int id) {
        if (isFadingOut) {
            return;
        }
        soundPool.autoPause();
        int soundId = 0;
        switch (id) {
            case R.id.button_tractor:
                soundId = tractor;
                break;
            case R.id.button_train:
                soundId = train;
                break;
            case R.id.button_aeroplane:
                soundId = aeroplane;
                break;
            case R.id.button_whitenoise:
                soundId = whitenoise;
                break;
            case R.id.button_lakewater:
                soundId = lakewater;
                break;
            case R.id.button_ocean:
                soundId = ocean;
                break;
        }
        streamId = soundPool.play(soundId, 0, 0, 0, -1, 1);
        isFadingIn = true;
        fadeIn(0.0f);
    }

    private void fadeIn(float fadeInVol) {
        handler.postDelayed(() -> {
            if (fadeInVol < 1.0f) {
                soundPool.setVolume(streamId, fadeInVol, fadeInVol);
                fadeIn(fadeInVol + 0.05f);
            } else {
                isFadingIn = false;
                soundPool.autoResume();
            }
        }, 75);
    }

    private void fadeOut(float fadeOutVol) {
        if (isFadingIn) {
            return;
        }
        isFadingOut = true;
        handler.postDelayed(() -> {
            if (fadeOutVol > 0.0f) {
                soundPool.setVolume(streamId, fadeOutVol, fadeOutVol);
                fadeOut(fadeOutVol - 0.05f);
            } else {
                soundPool.autoPause();
                isFadingOut = false;
            }
        }, 75);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundPool.release();
        soundPool = null;
    }
}
