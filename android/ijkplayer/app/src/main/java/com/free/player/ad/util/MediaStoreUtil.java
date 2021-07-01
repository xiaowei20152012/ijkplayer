package com.free.player.ad.util;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore;

import com.free.player.ad.App;
import com.free.player.ad.model.Video;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.WorkerThread;

public final class MediaStoreUtil {

    @WorkerThread
    public static List<Video> getVideoFromCursor() {
        ContentResolver contentResolver = App.appContext().getContentResolver();
        ArrayList<Video> list = new ArrayList<>(64);
        try (Cursor cursor = contentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Video.Media.DEFAULT_SORT_ORDER)) {
            while (cursor.moveToNext()) {
                Video video = new Video();
                String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));// 路径
//                if (!FileUtils.isExists(path)) {
//                    continue;
//                }
                video.setPath(path);
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID));// 视频的id
                video.setId(id);
                String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)); // 视频名称
                video.setName(name);
                String resolution = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.RESOLUTION)); //分辨率
                video.setResolution(resolution);
                long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));// 大小
                video.setSize(size);
                long duration = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));// 时长
                video.setDuration(duration);
                long date = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_MODIFIED));//修改时间
                video.setDate(date);
                list.add(video);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
