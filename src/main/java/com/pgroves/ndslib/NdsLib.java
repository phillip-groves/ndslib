package com.pgroves.ndslib;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel.MapMode;
import java.util.HashMap;

/**
 * A centralized point of entry for use with the NdsLib library. 
 * Obtain the instance of this class using {@link NdsLib.instance()}
 * 
 * @author Phillip Groves
 */
public class NdsLib {
	
	/** Cache of currently managed NDS ROMs */
	private HashMap<String, NdsRom> cache = new HashMap<String, NdsRom>();
	
	/** Game code of the current ROM */
	String current;
	
	/** Static instance allows only 1 per application */
	private static NdsLib instance;
	private NdsLib() { }
	
	/**
	 * 
	 * @return The only allowed instance of this class
	 */
	public static NdsLib instance() {
		if (instance == null)
			instance = new NdsLib();
		return instance;
	}
	
	/**
	 * Loads the NDS ROM and sets it to current if no current ROM exists.
	 * 
	 * @param file .nds ROM file
	 */
	public void load(File file) {
		try {
			RandomAccessFile raf = new RandomAccessFile(file, "r");
			ByteBuffer buffer = raf.getChannel().map(MapMode.READ_ONLY, 0, raf.length()).order(ByteOrder.LITTLE_ENDIAN);
			
			NdsRom rom = new NdsRom(buffer);
			
			if (cache.containsKey(rom.getHeader().getGameCode()))
				cache.remove(rom.getHeader().getGameCode());
			if (current == null)
				current = rom.getHeader().getGameCode();
			
			
			cache.put(rom.getHeader().getGameCode(), rom);
			raf.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @return The currently managed NDS ROM
	 */
	public NdsRom getCurrentRom() {
		return cache.get(current);
	}
	
	/**
	 * 
	 * @param gameCode The currently managed NDS ROM
	 */
	public void setCurrent(String gameCode) {
		this.current = gameCode;
	}
}