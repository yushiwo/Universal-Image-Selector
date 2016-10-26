package com.example.zr.universalimageselector;



import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;



/**
 * @author hzzhengrui
 * @Date 16/10/24
 * @Description
 */
public class MyAdapter extends BaseAdapter {

    public interface MyAdapterListener {
        void onAddButtonClick();    //添加按钮点击事件
    }

    private Context context;
    private List<String> images;
    private MyAdapterListener listener;

    private static final int MAX_IMAGE_SIZE = 9;    //默认显示的图片个数
    private static final int DEFAULT_IMAGE_SIZE = 200;  //默认图片压缩大小，宽高相同

    private LruCache<String, Bitmap> imageCache;    //缓存已上传的图片，防止多次上传相同的图片

    public MyAdapter(Context context, List<String> images, MyAdapterListener listener) {
        this.context = context;
        this.images = images;
        this.listener = listener;

        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 8;
        imageCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String path, Bitmap bitmap) {
                return bitmap.getByteCount();
            }
        };
    }

    public List<String> getSelectedImages() {
        return images;
    }

    @Override
    public int getCount() {
        if (images == null) {
            return 1;
        }
        if (images.size() > MAX_IMAGE_SIZE) {
            return MAX_IMAGE_SIZE + 1;
        }
        return images.size() + 1;
    }

    @Override
    public Object getItem(int i) {
        return i < images.size() ? images.get(i) : null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_grid_answer, viewGroup, false);
            holder = new ViewHolder();
            holder.addBtn = (ImageView) view.findViewById(R.id.id_item_answer_add_view);
            holder.img = (ImageView) view.findViewById(R.id.id_item_answer_image_view);
            holder.addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onAddButtonClick();
                }
            });
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        if (i < getCount() - 1) {
            holder.img.setVisibility(View.VISIBLE);
            holder.img.setImageResource(R.drawable.uis_ic_placeholder);
            setImageView(images.get(i), holder.img);

            holder.addBtn.setVisibility(View.GONE);
        } else if (i < getCount() && i < MAX_IMAGE_SIZE) {
            holder.addBtn.setVisibility(View.VISIBLE);
            holder.img.setVisibility(View.GONE);
        } else {
            holder.addBtn.setVisibility(View.GONE);
            holder.img.setVisibility(View.GONE);
        }
        return view;
    }

    static class ViewHolder {
        ImageView addBtn;
        ImageView img;
    }

    private void setImageView(String imagePath, ImageView imageView) {
        Bitmap bitmap = getBitmapFromMemoryCache(imagePath);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
            BitmapWorkerTask task = new BitmapWorkerTask(imageView);
            task.execute(imagePath);
        }
    }

    private Bitmap getBitmapFromMemoryCache(String key) {
        return imageCache.get(key);
    }

    private void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemoryCache(key) == null) {
            imageCache.put(key, bitmap);
        }
    }

    class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {

        ImageView imageView;

        BitmapWorkerTask(ImageView imageView) {
            this.imageView = imageView;
        }

        // 在后台加载图片
        @Override
        protected Bitmap doInBackground(String... params) {
            final Bitmap bitmap = ImageUtils.decodeSampledBitmapFromFile(
                    params[0], DEFAULT_IMAGE_SIZE, DEFAULT_IMAGE_SIZE);
            addBitmapToMemoryCache(String.valueOf(params[0]), bitmap);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imageView.setImageBitmap(bitmap);
        }
    }


}

