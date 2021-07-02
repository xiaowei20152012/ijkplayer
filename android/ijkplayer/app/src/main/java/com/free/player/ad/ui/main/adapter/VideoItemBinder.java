package com.free.player.ad.ui.main.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.free.player.ad.R;
import com.free.player.ad.model.VideoInfo;
import com.free.player.common.ui.OnClickListener;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import me.drakeet.multitype.ItemViewBinder;

public class VideoItemBinder extends ItemViewBinder<VideoInfo, VideoItemBinder.InnerBinder> {

    private final OnClickListener listener;

    public VideoItemBinder(OnClickListener clickListener) {
        this.listener = clickListener;
    }

    @NonNull
    @Override
    protected InnerBinder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new InnerBinder(inflater.inflate(R.layout.video_item_layout, parent, false), listener);
    }

    @Override
    protected void onBindViewHolder(@NonNull InnerBinder holder, @NonNull VideoInfo item) {
        holder.bind(item);
    }

    public static class InnerBinder extends RecyclerView.ViewHolder {
        private final TextView titleView;
        private VideoInfo item;

        public InnerBinder(@NonNull View itemView, OnClickListener listener) {
            super(itemView);
            titleView = itemView.findViewById(R.id.video_title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(item);
                }
            });
        }

        public void bind(VideoInfo item) {
            this.item = item;
            titleView.setText(item.getName());
        }

    }
}
