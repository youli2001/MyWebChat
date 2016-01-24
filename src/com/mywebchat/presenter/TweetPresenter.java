package com.mywebchat.presenter;

import java.util.ArrayList;

import com.mywbchat.model.Tweet;
import com.mywbchat.model.UIData;

import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 
 * Tweet列表展示逻辑封装
 *
 */
public class TweetPresenter implements IPresenter {

	private Context mcxt;
	//更多加载时显示进度条
	private LinearLayout mLoadLayout;
	//数据承载listview
	private ListView mListView;
	private TweetAdapter mListViewAdapter;
	//listview已经加载的tweet数据
	private Tweet[] mCurrentTweetList = new Tweet[0];
	//全部的tweet数据
	private Tweet[] mAllTweetList = new Tweet[0];

	//一次加载的tweet数
	private final int Page_Size = 5;
	//用于异步处理
	private Handler mHandler;

	public TweetPresenter(Context cxt, ListView lv) {
		mListView = lv;
		mcxt = cxt;
	}

	@Override
	public void Initialize() {	
		mListViewAdapter = new TweetAdapter(mcxt, new Tweet[0]);
		mListView.setAdapter(mListViewAdapter);
		mHandler = new Handler();
	}

	/**
	 * 准备数据加载进度条
	 */
	private void CreateProcessBar() {
		ProgressBar mProgressBar = new ProgressBar(mcxt);
		mProgressBar.setPadding(0, 0, 15, 0);
		TextView mTipContent = new TextView(mcxt);
		mTipContent.setText("加载中...");

		mLoadLayout = new LinearLayout(mcxt);
		mLoadLayout.setMinimumHeight(60);
		mLoadLayout.setGravity(Gravity.CENTER);
		mLoadLayout.setOrientation(LinearLayout.HORIZONTAL);
		mLoadLayout.addView(mProgressBar, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));
		mLoadLayout.addView(mTipContent, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));

		mListView.addFooterView(mLoadLayout);
	}

	private void RemoveProcessBar()
	{
		//数据已经加载完毕，移除进度条
		if(mLoadLayout!=null)
		{
			mListView.removeFooterView(mLoadLayout);
		}
	}

	/**
	 * 第一次默认加载限定的数据
	 */
	@Override
	public void FillData(UIData data) {
		mAllTweetList = data.tweets;

		if (mAllTweetList.length <= Page_Size) {
			this.mCurrentTweetList = mAllTweetList.clone();
		} else {
			mCurrentTweetList = GetSubTweets(Page_Size);
		}

		this.mListViewAdapter.SetItems(mCurrentTweetList);
		this.mListView.setSelection(0);
	}

	@Override
	public void FillMore() {
		if (mCurrentTweetList.length < mAllTweetList.length) {
			CreateProcessBar();

			//还有数据没有加载完，则直接加载
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					mCurrentTweetList = GetSubTweets(mCurrentTweetList.length
							+ Page_Size);
					mListViewAdapter.SetItems(mCurrentTweetList);
					mListView.setSelection(mCurrentTweetList.length);

					RemoveProcessBar();
				}
			});
		} 
	}

	/**
	 * 
	 * 从全部数据中返回指定数据量的分段数据
	 */
	private Tweet[] GetSubTweets(int maxLen) {
		ArrayList<Tweet> list = new ArrayList<Tweet>();
		for (int index = 0; index < maxLen && index < mAllTweetList.length; index++) {
			list.add(mAllTweetList[index]);
		}

		Tweet[] ret = new Tweet[list.size()];
		ret = list.toArray(ret);
		return ret;
	}

}
