/*
 * Copyright (C) 2015 Bilibili
 * Copyright (C) 2015 Zhang Rui <bbcallen@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.free.exo;

import android.content.Context;
import android.net.Uri;
import android.view.Surface;
import android.view.SurfaceHolder;


import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;
import com.google.android.exoplayer2.source.MediaSourceFactory;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.util.EventLogger;

import java.io.FileDescriptor;
import java.util.Map;

import tv.danmaku.ijk.media.player.AbstractMediaPlayer;
import tv.danmaku.ijk.media.player.MediaInfo;
import tv.danmaku.ijk.media.player.misc.IjkTrackInfo;

public class ExoMediaPlayer extends AbstractMediaPlayer {
    private Context context;
    private SimpleExoPlayer internalPlayer;
    private String dataSource;
    private int mVideoWidth;
    private int mVideoHeight;
    private DataSource.Factory dataSourceFactory;
    public ExoMediaPlayer(Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    public void setDisplay(SurfaceHolder sh) {
        if (sh == null)
            setSurface(null);
        else
            setSurface(sh.getSurface());
    }

    @Override
    public void setSurface(Surface surface) {
//        mSurface = surface;
        if (internalPlayer != null) {
            internalPlayer.setVideoSurface(surface);
        }
    }

    @Override
    public void setDataSource(Context context, Uri uri) {
        dataSource = uri.toString();
    }

    @Override
    public void setDataSource(Context context, Uri uri, Map<String, String> headers) {
        // TODO: handle headers
        setDataSource(context, uri);
    }

    @Override
    public void setDataSource(String path) {
        setDataSource(context, Uri.parse(path));
    }

    @Override
    public void setDataSource(FileDescriptor fd) {
        // TODO: no support
        throw new UnsupportedOperationException("no support");
    }

    @Override
    public String getDataSource() {
        return dataSource;
    }

    @Override
    public void prepareAsync() throws IllegalStateException {
        if (internalPlayer != null) {
            throw new IllegalStateException("can't prepare a prepared player");
        }

        initPlayer(false);
        internalPlayer.setMediaItem(new MediaItem.Builder().setUri(dataSource).build());
        internalPlayer.prepare();
        start();
    }

    @Override
    public void start() throws IllegalStateException {
        if (internalPlayer == null) {
            return;
        }
        internalPlayer.setPlayWhenReady(true);
    }

    @Override
    public void stop() throws IllegalStateException {
        if (internalPlayer == null) {
            return;
        }
        internalPlayer.release();
    }

    @Override
    public void pause() throws IllegalStateException {
        if (internalPlayer == null) {
            return;
        }
        internalPlayer.setPlayWhenReady(false);
    }

    @Override
    public void setWakeMode(Context context, int mode) {
        // FIXME: implement
    }

    @Override
    public void setScreenOnWhilePlaying(boolean screenOn) {
        // TODO: do nothing
    }

    @Override
    public IjkTrackInfo[] getTrackInfo() {
        // TODO: implement
        return null;
    }

    @Override
    public int getVideoWidth() {
        return mVideoWidth;
    }

    @Override
    public int getVideoHeight() {
        return mVideoHeight;
    }

    @Override
    public boolean isPlaying() {
//        if (mInternalPlayer == null)
//            return false;
//        int state = mInternalPlayer.getPlaybackState();
//        switch (state) {
//            case ExoPlayer.STATE_BUFFERING:
//            case ExoPlayer.STATE_READY:
//                return mInternalPlayer.getPlayWhenReady();
//            case ExoPlayer.STATE_IDLE:
//            case ExoPlayer.STATE_PREPARING:
//            case ExoPlayer.STATE_ENDED:
//            default:
//                return false;
//        }
        if (internalPlayer == null) {
            return false;
        }

        return internalPlayer.getPlayWhenReady();
    }

    @Override
    public void seekTo(long msec) throws IllegalStateException {
//        if (mInternalPlayer == null)
//            return;
//        mInternalPlayer.seekTo(msec);
        if (internalPlayer == null) {
            return;
        }
        internalPlayer.seekTo(msec);
    }

    @Override
    public long getCurrentPosition() {
        if (internalPlayer == null)
            return 0;
        return internalPlayer.getCurrentPosition();

    }

    @Override
    public long getDuration() {
        if (internalPlayer == null)
            return 0;
        return internalPlayer.getDuration();
    }

    @Override
    public int getVideoSarNum() {
        return 1;
    }

    @Override
    public int getVideoSarDen() {
        return 1;
    }

    @Override
    public void reset() {
        if (internalPlayer != null) {
            internalPlayer.release();
            internalPlayer.removeListener(eventListener);
//            internalPlayer.removeListener(mEventLogger);
//            internalPlayer.setInfoListener(null);
//            internalPlayer.setInternalErrorListener(null);
            internalPlayer = null;
        }

//        mSurface = null;
        dataSource = null;
        mVideoWidth = 0;
        mVideoHeight = 0;
    }

    @Override
    public void setLooping(boolean looping) {
        // TODO: no support
        throw new UnsupportedOperationException("no support");
    }

    @Override
    public boolean isLooping() {
        // TODO: no support
        return false;
    }

    @Override
    public void setVolume(float leftVolume, float rightVolume) {
        // TODO: no support
    }


    @Override
    public int getAudioSessionId() {
        // TODO: no support
        return 0;
    }

    @Override
    public MediaInfo getMediaInfo() {
        // TODO: no support
        return null;
    }

    @Override
    public void setLogEnabled(boolean enable) {
        // do nothing
    }

    @Override
    public boolean isPlayable() {
        return true;
    }

    @Override
    public void setAudioStreamType(int streamtype) {
        // do nothing
    }

    @Override
    public void setKeepInBackground(boolean keepInBackground) {
        // do nothing
    }

    @Override
    public void release() {
        if (internalPlayer != null) {
            reset();

//            mDemoListener = null;
//
//            mEventLogger.endSession();
//            mEventLogger = null;
        }
    }

//    public int getBufferedPercentage() {
//        if (mInternalPlayer == null)
//            return 0;
//
//        return mInternalPlayer.getBufferedPercentage();
//    }

//    private RendererBuilder getRendererBuilder() {
//        Uri contentUri = Uri.parse(mDataSource);
//        String userAgent = Util.getUserAgent(mAppContext, "IjkExoMediaPlayer");
//        int contentType = inferContentType(contentUri);
//        switch (contentType) {
//            case Util.TYPE_SS:
//                return new SmoothStreamingRendererBuilder(mAppContext, userAgent, contentUri.toString(),
//                        new SmoothStreamingTestMediaDrmCallback());
//         /*   case Util.TYPE_DASH:
//                return new DashRendererBuilder(mAppContext , userAgent, contentUri.toString(),
//                        new WidevineTestMediaDrmCallback(contentId, provider));*/
//            case Util.TYPE_HLS:
//                return new HlsRendererBuilder(mAppContext, userAgent, contentUri.toString());
//            case Util.TYPE_OTHER:
//            default:
//                return new ExtractorRendererBuilder(mAppContext, userAgent, contentUri);
//        }
//    }
    
    /**
     * Makes a best guess to infer the type from a media {@link Uri}
     *
     * @param uri The {@link Uri} of the media.
     * @return The inferred type.
     */
