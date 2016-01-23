package com.mywebchat.proxy;

import com.mywbchat.model.Tweet;
import com.mywbchat.model.UserInfo;

public interface IDataProvider {
	public UserInfo GetUser();
	public Tweet[] GetTweets();
}
