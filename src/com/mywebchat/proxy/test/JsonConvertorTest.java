package com.mywebchat.proxy.test;

import com.mywbchat.model.Comment;
import com.mywbchat.model.Tweet;
import com.mywbchat.model.UserInfo;
import com.mywebchat.proxy.JsonConvertor;

import junit.framework.Assert;
import junit.framework.TestCase;

public class JsonConvertorTest extends TestCase {

	private JsonConvertor cvt;

	protected void setUp() throws Exception {
		cvt=new JsonConvertor();
		super.setUp();
	}

	protected void tearDown() throws Exception {
		cvt=null;
		super.tearDown();
	}

	public void testJson2Object()
	{
		String json="{\"profile-image\": \"http://img2.findthebest.com/sites/default/files/688/media/images/Mingle_159902_i0.png\",\n" + 
				"\"avatar\": \"http://info.thoughtworks.com/rs/thoughtworks2/images/glyph_badge.png\",\n" + 
				"\"nick\": \"John Smith\",\n" + 
				"\"username\": \"jsmith\"}";
		UserInfo u=cvt.ConvertFromJson(json, UserInfo.class);
		Assert.assertNotNull(u);

		json="{\"content\": \"沙发！\","+
				"\"images\": ["+
				"{"+
				"\"url\": \"https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcRDy7HZaHxn15wWj6pXE4uMKAqHTC_uBgBlIzeeQSj2QaGgUzUmHg\""+
				"}"+
				"],"+
				"\"sender\": {"+
				"\"username\": \"jport\","+
				"\"nick\": \"Joe Portman\","+
				"\"avatar\": \"https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcRJm8UXZ0mYtjv1a48RKkFkdyd4kOWLJB0o_l7GuTS8-q8VF64w\""+
				"},"+
				"\"comments\": ["+
				"{"+
				"\"content\": \"Good.\","+
				"\"sender\": {"+
				"\"username\": \"outman\","+
				"\"nick\": \"Super hero\","+
				"\"avatar\": \"https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcRJm8UXZ0mYtjv1a48RKkFkdyd4kOWLJB0o_l7GuTS8-q8VF64w\""+
				"}"+
				"}"+
				"]"+
				"}";
		Tweet t=cvt.ConvertFromJson(json, Tweet.class);
		Assert.assertNotNull(t);
		Assert.assertNotNull(t.sender);
		Assert.assertTrue(t.comments!=null && t.comments.length>0);
		Assert.assertTrue(t.images!=null && t.images.length>0);

		json="{\"content\": \"Good.\","+
				"\"sender\": {"+
				"\"username\": \"outman\","+
				"\"nick\": \"Super hero\","+
				"\"avatar\": \"https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcRJm8UXZ0mYtjv1a48RKkFkdyd4kOWLJB0o_l7GuTS8-q8VF64w\"}}";

		Comment c=cvt.ConvertFromJson(json, Comment.class);
		Assert.assertNotNull(c);
	}

}
