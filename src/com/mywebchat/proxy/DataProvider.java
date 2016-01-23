package com.mywebchat.proxy;

import com.mywbchat.model.Tweet;
import com.mywbchat.model.UserInfo;

public class DataProvider implements IDataProvider {

	private IFilter filter;
	private JsonConvertor cvt;

	public DataProvider(IFilter f)
	{
		filter=f;		
	}

	@Override
	public UserInfo GetUser() {
		UserInfo user=null;
		String json=WebHelper.GetJsonData(Settings.UserURL);
		json=filter.FilterJSON(json);
		
		if(!Utility.IsEmptyString(json))
		{
			user=this.GetConvertor().ConvertFromJson(json, UserInfo.class);			
		}

		return user;
	}

	@Override
	public Tweet[] GetTweets() {
		Tweet[] list=null;
		String json=WebHelper.GetJsonData(Settings.TweetURL);
		json=filter.FilterJSON(json);
		
		if(!Utility.IsEmptyString(json))
		{
			list=this.GetConvertor().ConvertFromJson(json, Tweet[].class);
		}
		if(list==null)
		{
			list=new Tweet[0];
		}

		return filter.FilterTweet(list);
	}

	private JsonConvertor GetConvertor()
	{
		if(cvt==null)
		{
			cvt=new JsonConvertor();
		}
		return cvt;
	}

}
