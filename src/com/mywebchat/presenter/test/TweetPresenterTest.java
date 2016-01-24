package com.mywebchat.presenter.test;

import java.util.ArrayList;

import junit.framework.Assert;

import com.mywbchat.model.Comment;
import com.mywbchat.model.ImagePath;
import com.mywbchat.model.Tweet;
import com.mywbchat.model.UIData;
import com.mywbchat.model.UserInfo;
import com.mywebchat.presenter.TweetPresenter;

import android.content.Context;
import android.test.InstrumentationTestCase;
import android.widget.ListView;

public class TweetPresenterTest extends InstrumentationTestCase {

	private ListView lv;
	private TweetPresenter mPresenter;

	protected void setUp() throws Exception {
		super.setUp();
		Context cxt = this.getInstrumentation().getContext();
		lv = new ListView(cxt);
		mPresenter = new TweetPresenter(cxt, lv);
	}

	protected void tearDown() throws Exception {
		lv = null;
		mPresenter = null;
		super.tearDown();
	}

	public void TestFillData() {
		UIData data = new UIData();
		data.tweets = new Tweet[0];
		mPresenter.FillData(data);
		Assert.assertEquals(lv.getCount(), 0);

		data.tweets = CreateTweets(5);
		mPresenter.FillData(data);
		Assert.assertEquals(lv.getCount(), 5);
		
		data.tweets = CreateTweets(6);
		mPresenter.FillData(data);
		Assert.assertEquals(lv.getCount(), 5);		
	}
	
	public void TestFillMore()
	{
		UIData data = new UIData();
		data.tweets = CreateTweets(9);
		mPresenter.FillData(data);
		Assert.assertEquals(lv.getCount(), 5);
		
		mPresenter.FillMore();
		Assert.assertEquals(lv.getCount(), 9);
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
