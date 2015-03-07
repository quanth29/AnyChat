package com.xwh.anychat.entity;

public class FileEntityForFileExplorer {
	private String fileName;
	private String filePath;
	private boolean isSelected;

	public FileEntityForFileExplorer() {
		super();
	}

	public FileEntityForFileExplorer(String fileName, String filePath, boolean isSelected) {
		super();
		this.fileName = fileName;
		this.filePath = filePath;
		this.isSelected = isSelected;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	@Override
	public String toString() {
		return "File: " + fileName + " at " + filePath + ", and the selection status is " + isSelected;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof FileEntityForFileExplorer) {
			return false;
		} else {
			if (((FileEntityForFileExplorer) o).getFilePath().equals(filePath)) {
				return true;
			} else {
				return false;
			}
		}
	}

}
