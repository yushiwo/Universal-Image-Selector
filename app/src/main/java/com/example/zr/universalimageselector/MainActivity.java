package com.example.zr.universalimageselector;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.netease.imageSelector.ImageSelector;
import com.netease.imageSelector.ImageSelectorConfiguration;
import com.netease.imageSelector.ImageSelectorConstant;

import java.util.ArrayList;

import static com.netease.imageSelector.ImageSelectorConstant.REQUEST_IMAGE;
import static com.netease.imageSelector.ImageSelectorConstant.REQUEST_PREVIEW;
import static com.netease.imageSelector.ImageSelectorConstant.OUTPUT_LIST;

/**
 * author: zhengrui
 * time: 16/10/18
 */
public class MainActivity extends AppCompatActivity implements MyAdapter.MyAdapterListener, View.OnClickListener {

    private final static String TAG = MainActivity.class.getSimpleName();



    private GridView mImagesWall;   //照片墙，显示勾选的照片
    private MyAdapter mAdapter;

    private static ArrayList<String> mSelectedImages;   //已勾选的图片



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSelectedImages = null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        // 接收图片选择器返回结果，更新所选图片集合
        if (requestCode == REQUEST_PREVIEW || requestCode == REQUEST_IMAGE) {
            ArrayList<String> newFiles = data.getStringArrayListExtra(OUTPUT_LIST);
            if (newFiles != null) {
                updateImages(newFiles);
            }
        }
    }

    /**
     * 更新所选图片集合
     *
     * @param images
     */
    private void updateImages(ArrayList<String> images) {
        if (mSelectedImages == null) {
            mSelectedImages = new ArrayList<>();
        }
        mSelectedImages.clear();
        for (String s : images) {
            mSelectedImages.add(s);
        }

        if (mAdapter == null) {
            mAdapter = new MyAdapter(MainActivity.this, mSelectedImages, this);
        }
        mImagesWall.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }



    private void init() {
        initData();
        initView();
    }

    private void initData() {
        mSelectedImages = new ArrayList<>();
    }

    private void initView() {
        mImagesWall = (GridView) findViewById(R.id.id_grid_view_commit_answers);
        mAdapter = new MyAdapter(getApplicationContext(), mSelectedImages, this);
        mImagesWall.setAdapter(mAdapter);
        mImagesWall.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mAdapter.getItem(position) != null) {
                    // 跳转到预览界面
                    ImageSelector.getInstance().launchDeletePreview(MainActivity.this, mSelectedImages, position);
                }
            }
        });


    }

    @Override
    public void onAddButtonClick() {
        //跳转到添加图片界面
        ImageSelector.getInstance().launchSelector(MainActivity.this, mSelectedImages);
    }


    @Override
    public void onClick(View v) {

    }
}

