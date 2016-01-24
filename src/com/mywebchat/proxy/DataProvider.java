package com.mywebchat.proxy;

import com.mywbchat.model.Tweet;
import com.mywbchat.model.UserInfo;

/**
 * 
 * 数据获取接口
 *
 */
public class DataProvider implements IDataProvider {

	private IFilter filter;
	private JsonConvertor cvt;

	public DataProvider(IFilter f)
	{
		filter=f;		
	}

	/**
	 * 获取用户信息
	 */
	@Override
	public UserInfo GetUser() {
		UserInfo user=null;
		//获取json内容并替换掉非法变量名
		String json=NetUtility.GetJsonData(Settings.UserURL);
		json=filter.FilterJSON(json);
		
		//将json转换为实体对象
		if(!Utility.IsEmptyString(json))
		{
			user=this.GetConvertor().ConvertFromJson(json, UserInfo.class);			
		}

		return user;
	}

	/**
	 * 获取tweet列表数据
	 */
	@Override
	public Tweet[] GetTweets() {
		Tweet[] list=null;
		//获取json内容并替换掉非法变量名
		String json=NetUtility.GetJsonData(Settings.TweetURL);
		json=filter.FilterJSON(json);
		
		//将json转换为实体对象
		if(!Utility.IsEmptyString(json))
		{
			list=this.GetConvertor().ConvertFromJson(json, Tweet[].class);
		}
		if(list==null)
		{
			list=new Tweet[0];
		}

		//过滤掉非法数据
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
