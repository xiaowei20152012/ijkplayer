package com.free.player.ad.model;

import android.os.Parcel;
import android.os.Parcelable;

public class AudioInfo implements Parcelable {
    private int id;
    private String path;
    private String name;
    private String resolution;
    private long size;
    private long date;
    private long duration;

    public AudioInfo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(path);
        dest.writeString(name);
        dest.writeString(resolution);
        dest.writeLong(size);
        dest.writeLong(date);
        dest.writeLong(duration);
    }

    protected AudioInfo(Parcel in) {
        id = in.readInt();
        path = in.readString();
        name = in.readString();
        resolution = in.readString();
        size = in.readLong();
        date = in.readLong();
        duration = in.readLong();
    }

    public static final Creator<AudioInfo> CREATOR = new Creator<AudioInfo>() {
        @Override
        public AudioInfo createFromParcel(Parcel in) {
            return new AudioInfo(in);
        }

        @Override
        public AudioInfo[] newArray(int size) {
            return new AudioInfo[size];
        }
    };

}
