package com.example.xiong.uchat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by xiong on 2016/3/28.
 */
public abstract class HomeTabActivity extends BaseActionBarActivity implements View.OnClickListener {
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

        }
        return super.onKeyDown(keyCode, event);
    }

    private View firstTab;
    private View secondTab;
    private View thirdTab;
    private View[] views;
    private FragmentManager mFragmentManager;
    private List<Fragment> mFragmentList;
    private int mLastShowIndex;
    private ImageView[] imageViews;
    private int[] res;
    private int[] resBlue;

    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        this.setContentView(getLayoutId());
        initView();
        initFragmentPage();

    }

    protected void initView() {
        thirdTab = findViewById(R.id.tab_third);
        firstTab = findViewById(R.id.tab_first);
        secondTab = findViewById(R.id.tab_second);
        views = new View[]{firstTab, secondTab, thirdTab};
        imageViews = new ImageView[3];
        res = new int[]{R.drawable.iconfont_chat, R.drawable.iconfont_friend, R.drawable.dongtai};
        resBlue = new int[]{R.drawable.iconfont_chat_blue, R.drawable.iconfont_friend_blue, R.drawable.dongtai_blue};
        imageViews[0] = (ImageView) findViewById(R.id.img_tab_one);
        imageViews[1] = (ImageView) findViewById(R.id.img_tab_two);
        imageViews[2] = (ImageView) findViewById(R.id.img_tab_three);
        thirdTab.setOnClickListener(this);
        secondTab.setOnClickListener(this);
        firstTab.setOnClickListener(this);
    }

    private void initFragmentPage() {
        mFragmentList = supplyTabs();
        mFragmentManager = getSupportFragmentManager();
        for (Fragment add : mFragmentList) {
            mFragmentManager.beginTransaction().add(R.id.fragment_container, add).commit();
        }
        setTitle("联系人");
        getTvRight().setText("添加");
        mFragmentManager.beginTransaction().hide(mFragmentList.get(0)).commit();
        mFragmentManager.beginTransaction().hide(mFragmentList.get(2)).commit();
        imageViews[1].setImageResource(resBlue[1]);
        mLastShowIndex = 1;
    }

    protected abstract void doInPosition(int currentPosition);


    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.tab_third:
                switchTab(2);
                Log.d("asssss", "sssssssssssssssssss");
                break;
            case R.id.tab_second:
                switchTab(1);
                break;
            case R.id.tab_first:
                switchTab(0);
                break;
        }
    }

    protected void switchTab(int pos) {
        if (pos == mLastShowIndex) {
            return;
        }
        resetImg(pos);
        doInPosition(pos);
        mFragmentManager.beginTransaction().hide(mFragmentList.get(mLastShowIndex)).commit();
        mFragmentManager.beginTransaction().show(mFragmentList.get(pos)).commit();
        mLastShowIndex = pos;
    }

    private void resetImg(int pos) {
        imageViews[mLastShowIndex].setImageResource(res[mLastShowIndex]);
        imageViews[pos].setImageResource(resBlue[pos]);
    }

    protected abstract List<Fragment> supplyTabs();

    protected abstract int getLayoutId();
}
