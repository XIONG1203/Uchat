package com.example.xiong.uchat;

import android.os.Bundle;

import com.example.xiong.uchat.base.BaseActionBarActivity;

/**
 * Created by xiong on 2016/4/2.
 */
public class ChatUIActivity extends BaseActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_ui);
        setTitle("we are here");
    }
}
