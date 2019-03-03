package com.pgroves.ndslib.nitro.file.narc;

public class NarcFIMG {

	private int offset;
	
	private String id;
	
	private int sectionSize;

	public NarcFIMG(int offset) {
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

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}
}
