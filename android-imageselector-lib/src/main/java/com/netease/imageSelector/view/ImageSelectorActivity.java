package com.netease.imageSelector.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.netease.imageSelector.ImageSelectorProxy;
import com.netease.imageSelector.R;
import com.netease.imageSelector.adapter.ImageFolderAdapter;
import com.netease.imageSelector.adapter.ImageListAdapter;
import com.netease.imageSelector.model.LocalMedia;
import com.netease.imageSelector.model.LocalMediaFolder;
import com.netease.imageSelector.utils.FileUtils;
import com.netease.imageSelector.utils.GridSpacingItemDecoration;
import com.netease.imageSelector.utils.LocalMediaLoader;
import com.netease.imageSelector.utils.ScreenUtils;
import com.netease.imageSelector.utils.StatusBarUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.netease.imageSelector.ImageSelectorConstant.MODE_MULTIPLE;
import static com.netease.imageSelector.ImageSelectorConstant.OUTPUT_LIST;
import static com.netease.imageSelector.ImageSelectorConstant.REQUEST_CAMERA;
import static com.netease.imageSelector.ImageSelectorConstant.REQUEST_IMAGE;
import static com.netease.imageSelector.ImageSelectorConstant.REQUEST_PREVIEW;
import static com.netease.imageSelector.view.ImagePreviewActivity.OUTPUT_ISDONE;

/**
 * Created by dee on 15/11/19.
 */
public class ImageSelectorActivity extends AppCompatActivity {
//    public final static int REQUEST_IMAGE = 66;
//    public final static int REQUEST_CAMERA = 67;

    public final static String BUNDLE_CAMERA_PATH = "CameraPath";

//    public final static String REQUEST_OUTPUT = "outputList";

//    public final static String EXTRA_SELECT_MODE = "SelectMode";
//    public final static String EXTRA_SHOW_CAMERA = "ShowCamera";
//    public final static String EXTRA_ENABLE_PREVIEW = "EnablePreview";
//    public final static String EXTRA_ENABLE_CROP = "EnableCrop";
//    public final static String EXTRA_MAX_SELECT_NUM = "MaxSelectNum";
    public final static String EXTRA_SELECTED_IMAGES = "SelectedImages";

//    public final static int MODE_MULTIPLE = 1;
//    public final static int MODE_SINGLE = 2;

    private int maxSelectNum = 9;
    private int selectMode = MODE_MULTIPLE;
    private boolean showCamera = true;
    private boolean enablePreview = true;
    private boolean enableCrop = false;

    private int spanCount = 4;

    private RelativeLayout mTitleLayout;
    private TextView previewText;
    private ImageButton mBackImageButton;
    private LinearLayout mDoneLayout;
    private TextView mDoneTextView;
    private TextView mDoneNumTextView;

    private RecyclerView recyclerView;
    private ImageListAdapter imageAdapter;

    private RelativeLayout folderLayout;
    private TextView folderName;
    private FolderWindow folderWindow;

    private String cameraPath;


    static ArrayList<LocalMedia> selectedImages;
    // 外部传入的图片列表
    private static ArrayList<String> inputImages = new ArrayList<>();


//    public static void start(Activity activity, int maxSelectNum, int mode, boolean isShow, boolean enablePreview, boolean enableCrop, ArrayList<String> selectedImageList) {
//        Intent intent = new Intent(activity, ImageSelectorActivity.class);
//        intent.putExtra(EXTRA_MAX_SELECT_NUM, maxSelectNum);
//        intent.putExtra(EXTRA_SELECT_MODE, mode);
//        intent.putExtra(EXTRA_SHOW_CAMERA, isShow);
//        intent.putExtra(EXTRA_ENABLE_PREVIEW, enablePreview);
//        intent.putExtra(EXTRA_ENABLE_CROP, enableCrop);
//
//        ArrayList<LocalMedia> sImages = null;
//        if (selectedImageList != null && selectedImageList.size() > 0) {
//            sImages = new ArrayList<>();
//            for (int i = 0; i < selectedImageList.size(); i++) {
//                LocalMedia media = new LocalMedia(selectedImageList.get(i), 0, 0);
//                sImages.add(media);
//            }
//        }
//        intent.putExtra(EXTRA_SELECTED_IMAGES, (ArrayList) sImages);
//        activity.startActivityForResult(intent, REQUEST_IMAGE);
//    }

