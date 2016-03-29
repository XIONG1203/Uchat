package com.example.xiong.uchat.adapter;

import android.content.Context;

import com.example.xiong.uchat.R;
import com.example.xiong.uchat.bean.MessageListItemBean;

/**
 * Created by xiong on 2016/3/29.
 */
public class MessageListAdapter extends QuickAdapter<MessageListItemBean> {

    public MessageListAdapter(Context context, int layoutResId) {
        super(context, layoutResId);
    }

    @Override
    protected void convert(BaseAdapterHelper helper, MessageListItemBean item) {
        helper.setText(R.id.txt_content,item.getTempContent());
        helper.setText(R.id.txt_name,item.getName());
        helper.setText(R.id.txt_time,item.getTime());
        helper.setImageUrl(R.id.avatar,item.getPath());
    }
}
