package com.example.xiong.uchat.customview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.AbsListView;
import android.widget.ListView;

import java.lang.reflect.Field;

/**
 * Created by xiong on 2016/2/1.
 */
public class BounceListView extends ListView implements AbsListView.OnScrollListener {

    private int paddingLeft = 0;

    private int paddingRight = 0;

    private int paddingTop = 0;

    private int paddingBottom = 0;

    private float lastY = 0;

    private boolean isPull = true;// 是否下拉/上拉//true下拉、false上拉

    private boolean isTop = true;// 是否滚动到第一行

    private boolean isBottom = false;// 是否滚动到最后一行

    private int scrollState = 0;

    private Handler mHandler = new Handler();

    public BounceListView(Context context) {

        super(context);

        init(context);

    }

    public BounceListView(Context context, AttributeSet attrs) {

        super(context, attrs);

        init(context);

    }

    public BounceListView(Context context, AttributeSet attrs, int defStyle) {

        super(context, attrs, defStyle);

        init(context);

    }

    private void init(Context context) {

        try {
            Class<?> c = (Class<?>) Class.forName(AbsListView.class.getName());
            Field egtField = c.getDeclaredField("mEdgeGlowTop");
            Field egbBottom = c.getDeclaredField("mEdgeGlowBottom");
            egtField.setAccessible(true);
            egbBottom.setAccessible(true);
            Object egtObject = egtField.get(this); // this 指的是ListiVew实例
            Object egbObject = egbBottom.get(this);

            // egtObject.getClass() 实际上是一个 EdgeEffect 其中有两个重要属性 mGlow mEdge
            // 并且这两个属性都是Drawable类型
            Class<?> cc = (Class<?>) Class.forName(egtObject.getClass()
                    .getName());
            Field mGlow = cc.getDeclaredField("mGlow");
            mGlow.setAccessible(true);
            mGlow.set(egtObject, new ColorDrawable(Color.TRANSPARENT));
            mGlow.set(egbObject, new ColorDrawable(Color.TRANSPARENT));

            Field mEdge = cc.getDeclaredField("mEdge");
            mEdge.setAccessible(true);
            mEdge.set(egtObject, new ColorDrawable(Color.TRANSPARENT));
            mEdge.set(egbObject, new ColorDrawable(Color.TRANSPARENT));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 初始化padding的值

        paddingLeft = getPaddingLeft();

        paddingRight = getPaddingRight();

        paddingTop = getPaddingTop();

        paddingBottom = getPaddingBottom();

        //

        setOnScrollListener(this);

    }

    @Override

    public boolean onTouchEvent(MotionEvent ev) {

        float y = ev.getY();

        switch (ev.getAction()) {

            case MotionEvent.ACTION_DOWN:

                lastY = y;

                break;

            case MotionEvent.ACTION_MOVE:

                float historicalY = ev.getY();

                int dy = (int) (historicalY-lastY)/3;

                isPull = dy > 0;

                if (isPull) {// 下拉

                    if (isTop && scrollState != SCROLL_STATE_FLING) {

                        dy += paddingTop;

                        setPadding(paddingLeft, dy, paddingRight, paddingBottom);

                        setSelection(0);// 选中第一个item，不然没有下拉效果

                    }

                } else {// 上拉

                    if (isBottom && scrollState != SCROLL_STATE_FLING) {

                        dy -= paddingBottom;

                        setPadding(paddingLeft, paddingTop, paddingRight, -dy);

                        setSelection(getCount());// 选中最后一个item，不然没有上拉效果

                    }

                }

                break;

            case MotionEvent.ACTION_UP:// 回弹

                if (isPull) {

                    int top = getPaddingTop();

                    int duration = 0;

                    while (top > paddingTop) {

                        top -= 10;

                        duration += 10;

                        final int t = top;

                        mHandler.postDelayed(new Runnable() {

                            @Override

                            public void run() {

                                setPadding(paddingLeft, t, paddingRight,

                                        paddingBottom);

                            }

                        }, duration);

                    }

                } else {

                    int bottom = getPaddingBottom();

                    int duration = 0;

                    while (bottom > paddingTop) {

                        bottom -= 10;

                        duration += 10;

                        final int b = bottom;

                        mHandler.postDelayed(new Runnable() {

                            @Override

                            public void run() {

                                setPadding(paddingLeft, paddingTop, paddingRight, b);

                            }

                        }, duration);

                    }

                }

                break;

        }

        return super.onTouchEvent(ev);

    }

    @Override

    public void onScroll(AbsListView lv, int firstVisibleItem,

                         int visibleItemCount, int totalItemCount) {

        isTop = firstVisibleItem == 0;

        isBottom = firstVisibleItem + visibleItemCount == totalItemCount;

    }

    @Override

    public void onScrollStateChanged(AbsListView lv, int scrollState) {

        this.scrollState = scrollState;

    }
}

