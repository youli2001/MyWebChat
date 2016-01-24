package com.mywebchat.proxy.test;

import com.mywbchat.model.Tweet;
import com.mywbchat.model.UserInfo;
import com.mywebchat.proxy.DataFilter;
import com.mywebchat.proxy.DataProvider;
import com.mywebchat.proxy.IDataProvider;
import com.mywebchat.proxy.IFilter;

import junit.framework.Assert;
import junit.framework.TestCase;

public class DataProviderTest extends TestCase {

	private IDataProvider mProvider;

	protected void setUp() throws Exception {
		IFilter filter=new DataFilter();
		mProvider=new DataProvider(filter);
		super.setUp();
	}

	protected void tearDown() throws Exception {
		mProvider=null;
		super.tearDown();
	}

	public void testGetUser()
	{
		UserInfo u=mProvider.GetUser();
		Assert.assertNotNull(u);
	}

	public void testGetTweet()
	{
		Tweet[] tlist=mProvider.GetTweets();
		Assert.assertNotNull(tlist);
		Assert.assertTrue(tlist!=null && tlist.length>0);
	}
}
