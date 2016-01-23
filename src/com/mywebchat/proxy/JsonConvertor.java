package com.mywebchat.proxy;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonConvertor {
	private Gson mGson;

	public JsonConvertor() {
		mGson = new GsonBuilder().setExclusionStrategies(
				new ExclusionStrategy() {
					@Override
					public boolean shouldSkipField(FieldAttributes f) {
						// ¹ýÂËµô×Ö¶ÎÃû°üº¬"unknown error","error"µÄ×Ö¶Î
						return f.getName().contains("error")
								| f.getName().contains("unknown error");
					}

					@Override
					public boolean shouldSkipClass(Class<?> clazz) {
						return false;
					}
				}).create();
	}

	public <T> T ConvertFromJson(String json, Class<T> cls) {
		return mGson.fromJson(json, cls);
	}
}
