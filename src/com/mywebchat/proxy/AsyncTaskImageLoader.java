package com.mywebchat.proxy;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;

public class AsyncTaskImageLoader extends AsyncTask<String, Integer, Drawable> {

    private ImageView Image=null;
    
    public AsyncTaskImageLoader(ImageView img) 
    {
        Image=img;
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
            Image.setImageDrawable(result);
        }
        
        super.onPostExecute(result);
    }
}
