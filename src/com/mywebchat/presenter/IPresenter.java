package com.mywebchat.presenter;

import com.mywbchat.model.UIData;

/**
 * 
 * 画面操作接口
 *
 */
public interface IPresenter {
	//初始化
	public void Initialize();
	//填充数据
    public void FillData(UIData data);
    //填充更多
    public void FillMore();
}
