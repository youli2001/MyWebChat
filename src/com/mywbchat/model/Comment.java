package com.mywbchat.model;

import com.mywebchat.proxy.Utility;

/**
 * 
 * 评论实体
 *
 */
public class Comment extends TweetBase {

	@Override
	public String toString() {
		return this.sender.username + "：" + this.content;
	}
	
	@Override
	public boolean IsEmpty()
	{
		return super.IsEmpty() || Utility.IsEmptyString(content);
	}
}
