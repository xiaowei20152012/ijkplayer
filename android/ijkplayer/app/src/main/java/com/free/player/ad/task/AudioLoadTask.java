package com.free.player.ad.task;

import android.os.AsyncTask;

import com.free.player.ad.model.AudioInfo;
import com.free.player.ad.util.MediaStoreUtil;

import java.util.List;

public class AudioLoadTask extends AsyncTask<Void, Void, List<AudioInfo>> {

    private final OnResultCallback callback;

    public AudioLoadTask(OnResultCallback callback) {
        this.callback = callback;
    }

    @Override
    protected List<AudioInfo> doInBackground(Void... voids) {
        return MediaStoreUtil.getAudioFromCursor();
    }

    @Override
    protected void onPostExecute(List<AudioInfo> audios) {
        super.onPostExecute(audios);
        if (callback != null) {
            callback.onSuccess(audios);
        }
    }
}