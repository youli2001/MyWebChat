package com.mywebchat.presenter;

import android.os.Handler;

import com.mywbchat.model.Tweet;
import com.mywbchat.model.UIData;
import com.mywbchat.model.UserInfo;
import com.mywebchat.proxy.IDataProvider;

/**
 * 
 * 画面主体画面封装
 *
 */
public class ChatPresenter implements IPresenter {
	
	//包含的子画面封装
	private IPresenter[] mChildPresenters;
	//数据获取器
	private IDataProvider mProvider;
	//用于异步处理
	private Handler mHandler;
	//暂存画面数据
	private UIData mData;
	//数据异步加载完成标记
	private final int GetData_Finish = 100;
	//进度条操作监听器
	private ProgressListener mProgressListener;
		
	public ChatPresenter(IPresenter[] presenters,IDataProvider provider)
	{
		mChildPresenters=presenters;
		mProvider=provider;
		
		mHandler=new Handler(){
			public void handleMessage(android.os.Message msg) {
				if (msg.what == GetData_Finish) {
					//数据获取完成填充数据
					FillChildData(mData);
				}
			};
		};
	}
	
	/**
	 * 初始化话转调子画面封装的初始化
	 */
	@Override
	public void Initialize() {
		for(int index=0;index<mChildPresenters.length;index++)
		{
			mChildPresenters[index].Initialize();
		}
	}

	/**
	 * 异步数据获取，完成后再通知填充数据
	 */
	@Override
	public void FillData(UIData data) {
		//开始之前显示进度条
		if(mProgressListener!=null)
		{
			mProgressListener.StartProgress();
		}
		
		//异步数据获取
		Runnable r = new Runnable() {
			@Override
			public void run() {
				GetData();
				//数据获取完成，通知主线程填充数据
				mHandler.sendEmptyMessage(GetData_Finish);
			}		
		};

		Thread th = new Thread(r);
		th.start();
	}

	/**
	 * 转调子画面封装的更多数据加载
	 */
	@Override
	public void FillMore() {
		for(int index=0;index<mChildPresenters.length;index++)
		{
			mChildPresenters[index].FillMore();
		}
	}
	
	/**
	 * 
	 * 绑定进度条操作监听器
	 */
	public void SetProgressListener(ProgressListener listener)
	{
		mProgressListener=listener;
	}
	
	/**
	 * 获取数据
	 */
	private void GetData() {
		mData=new UIData();
		mData.user = mProvider.GetUser();
		mData.tweets = mProvider.GetTweets();
		
		//如果获取数据失败，也要设置一个空白实体，以避免空引用
		if(mData.user==null)
		{
			mData.user=new UserInfo();
		}
		if(mData.tweets==null)
		{
			mData.tweets=new Tweet[0];
		}
	}
	
	/**
	 * 
	 * 转调子画面封装的数据填充
	 */
	private void FillChildData(UIData data)
	{		
		for(int index=0;index<mChildPresenters.length;index++)
		{
			mChildPresenters[index].FillData(data);
		}
		
		//画面填充完毕，关闭进度条
		if(mProgressListener!=null)
		{
			mProgressListener.StopProgress();
		}
	}
	
	public interface ProgressListener {
		void StartProgress();
		void StopProgress();
	}
	
}
