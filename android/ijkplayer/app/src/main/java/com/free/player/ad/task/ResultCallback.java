package com.free.player.ad.task;

public interface ResultCallback<T> {

    void onFail(Exception e);

    void onSuccess(T result);

}
