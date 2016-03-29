package com.example.xiong.uchat.base;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xiong.uchat.R;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * actionbar 主题样式设置
 * Created by dreamfly_lzw on 2015/12/23.
 */
public class BaseActionBarActivity extends FragmentActivity {

    private ViewGroup parent;
    private RelativeLayout basetitle;

    private TextView title;

    private RelativeLayout relativeLayoutRightText;
    protected TextView tvRight;

    private RelativeLayout relativeLayoutLeftArea;
    private SimpleDraweeView imgGoBack;
    private RelativeLayout relativeLayoutRightImg;
    private ImageView imgRightIcon;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.action_bar_layout);
        parent = (ViewGroup) findViewById(R.id.title_activity_parent);
        basetitle = (RelativeLayout) findViewById(R.id.base_title);
        basetitle.setBackgroundResource(R.color.qq_blue);
        title = (TextView) findViewById(R.id.textview_actionbar_title);
        relativeLayoutLeftArea = (RelativeLayout) findViewById(R.id.action_bar_relativelayout_left_area);
        imgGoBack = (SimpleDraweeView) findViewById(R.id.img_actionbar_goback);
        relativeLayoutRightImg = (RelativeLayout) findViewById(R.id.action_bar_relativelayout_right_area_image);
        imgRightIcon = (ImageView) findViewById(R.id.img_actionbar_righticon);
        relativeLayoutRightText = (RelativeLayout) findViewById(R.id.action_bar_relativelayout_right_area_text);
        tvRight = (TextView) findViewById(R.id.textview_actionbar_righticon);
        relativeLayoutLeftArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgGoBackAction();
            }
        });
        int resIdTextColor = R.color.font_white;
        setTitleTextColor(resIdTextColor);
        imgGoBack.setVisibility(View.VISIBLE);
        imgRightIcon.setVisibility(View.INVISIBLE);

        //检测网络状态
        if (isNetworkAvailable(BaseActionBarActivity.this)) {
//            Toast.makeText(getApplicationContext(), "当前有可用网络！", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "当前网络不可用，请检查网络设置", Toast.LENGTH_LONG).show();
        }
    }

    public void setActionBarVisibility(int visibility) {
        basetitle.setVisibility(visibility);
    }

    public void setActionbarColor(int color) {
        basetitle.setBackgroundColor(getResources().getColor(color));
    }

    public void setContentView(int layoutResID) {
        parent.addView(LayoutInflater.from(this).inflate(layoutResID, null));
    }

    /**
     * 左边标题的事件
     */
    protected void imgGoBackAction() {
        finish();
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
        }
        return false;
    }


    protected void setImgLeftIcon(int resId) {
        this.relativeLayoutLeftArea.setVisibility(View.VISIBLE);
        this.imgGoBack.setVisibility(View.VISIBLE);
        this.imgGoBack.setImageResource(resId);
    }


    /**
     * 设置actionbar的主标题
     *
     * @param title string
     */
    public void setTitle(String title) {
        this.title.setText(title);
    }

    /**
     * 设置actionbar的主标题 string
     *
     * @param resId int
     */
    public void setTitle(int resId) {
        this.title.setText(resId);
    }

    protected RelativeLayout getRight() {
        return relativeLayoutRightText;
    }

    protected TextView getTvRight() {
        return tvRight;
    }


    /**
     * 设置左边的图标的背景id
     *
     * @param resId
     */
    protected void setImgLeftBackGround(int resId) {
        this.imgGoBack.setVisibility(View.VISIBLE);
        this.imgGoBack.setBackgroundResource(resId);
    }


    protected void setImgRightIcon(int resId) {
        this.imgRightIcon.setVisibility(View.VISIBLE);
        this.relativeLayoutRightImg.setVisibility(View.VISIBLE);
        this.tvRight.setVisibility(View.GONE);
        this.relativeLayoutRightText.setVisibility(View.GONE);
        this.imgRightIcon.setImageResource(resId);
    }

    protected void setTextRight(int resId) {
        this.imgRightIcon.setVisibility(View.GONE);
        this.relativeLayoutRightImg.setVisibility(View.GONE);
        this.tvRight.setVisibility(View.VISIBLE);
        this.relativeLayoutRightText.setVisibility(View.VISIBLE);
        this.tvRight.setText(resId);
    }

    protected void hideRightText() {
        this.tvRight.setVisibility(View.GONE);
        this.relativeLayoutRightText.setVisibility(View.GONE);
    }

    protected void setTextRight(String resId) {
        this.imgRightIcon.setVisibility(View.GONE);
        this.relativeLayoutRightImg.setVisibility(View.GONE);
        this.tvRight.setVisibility(View.VISIBLE);
        this.relativeLayoutRightText.setVisibility(View.VISIBLE);
        this.tvRight.setText(resId);
    }

    public SimpleDraweeView getImgGoBack() {
        return imgGoBack;
    }

    protected void setRightTextColor(int resId) {
        this.tvRight.setTextColor(this.getResources().getColor(resId));
    }

    protected void setImgRightBackGround(int resId) {
        this.imgGoBack.setVisibility(View.VISIBLE);
        this.imgRightIcon.setBackgroundResource(resId);
    }


    protected void setTitleTextColor(int resId) {
        this.title.setTextColor(this.getResources().getColor(resId));
    }


    protected void hideLeftIcon() {
        this.imgGoBack.setVisibility(View.GONE);
        this.relativeLayoutLeftArea.setVisibility(View.GONE);
    }


    protected void hideRightIcon() {
        this.imgRightIcon.setVisibility(View.GONE);
        this.relativeLayoutRightImg.setVisibility(View.GONE);
    }

    protected RelativeLayout getLeftView() {
        this.relativeLayoutLeftArea.setVisibility(View.VISIBLE);
        return relativeLayoutLeftArea;
    }

    protected RelativeLayout getImgRightIcon() {
        return relativeLayoutRightImg;
    }

    public boolean isNetworkAvailable(Activity activity) {
        Context context = activity.getApplicationContext();
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return false;
        } else {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    System.out.println(i + "===状态===" + networkInfo[i].getState());
                    System.out.println(i + "===类型===" + networkInfo[i].getTypeName());
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
