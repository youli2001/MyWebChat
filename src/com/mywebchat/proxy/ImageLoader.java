package com.mywebchat.proxy;

import java.util.HashMap;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * 
 * 图片加载处理
 *
 */
public class ImageLoader {

	private static HashMap<String, Drawable> _imageCache;
	public static final int IamgeCacheSize = 100;

	/**
	 * 
	 * 带缓存的同步图片获取
	 */
	public static Drawable Download(String path) {
		if (path == null || path.length() == 0)
			return null;

		Drawable d;
		if (_imageCache != null && _imageCache.containsKey(path)) {
			d = _imageCache.get(path);
		} else {
			//通过网络获取图片
			d = WebHelper.DownloadImage(path);
			
			//缓存处理
			if (d != null) {
				if (_imageCache == null) {
					_imageCache = new HashMap<String, Drawable>();
				}
				if (_imageCache.size() > IamgeCacheSize) {
					_imageCache.clear();
				}

				_imageCache.put(path, d);
			}
		}

		return d;
	}

	/**
	 * 图片异步设置
	 * @param img
	 * @param path
	 * @param DefaultResoure 图片加载失败时的默认设置
	 */
	public static void AsyncLoadImage(ImageView img, String path,
			int DefaultResoure) {
		if (path == null || path.length() == 0) {
			if (DefaultResoure > 0) {
				img.setImageResource(DefaultResoure);
			} else {
				img.setImageDrawable(null);
			}
		} else {
			// 异步加载图片资源
			AsyncTaskImageLoader async = new AsyncTaskImageLoader(img,
					DefaultResoure);
			// 执行异步加载，并把图片的路径传送过去
			async.execute(path);
		}
	}

}
