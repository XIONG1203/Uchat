package com.example.xiong.uchat;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bluemor.reddotface.util.Util;
import com.bluemor.reddotface.view.DragLayout;
import com.example.xiong.uchat.Util.LogUtil;
import com.example.xiong.uchat.customview.TakePicOrChoosePicDialog;
import com.example.xiong.uchat.data.UserInfoPrefCache;
import com.example.xiong.uchat.data.bean.UserInfoBean;
import com.facebook.drawee.view.SimpleDraweeView;
import com.nineoldandroids.view.ViewHelper;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiong on 2016/3/28.
 */
public class HomeActivity extends HomeTabActivity implements OnMenuItemClickListener,
        OnMenuItemLongClickListener {

    private Uri uri;

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

    private FragmentManager fragmentManager;
    private DialogFragment mMenuDialogFragment;


    //draglayout 里面的变量

    private ListView listViewDrag;
    private TextView textViewMail;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setActionBarVisibility(View.GONE);
        init();          //初始化主界面数据
        initDrag();    //初始化draglayout里面的数据
        fragmentManager = getSupportFragmentManager();
        initMenuFragment();

    }

    private void init() {


        basetitle = (RelativeLayout) findViewById(R.id.base_title);
        basetitle.setBackgroundResource(R.color.qq_blue);
        title = (TextView) findViewById(R.id.txt_actionbar_title);
        relativeLayoutLeftArea = (RelativeLayout) findViewById(R.id.action_bar_relativelayout_left);
        imageViewAvatar = (SimpleDraweeView) findViewById(R.id.avatar);
        relativeLayoutRightImg = (RelativeLayout) findViewById(R.id.action_bar_relativelayout_right_image);
        imgRightIcon = (ImageView) findViewById(R.id.img_actionbar_right);

        relativeLayoutRightText = (RelativeLayout) findViewById(R.id.action_bar_relativelayout_right_text);
        tvRight = (TextView) findViewById(R.id.textview_actionbar_right);
        int resIdTextColor = R.color.font_white;
        setTitleTextColor(resIdTextColor);
        imageViewAvatar.setVisibility(View.VISIBLE);
        imageViewAvatar.setImageURI(Uri.parse(UserInfoPrefCache.getUserInfo(getApplicationContext(), "avatarPath")));
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
                imgRightIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (fragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
                            mMenuDialogFragment.show(fragmentManager, ContextMenuDialogFragment.TAG);
                        }
                    }
                });
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

        textViewMail = (TextView) findViewById(R.id.tv_mail);
        dragAvatar = (SimpleDraweeView) findViewById(R.id.iv_drag_avatar);
        dragAvatar.setImageURI(Uri.parse(UserInfoPrefCache.getUserInfo(getApplicationContext(), "avatarPath")));
        dragAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TakePicOrChoosePicDialog.dialog(HomeActivity.this, HomeActivity.this, false);
            }
        });
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

        textViewMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserInfoPrefCache.clearPersonalInfo(getApplicationContext());
                finish();
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


    private void initMenuFragment() {
        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize((int) getResources().getDimension(R.dimen.action_bar_height));
        menuParams.setMenuObjects(getMenuObjects());
        menuParams.setClosableOutside(false);
        mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
    }

    private List<MenuObject> getMenuObjects() {
        // You can use any [resource, bitmap, drawable, color] as image:
        // item.setResource(...)
        // item.setBitmap(...)
        // item.setDrawable(...)
        // item.setColor(...)
        // You can set image ScaleType:
        // item.setScaleType(ScaleType.FIT_XY)
        // You can use any [resource, drawable, color] as background:
        // item.setBgResource(...)
        // item.setBgDrawable(...)
        // item.setBgColor(...)
        // You can use any [color] as text color:
        // item.setTextColor(...)
        // You can set any [color] as divider color:
        // item.setDividerColor(...)

        List<MenuObject> menuObjects = new ArrayList<>();

        MenuObject close = new MenuObject();
        close.setResource(R.drawable.icn_close);

        MenuObject send = new MenuObject("创建讨论组");
        send.setResource(R.drawable.icn_1);
        send.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        MenuObject addFr = new MenuObject("加好友");
        BitmapDrawable bd = new BitmapDrawable(getResources(),
                BitmapFactory.decodeResource(getResources(), R.drawable.icn_3));
        addFr.setDrawable(bd);

        MenuObject addFav = new MenuObject("我的收藏");
        addFav.setResource(R.drawable.icn_4);

        menuObjects.add(close);
        menuObjects.add(send);
        menuObjects.add(addFr);
        menuObjects.add(addFav);
        return menuObjects;
    }

    @Override
    public void onBackPressed() {
        if (mMenuDialogFragment != null && mMenuDialogFragment.isAdded()) {
            mMenuDialogFragment.dismiss();
        } else {
            finish();
        }
    }

    @Override
    public void onMenuItemClick(View clickedView, int position) {
        //Toast.makeText(this, "Clicked on position: " + position, Toast.LENGTH_SHORT).show();
        showToastMes("Clicked on position: " + position);
    }

    @Override
    public void onMenuItemLongClick(View clickedView, int position) {
        Toast.makeText(this, "Long clicked on position: " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == 1) {
            //paizhao
            if (resultCode == RESULT_OK) {
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(TakePicOrChoosePicDialog.imageUri2, "image/*");
                intent.putExtra("scale", true);
                intent.putExtra("aspectX", 3);
                intent.putExtra("aspectY", 3);
                intent.putExtra("return-data", false);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, TakePicOrChoosePicDialog.imageUri);
                startActivityForResult(intent, 3);
            } else {
//                Toast.makeText(EditPictureActivity.this,"aa",Toast.LENGTH_SHORT).show();
                //finish();
            }
        } else if (requestCode == 2) {
            try {
                uri = data.getData();
            } catch (Exception e) {
                //finish();
                return;
            }
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(uri, "image/*");
            intent.putExtra("scale", true);
            intent.putExtra("aspectX", 3);
            intent.putExtra("aspectY", 3);
            intent.putExtra("return-data", false);

            intent.putExtra(MediaStore.EXTRA_OUTPUT, TakePicOrChoosePicDialog.imageUri);
            startActivityForResult(intent, 3);

        } else if (requestCode == 3) {
            LogUtil.m("设置头像");
            if (data == null) {
                LogUtil.m("data为空");
                return;
            } else {
                LogUtil.m("datano空");
            }
            LogUtil.m("头像数据不空");
            if (TakePicOrChoosePicDialog.imageUri == null) {
                LogUtil.m("imageUrikong");
            } else {
                LogUtil.m("TakePicOrChoosePicDialog.imageUri " + TakePicOrChoosePicDialog.imageUri.toString());
            }
            try {
                Uri uri = Uri.parse(getRealFilePath(TakePicOrChoosePicDialog.imageUri));
                LogUtil.m(TakePicOrChoosePicDialog.imageUri + "=--==");
                LogUtil.m(uri + "=--==");
                dragAvatar.setImageURI(TakePicOrChoosePicDialog.imageUri);
                imageViewAvatar.setImageURI(TakePicOrChoosePicDialog.imageUri);
                UserInfoBean userInfoBean = getDbManager().findById(UserInfoBean.class, UserInfoPrefCache.getUserInfo(getApplicationContext(), "userid"));
                userInfoBean.setAvatarPath(TakePicOrChoosePicDialog.imageUri.toString());
                getDbManager().update(userInfoBean);
            } catch (Exception e) {
                LogUtil.m("有错");
                e.printStackTrace();
            }
        }

    }

    public String getRealFilePath(final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }


}
