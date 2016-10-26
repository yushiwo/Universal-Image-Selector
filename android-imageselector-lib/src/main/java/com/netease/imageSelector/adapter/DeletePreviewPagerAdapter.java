package com.netease.imageSelector.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.netease.imageSelector.model.LocalMedia;
import com.netease.imageSelector.view.ImagePreviewFragment;

import java.util.ArrayList;

/**
 * @author hzzhengrui
 * @Date 16/10/17
 * @Description
 */
public class DeletePreviewPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Fragment> mFragmentList;

    public DeletePreviewPagerAdapter(FragmentManager fm, ArrayList<LocalMedia> mDatas) {
        super(fm);
        updateData(mDatas);
    }

    public void updateData(ArrayList<LocalMedia> dataList) {
        ArrayList<Fragment> fragments = new ArrayList<>();
        for (int i = 0, size = dataList.size(); i < size; i++) {
            Log.e("FPagerAdapter1", dataList.get(i).toString());
            fragments.add(ImagePreviewFragment.getInstance(dataList.get(i).getPath()));
        }
        setFragmentList(fragments);
    }

    private void setFragmentList(ArrayList<Fragment> fragmentList) {
        if(this.mFragmentList != null){
            mFragmentList.clear();
        }
        this.mFragmentList = fragmentList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return this.mFragmentList.size();
    }

    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }
}
