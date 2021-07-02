package com.free.player.ad.util;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore;

import com.free.player.ad.App;
import com.free.player.ad.model.AudioInfo;
import com.free.player.ad.model.VideoInfo;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.WorkerThread;

public final class MediaStoreUtil {

    @WorkerThread
    public static List<VideoInfo> getVideoFromCursor() {
        ContentResolver contentResolver = App.appContext().getContentResolver();
        ArrayList<VideoInfo> list = new ArrayList<>(64);
        try (Cursor cursor = contentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Video.Media.DEFAULT_SORT_ORDER)) {
            while (cursor.moveToNext()) {
                VideoInfo video = new VideoInfo();
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



    @WorkerThread
    public static List<AudioInfo> getAudioFromCursor() {
        ContentResolver contentResolver = App.appContext().getContentResolver();
        ArrayList<AudioInfo> list = new ArrayList<>(64);
        try (Cursor cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER)) {
            while (cursor.moveToNext()) {
                AudioInfo audio = new AudioInfo();
                String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));// 路径
//                if (!FileUtils.isExists(path)) {
//                    continue;
//                }
                audio.setPath(path);
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));// 视频的id
                audio.setId(id);
                String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)); // 视频名称
                audio.setName(name);
//                String resolution = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.RESOLUTION)); //分辨率
//                audio.setResolution(resolution);
                long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));// 大小
                audio.setSize(size);
                long duration = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));// 时长
                audio.setDuration(duration);
                long date = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_MODIFIED));//修改时间
                audio.setDate(date);
                list.add(audio);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
