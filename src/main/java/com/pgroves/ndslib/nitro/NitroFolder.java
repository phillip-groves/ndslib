package com.pgroves.ndslib.nitro;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a folder and its containing data in the NitroROM file 
 * system.
 * 
 * @author Phillip Groves
 */
public class NitroFolder {

	/** Files in this folder */
	private List<NitroFile> files = new ArrayList<NitroFile>();
	
	/** Folders in this folder */
	private List<NitroFolder> folders = new ArrayList<NitroFolder>();
	
	/** Folder name */
	private String name;
	
	/** Folder ID */
	private int id;
	
	/** Optional folder information */
	private Object tag;
	
	/**
	 * 
	 * @return Files in this folder
	 */
	public List<NitroFile> getFiles() {
		return files;
	}
	
	/**
	 * 
	 * @param files Files in this folder
	 */
	public void setFiles(List<NitroFile> files) {
		this.files = files;
	}
	
	/**
	 * 
	 * @return Folders in this folder
	 */
	public List<NitroFolder> getFolders() {
		return folders;
	}
	
	/**
	 * 
	 * @param folders Folders in this folder
	 */
	public void setFolders(List<NitroFolder> folders) {
		this.folders = folders;
	}
	
	/**
	 * 
	 * @return Folder name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 
	 * @param name Folder name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 
	 * @return Folder ID
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * 
	 * @param id Folder ID
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * 
	 * @return Optional folder information
	 */
	public Object getTag() {
		return tag;
	}
	
	/**
	 * 
	 * @param tag Optional folder information
	 */
	public void setTag(Object tag) {
		this.tag = tag;
	}
}
