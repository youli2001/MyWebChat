package com.mywebchat.presenter;

import com.mywbchat.model.UIData;
import com.mywebchat.org.R;
import com.mywebchat.proxy.ImageLoader;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * 用户数据画面封装
 *
 */
public class UserPresenter implements IPresenter {

	private ImageView imgProfile;
	private ImageView imgAvatar;
	private TextView txtUserName;

	public UserPresenter(ImageView profile, ImageView avatar, TextView userName) {
		imgProfile = profile;
		imgAvatar = avatar;
		txtUserName = userName;
	}

	@Override
	public void Initialize() {

	}

	@Override
	public void FillData(UIData data) {
		if (data == null || data.user == null)
			return;

		txtUserName.setText(data.user.username);
		//图片采用异步加载的方式
		ImageLoader.AsyncLoadImage(imgProfile, data.user.profile,0);
		ImageLoader.AsyncLoadImage(imgAvatar, data.user.avatar,R.drawable.avatar);
	}

	@Override
	public void FillMore() {
		//没有更多数据的加载
	}

}
