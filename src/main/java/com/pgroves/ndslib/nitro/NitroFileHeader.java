package com.pgroves.ndslib.nitro;

import java.nio.ByteOrder;

public class NitroFileHeader {

	private String extension;
	
	private ByteOrder endian;
	
	private int fileSize;
	
	private int sectionCount;

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public ByteOrder getEndian() {
		return endian;
	}

	public void setEndian(ByteOrder endian) {
		this.endian = endian;
	}

	public int getFileSize() {
		return fileSize;
	}

	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}

	public int getSectionCount() {
		return sectionCount;
	}

	public void setSectionCount(int sectionCount) {
		this.sectionCount = sectionCount;
	}
}
