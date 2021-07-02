package com.free.player.ad.player;

import com.free.player.common.log.ZenLogger;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkTimedText;

public interface PlayerEventListener {

    default void onPrepared(IMediaPlayer mp) {
        ZenLogger.d("onPrepared");
    }

    default void onVideoSizeChanged(IMediaPlayer mp, int width, int height, int sar_num, int sar_den) {
        ZenLogger.d("onVideoSizeChanged");
    }

    default void onCompletion(IMediaPlayer mp) {
        ZenLogger.d("onCompletion");
    }

    default boolean onError(IMediaPlayer mp, int what, int extra) {
        ZenLogger.d("onError");
        return false;
    }

    default boolean onInfo(IMediaPlayer mp, int what, int extra) {
        ZenLogger.d("onInfo");
        return false;
    }

    default void onSeekComplete(IMediaPlayer mp) {
        ZenLogger.d("onSeekComplete");
    }

    default void onBufferingUpdate(IMediaPlayer mp, int percent) {
        ZenLogger.d("onBufferingUpdate");
    }


    default void onTimedText(IMediaPlayer mp, IjkTimedText text) {
        ZenLogger.d("onTimedText");
    }

}
