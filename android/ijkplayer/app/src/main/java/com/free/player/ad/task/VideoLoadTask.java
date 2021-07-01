package com.free.player.ad.task;

import android.os.AsyncTask;

import com.free.player.ad.model.Video;
import com.free.player.ad.util.MediaStoreUtil;

import java.util.List;

public class VideoLoadTask extends AsyncTask<Void, Void, List<Video>> {

    private final ResultCallback callback;

    public VideoLoadTask(ResultCallback callback) {
        this.callback = callback;
    }

    @Override
    protected List<Video> doInBackground(Void... voids) {
        return MediaStoreUtil.getVideoFromCursor();
    }

    @Override
    protected void onPostExecute(List<Video> videos) {
        super.onPostExecute(videos);
        if (callback != null) {
            callback.onSuccess(videos);
        }
    }
}