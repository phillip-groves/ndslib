package com.pgroves.ndslib.nitro.file.narc;

import java.util.ArrayList;
import java.util.List;

import com.pgroves.ndslib.nitro.NitroFile;
import com.pgroves.ndslib.nitro.NitroFolder;

public class NarcFNTRoot {

	private int offset;
	
	private int firstFileId;
	
	private int parentFolderId;
	
	private List<NitroFolder> folders = new ArrayList<NitroFolder>();
	
	private List<NitroFile> files = new ArrayList<NitroFile>();

	
	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getFirstFileId() {
		return firstFileId;
	}

	public void setFirstFileId(int firstFileId) {
		this.firstFileId = firstFileId;
	}

	public int getParentFolderId() {
		return parentFolderId;
	}

	public void setParentFolderId(int parentFolderId) {
		this.parentFolderId = parentFolderId;
	}

	public List<NitroFolder> getFolders() {
		return folders;
	}

	public void setFolders(List<NitroFolder> folders) {
		this.folders = folders;
	}

	public List<NitroFile> getFiles() {
		return files;
	}

	public void setFiles(List<NitroFile> files) {
		this.files = files;
	}
}
