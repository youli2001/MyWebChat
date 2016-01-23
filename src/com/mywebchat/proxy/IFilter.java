package com.mywebchat.proxy;

import com.mywbchat.model.Tweet;

public interface IFilter {
	public String FilterJSON(String data);
	public Tweet[] FilterTweet(Tweet[] data);
}
