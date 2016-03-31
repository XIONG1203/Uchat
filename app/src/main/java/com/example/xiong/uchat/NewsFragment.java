package com.example.xiong.uchat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created by xiong on 2016/3/28.
 */
public class NewsFragment extends Fragment {
    private View contentView;

    private RelativeLayout relativeLayoutHaoyoudongtai;
    private RelativeLayout relativeLayoutFujin;
    private RelativeLayout relativeLayoutXingqubuluo;

    private MyClickListener myClickListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.contentView = inflater.inflate(R.layout.news_fragment, null);
        this.contentView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        return contentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initview();
    }


    private void initview() {
        myClickListener = new MyClickListener();
        relativeLayoutHaoyoudongtai = (RelativeLayout) contentView.findViewById(R.id.layout_news_haoyoudongtai);
        relativeLayoutFujin = (RelativeLayout) contentView.findViewById(R.id.layout_news_fujin);
        relativeLayoutXingqubuluo = (RelativeLayout) contentView.findViewById(R.id.layout_news_xingqubuluo);
        relativeLayoutHaoyoudongtai.setOnClickListener(myClickListener);
        relativeLayoutFujin.setOnClickListener(myClickListener);
        relativeLayoutXingqubuluo.setOnClickListener(myClickListener);
    }


    class MyClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.layout_news_haoyoudongtai:
                    Toast.makeText(getContext(), "sssssssssss", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
