package com.example.xiong.uchat;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.xiong.uchat.adapter.MessageListAdapter;
import com.example.xiong.uchat.bean.MessageListItemBean;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener;

import java.util.ArrayList;

/**
 * Created by xiong on 2016/3/28.
 */
public class MessageFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, OnMenuItemClickListener,
        OnMenuItemLongClickListener {

    private final int REFRESH_COMPLETE = 0x11;
    private View contentView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListView mListView;
    private MessageListAdapter mMessageListAdapter;
    private ArrayList<MessageListItemBean> messageListItemBeanArrayList;

    private RelativeLayout relativeLayoutHeader;

    private DialogFragment mMenuDialogFragment;
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case REFRESH_COMPLETE:
                    mMessageListAdapter.addAll(messageListItemBeanArrayList);
                    mMessageListAdapter.notifyDataSetChanged();
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
        initListener();
    }

    private void init() {
        messageListItemBeanArrayList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            MessageListItemBean messageListItemBean = new MessageListItemBean();
            messageListItemBean.setName("熊嘉琛帅哥___" + i);
            messageListItemBean.setTempContent("吃了没有" + i * 3);
            messageListItemBean.setTime("12:1" + i);
            messageListItemBean.setPath("res:///" + R.drawable.avatar_kin);
            messageListItemBeanArrayList.add(messageListItemBean);
        }

        mMessageListAdapter = new MessageListAdapter(getContext(), R.layout.message_list_item);
        mMessageListAdapter.addAll(messageListItemBeanArrayList);

        relativeLayoutHeader = (RelativeLayout) LayoutInflater.from(getContext()).inflate(R.layout.search_input, null);
        mSwipeRefreshLayout = (SwipeRefreshLayout) contentView.findViewById(R.id.swipe_message);
        mListView = (ListView) contentView.findViewById(R.id.listview_message);
        mListView.setFadingEdgeLength(0);
        mListView.addHeaderView(relativeLayoutHeader);
        mListView.setAdapter(mMessageListAdapter);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
    }

    private void initListener() {
        relativeLayoutHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "搜索", Toast.LENGTH_SHORT).show();
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(), i + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static android.support.v4.app.Fragment newInstance() {
        return new MessageFragment();
    }

    @Override
    public void onRefresh() {
        mHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 2500);
    }

    @Override
    public void onMenuItemClick(View clickedView, int position) {

    }

    @Override
    public void onMenuItemLongClick(View clickedView, int position) {

    }
}
