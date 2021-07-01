package com.free.player.ad;

import android.os.Bundle;

import com.free.player.ad.ui.main.MainFragment;
import com.free.player.common.ui.BaseAppCompatActivity;

public class MainActivity extends BaseAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }
    }
}