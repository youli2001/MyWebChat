package com.mywebchat.proxy;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 
 * 基于Gson的JSON转换器
 *
 */
public class JsonConvertor {
	private Gson mGson;

	public JsonConvertor() {
		mGson = new GsonBuilder().setExclusionStrategies(
				new ExclusionStrategy() {
					@Override
					public boolean shouldSkipField(FieldAttributes f) {
						//跳过非法字段
						return f.getName().contains("error")
								| f.getName().contains("unknown error");
					}

					@Override
					public boolean shouldSkipClass(Class<?> clazz) {
						return false;
					}
				}).create();
	}

	/**
	 * 
	 * 将JSON转换为指定类型实体
	 */
	public <T> T ConvertFromJson(String json, Class<T> cls) {
		return mGson.fromJson(json, cls);
	}
}
