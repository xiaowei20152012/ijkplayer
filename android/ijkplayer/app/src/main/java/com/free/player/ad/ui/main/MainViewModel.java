package com.free.player.ad.ui.main;


import com.free.player.ad.model.Video;
import com.free.player.ad.task.ResultCallback;
import com.free.player.ad.task.VideoLoadTask;
import com.free.player.ad.util.MXExecutors;
import com.free.player.ad.util.ReleaseUtil;
import com.free.player.common.ui.IViewModelRelease;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel implements IViewModelRelease, ResultCallback<List<Video>> {


    public MutableLiveData<List<Video>> liveData = new MutableLiveData<>();
    private VideoLoadTask loadTask;


    @Override
    public void onViewCreated() {
        load();
    }

    @Override
    public void onViewDestroyed() {

    }


    private void load() {
        List<Video> caches = liveData.getValue();
        if (caches != null) {
            return;
        }
        ReleaseUtil.cancelTask(loadTask);
        loadTask = new VideoLoadTask(this);
        loadTask.executeOnExecutor(MXExecutors.io());
    }


    @Override
    public void onFail(Exception e) {

    }

    @Override
    public void onSuccess(List<Video> result) {
        liveData.setValue(result);
    }
}