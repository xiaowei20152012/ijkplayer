package com.free.player.ad.player;

import android.content.Context;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;

import com.free.exo.ExoMediaPlayer;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

import tv.danmaku.ijk.media.player.AndroidMediaPlayer;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;
import tv.danmaku.ijk.media.player.IjkTimedText;

public class MusicPlayer {
    private final Context context;
    private IMediaPlayer mediaPlayer;
    private final CopyOnWriteArraySet<PlayerEventListener> eventListeners;

    public MusicPlayer(Context context) {
        eventListeners = new CopyOnWriteArraySet<>();
        this.context = context;
        // init player
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");
    }

    public void start(Uri uri) {
        AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        am.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

        mediaPlayer = createPlayer(PlayerType.EXO_MEDIA_PLAYER);

        mediaPlayer.setOnPreparedListener(preparedListener);
        mediaPlayer.setOnVideoSizeChangedListener(sizeChangedListener);
        mediaPlayer.setOnCompletionListener(completionListener);
        mediaPlayer.setOnErrorListener(errorListener);
        mediaPlayer.setOnInfoListener(infoListener);
        mediaPlayer.setOnBufferingUpdateListener(bufferingUpdateListener);
        mediaPlayer.setOnSeekCompleteListener(seekCompleteListener);
        mediaPlayer.setOnTimedTextListener(onTimedTextListener);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            try {
                mediaPlayer.setDataSource(context, uri, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                mediaPlayer.setDataSource(uri.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setScreenOnWhilePlaying(true);
        mediaPlayer.prepareAsync();
    }

    public void pause() {
        if (mediaPlayer == null) {
            return;
        }
        mediaPlayer.pause();
    }

    public void reset() {
        if (mediaPlayer == null) {
            return;
        }
        mediaPlayer.reset();
    }

    public void stop() {
        if (mediaPlayer == null) {
            return;
        }
        mediaPlayer.stop();
    }

    public void release() {
        if (mediaPlayer == null) {
            return;
        }
        mediaPlayer.reset();
        mediaPlayer.release();
        mediaPlayer = null;
        AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        am.abandonAudioFocus(null);
    }


    private IMediaPlayer createPlayer(int type) {
        IMediaPlayer player = null;
        switch (type) {
            case PlayerType.ANDROID_MEDIA_PLAYER:
                player = new AndroidMediaPlayer();
                break;
            case PlayerType.IJK_MEDIA_PLAYER:
                IjkMediaPlayer ijkPlayer = new IjkMediaPlayer();
                IjkMediaPlayer.native_setLogLevel(IjkMediaPlayer.IJK_LOG_DEBUG);
                ijkPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", 1);
                ijkPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "framedrop", 1);
                ijkPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "start-on-prepared", 0);
                ijkPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "http-detect-range-support", 0);
                ijkPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_CODEC, "skip_loop_filter", 48);

                player = ijkPlayer;
                break;
            case PlayerType.EXO_MEDIA_PLAYER:
                player = new ExoMediaPlayer(context);
                break;
            default:
        }
        return player;
    }

    public void addPlayerEventListener(PlayerEventListener listener) {
        eventListeners.add(listener);
    }

    public void removePlayerEventListener(PlayerEventListener listener) {
        eventListeners.remove(listener);
    }


    private final IMediaPlayer.OnPreparedListener preparedListener = new IMediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(IMediaPlayer mp) {
            for (PlayerEventListener listener : eventListeners) {
                listener.onPrepared(mp);
            }
        }
    };

    private final IMediaPlayer.OnVideoSizeChangedListener sizeChangedListener = new IMediaPlayer.OnVideoSizeChangedListener() {
        @Override
        public void onVideoSizeChanged(IMediaPlayer mp, int width, int height, int sar_num, int sar_den) {
            for (PlayerEventListener listener : eventListeners) {
                listener.onVideoSizeChanged(mp, width, height, sar_num, sar_den);
            }
        }
    };

    private final IMediaPlayer.OnCompletionListener completionListener = new IMediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(IMediaPlayer mp) {
            for (PlayerEventListener listener : eventListeners) {
                listener.onCompletion(mp);
            }
        }
    };

    private final IMediaPlayer.OnErrorListener errorListener = new IMediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(IMediaPlayer mp, int what, int extra) {
            for (PlayerEventListener listener : eventListeners) {
                listener.onError(mp, what, extra);
            }
            return false;
        }
    };

    private final IMediaPlayer.OnInfoListener infoListener = new IMediaPlayer.OnInfoListener() {
        @Override
        public boolean onInfo(IMediaPlayer mp, int what, int extra) {
            for (PlayerEventListener listener : eventListeners) {
                listener.onInfo(mp, what, extra);
            }
            return false;
        }
    };

    private final IMediaPlayer.OnSeekCompleteListener seekCompleteListener = new IMediaPlayer.OnSeekCompleteListener() {
        @Override
        public void onSeekComplete(IMediaPlayer mp) {
            for (PlayerEventListener listener : eventListeners) {
                listener.onSeekComplete(mp);
            }
        }
    };

    private final IMediaPlayer.OnBufferingUpdateListener bufferingUpdateListener = new IMediaPlayer.OnBufferingUpdateListener() {
        @Override
        public void onBufferingUpdate(IMediaPlayer mp, int percent) {
            for (PlayerEventListener listener : eventListeners) {
                listener.onBufferingUpdate(mp, percent);
            }
        }
    };


    private final IMediaPlayer.OnTimedTextListener onTimedTextListener = new IMediaPlayer.OnTimedTextListener() {
        @Override
        public void onTimedText(IMediaPlayer mp, IjkTimedText text) {
            for (PlayerEventListener listener : eventListeners) {
                listener.onTimedText(mp, text);
            }
        }
    };

}
