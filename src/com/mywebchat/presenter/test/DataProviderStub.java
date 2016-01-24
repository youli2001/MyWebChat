package com.mywebchat.presenter.test;

import com.mywbchat.model.Tweet;
import com.mywbchat.model.UserInfo;
import com.mywebchat.proxy.IDataProvider;

public class DataProviderStub implements IDataProvider {
    
	public UserInfo mUser;
	public Tweet[] tweets;
	
	@Override
	public UserInfo GetUser() {
		return mUser;
	}

	@Override
	public Tweet[] GetTweets() {
		return tweets;
	}

}
