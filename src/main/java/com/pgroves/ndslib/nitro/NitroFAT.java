package com.pgroves.ndslib.nitro;

import com.pgroves.ndslib.NdsRom;

/**
 * Represents the file allocation table within the NitroROM file system, 
 * which most NDS ROMs use.
 * 
 * @author Phillip Groves
 */
public class NitroFAT {
	
	/** A list of all file entries, which hold location and size data */
	public NitroFATEntry[] entries;
	
	/**
	 * 
	 * @param ndsRom The ROM holding this file allocation table
	 */
	public NitroFAT(NdsRom ndsRom) {
		int fatOffset = ndsRom.getHeader().getFatOffset();
		int fatSize = ndsRom.getHeader().getFatSize();
		this.entries = new NitroFATEntry[fatSize / 8];
		
		ndsRom.setPosition(fatOffset);
		for (int i = 0; i < entries.length; i++) {
			entries[i] = new NitroFATEntry();
			
			entries[i].offset = ndsRom.getInt();
			entries[i].size = ndsRom.getInt() - entries[i].offset;
		}
	}
	
	/**
	 * 
	 * @return A list of all file entries, which hold location and size data
	 */
	public NitroFATEntry[] getEntries() {
		return entries;
	}
	
	/**
	 * Simple POJO used to hold each file's offset and size
	 * 
	 * @author Phillip Groves
	 */
	public static class NitroFATEntry {
		
		private int offset;
		private int size;
		
		public void setOffset(int offset) {
			this.offset = offset;
		}
		
		public int getOffset() {
			return offset;
		}
		
		public void setSize(int size) {
			this.size = size;
		}
		
		public int getSize() {
			return size;
		}
	}
}
