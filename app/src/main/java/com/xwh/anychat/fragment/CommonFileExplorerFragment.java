package com.xwh.anychat.fragment;

/**
 * Created by 萧文翰 on 2015/3/18.
 */
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xwh.anychat.R;
import com.xwh.anychat.adapter.FileExplorerAdapter;
import com.xwh.anychat.biz.FileExplorerBiz;
import com.xwh.anychat.biz.impl.FileExplorerBizImpl;
import com.xwh.anychat.config.Constants;
import com.xwh.anychat.entity.FileEntityForFileExplorer;
import com.xwh.anychat.util.DebugUtil;

import java.io.File;
import java.util.ArrayList;

public class CommonFileExplorerFragment extends Fragment {
	private ListView fileNameLv;
	private Button okBtn, cancelBtn;

	private boolean isMultipleMode;

	private ArrayList<FileEntityForFileExplorer> fileEntityForFileExplorers;

	private FileExplorerBiz fileExplorerBiz;
	private String startPath;
	private String currentPath;

	private FileExplorerAdapter fileExplorerAdapter;

	private TextView upTv;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View layoutView = inflater.inflate(R.layout.fragment_file_explorer, container, false);
		fileNameLv = (ListView) layoutView.findViewById(R.id.file_explorer_ll);
		okBtn = (Button) layoutView.findViewById(R.id.file_explorer_okbtn);
		cancelBtn = (Button) layoutView.findViewById(R.id.file_explorer_cancelbtn);
		upTv = (TextView) layoutView.findViewById(R.id.file_explorer_up_tv);
		return layoutView;
	}

	@Override
	public void onResume() {
		super.onResume();
		initData();
	}

	// 初始化文件管理器数据
	private void initData() {
		// 读取选取模式
		switch (getArguments().getInt(Constants.FILE_EXPLORER_SELECT_MODE)) {
		// 单选模式
		case Constants.FILE_EXPLORER_MODE_SINGLE:
			isMultipleMode = false;
			okBtn.setVisibility(View.GONE);
			cancelBtn.setVisibility(View.GONE);
			break;
		// 多选模式
		case Constants.FILE_EXPLORER_MODE_MULTIPLE:
			okBtn.setVisibility(View.VISIBLE);
			cancelBtn.setVisibility(View.VISIBLE);
			isMultipleMode = true;
			break;
		default:
			break;
		}
		startPath = getArguments().getString(Constants.FILE_EXPLORER_START_PATH);
		currentPath = startPath;
		fileExplorerBiz = new FileExplorerBizImpl();
		// 判断存储设备是否可用
		if (!fileExplorerBiz.isExternalStorageAvailable()) {
			Toast.makeText(getActivity(), getResources().getString(R.string.fileexplorer_storagenotavailable), Toast.LENGTH_SHORT).show();
			return;
		}
		DebugUtil.Log("The storage is available, so let's start at" + startPath);
		fileEntityForFileExplorers = fileExplorerBiz.getFileList(startPath);
		fileExplorerAdapter = new FileExplorerAdapter(getActivity(), fileEntityForFileExplorers, isMultipleMode);
		fileNameLv.setAdapter(fileExplorerAdapter);
		setListener();
	}

	private void setListener() {
		// 上一层监听
		upTv.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (fileEntityForFileExplorers != null) {
					if (new File(currentPath).getParent() != null) {
						fileEntityForFileExplorers.clear();
						currentPath = new File(currentPath).getParent();
						DebugUtil.Log("Back to" + currentPath);
						fileEntityForFileExplorers.addAll(fileExplorerBiz.getFileList(currentPath));
						fileExplorerAdapter.notifyDataSetChanged();
					} else {
						Toast.makeText(getActivity(), getResources().getString(R.string.fileexplorer_noparent), Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
		// 文件名列表监听
		fileNameLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				if (isMultipleMode) {

				} else {
					currentPath = fileEntityForFileExplorers.get(position).getFilePath();
					File targetFile = new File(currentPath);
					if (targetFile.isDirectory()) {
						// 若所选文件对象是一个目录，则进入该目录，重新刷新列表内容
						DebugUtil.Log(currentPath + " is a directory, so get inside.");
						fileEntityForFileExplorers.clear();
						fileEntityForFileExplorers.addAll(fileExplorerBiz.getFileList(currentPath));
						fileExplorerAdapter.notifyDataSetChanged();
					} else {
						// 如果所选文件对象满足要求，则返回路径
						Bundle bundle = new Bundle();
						bundle.putString(Constants.FILE_EXPLORER_RETURN_SINGLE_FILE, fileEntityForFileExplorers.get(position).getFilePath());
						Message message = new Message();
						message.setData(bundle);
						message.what = Constants.FILE_EXPLORER_SELECT_OVER;
						if (getArguments().getString(Constants.FILE_EXPLORER_FROM).equals(Constants.FILE_EXPLORER_FROM_AVATOR)) {
							// 来自头像选择的返回
							MyProfileFragment.avatorFileHandler.sendMessage(message);
						}
					}

				}
			}
		});
	}
}
