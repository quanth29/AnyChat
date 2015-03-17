package com.xwh.anychat.biz.impl;

import android.os.Environment;

import com.xwh.anychat.biz.FileExplorerBiz;
import com.xwh.anychat.entity.FileEntityForFileExplorer;

import java.io.File;
import java.util.ArrayList;

public class FileExplorerBizImpl implements FileExplorerBiz {

	@Override
	public ArrayList<FileEntityForFileExplorer> getFileList(String path) {
		ArrayList<FileEntityForFileExplorer> returnData = new ArrayList<FileEntityForFileExplorer>();
		File file = new File(path);
		File[] fileList = file.listFiles();
		if (fileList != null && fileList.length > 0) {
			for (int i = 0; i < fileList.length; i++) {
				returnData.add(new FileEntityForFileExplorer(fileList[i].getName(), fileList[i].getAbsolutePath(), false));
			}
		}
		return returnData;
	}

	@Override
	public boolean isDirectory(String path) {
		if (new File(path).isDirectory()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean isFilePathExist(String path) {
		if (new File(path).exists()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean isExternalStorageAvailable() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}

	@Override
	public String getDCIMPath() {
		return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath();
	}
}
