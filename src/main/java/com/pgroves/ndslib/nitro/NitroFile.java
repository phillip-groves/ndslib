package com.pgroves.ndslib.nitro;

/**
 * Represents default file information for any file in the 
 * NitroROM file system.
 * 
 * @author Phillip Groves
 */
public class NitroFile {
	
	/** Offset within the ROM of this file */
	private int offset;
	
	/** Size of this file */
	private int size;
	
	/** Name of this file */
	private String name;
	
	/** File ID */
	private int id;
	
	/** File path */
	private String path;
	
	/** Format of this file */
	private NitroFileFormat format;
	
	/** Optional additional data related to this file */
	private Object tag;
	
	/** Offset within the ROM of this file */
	public int getOffset() {
		return offset;
	}
	
	/** Offset within the ROM of this file */
	public void setOffset(int offset) {
		this.offset = offset;
	}
	
	/**
	 * 
	 * @return Size of this file
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * 
	 * @param size Size of this file
	 */
	public void setSize(int size) {
		this.size = size;
	}
	
	/**
	 * 
	 * @return Name of this file
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 
	 * @param name Size of this file
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 
	 * @return File ID
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * 
	 * @param id File ID
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * 
	 * @return File path
	 */
	public String getPath() {
		return path;
	}
	
	/**
	 * 
	 * @param path File path
	 */
	public void setPath(String path) {
		this.path = path;
	}
	
	/**
	 * 
	 * @return Format of this file
	 */
	public NitroFileFormat getFormat() {
		return format;
	}
	
	/**
	 * 
	 * @param format Format of this file
	 */
	public void setFormat(NitroFileFormat format) {
		this.format = format;
	}
	
	/**
	 * 
	 * @return Optional additional data related to this file
	 */
	public Object getTag() {
		return tag;
	}
	
	/**
	 * 
	 * @param tag Optional additional data related to this file
	 */
	public void setTag(Object tag) {
		this.tag = tag;
	}
}
