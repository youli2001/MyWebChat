package com.mywebchat.presenter;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnTouchListener;
import android.widget.ScrollView;

/**
 * 
 * 画面上下滑动的处理器
 *
 */
public class PullUpDownView implements OnTouchListener {

	/**
	 * 触发数据刷新的临界值
	 */
	public static final int PullUp_Distance = 30;
	public static final int PullDown_Distance = 5;

	/**
	 * 回调接口
	 */
	private PullUpDOwnListener mListener;

	private ScrollView msv;
	private GestureDetector mDector;

	public PullUpDownView(ScrollView sv)
	{
		msv=sv;	
		this.InitializeGesture(sv);
		//处理scrollview的触屏事件
		sv.setOnTouchListener(this);
	}

	private void InitializeGesture(ScrollView sv) {
		mDector = new GestureDetector(sv.getContext(), new SimpleOnGestureListener() {
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2,
					float velocityX, float velocityY) {

				if (velocityY >= PullUp_Distance && !isAbleToScroll(-1)) {
					//向下滑动且不能再向下滚动时，重新加载数据
					mListener.onRefresh();
					return true;
				}
				else if(velocityY<=-PullDown_Distance && !isAbleToScroll(1)){	
					//向上滑且不能再向上滚动时，加载更多数据
					mListener.fillMoreItem();
					return true;
				}
				return false;
			}
		});
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return mDector.onTouchEvent(event);
	}

	public void setOnRefreshListener(PullUpDOwnListener listener) {
		mListener = listener;
	}

	/**
	 * 
	 * 判断是否还能够向指定方向滚动
	 */
	private boolean isAbleToScroll(int direction) {
		return msv.canScrollVertically(direction);
	}

	public interface PullUpDOwnListener {
		void onRefresh();
		void fillMoreItem();
	}

}
