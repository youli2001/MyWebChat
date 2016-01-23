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
	
	public void SetItems(Tweet[] items)
	{
		listItems=items;
		this.notifyDataSetChanged();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	public int getCount() {		
		return listItems.length;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	public Object getItem(int position) {		
		return listItems[position];
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	public long getItemId(int position) {
		Tweet st=listItems[position];

		if(st!=null)
			return 0;
		else
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
				
		this.FillData(lstView,(Tweet)this.getItem(position));
		return convertView;
	}
	
	private void FillData(ListItemView view,Tweet data)
	{
		view.txt_SenderName.setText(data.sender.username);
		view.txt_Content.setText(data.content);
		ImageLoader.AsyncLoadImage(view.img_SenderAvatar,data.sender.avatar);
		view.pnl_Image.removeAllViews();
		view.pnl_Comment.removeAllViews();
				
		if(data.images!=null && data.images.length>0)
		{
			for(int index=0;index<data.images.length;index++)
			{
				ImageView iv=new ImageView(mcxt);
				ImageLoader.AsyncLoadImage(iv, data.images[index].url);
				view.pnl_Image.addView(iv,new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.WRAP_CONTENT,
						LinearLayout.LayoutParams.WRAP_CONTENT));
			}
		}
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

