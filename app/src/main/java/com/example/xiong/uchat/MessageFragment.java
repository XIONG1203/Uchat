package com.example.xiong.uchat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.os.Handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by xiong on 2016/3/28.
 */
public class MessageFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private final int REFRESH_COMPLETE = 0x11;
    private View contentView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListView mListView;
    private List<String> mDatas;
    private ArrayAdapter<String> mAdapter;

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case REFRESH_COMPLETE:
                    mDatas.addAll(Arrays.asList("Lucene", "Canvas", "Bitmap"));
                    mAdapter.notifyDataSetChanged();
                    mSwipeRefreshLayout.setRefreshing(false);
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.contentView = inflater.inflate(R.layout.message_fragment, null);
        this.contentView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        return contentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();

    }

    private void init() {
        mDatas = new ArrayList<>();
        mSwipeRefreshLayout = (SwipeRefreshLayout) contentView.findViewById(R.id.swipe_message);
        mListView = (ListView) contentView.findViewById(R.id.listview_message);
        for (int i = 0; i < 10; i++) {
            mDatas.add("第" + i + "个");
        }
        mAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, mDatas);
        mListView.setAdapter(mAdapter);

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
    }

    public static android.support.v4.app.Fragment newInstance() {
        return new MessageFragment();
    }

    @Override
    public void onRefresh() {
        mHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 2500);
    }
}
