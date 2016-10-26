package com.netease.imageSelector.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.netease.imageSelector.model.LocalMedia;
import com.netease.imageSelector.view.ImagePreviewFragment;

import java.util.ArrayList;

/**
 * @author hzzhengrui
 * @Date 16/10/17
 * @Description
 */
public class SimplePreviewPagerAdapter extends FragmentStatePagerAdapter {

    ArrayList<LocalMedia> mDatas;

    public SimplePreviewPagerAdapter(FragmentManager fm, ArrayList<LocalMedia> mDatas) {
        super(fm);

        this.mDatas = mDatas;
    }

    @Override
    public Fragment getItem(int position) {
        return ImagePreviewFragment.getInstance(mDatas.get(position).getPath());
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }
}
