package com.mywebchat.presenter;

import com.mywbchat.model.Tweet;
import com.mywebchat.org.R;
import com.mywebchat.proxy.ImageLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * listview数据填充适配
 *
 */
public class TweetAdapter extends BaseAdapter 
{
	private Tweet[] listItems;
	private LayoutInflater listContainer;
    private Context mcxt;
	
	/**
	 *控件缓存器
	 */
	private final class ListItemView{
		public LinearLayout pnl_Image;
		public LinearLayout pnl_Comment;
		public ImageView img_SenderAvatar;
		public TextView txt_SenderName;
		public TextView txt_Content;		
	}

	/**
	 * @param context
	 * @param listItems
	 */
	public TweetAdapter(Context context,Tweet[] listItems)
	{
		mcxt=context;
		listContainer = LayoutInflater.from(context);
		this.listItems = listItems;	
	}
	
	/**
	 * 
	 * 动态加载数据
	 */
	public void SetItems(Tweet[] items)
	{
		listItems=items;
		this.notifyDataSetChanged();
	}

	public int getCount() {		
		return listItems.length;
	}

	public Object getItem(int position) {		
		return listItems[position];
	}

	public long getItemId(int position) {
		return 0;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	public View getView(int position, View convertView, ViewGroup parent)
	{
		//创建一个item的显示控件
		ListItemView lstView = null;
		if(convertView ==null)
		{
			lstView = new ListItemView();
			LinearLayout lp = (LinearLayout) listContainer.inflate(R.layout.tweet_item, null);

			//将所有需要的控件找到并缓存起来，以方便使用
			lstView.img_SenderAvatar=(ImageView) lp.findViewById(R.id.imgSenderAvatar);
			lstView.txt_SenderName=(TextView) lp.findViewById(R.id.txtSenderName);
			lstView.txt_Content=(TextView) lp.findViewById(R.id.txtContent);
			lstView.pnl_Image=(LinearLayout) lp.findViewById(R.id.pnlImage);
			lstView.pnl_Comment=(LinearLayout) lp.findViewById(R.id.pnlComment);
			
			convertView=lp;
			convertView.setTag(lstView);
		}else
		{
			lstView = (ListItemView) convertView.getTag();
		}
			
		//填充明细行数据
		this.FillData(lstView,(Tweet)this.getItem(position));
		return convertView;
	}
	
	/**
	 * 
	 * 填充一条tweet数据
	 */
	private void FillData(ListItemView view,Tweet data)
	{
		//加载基本信息
		view.txt_SenderName.setText(data.sender.username);
		view.txt_Content.setText(data.content);
		ImageLoader.AsyncLoadImage(view.img_SenderAvatar,data.sender.avatar,R.drawable.avatar);
		
		//动态加载图片信息
		view.pnl_Image.removeAllViews();
		if(data.images!=null && data.images.length>0)
		{
			for(int index=0;index<data.images.length;index++)
			{
				ImageView iv=new ImageView(mcxt);
				ImageLoader.AsyncLoadImage(iv, data.images[index].url,0);
				view.pnl_Image.addView(iv,new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.WRAP_CONTENT,
						LinearLayout.LayoutParams.WRAP_CONTENT));
			}
		}
		
		//动态加载评论数据
		view.pnl_Comment.removeAllViews();
		if(data.comments!=null && data.comments.length>0)
		{
			for(int index=0;index<data.comments.length;index++)
			{
				TextView tv=new TextView(mcxt);
				tv.setText(data.comments[index].toString());
				view.pnl_Comment.addView(tv,new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.WRAP_CONTENT,
						LinearLayout.LayoutParams.WRAP_CONTENT));
			}
		}
	}

}

