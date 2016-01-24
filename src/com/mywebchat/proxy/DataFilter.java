package com.mywebchat.proxy;

import java.util.ArrayList;

import com.mywbchat.model.Comment;
import com.mywbchat.model.Tweet;

/**
 * 
 * 数据过滤器
 *
 */
public class DataFilter implements IFilter {

	/**
	 * 将JSON数据中不符合java命名规范的字段替换掉
	 */
	@Override
	public String FilterJSON(String data) {
		return data.replace("profile-image", "profile");
	}

	/**
	 * 不合法的tweet数据过滤掉
	 */
	@Override
	public Tweet[] FilterTweet(Tweet[] data) {	
		Tweet[] ret=new Tweet[0];		
		if(data!=null && data.length>0)
		{
			ArrayList<Tweet> list=new ArrayList<Tweet>();
			for(int index=0;index<data.length;index++)
			{
				//只保留非空的数据
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

	/**
	 * 
	 * comment列表中也要过滤掉空
	 */
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
