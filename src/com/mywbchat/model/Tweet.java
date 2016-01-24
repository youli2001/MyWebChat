package com.mywbchat.model;

import com.mywebchat.proxy.Utility;

/**
 * 
 * Tweet实体
 *
 */
public class Tweet extends TweetBase {
	public Comment[] comments;
	public ImagePath[] images;	

	@Override
	public boolean IsEmpty()
	{
		return super.IsEmpty() || Utility.IsEmptyString(content) && (images==null || images.length==0);
	}
}
