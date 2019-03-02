package com.pgroves.ndslib.nitro;

/**
 * Represents a base folder below root in the a NitroROM file system and is 
 * primarily used during the loading process of file tree information.
 * 
 * @author Phillip Groves
 */
public class NitroFolderRoot {

	/** Folder offset */
	private int offset;
	
	/** ID of the first file in the folder */
	private int firstFileId;
	
	/** The parent folder ID */
	private int parentFolderId;
	
	/** Unsorted files and folders below this place in the hierarchy */
	private NitroFolder subFolder = new NitroFolder();
	
	/**
	 * 
	 * @return Folder offset
	 */
	public int getOffset() {
		return offset;
	}
	
	/**
	 *  
	 * @param offset Folder offset
	 */
	public void setOffset(int offset) {
		this.offset = offset;
	}
	
	/**
	 * 
	 * @return ID of the first file in the folder
	 */
	public int getFirstFileId() {
		return firstFileId;
	}
	
	/**
	 * 
	 * @param firstFileId ID of the first file in the folder
	 */
	public void setFirstFileId(int firstFileId) {
		this.firstFileId = firstFileId;
	}
	
	/**
	 * 
	 * @return The parent folder ID
	 */
	public int getParentFolderId() {
		return parentFolderId;
	}
	
	/**
	 * 
	 * @param parentFolderId The parent folder ID
	 */
	public void setParentFolderId(int parentFolderId) {
		this.parentFolderId = parentFolderId;
	}
	
	/**
	 * 
	 * @return Unsorted files and folders below this place in the hierarchy
	 */
	public NitroFolder getSubFolder() {
		return subFolder;
	}
	
	/**
	 * 
	 * @param subFolder Unsorted files and folders below this place in the hierarchy
	 */
	public void setSubFolder(NitroFolder subFolder) {
		this.subFolder = subFolder;
	}
}
