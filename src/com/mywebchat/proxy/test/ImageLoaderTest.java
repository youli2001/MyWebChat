package com.mywebchat.proxy.test;

import junit.framework.Assert;
import com.mywebchat.proxy.ImageLoader;
import android.graphics.drawable.Drawable;
import android.test.InstrumentationTestCase;

public class ImageLoaderTest extends InstrumentationTestCase {

	public void testSyncLoadImage()
	{
		Drawable d=ImageLoader.Download("");
		Assert.assertNull(d);
		d=ImageLoader.Download("http://info.thoughtworks.com/rs/thoughtworks2/images/glyph_badge.png");
		Assert.assertNotNull(d);
	}	
}
