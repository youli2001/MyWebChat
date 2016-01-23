package com.mywbchat.model;

public class TweetBase {

	public String content;
	public UserInfo sender;

	public TweetBase() {
		super();
	}

	public boolean IsEmpty() {
		return sender==null;
	}

}