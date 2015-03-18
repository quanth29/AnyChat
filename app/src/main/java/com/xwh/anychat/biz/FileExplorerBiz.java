package com.xwh.anychat.biz;

/**
 * Created by 萧文翰 on 2015/3/18.
 */
import com.xwh.anychat.entity.FileEntityForFileExplorer;

import java.util.ArrayList;

public interface FileExplorerBiz {

	/**
	 * 得到指定路径下的文件列表（需要先调用isFile方法判断给定的路径是否为一个合法目录）
	 * 
	 * @param path
	 * @return
	 */
	public ArrayList<FileEntityForFileExplorer> getFileList(String path);

	/**
	 * 判断给定的路径是否为一个目录
	 * 
	 * @param path
	 * @return 若为一个目录，则返回true；反之，返回false
	 */
	public boolean isDirectory(String path);

	/**
	 * 判断给定的路径或文件是否可用，判断的依据是该文件或目录是否存在
	 * 
	 * @param path
	 * @return 若可用，则返回true；反之，返回false
	 */
	public boolean isFilePathExist(String path);

	/**
	 * 判断设备存储器是否可用
	 * 
	 * @return 若可用，则返回true；反之，返回false
	 */
	public boolean isExternalStorageAvailable();

	/**
	 * 得到系统相册路径
	 * 
	 * @return
	 */
	public String getDCIMPath();

}
