package com.pgroves.ndslib.nitro.file.narc;

import java.util.ArrayList;
import java.util.List;

public class NarcFNT {

	private int offset;
	
	private String id;
	
	private int sectionSize;
	
	private List<NarcFNTRoot> entries = new ArrayList<NarcFNTRoot>();

	public NarcFNT(int offset) {
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

	public List<NarcFNTRoot> getEntries() {
		return entries;
	}

	public void setEntries(List<NarcFNTRoot> entries) {
		this.entries = entries;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}
}
