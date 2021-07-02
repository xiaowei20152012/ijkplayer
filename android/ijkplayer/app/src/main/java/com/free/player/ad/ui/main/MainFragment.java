package com.free.player.ad.ui.main;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.free.player.ad.R;
import com.free.player.ad.model.AudioInfo;
import com.free.player.ad.model.VideoInfo;
import com.free.player.ad.player.MusicPlayer;
import com.free.player.ad.ui.main.adapter.AudioItemBinder;
import com.free.player.ad.ui.main.adapter.VideoItemBinder;
import com.free.player.common.ui.BaseFragment;
import com.free.player.common.ui.OnClickListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import me.drakeet.multitype.MultiTypeAdapter;

public class MainFragment extends BaseFragment {

    private MainViewModel viewModel;

    private RecyclerView recyclerView;
    private MultiTypeAdapter adapter;
    private MusicPlayer musicPlayer;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        adapter = new MultiTypeAdapter();
        adapter.register(VideoInfo.class, new VideoItemBinder(videoClickListener));
        adapter.register(AudioInfo.class, new AudioItemBinder(audioClickListener));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        viewModel = new ViewModelProvider(getActivity(), new ViewModelProvider.NewInstanceFactory()).get(MainViewModel.class);
        viewModel.videoLiveData.observe(this, videoObserver);
        viewModel.audioLiveData.observe(this, audioObserver);
        viewModel.onViewCreated();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewModel.videoLiveData.removeObserver(videoObserver);
        viewModel.audioLiveData.removeObserver(audioObserver);
        viewModel.onViewDestroyed();
        if (musicPlayer != null) {
            musicPlayer.release();
        }
    }

    private final Observer<List<VideoInfo>> videoObserver = new Observer<List<VideoInfo>>() {
        @Override
        public void onChanged(List<VideoInfo> videoInfos) {
            adapter.setItems(videoInfos);
            adapter.notifyDataSetChanged();
        }
    };

    private final Observer<List<AudioInfo>> audioObserver = new Observer<List<AudioInfo>>() {
        @Override
        public void onChanged(List<AudioInfo> audioList) {
            adapter.setItems(audioList);
            adapter.notifyDataSetChanged();
        }
    };


    private final OnClickListener<VideoInfo> videoClickListener = new OnClickListener<VideoInfo>() {
        @Override
        public void onClick(VideoInfo item) {
            if (musicPlayer == null) {
                musicPlayer = new MusicPlayer(getActivity());
            }
            musicPlayer.release();
            musicPlayer.start(Uri.parse(item.getPath()));
        }
    };

    private final OnClickListener<AudioInfo> audioClickListener = new OnClickListener<AudioInfo>() {
        @Override
        public void onClick(AudioInfo item) {
            if (musicPlayer == null) {
                musicPlayer = new MusicPlayer(getActivity());
            }
            musicPlayer.release();
            musicPlayer.start(Uri.parse(item.getPath()));
        }
    };

}