package com.example.xiong.uchat.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Scroller;

import com.example.xiong.uchat.R;

/**
 * Created by xiong on 2016/4/12.
 * <p/>
 * 实现一个listview  可以滑动删除item    以后添加滑动的时候有不同的布局
 */
public class DeleteListView extends ListView {


    private boolean isDelete;

    private Context context;

    /**
     * 屏幕宽度
     */
    private int SCREEN_WIDTH;


    private Scroller scroller;

    /**
     * 最小滑动距离
     */
    private int minDelta;

    /**
     * 手指按下时的坐标
     */
    private int mDownX;

    private int mDownY;

    /**
     * 手指按下时所对应的item位置
     */
    private int itemPosition;

    /**
     * 是否item可以滑动
     */

    private boolean isSlide;


    private static int S_VELOCITY = 1200;


    /**
     * 速度跟踪器
     */

    private VelocityTracker velocityTracker;

    /**
     * 得到滑动的item
     */
    private View itemview;


    private String TAG = "XIONG";


    //监听滑动

    private SubViewClickListener subViewClickListener;

    //滑动方向
    int removeDirection;

    private final int TO_LEFT = 0;
    private final int TO_RITHG = 1;

    //删除  宽度

    int d_width = 0;

    public DeleteListView(Context context) {
        this(context, null);
    }

    public DeleteListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DeleteListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        scroller = new Scroller(context);
        SCREEN_WIDTH = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
        minDelta = ViewConfiguration.get(getContext()).getScaledTouchSlop();

        if (getCount() > 0) {
            d_width = getChildAt(0).findViewById(R.id.deledt_item).getWidth();
        }
    }

    /**
     * 通过dispatchTouchEvent 得到一个 速度跟踪器
     */


    private void addVelocityTracker(MotionEvent event) {
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }

        velocityTracker.addMovement(event);
    }

    /**
     * 得到滑动的加速度
     */

    private int getVelocity() {
        velocityTracker.computeCurrentVelocity(1000);
        return (int) velocityTracker.getXVelocity();
    }

    private void clearVelocityTracker() {
        if (velocityTracker != null) {
            velocityTracker.recycle();
            velocityTracker = null;
        }
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                isSlide = false;
                addVelocityTracker(event);

                //如果listview滑动还没有结束的时候    直接返回让listview处理滑动事件
                if (!scroller.isFinished()) {
                    return super.dispatchTouchEvent(event);
                }

                mDownX = (int) event.getX();
                mDownY = (int) event.getY();

                //通过点击的坐标得到点击的子item的位置
                itemPosition = pointToPosition(mDownX, mDownY);
                Log.d(TAG, "touch  position" + itemPosition);


                if (itemPosition == AdapterView.INVALID_POSITION) {
                    return super.dispatchTouchEvent(event);
                }

                //获得手指滑动是对应的item

                itemview = getChildAt(itemPosition - getFirstVisiblePosition());
                itemview.findViewById(R.id.deledt_item).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subViewClickListener.subClick(itemPosition);
                    }
                });
                break;
            case MotionEvent.ACTION_MOVE:
                float tX = Math.abs(event.getX() - mDownX);
                float tY = Math.abs(event.getY() - mDownY);
                if (((tX > minDelta) && (tY < minDelta) && tX > tY) || getVelocity() > S_VELOCITY) {
                    isSlide = true;
                }


                break;
            case MotionEvent.ACTION_UP:
                clearVelocityTracker();
                break;
        }
        return super.dispatchTouchEvent(event);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        if (isSlide && itemPosition != AdapterView.INVALID_POSITION) {

            // 拦截监听事件
            requestDisallowInterceptTouchEvent(true);

            addVelocityTracker(ev);

            int x = (int) ev.getX();
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_MOVE:
                    MotionEvent cancelEvent = MotionEvent.obtain(ev);
                    cancelEvent.setAction(MotionEvent.ACTION_CANCEL |
                            (ev.getActionIndex() << MotionEvent.ACTION_POINTER_INDEX_SHIFT));
                    onTouchEvent(cancelEvent);
                    int deltaX = mDownX - x;
                    mDownX = x;


                    // 向左滑动的时候 ， deltax>0  但是如果滑动的速度比较快，会造成删除部分太过向左
                    //   所以 当deltax超出范围时，控制到能达到的最大距离
                    if (deltaX > 0) {
                        if (Math.abs(itemview.getScrollX()) < d_width - 40) {
                            if (itemview.getScrollX() + deltaX > 400) {
                                deltaX = 400 - itemview.getScrollX();
                            }
                            removeDirection = TO_LEFT;
                            itemview.scrollBy(deltaX, 0);
                        }
                    } else {
                        if (itemview.getScrollX() > 0) {

                            removeDirection = TO_RITHG;
                            itemview.scrollBy(deltaX, 0);
                        }
                    }


                    return true;
                case MotionEvent.ACTION_UP:

                    // 当手指滑动距离短但是速度比较快时   ，判断向左还是向右

                    int v = getVelocity();
                    if (v > S_VELOCITY) {
                        scrollToLeft();
                    } else if (v < -S_VELOCITY) {
                        scrollToRight(itemview);
                    } else {
                        scrollByDistanceX();
                    }

            }
        }
        return super.onTouchEvent(ev);
    }

    private void scrollToRight(View view) {

        int delta = view.getScrollX();

        // 调用startScroll方法来设置一些滚动的参数，我们在computeScroll()方法中调用scrollTo来滚动item
        scroller.startScroll(view.getScrollX(), 0, -delta, 0,
                Math.abs(delta));
        postInvalidate();
    }

    private void scrollToLeft() {

        int delta = (d_width - itemview.getScrollX());


        // 调用startScroll方法来设置一些滚动的参数，我们在computeScroll()方法中调用scrollTo来滚动item
        scroller.startScroll(itemview.getScrollX(), 0, delta, 0,
                Math.abs(delta));
        postInvalidate();
    }

    //通过手指滑动的距离来判断是否要自动滑行

    private void scrollByDistanceX() {

        if (itemview.getScrollX() >= d_width / 4 && removeDirection == TO_LEFT) {
            scrollToLeft();
            isDelete = true;
        } else if ((itemview.getScrollX() <= d_width * 2 / 3) && removeDirection == TO_RITHG) {
            scrollToRight(itemview);
        } else {
            isDelete = false;
            int dx = itemview.getScrollX();
            scroller.startScroll(itemview.getScrollX(), 0, -dx, 0,
                    Math.abs(dx));
        }
    }


    @Override
    public void computeScroll() {
        // 调用startScroll的时候scroller.computeScrollOffset()返回true，
        if (scroller.computeScrollOffset()) {
            // 让ListView item根据当前的滚动偏移量进行滚动
            itemview.scrollTo(scroller.getCurrX(), scroller.getCurrY());
            postInvalidate();
        }
    }

    public void setSubViewClickListener(SubViewClickListener subViewClickListener) {
        this.subViewClickListener = subViewClickListener;
    }

    public interface SubViewClickListener {
        void subClick(int position);
    }
}

