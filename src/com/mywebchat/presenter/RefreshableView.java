package com.mywebchat.presenter;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnTouchListener;
import android.widget.ScrollView;

public class RefreshableView implements OnTouchListener {

	/**
	 * 触发数据刷新的临界值
	 */
	public static final int PullUp_Distance = 20;
	public static final int PullDown_Distance = 10;

	/**
	 * 回调接口
	 */
	private PullToRefreshListener mListener;

	private ScrollView msv;
	private GestureDetector mDector;

	public RefreshableView(ScrollView sv)
	{
		msv=sv;	
		mDector = new GestureDetector(sv.getContext(), new SimpleOnGestureListener() {
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2,
					float velocityX, float velocityY) {

				if (velocityY >= PullUp_Distance && !isAbleToScroll(-1)) {
					mListener.onRefresh();
					return true;
				}
				else if(velocityY<=-PullDown_Distance && !isAbleToScroll(1)){				
					mListener.fillMoreItem();
					return true;
				}
				return false;
			}
		});
		
		sv.setOnTouchListener(this);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return mDector.onTouchEvent(event);
	}

	public void setOnRefreshListener(PullToRefreshListener listener) {
		mListener = listener;
	}

	private boolean isAbleToScroll(int direction) {
		//判断是否还能够向上滚动
		return msv.canScrollVertically(direction);
	}

	public interface PullToRefreshListener {
		void onRefresh();
		void fillMoreItem();
	}

}