//    private static int inferContentType(Uri uri) {
//        String lastPathSegment = uri.getLastPathSegment();
//        return Util.inferContentType(lastPathSegment);
//    }

   /* private class DemoPlayerListener implements DemoPlayer.Listener {
        private boolean mIsPrepareing = false;
        private boolean mDidPrepare = false;
        private boolean mIsBuffering = false;

        public void onStateChanged(boolean playWhenReady, int playbackState)
        {
            if (mIsBuffering) {
                switch (playbackState) {
                    case ExoPlayer.STATE_ENDED:
                    case ExoPlayer.STATE_READY:
                        notifyOnInfo(IMediaPlayer.MEDIA_INFO_BUFFERING_END, mInternalPlayer.getBufferedPercentage());
                        mIsBuffering = false;
                        break;
                }
            }

            if (mIsPrepareing) {
                switch (playbackState) {
                    case ExoPlayer.STATE_READY:
                        notifyOnPrepared();
                        mIsPrepareing = false;
                        mDidPrepare = false;
                        break;
                }
            }

            switch (playbackState) {
                case ExoPlayer.STATE_IDLE:
                    notifyOnCompletion();
                    break;
                case ExoPlayer.STATE_PREPARING:
                    mIsPrepareing = true;
                    break;
                case ExoPlayer.STATE_BUFFERING:
                    notifyOnInfo(IMediaPlayer.MEDIA_INFO_BUFFERING_START, mInternalPlayer.getBufferedPercentage());
                    mIsBuffering = true;
                    break;
                case ExoPlayer.STATE_READY:
                    break;
                case ExoPlayer.STATE_ENDED:
                    notifyOnCompletion();
                    break;
                default:
                    break;
            }
        }

        public void onError(Exception e)
        {
            notifyOnError(IMediaPlayer.MEDIA_ERROR_UNKNOWN, IMediaPlayer.MEDIA_ERROR_UNKNOWN);
        }

        public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees,
                                float pixelWidthHeightRatio)
        {
            mVideoWidth = width;
            mVideoHeight = height;
            notifyOnVideoSizeChanged(width, height, 1, 1);
            if (unappliedRotationDegrees > 0)
                notifyOnInfo(IMediaPlayer.MEDIA_INFO_VIDEO_ROTATION_CHANGED, unappliedRotationDegrees);
        }
    }*/

//    private DemoPlayerListener mDemoListener;

    private DefaultTrackSelector trackSelector;
    private void initPlayer(boolean startAutoPlay) {
        dataSourceFactory = Util.getDataSourceFactory(/* context= */ context);

//        boolean preferExtensionDecoders =
//                intent.getBooleanExtra(IntentUtil.PREFER_EXTENSION_DECODERS_EXTRA, false);
        RenderersFactory renderersFactory =
                Util.buildRenderersFactory(/* context= */ context, true);
        MediaSourceFactory mediaSourceFactory =
                new DefaultMediaSourceFactory(dataSourceFactory);
//                        .setAdsLoaderProvider(this::getAdsLoader)
//                        .setAdViewProvider(playerView);

        trackSelector = new DefaultTrackSelector(/* context= */ context);
        DefaultTrackSelector.ParametersBuilder builder =
                new DefaultTrackSelector.ParametersBuilder(/* context= */ context);
        DefaultTrackSelector.Parameters trackSelectorParameters = builder.build();
        trackSelector.setParameters(trackSelectorParameters);
//        lastSeenTrackGroupArray = null;
        internalPlayer =
                new SimpleExoPlayer.Builder(/* context= */ context, renderersFactory)
                        .setMediaSourceFactory(mediaSourceFactory)
                        .setTrackSelector(trackSelector)
                        .build();
        internalPlayer.addListener(eventListener);
        internalPlayer.addAnalyticsListener(new EventLogger(trackSelector));
        internalPlayer.setAudioAttributes(AudioAttributes.DEFAULT, /* handleAudioFocus= */ true);
        internalPlayer.setPlayWhenReady(startAutoPlay);
    }


    private final Player.EventListener eventListener = new Player.EventListener() {
        @Override
        public void onTimelineChanged(Timeline timeline, int reason) {

        }
    };


}
