package com.mywbchat.model;

/**
 * 
 * Tweet，Comment的基类
 *
 */
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