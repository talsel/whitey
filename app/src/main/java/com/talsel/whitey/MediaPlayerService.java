package com.talsel.whitey;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.media.session.MediaButtonReceiver;
//
//public class MediaPlayerService extends Service {
//    private static final String CHANNEL_ID = "MediaPlayerServiceChannel";
//    private MediaPlayer mediaPlayer;
//    private MediaSessionCompat mediaSession;
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        createNotificationChannel();
//        mediaSession = new MediaSessionCompat(this, "MediaPlayerService");
//
//        mediaSession.setCallback(new MediaSessionCompat.Callback() {
//            @Override
//            public void onPlay() {
//                if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
//                    mediaPlayer.start();
//                    showNotification();
//                }
//            }
//
//            @Override
//            public void onPause() {
//                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
//                    mediaPlayer.pause();
//                    showNotification();
//                }
//            }
//
//            @Override
//            public void onStop() {
//                if (mediaPlayer != null) {
//                    mediaPlayer.stop();
//                    stopForeground(true);
//                }
//            }
//
//            @Override
//            public void onSeekTo(long pos) {
//                if (mediaPlayer != null) {
//                    mediaPlayer.seekTo((int) pos);
//                }
//            }
//        });
//
//        mediaSession.setActive(true);
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        MediaButtonReceiver.handleIntent(mediaSession, intent);
//
//        if (intent != null && "PLAY".equals(intent.getAction())) {
//            int soundResourceId = intent.getIntExtra("SOUND_RESOURCE_ID", 0);
//            if (soundResourceId != 0) {
//                playSound(soundResourceId);
//            }
//        }
//        return START_NOT_STICKY;
//    }
//
//    public void playSound(int soundResourceId) {
//        if (mediaPlayer != null) {
//            mediaPlayer.stop();
//            mediaPlayer.release();
//        }
//
//        mediaPlayer = MediaPlayer.create(this, soundResourceId);
//        mediaPlayer.setLooping(true);
//        mediaPlayer.start();
//
//        showNotification();
//    }
//
//    private void showNotification() {
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
//                .setSmallIcon(R.drawable.ic_music_note)
//                .setContentTitle("Playing Sound")
//                .setContentText("Your sound is playing")
//                .setPriority(NotificationCompat.PRIORITY_LOW)
//                .setOnlyAlertOnce(true)
//                .setOngoing(true)
//                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
//                .addAction(new NotificationCompat.Action(
//                        R.drawable.ic_pause, "Pause",
//                        MediaButtonReceiver.buildMediaButtonPendingIntent(this,
//                                PlaybackStateCompat.ACTION_PAUSE)))
//                .addAction(new NotificationCompat.Action(
//                        R.drawable.ic_stop, "Stop",
//                        MediaButtonReceiver.buildMediaButtonPendingIntent(this,
//                                PlaybackStateCompat.ACTION_STOP)))
//                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
//                        .setMediaSession(mediaSession.getSessionToken())
//                        .setShowActionsInCompactView(0, 1));
//
//        startForeground(1, builder.build());
//    }
//
//    private void createNotificationChannel() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel serviceChannel = new NotificationChannel(
//                    CHANNEL_ID,
//                    "Media Player Service Channel",
//                    NotificationManager.IMPORTANCE_LOW
//            );
//            NotificationManager manager = getSystemService(NotificationManager.class);
//            manager.createNotificationChannel(serviceChannel);
//        }
//    }
//
//    @Override
//    public void onDestroy() {
//        if (mediaPlayer != null) {
//            mediaPlayer.stop();
//            mediaPlayer.release();
//            mediaPlayer = null;
//        }
//        mediaSession.release();
//        super.onDestroy();
//    }
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//}

public class MediaPlayerService extends Service {
    private static final String CHANNEL_ID = "MediaPlayerServiceChannel";
    private MediaPlayer mediaPlayer;
    private MediaSessionCompat mediaSession;

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        mediaSession = new MediaSessionCompat(this, "MediaPlayerService");

        mediaSession.setCallback(new MediaSessionCompat.Callback() {
            @Override
            public void onPlay() {
                if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                    showNotification();
                }
            }

            @Override
            public void onPause() {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    showNotification();
                }
            }

            @Override
            public void onStop() {
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    stopForeground(true);
                }
            }

            @Override
            public void onSeekTo(long pos) {
                if (mediaPlayer != null) {
                    mediaPlayer.seekTo((int) pos);
                }
            }
        });

        mediaSession.setActive(true);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        MediaButtonReceiver.handleIntent(mediaSession, intent);

        if (intent != null && "PLAY".equals(intent.getAction())) {
            int soundResourceId = intent.getIntExtra("SOUND_RESOURCE_ID", 0);
            if (soundResourceId != 0) {
                playSound(soundResourceId);
            }
        }
        return START_NOT_STICKY;
    }

    public void playSound(int soundResourceId) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }

        mediaPlayer = MediaPlayer.create(this, soundResourceId);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        showNotification();
    }

    private void showNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_music_note)
                .setContentTitle("Playing Sound")
                .setContentText("Your sound is playing")
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setOnlyAlertOnce(true)
                .setOngoing(true)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .addAction(new NotificationCompat.Action(
                        R.drawable.ic_pause, "Pause",
                        MediaButtonReceiver.buildMediaButtonPendingIntent(this,
                                PlaybackStateCompat.ACTION_PAUSE)))
                .addAction(new NotificationCompat.Action(
                        R.drawable.ic_stop, "Stop",
                        MediaButtonReceiver.buildMediaButtonPendingIntent(this,
                                PlaybackStateCompat.ACTION_STOP)))
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setMediaSession(mediaSession.getSessionToken())
                        .setShowActionsInCompactView(0, 1));

        startForeground(1, builder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Media Player Service Channel",
                    NotificationManager.IMPORTANCE_LOW
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

    @Override
    public void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        mediaSession.release();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}