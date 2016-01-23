package com.mywebchat.proxy;

import java.util.HashMap;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class ImageLoader {

	private static HashMap<String, Drawable> _imageCache;
	public static final int IamgeCacheSize = 100;

	public static Drawable Download(String path) {
		if (path == null || path.length() == 0)
			return null;

		Drawable d;
		if (_imageCache != null && _imageCache.containsKey(path)) {
			d = _imageCache.get(path);
		} else {
			d = WebHelper.DownloadImage(path);
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

	public static void AsyncLoadImage(ImageView img, String path) {
		if (path == null || path.length() == 0) {
			img.setImageDrawable(null);
		} else {
			// 异步加载图片资源
			AsyncTaskImageLoader async = new AsyncTaskImageLoader(img);
			// 执行异步加载，并把图片的路径传送过去
			async.execute(path);
		}
	}

}
