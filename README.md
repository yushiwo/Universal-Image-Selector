## 展示  
<img src="https://github.com/yushiwo/Universal-Image-Selector/blob/master/picture/test.gif?raw=true"/>

## 使用  
* build.gradle配置  

```
compile 'com.netease.imageSelector:android-imageselector-lib:1.0.1'
```
* AndroidManifest配置

```
	// 设置权限
	<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
	<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
	<uses-permission android:name="android.permission.INTERNET"/>
	
	// 注册activity
	<activity
        android:name="com.netease.imageSelector.view.ImageSelectorActivity"
        android:theme="@style/Theme.AppCompat.NoActionBar" />
    <activity
        android:name="com.netease.imageSelector.view.ImagePreviewActivity"
        android:theme="@style/Theme.AppCompat.NoActionBar" />
    <activity
        android:name="com.netease.imageSelector.view.ImageCropActivity"
        android:theme="@style/Theme.AppCompat.NoActionBar" />
```
* 初始化  
在需要使用此组件的Activity的onCreate方法中，或者在自定义Application的onCreate方法中初始化。

```
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // 获取默认配置
        ImageSelectorConfiguration configuration = ImageSelectorConfiguration.createDefault(this);

		  // 自定义图片选择器
//        ImageSelectorConfiguration configuration = new ImageSelectorConfiguration.Builder(this)
//                .setMaxSelectNum(9)
//                .setSpanCount(4)
//                .setSelectMode(ImageSelectorConstant.MODE_MULTIPLE)
//                .setTitleHeight(48)
//                .build();
		  
        ImageSelector.getInstance().init(configuration);
    }
}
```
* 启动组件  
	* 开启图片选择界面  
	
	```
	/**
     * 开启图片选择页面
     * @param activity
     * @param imageList
     */
    public void launchSelector(Activity activity, ArrayList<String> imageList)
	```
	* 开启图片预览界面（带删除功能）  
	
	```
	/**
     * 开启图片可删除选中图片的预览界面
     * @param activity
     * @param imageList
     * @param position
     */
    public void launchDeletePreview(Activity activity, ArrayList<String> imageList, int position)  ImagePreviewActivity.startDeletePreview(activity, imageList, position)
	```
* 处理回调结果  
组件的界面，都是通过startActivityForResult的方式启动，所以，我们只需在自己调起组件的界面，处理返回结果即可。

```
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
            updateUI(newFiles);
        }
    }
}
```

## 参考
GitHUb地址：[https://github.com/ioneday/ImageSelector](https://github.com/ioneday/ImageSelector)