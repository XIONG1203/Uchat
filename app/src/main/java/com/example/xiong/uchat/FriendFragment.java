package com.example.xiong.uchat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.xiong.uchat.adapter.PinnedHeaderExpandableAdapter;
import com.example.xiong.uchat.customview.PinnedHeaderExpandableListView;

/**
 * Created by xiong on 2016/3/28.
 */
public class FriendFragment extends Fragment {
    private final int GROUP_NUMBER = 5;
    private View contentView;
    private PinnedHeaderExpandableListView explistview;
    private String[][] childrenData = new String[10][10];
    private String[] groupData;
    private int expandFlag = -1;//控制列表的展开
    private PinnedHeaderExpandableAdapter adapter;

    private FriendFragment.MyClickListener myClickListener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.contentView = inflater.inflate(R.layout.friend_fragment, null);
        this.contentView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        return contentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initview();
        initData();
    }

    private void initview() {
//        myClickListener = new MyClickListener();
        explistview = (PinnedHeaderExpandableListView) contentView.findViewById(R.id.explistview);
    }

    private void initData() {
        groupData = new String[]{"家人", "同学", "朋友"};

        for (int i = 0; i < groupData.length; i++) {
            for (int j = 0; j < 10; j++) {
                childrenData[i][j] = "好友" + j;
            }
        }
        //设置悬浮头部VIEW
        View view = LayoutInflater.from(getContext()).inflate(R.layout.friend_head_view, null);
        explistview.addHeaderView(view);
        view.findViewById(R.id.layout_news_haoyoudongtai).setOnClickListener(myClickListener);
        view.findViewById(R.id.layout_news_fujin).setOnClickListener(myClickListener);
        view.findViewById(R.id.layout_news_xingqubuluo).setOnClickListener(myClickListener);
        view.findViewById(R.id.search_header).setOnClickListener(myClickListener);
        explistview.setHeaderView(View.inflate(getContext(), R.layout.group, null));
        adapter = new PinnedHeaderExpandableAdapter(childrenData, groupData, getContext(), explistview);
        explistview.setAdapter(adapter);
        //Utility.setListViewHeight(explistview);
        explistview.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(getContext(), "分组:" + groupData[groupPosition] + "好友"
                        + childrenData[groupPosition][childPosition], Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    class GroupClickListener implements ExpandableListView.OnGroupClickListener {
        @Override
        public boolean onGroupClick(ExpandableListView parent, View v,
                                    int groupPosition, long id) {
            if (expandFlag == -1) {
                // 展开被选的group
                explistview.expandGroup(groupPosition);
                // 设置被选中的group置于顶端
                explistview.setSelectedGroup(groupPosition);
                expandFlag = groupPosition;
            } else if (expandFlag == groupPosition) {
                explistview.collapseGroup(expandFlag);
                expandFlag = -1;
            } else {
                explistview.collapseGroup(expandFlag);
                // 展开被选的group
                explistview.expandGroup(groupPosition);
                // 设置被选中的group置于顶端
                explistview.setSelectedGroup(groupPosition);
                expandFlag = groupPosition;
            }
            return true;
        }
    }

    class MyClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.layout_news_xingqubuluo:

                    break;
                case R.id.layout_news_fujin:

                    break;
                case R.id.layout_news_haoyoudongtai:

                    break;
                case R.id.search_header:

                    break;

            }
        }
    }


}
