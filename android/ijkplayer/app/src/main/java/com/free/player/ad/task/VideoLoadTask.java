package com.free.player.ad.task;

import android.os.AsyncTask;

import com.free.player.ad.model.VideoInfo;
import com.free.player.ad.util.MediaStoreUtil;

import java.util.List;

public class VideoLoadTask extends AsyncTask<Void, Void, List<VideoInfo>> {

    private final OnResultCallback callback;

    public VideoLoadTask(OnResultCallback callback) {
        this.callback = callback;
    }

    @Override
    protected List<VideoInfo> doInBackground(Void... voids) {
        return MediaStoreUtil.getVideoFromCursor();
    }

    @Override
    protected void onPostExecute(List<VideoInfo> videos) {
        super.onPostExecute(videos);
        if (callback != null) {
            callback.onSuccess(videos);
        }
    }
}