    public static void start(Activity activity, ArrayList<String> selectedImageList) {
        Intent intent = new Intent(activity, ImageSelectorActivity.class);

        inputImages = selectedImageList;
        ArrayList<LocalMedia> sImages = null;
        if (selectedImageList != null && selectedImageList.size() > 0) {
            sImages = new ArrayList<>();
            for (int i = 0; i < selectedImageList.size(); i++) {
                LocalMedia media = new LocalMedia(selectedImageList.get(i), 0, 0);
                sImages.add(media);
            }
        }
        intent.putExtra(EXTRA_SELECTED_IMAGES, (ArrayList) sImages);
        activity.startActivityForResult(intent, REQUEST_IMAGE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageselector);

        maxSelectNum = ImageSelectorProxy.getInstance().getMaxSelectNum();
        spanCount = ImageSelectorProxy.getInstance().getSpanCount();
        selectMode = ImageSelectorProxy.getInstance().getSelectMode();
        showCamera = ImageSelectorProxy.getInstance().isShowCamera();
        enablePreview = ImageSelectorProxy.getInstance().isEnablePreview();
        enableCrop = ImageSelectorProxy.getInstance().isEnableCorp();

//        maxSelectNum = getIntent().getIntExtra(EXTRA_MAX_SELECT_NUM, 9);
//        selectMode = getIntent().getIntExtra(EXTRA_SELECT_MODE, MODE_MULTIPLE);
//        showCamera = getIntent().getBooleanExtra(EXTRA_SHOW_CAMERA, true);
//        enablePreview = getIntent().getBooleanExtra(EXTRA_ENABLE_PREVIEW, true);
//        enableCrop = getIntent().getBooleanExtra(EXTRA_ENABLE_CROP, false);
        selectedImages = (ArrayList<LocalMedia>) getIntent().getSerializableExtra(EXTRA_SELECTED_IMAGES);

        if (selectMode == MODE_MULTIPLE) {
            enableCrop = false;
        } else {
            enablePreview = false;
        }
        if (savedInstanceState != null) {
            cameraPath = savedInstanceState.getString(BUNDLE_CAMERA_PATH);
        }
        initView();
        registerListener();
        new LocalMediaLoader(this, LocalMediaLoader.TYPE_IMAGE).loadAllImage(new LocalMediaLoader.LocalMediaLoadListener() {

            @Override
            public void loadComplete(List<LocalMediaFolder> folders) {
                folderWindow.bindFolder(folders);
                imageAdapter.bindImages(folders.get(0).getImages());
                if(selectedImages != null && selectedImages.size() > 0){
                    imageAdapter.bindSelectImages(selectedImages);
                }
            }
        });
    }

    public void initView() {
        folderWindow = new FolderWindow(this);


        mTitleLayout = (RelativeLayout)findViewById(R.id.layout_title);
        mBackImageButton = (ImageButton)findViewById(R.id.select_back_btn);
        mDoneLayout = (LinearLayout)findViewById(R.id.done_layout);
        mDoneNumTextView = (TextView)findViewById(R.id.done_num_text);
        mDoneTextView = (TextView)findViewById(R.id.done_text);

        mDoneLayout.setVisibility(selectMode == MODE_MULTIPLE ? View.VISIBLE : View.GONE);

        previewText = (TextView) findViewById(R.id.preview_text);
        if(selectedImages != null && selectedImages.size() > 0){
            previewText.setVisibility(View.VISIBLE);
        }else {
            previewText.setVisibility(View.GONE);
        }
//        previewText.setVisibility((selectedImages.size() > 0) ? View.VISIBLE : View.GONE);

        folderLayout = (RelativeLayout) findViewById(R.id.folder_layout);
        folderName = (TextView) findViewById(R.id.folder_name);

        recyclerView = (RecyclerView) findViewById(R.id.folder_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, ScreenUtils.dip2px(this, 3), false));
        recyclerView.setLayoutManager(new GridLayoutManager(this, spanCount));

        imageAdapter = new ImageListAdapter(this, maxSelectNum, selectMode, showCamera,enablePreview);
        recyclerView.setAdapter(imageAdapter);

