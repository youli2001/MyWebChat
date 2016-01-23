package com.mywebchat.proxy;

import java.util.ArrayList;

import com.mywbchat.model.Comment;
import com.mywbchat.model.Tweet;

public class DataFilter implements IFilter {

	@Override
	public String FilterJSON(String data) {
		return data.replace("profile-image", "profile");
	}

	@Override
	public Tweet[] FilterTweet(Tweet[] data) {	
		Tweet[] ret=new Tweet[0];		
		if(data!=null && data.length>0)
		{
			ArrayList<Tweet> list=new ArrayList<Tweet>();
			for(int index=0;index<data.length;index++)
			{
				if(data[index]!=null && !data[index].IsEmpty())
				{
					data[index].comments=filterComment(data[index]);
					list.add(data[index]);
				}
			}
			ret=new Tweet[list.size()];
			ret= list.toArray(ret);
		}

		return ret;
	}

	private Comment[] filterComment(Tweet data)
	{
		if(data.comments==null || data.comments.length==0) return data.comments;
		
		ArrayList<Comment> list=new ArrayList<Comment>();
		for(int index=0;index<data.comments.length;index++)
		{
			if(!data.comments[index].IsEmpty())
			{
				list.add(data.comments[index]);
			}
		}
		Comment[] ret=new Comment[list.size()];
		ret= list.toArray(ret);

		return ret;
	}

}
