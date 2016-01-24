package com.mywebchat.org;

import com.mywebchat.presenter.ChatPresenter;
import com.mywebchat.presenter.ChatPresenter.ProgressListener;
import com.mywebchat.presenter.IPresenter;
import com.mywebchat.presenter.PullUpDownView;
import com.mywebchat.presenter.PullUpDownView.PullUpDOwnListener;
import com.mywebchat.presenter.TweetPresenter;
import com.mywebchat.presenter.UserPresenter;
import com.mywebchat.proxy.DataFilter;
import com.mywebchat.proxy.DataProvider;
import com.mywebchat.proxy.IDataProvider;
import com.mywebchat.proxy.IFilter;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * 
 * Chat moment页面
 *
 */
public class MainActivity extends ListActivity implements PullUpDOwnListener,
ProgressListener {

	private ChatPresenter mChatPrenseter;
	private boolean mDataLoaded=false;
	private boolean mIsLoadding=false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_page);

		Initialize();
		this.onRefresh();
	}

	/**
	 * 构建画面的初始化
	 */
	private void Initialize() {
		//设置presenter
		TextView txtUserName = (TextView) this.findViewById(R.id.txtUserName);
		ImageView imgProfile = (ImageView) this.findViewById(R.id.imgProfile);
		ImageView imgAvatar = (ImageView) this.findViewById(R.id.imgAvatar);
		ListView lv = (ListView) this.findViewById(android.R.id.list);
		IFilter filter = new DataFilter();
		IDataProvider provider = new DataProvider(filter);

		IPresenter userP = new UserPresenter(imgProfile, imgAvatar, txtUserName);
		IPresenter tweetP = new TweetPresenter(this, lv);
		mChatPrenseter = new ChatPresenter(new IPresenter[] { userP, tweetP },
				provider);
		mChatPrenseter.Initialize();
		mChatPrenseter.SetProgressListener(this);

		//监听画面滑动，以确定是否应该刷新或者加载更多数据
		ScrollView sv = (ScrollView) this.findViewById(R.id.scrollContent);
		PullUpDownView rv = new PullUpDownView(sv);
		rv.setOnRefreshListener(this);
	}

	/**
	 * 刷新画面
	 */
	@Override
	public void onRefresh() {
		if(!mIsLoadding)
		{
			mChatPrenseter.FillData(null);
		}
	}

	/**
	 * 上拉填充更多数据
	 */
	@Override
	public void fillMoreItem() {
		if(!mIsLoadding)
		{
			mChatPrenseter.FillMore();
		}
	}

	/**
	 * 显示画面刷新进度
	 */
	@Override
	public void StartProgress() {
		mIsLoadding=true;
		int ProgressPnlID=!mDataLoaded?R.id.pnlCenterProgress:R.id.pnlTopProgress;
		int ProgressBarID=!mDataLoaded?R.id.barCenterProgress:R.id.barTopProgress;

		LinearLayout pnl = (LinearLayout) this.findViewById(ProgressPnlID);
		ProgressBar pbar = (ProgressBar) this.findViewById(ProgressBarID);
		pnl.setVisibility(View.VISIBLE);
		pnl.invalidate();
		pnl.getParent().requestLayout();
		pbar.setIndeterminate(true);
	}

	/**
	 * 停止画面刷新进度条
	 */
	@Override
	public void StopProgress() {
		mIsLoadding=false;
		int ProgressPnlID=!mDataLoaded?R.id.pnlCenterProgress:R.id.pnlTopProgress;

		LinearLayout pnl = (LinearLayout) this.findViewById(ProgressPnlID);
		pnl.setVisibility(View.GONE);

		mDataLoaded=true;
	}
}