        mTitleLayout.setBackgroundColor(ImageSelectorProxy.getInstance().getTitleBarColor(this.getResources()));
        StatusBarUtils.setColor(this, ImageSelectorProxy.getInstance().getStatusBarColor(this.getResources()));
        mTitleLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ScreenUtils.dip2px(ImageSelectorActivity.this, ImageSelectorProxy.getInstance().getTitleHeight())));
    }

    public void registerListener() {
        mBackImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        folderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (folderWindow.isShowing()) {
                    folderWindow.dismiss();
                } else {
                    folderWindow.showAsDropDown(mTitleLayout);
                }
            }
        });
        imageAdapter.setOnImageSelectChangedListener(new ImageListAdapter.OnImageSelectChangedListener() {
            @SuppressLint("StringFormatMatches")
            @Override
            public void onChange(List<LocalMedia> selectImages) {
                boolean enable = selectImages.size() != 0;
                mDoneLayout.setEnabled(enable ? true : false);
                previewText.setEnabled(enable ? true : false);
                mDoneTextView.setEnabled(enable ? true : false);
                previewText.setVisibility(enable ? View.VISIBLE : View.GONE);
                if (enable) {
                    mDoneNumTextView.setVisibility(View.VISIBLE);
                    mDoneNumTextView.setText(String.valueOf(selectImages.size()));
                } else {
                    mDoneNumTextView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTakePhoto() {
                startCamera();
            }

            @Override
            public void onPictureClick(LocalMedia media, int position) {
                if (enablePreview) {
                    startPreview(imageAdapter.getImages(), position, 0);
                } else if (enableCrop) {
                    startCrop(media.getPath());
                } else {
                    onSelectDone(media.getPath());
                }
            }
        });
        mDoneLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectDone(imageAdapter.getSelectedImages());
            }
        });
        folderWindow.setOnItemClickListener(new ImageFolderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String name, List<LocalMedia> images) {
                folderWindow.dismiss();
                imageAdapter.bindImages(images);
                folderName.setText(name);
            }
        });
        previewText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPreview(imageAdapter.getSelectedImages(), 0, 0);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            // on take photo success
            if (requestCode == REQUEST_CAMERA) {
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(cameraPath))));
                if (enableCrop) {
                    startCrop(cameraPath);
                } else {
                    onSelectDone(cameraPath);
                }
            }
            //on preview select change
            else if (requestCode == REQUEST_PREVIEW) {
                boolean isDone = data.getBooleanExtra(OUTPUT_ISDONE, false);
                List<LocalMedia> images = (List<LocalMedia>) data.getSerializableExtra(OUTPUT_LIST);
                if (isDone) {
                    onSelectDone(images);
                }else{
                    imageAdapter.bindSelectImages(images);
                }

            }
            // on crop success
            else if (requestCode == ImageCropActivity.REQUEST_CROP) {
                String path = data.getStringExtra(ImageCropActivity.OUTPUT_PATH);
                onSelectDone(path);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(BUNDLE_CAMERA_PATH, cameraPath);
    }

    /**
     * start to camera、preview、crop
     */
    public void startCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            File cameraFile = FileUtils.createCameraFile(this);
            cameraPath = cameraFile.getAbsolutePath();
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile));
            startActivityForResult(cameraIntent, REQUEST_CAMERA);
        }
    }

    public void startPreview(List<LocalMedia> previewImages, int position, int mode) {
        ImagePreviewActivity.startPreview(this, previewImages, imageAdapter.getSelectedImages(), position, mode);
    }

    public void startCrop(String path) {
        ImageCropActivity.startCrop(this, path);
    }

    /**
     * on select done
     *
     * @param medias
     */
    public void onSelectDone(List<LocalMedia> medias) {
        ArrayList<String> images = new ArrayList<>();
        for (LocalMedia media : medias) {
            images.add(media.getPath());
        }
        onResult(images);
    }

    public void onSelectDone(String path) {
//        ArrayList<String> images = new ArrayList<>();
        inputImages.add(path);
        onResult(inputImages);
    }

    public void onResult(ArrayList<String> images) {
        setResult(RESULT_OK, new Intent().putStringArrayListExtra(OUTPUT_LIST, images));
        finish();
    }
}
