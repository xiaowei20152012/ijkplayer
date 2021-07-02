package com.free.player.ad.task;

public interface OnResultCallback<T> {

    void onFail(Exception e);

    void onSuccess(T result);

}
