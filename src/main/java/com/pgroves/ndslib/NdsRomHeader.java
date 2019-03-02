package com.pgroves.ndslib;

/**
 * Represents basic NDS ROM information listed at the beginning of the file. 
 * For now, only a few values are read from the header. This will change in 
 * future builds.
 * 
 * @author Phillip Groves
 */
public class NdsRomHeader {

	/** 12-character title */
	private String title;
	
	/** 4-character unique game code */
	private String gameCode;
	
	/** Offset to file name table */
	private int fntOffset;
	
	/** File name table size */
	private int fntSize;
	
	/** Offset to file allocation table */
	private int fatOffset;
	
	/** File allocation table size */
	private int fatSize;
	
	/**
	 * 
	 * @param ndsRom The NDS ROM holding this header
	 */
	public NdsRomHeader(NdsRom ndsRom) {
		this.title = ndsRom.getString(0x00, 12);
		this.gameCode = ndsRom.getString(0x00C, 4);
		this.fntOffset = ndsRom.getInt(0x40);
		this.fntSize = ndsRom.getInt(0x44);
		this.fatOffset = ndsRom.getInt(0x48);
		this.fatSize = ndsRom.getInt(0x4C);
	}
	
	/**
	 * 
	 * @return 12-character title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * 
	 * @return 4-character unique game code
	 */
	public String getGameCode() {
		return gameCode;
	}

	/**
	 * 
	 * @return Offset to file name table
	 */
	public int getFntOffset() {
		return fntOffset;
	}

	/**
	 * 
	 * @return File name table size
	 */
	public int getFntSize() {
		return fntSize;
	}

	/**
	 * 
	 * @return Offset to file allocation table
	 */
	public int getFatOffset() {
		return fatOffset;
	}

	/**
	 * 
	 * @return File allocation table size
	 */
	public int getFatSize() {
		return fatSize;
	}
}
