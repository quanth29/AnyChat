package com.xwh.anychat.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.xwh.anychat.R;
import com.xwh.anychat.entity.FileEntityForFileExplorer;

public class FileExplorerAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<FileEntityForFileExplorer> data;
	private boolean isMultipleSelection;

	public FileExplorerAdapter(Context context, ArrayList<FileEntityForFileExplorer> data, boolean isMultipleSelection) {
		super();
		this.context = context;
		this.data = data;
		this.isMultipleSelection = isMultipleSelection;
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
		ViewHolder viewHolder;
		if (convertView != null && convertView.getTag() != null) {
			viewHolder = (ViewHolder) convertView.getTag();
		} else {
			convertView = LayoutInflater.from(context).inflate(R.layout.item_file_explorer, null);
			viewHolder = new ViewHolder();
			viewHolder.fileNameTv = (TextView) convertView.findViewById(R.id.file_explorer_item_name_tv);
			viewHolder.selectStatusCb = (CheckBox) convertView.findViewById(R.id.file_explorer_item_cb);
			convertView.setTag(viewHolder);
		}
		viewHolder.fileNameTv.setText(data.get(position).getFileName());
		if (isMultipleSelection) {
			viewHolder.selectStatusCb.setSelected(data.get(position).isSelected());
		} else {
			viewHolder.selectStatusCb.setVisibility(View.GONE);
		}
		return convertView;
	}

	private class ViewHolder {
		private TextView fileNameTv;
		private CheckBox selectStatusCb;
	}

}
