package com.mywebchat.proxy;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class WebHelper {

	public static boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	public static String GetJsonData(String Url)
	{
		String ret="";
		try {
			//创建连接
			URL url = new URL(Url);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setConnectTimeout(5*1000);
			connection.setRequestMethod("GET");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestProperty("Content-Type",
					"application/json");
			connection.connect();

			//读取响应
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String lines;
			StringBuffer sb = new StringBuffer("");
			while ((lines = reader.readLine()) != null) {
				lines = new String(lines.getBytes(), "utf-8");
				sb.append(lines);
			}         
			reader.close();
			ret=sb.toString();
			
			// 断开连接
			connection.disconnect();               
		} catch (Exception e) {		
			e.printStackTrace();
		} 

		return ret;
	}

	public static Drawable DownloadImage(String path) {
		Drawable d=null;
		try
		{
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5*1000);
			conn.setDoInput(true);
			conn.connect();
			InputStream stream = conn.getInputStream();
			d= Drawable.createFromStream(stream, "src");
			stream.close();
		}
		catch(Exception e)
		{
			d=null;
		}
		return d;
	}

}
