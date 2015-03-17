package com.xwh.anychat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xwh.anychat.R;
import com.xwh.anychat.entity.SearchResultEntity;

import java.util.List;

public class SearchResultListAdapter extends BaseAdapter {

	private Context context;
	private List<SearchResultEntity> data;

	public SearchResultListAdapter(Context context, List<SearchResultEntity> data) {
		super();
		this.context = context;
		this.data = data;
	}

	@Override
	public int getCount() {
		if (data == null) {
			return 0;
		}
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		if (data == null) {
			return null;
		} else {
			return data.get(position);
		}
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView != null && convertView.getTag() != null) {
			holder = (ViewHolder) convertView.getTag();
		} else {
			convertView = LayoutInflater.from(context).inflate(R.layout.item_search_result, null);
			holder = new ViewHolder();
			holder.jidTv = (TextView) convertView.findViewById(R.id.searchresult_jidtv);
			holder.usernameTv = (TextView) convertView.findViewById(R.id.searchresult_nametv);
			convertView.setTag(holder);
		}
		holder.jidTv.setText(data.get(position).getJid());
		holder.jidTv.startAnimation(AnimationUtils.loadAnimation(context, android.R.anim.fade_in));
		holder.usernameTv.setText(data.get(position).getUsername());
		holder.usernameTv.startAnimation(AnimationUtils.loadAnimation(context, android.R.anim.fade_in));
		return convertView;
	}

	private class ViewHolder {
		private TextView usernameTv;
		private TextView jidTv;
	}

}
