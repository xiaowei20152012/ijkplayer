package com.free.player.ad.util;

import android.os.AsyncTask;

public final class ReleaseUtil {
    public static void cancelTask(AsyncTask task) {
        if (task != null && !task.isCancelled()) {
            task.cancel(true);
        }
    }

    public static void cancelTasks(AsyncTask... tasks) {
        for (AsyncTask task : tasks) {
            cancelTask(task);
        }
    }
}
