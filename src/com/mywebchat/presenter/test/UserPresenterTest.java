package com.mywebchat.presenter.test;

import junit.framework.Assert;

import com.mywbchat.model.UIData;
import com.mywbchat.model.UserInfo;
import com.mywebchat.presenter.UserPresenter;

import android.content.Context;
import android.test.InstrumentationTestCase;
import android.widget.ImageView;
import android.widget.TextView;

public class UserPresenterTest extends InstrumentationTestCase {

	private UserPresenter mPresenter;
	private ImageView imgProfile;
	private ImageView imgAvatar;
	private TextView tv;

	protected void setUp() throws Exception {	
		super.setUp();
		Context cxt=this.getInstrumentation().getContext();
		imgProfile=new ImageView(cxt);
		imgAvatar =new ImageView(cxt);
		tv=new TextView(cxt);
		mPresenter=new UserPresenter(imgProfile, imgAvatar, tv);
	}

	protected void tearDown() throws Exception {
		mPresenter=null;
		imgProfile=null;
		imgAvatar=null;
		tv=null;
		super.tearDown();
	}

	public void testFillData()
	{
		UIData data=new UIData();
		data.user=new UserInfo();
		data.user.username="UserA";

		mPresenter.FillData(data);
        Assert.assertEquals(tv.getText().toString(), data.user.username);
        
        data.user.username="";
        mPresenter.FillData(data);
        Assert.assertEquals(tv.getText().toString(), data.user.username);
	}
}
