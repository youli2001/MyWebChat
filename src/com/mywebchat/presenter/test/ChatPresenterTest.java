package com.mywebchat.presenter.test;

import java.util.ArrayList;

import junit.framework.Assert;

import com.mywbchat.model.Comment;
import com.mywbchat.model.ImagePath;
import com.mywbchat.model.Tweet;
import com.mywbchat.model.UIData;
import com.mywbchat.model.UserInfo;
import com.mywebchat.presenter.ChatPresenter;
import com.mywebchat.presenter.IPresenter;
import android.test.InstrumentationTestCase;

public class ChatPresenterTest extends InstrumentationTestCase {

	private ChatPresenter mPresenter;
	private DataProviderStub mProvider;
	
	protected void setUp() throws Exception {
		super.setUp();
		
		mProvider=new DataProviderStub();
		mPresenter=new ChatPresenter(new IPresenter[]{},mProvider );
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		mProvider=null;
		mPresenter=null;
	}
	
	public void testFillData()
	{
		mPresenter.FillData(null);
		try {
			Thread.sleep(2*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		UIData data=mPresenter.GetUIData();
		Assert.assertTrue(data!=null && data.user!=null && data.tweets!=null);
		
		mProvider.mUser=new UserInfo();
		mProvider.mUser.username="UserA";
		mProvider.tweets=CreateTweets(2);
		mPresenter.FillData(null);
		
		try {
			Thread.sleep(2*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		data=mPresenter.GetUIData();
		Assert.assertTrue(data!=null && data.user!=null && data.user.username=="UserA" 
				&& data.tweets!=null && data.tweets.length==2);
	}
	
	private Tweet[] CreateTweets(int size) {
		Tweet[] ret = new Tweet[size];

		Tweet t;
		ArrayList<Tweet> list = new ArrayList<Tweet>();
		for (int index = 0; index < size; index++) {
			t = new Tweet();
			t.content = "Content_" + String.valueOf(index);
			t.images = new ImagePath[] {};
			t.sender = new UserInfo();
			t.sender.username = "User_" + String.valueOf(index);
			t.comments = new Comment[] {};

			list.add(t);
		}

		ret = list.toArray(ret);
		return ret;
	}

}
