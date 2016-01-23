package com.mywebchat.org;

import java.util.ArrayList;

import com.mywbchat.model.Tweet;
import com.mywbchat.model.UserInfo;
import com.mywebchat.presenter.RefreshableView;
import com.mywebchat.presenter.RefreshableView.PullToRefreshListener;
import com.mywebchat.presenter.TweetAdapter;
import com.mywebchat.proxy.DataFilter;
import com.mywebchat.proxy.DataProvider;
import com.mywebchat.proxy.IDataProvider;
import com.mywebchat.proxy.IFilter;
import com.mywebchat.proxy.ImageLoader;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends ListActivity implements
		PullToRefreshListener {
	private LinearLayout mLoadLayout;
	private ListView mListView;
	private TweetAdapter mListViewAdapter;
	private Tweet[] mCurrentTweetList;
	private Tweet[] mAllTweetList = new Tweet[0];
	private UserInfo mUser;
	private int mLastItem = 0;
	private Handler mHandler;
	private IDataProvider mProvider;
	private final int GetData_Finish = 100;
	private final int Page_Size = 5;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_page);

		Initialize();
		GetData();
	}

	private void Initialize() {
		mListViewAdapter = new TweetAdapter(this, new Tweet[0]);
		mListView = (ListView) this.findViewById(android.R.id.list);
		mListView.setAdapter(mListViewAdapter);
		//mListView.setOnScrollListener(this);

		IFilter filter = new DataFilter();
		mProvider = new DataProvider(filter);

		ScrollView sv = (ScrollView) this.findViewById(R.id.scrollContent);
		RefreshableView rv = new RefreshableView(sv);
		rv.setOnRefreshListener(this);

		InitializeHandler();
		InitializeProcessBar();
	}

	private void InitializeHandler() {
		mHandler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				if (msg.what == GetData_Finish) {
					FillData();
				}
			};
		};
	}

	private void InitializeProcessBar() {
		ProgressBar mProgressBar = new ProgressBar(this);
		mProgressBar.setPadding(0, 0, 15, 0);
		TextView mTipContent = new TextView(this);
		mTipContent.setText("加载中...");

		mLoadLayout = new LinearLayout(this);
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

	private void ShowProcess()
	{
		RelativeLayout pnl=	(RelativeLayout) this.findViewById(R.id.pnlProgress);
		ProgressBar pbar=(ProgressBar) this.findViewById(R.id.barProgress);
		pnl.setVisibility(0);
		pnl.bringToFront();
		pbar.setVisibility(0);	
		pbar.setIndeterminate(true);
	}

	private void HideProcess()
	{
		RelativeLayout pnl=	(RelativeLayout) this.findViewById(R.id.pnlProgress);
		ProgressBar pbar=(ProgressBar) this.findViewById(R.id.barProgress);        	
		pnl.setVisibility(4);
		pbar.setVisibility(4);
	}
	
	private void GetData() {
		this.ShowProcess();
		
		Runnable r = new Runnable() {
			@Override
			public void run() {
				mUser = mProvider.GetUser();
				mAllTweetList = mProvider.GetTweets();
				mHandler.sendEmptyMessage(GetData_Finish);
			}
		};

		Thread th = new Thread(r);
		th.start();
	}

	private void FillData() {
		this.FillBasicInfo();
		this.FillDefaultTweets();
		this.HideProcess();
	}

	private void FillBasicInfo() {
		if(mUser==null) return;
		
		TextView txtUserName = (TextView) this.findViewById(R.id.txtUserName);
		ImageView imgProfile = (ImageView) this.findViewById(R.id.imgProfile);
		ImageView imgAvatar = (ImageView) this.findViewById(R.id.imgAvatar);
		txtUserName.setText(this.mUser.username);
		
		ImageLoader.AsyncLoadImage(imgProfile, mUser.profile);
		ImageLoader.AsyncLoadImage(imgAvatar, mUser.avatar);
	}

	private void FillDefaultTweets() {
		if (mAllTweetList.length <= Page_Size) {
			this.mCurrentTweetList = mAllTweetList.clone();
		} else {
			mCurrentTweetList = GetSubTweets(Page_Size);
		}
		this.mListViewAdapter.SetItems(mCurrentTweetList);
		this.mListView.setSelection(0);
	}

	private void FillMoreTweets() {
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				if (mAllTweetList.length > mCurrentTweetList.length) {
					mCurrentTweetList = GetSubTweets(mCurrentTweetList.length
							+ Page_Size);
					mListViewAdapter.SetItems(mCurrentTweetList);
					mListView.setSelection(mLastItem);
				}
			}
		});
	}

	private Tweet[] GetSubTweets(int maxLen) {
		ArrayList<Tweet> list = new ArrayList<Tweet>();
		for (int index = 0; index < maxLen && index < mAllTweetList.length; index++) {
			list.add(mAllTweetList[index]);
		}

		Tweet[] ret = new Tweet[list.size()];
		ret = list.toArray(ret);
		return ret;
	}

	@Override
	public void onRefresh() {
		this.GetData();
	}

	@Override
	public void fillMoreItem() {
		if (mListViewAdapter.getCount() < mAllTweetList.length) {
			FillMoreTweets();
		}
		else
		{
			mListView.removeFooterView(mLoadLayout);
		}
	}
}
