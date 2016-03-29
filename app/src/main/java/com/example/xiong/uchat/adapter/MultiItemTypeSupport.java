package com.example.xiong.uchat.adapter;

/**
 * Created by asus on 2016/1/26.
 */
public interface MultiItemTypeSupport<T> {
    int getLayoutId(int position, T t);

    int getViewTypeCount();

    int getItemViewType(int postion, T t);
}
