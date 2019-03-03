package com.pgroves.ndslib.nitro.file.narc;

public class NarcFAT {

	private int offset;
	
	private String id;
	
	private int sectionSize;
	
	private int fileCount;
	
	private NarcFATEntry[] entries;

	public NarcFAT(int offset) {
		this.offset = offset;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getSectionSize() {
		return sectionSize;
	}

	public void setSectionSize(int sectionSize) {
		this.sectionSize = sectionSize;
	}

	public int getFileCount() {
		return fileCount;
	}

	public void setFileCount(int fileCount) {
		this.fileCount = fileCount;
	}

	public NarcFATEntry[] getEntries() {
		return entries;
	}

	public void setEntries(NarcFATEntry[] entries) {
		this.entries = entries;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}
}
