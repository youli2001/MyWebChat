package com.mywebchat.org.test;

import com.mywebchat.org.MainActivity;

import junit.framework.Assert;
import android.content.Intent;
import android.test.InstrumentationTestCase;

public class MainActivityTest extends InstrumentationTestCase {

	public void testActivity() throws Exception {
		Intent intent=new Intent();
		intent.setClassName("com.mywebchat.org", MainActivity.class.getName());
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		try
		{
			//主画面几乎没有逻辑，所以这里只要不异常就可以了
			this.getInstrumentation().startActivitySync(intent);
			Assert.assertTrue(true);
		}
		catch(Exception ex)
		{
			Assert.fail(ex.getMessage());
		}
	}
}
