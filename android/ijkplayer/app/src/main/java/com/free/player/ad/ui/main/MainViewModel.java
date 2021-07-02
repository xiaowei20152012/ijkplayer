package com.free.player.ad.ui.main;


import com.free.player.ad.model.AudioInfo;
import com.free.player.ad.model.VideoInfo;
import com.free.player.ad.task.AudioLoadTask;
import com.free.player.ad.task.OnResultCallback;
import com.free.player.ad.task.VideoLoadTask;
import com.free.player.ad.util.MXExecutors;
import com.free.player.ad.util.ReleaseUtil;
import com.free.player.common.ui.IViewModelRelease;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel implements IViewModelRelease {


    public MutableLiveData<List<VideoInfo>> videoLiveData = new MutableLiveData<>();
    private VideoLoadTask videoLoadTask;

    public MutableLiveData<List<AudioInfo>> audioLiveData = new MutableLiveData<>();
    private AudioLoadTask audioLoadTask;


    @Override
    public void onViewCreated() {
//        loadVideo();
        loadAudio();
    }

    @Override
    public void onViewDestroyed() {

    }


    private void loadVideo() {
        List<VideoInfo> caches = videoLiveData.getValue();
        if (caches != null) {
            return;
        }
        ReleaseUtil.cancelTask(videoLoadTask);
        videoLoadTask = new VideoLoadTask(new OnResultCallback<List<VideoInfo>>() {
            @Override
            public void onFail(Exception e) {

            }

            @Override
            public void onSuccess(List<VideoInfo> result) {
                videoLiveData.setValue(result);
            }

        });
        videoLoadTask.executeOnExecutor(MXExecutors.io());
    }

    private void loadAudio() {
        List<AudioInfo> caches = audioLiveData.getValue();
        if (caches != null) {
            return;
        }
        ReleaseUtil.cancelTask(audioLoadTask);
        audioLoadTask = new AudioLoadTask(new OnResultCallback<List<AudioInfo>>() {

            @Override
            public void onFail(Exception e) {

            }

            @Override
            public void onSuccess(List<AudioInfo> result) {
                audioLiveData.setValue(result);
            }
        });
        audioLoadTask.executeOnExecutor(MXExecutors.io());
    }

}