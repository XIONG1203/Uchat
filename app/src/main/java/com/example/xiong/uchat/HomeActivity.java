package com.example.xiong.uchat;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.support.v4.widget.SwipeRefreshLayout;

import com.bluemor.reddotface.util.Util;
import com.bluemor.reddotface.view.DragLayout;
import com.facebook.drawee.view.SimpleDraweeView;
import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiong on 2016/3/28.
 */
public class HomeActivity extends HomeTabActivity {


    //主界面里面的变量
    private List<Fragment> list;
    private DragLayout dragLayout;
    private SimpleDraweeView imageViewAvatar;
    private SimpleDraweeView dragAvatar;
    private RelativeLayout basetitle;
    private TextView title;

    private RelativeLayout relativeLayoutRightText;
    protected TextView tvRight;
    private RelativeLayout relativeLayoutLeftArea;
    private SimpleDraweeView imgGoBack;
    private RelativeLayout relativeLayoutRightImg;
    private ImageView imgRightIcon;

    private SwipeRefreshLayout swipeRefreshLayout;


    //draglayout 里面的变量

    private ListView listViewDrag;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setActionBarVisibility(View.GONE);
        init();          //初始化主界面数据

        initDrag();    //初始化draglayout里面的数据

    }

    private void init() {


        basetitle = (RelativeLayout) findViewById(R.id.base_title);
        basetitle.setBackgroundResource(R.color.qq_blue);
        title = (TextView) findViewById(R.id.txt_actionbar_title);
        relativeLayoutLeftArea = (RelativeLayout) findViewById(R.id.action_bar_relativelayout_left);
        imageViewAvatar = (SimpleDraweeView) findViewById(R.id.avatar);
        relativeLayoutRightImg = (RelativeLayout) findViewById(R.id.action_bar_relativelayout_right_image);
        imgRightIcon = (ImageView) findViewById(R.id.img_actionbar_righticon);
        relativeLayoutRightText = (RelativeLayout) findViewById(R.id.action_bar_relativelayout_right_text);
        tvRight = (TextView) findViewById(R.id.textview_actionbar_right);
        int resIdTextColor = R.color.font_white;
        setTitleTextColor(resIdTextColor);
        imageViewAvatar.setVisibility(View.VISIBLE);
        imageViewAvatar.setImageURI(Uri.parse("res:///" + R.drawable.avatar));
        imageViewAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dragLayout.open();
            }
        });
        dragLayout = (DragLayout) findViewById(R.id.dl);
        dragLayout.setDragListener(new DragLayout.DragListener() {
            @Override
            public void onOpen() {

            }

            @Override
            public void onClose() {
                shake();
            }

            @Override
            public void onDrag(float percent) {
                ViewHelper.setAlpha(imageViewAvatar, 1 - percent);
            }
        });

        title.setText("联系人");
        tvRight.setText("添加");
    }

    @Override
    protected void doInPosition(int currentPosition) {
        switch (currentPosition) {
            case 0:
                title.setText("消息");
                setRightIcon(R.drawable.message_fragment_position_right);
                break;
            case 1:
                title.setText("联系人");
                setRightText("添加");

                break;
            case 2:
                title.setText("动态");
                setRightText("更多");
                break;
        }
    }

    private void setRightText(String text) {
        tvRight.setVisibility(View.VISIBLE);
        imgRightIcon.setVisibility(View.GONE);
        tvRight.setText(text);
    }

    private void setRightIcon(int res) {
        imgRightIcon.setVisibility(View.VISIBLE);
        tvRight.setVisibility(View.GONE);
        imgRightIcon.setImageResource(res);
    }

    private void initDrag() {
        //初始化draglayout里面的数据

        dragAvatar = (SimpleDraweeView) findViewById(R.id.iv_drag_avatar);
        dragAvatar.setImageURI(Uri.parse("res:///" + R.drawable.avatar));

        listViewDrag = (ListView) findViewById(com.bluemor.reddotface.R.id.lv);
        listViewDrag.setAdapter(new ArrayAdapter<String>(HomeActivity.this,
                com.bluemor.reddotface.R.layout.item_text, new String[]{"我的超级会员", "QQ钱包",
                "个性装扮", "我的收藏", "我的相册", "我的文件",
        }));
        listViewDrag.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                Util.t(getApplicationContext(), "click " + position);
            }
        });
    }

    @Override
    protected List<Fragment> supplyTabs() {
        list = new ArrayList<>();
        list.add(MessageFragment.newInstance());
        list.add(new FriendFragment());
        list.add(new NewsFragment());
        return list;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    private void shake() {
        imageViewAvatar.startAnimation(AnimationUtils.loadAnimation(this, com.bluemor.reddotface.R.anim.shake));
    }
}
