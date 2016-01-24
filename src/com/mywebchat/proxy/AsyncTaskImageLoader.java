package com.mywebchat.proxy;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;

/**
 * 
 * ImageView的异步图片设置任务
 *
 */
public class AsyncTaskImageLoader extends AsyncTask<String, Integer, Drawable> {

	private ImageView Image=null;
	private int mDefaultResource;

	public AsyncTaskImageLoader(ImageView img,int DefaultResource) 
	{
		Image=img;
		mDefaultResource=DefaultResource;
	}

	protected Drawable doInBackground(String... params) {
		try 
		{
			return ImageLoader.Download(params[0]);
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return null;
	}

	protected void onPostExecute(Drawable result)
	{
		if(Image!=null)
		{
			if(result!=null || mDefaultResource<=0)
			{
				Image.setImageDrawable(result);
			}
			else
			{
				Image.setImageResource(mDefaultResource);
			}
		}

		super.onPostExecute(result);
	}
}
