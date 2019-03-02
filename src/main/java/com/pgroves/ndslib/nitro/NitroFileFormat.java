package com.pgroves.ndslib.nitro;

import com.pgroves.ndslib.NdsRom;

/**
 * A simple enumerated type holding information for each 
 * "kind" of file within the NitroROM file system. Note that this 
 * value does not correspond to file extension or internal file layout.
 * 
 * @author Phillip Groves
 */
public enum NitroFileFormat {
	Palette,
	Tile,
	Map,
	Cell,
	Animation,
	FullImage,
	Text, 
	Video,
	Sound,
	Font,
	Compressed,
	Unknown,
	System,
	Script,
	Pack,
	Model3D,
	Texture;
	
	/**
	 * 
	 * @param rom Set to file extension position
	 * @param name File name
	 * @param id File ID
	 * @param size File size
	 * @return This file's format
	 */
	public static NitroFileFormat fromFileData(NdsRom rom, String name, int id, int size) {
		if (size <= 0)
			return Unknown;
		
		/* TODO
		String extension;
		if (size >= 4) {
			extension = rom.getString(4);
			rom.setPosition(rom.getPosition() - 4);
		} else {
			extension = rom.getString(size);
			rom.setPosition(rom.getPosition() - size);
		}
		*/
		
		name = name.toUpperCase();
		if (name.equals("FNT.BIN") || name.equals("FAT.BIN") || name.startsWith("OVERLAY9_") || name.startsWith("OVERLAY7_")
			|| name.equals("ARM9.BIN") || name.equals("ARM7.BIN") || name.equals("Y9.BIN") || name.equals("BANNER.BIN")
			|| name.endsWith(".SRL") || name.endsWith(".NDS")) {
			return System;
		}
		
		return Unknown;
	}
}
