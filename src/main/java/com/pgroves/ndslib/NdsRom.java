package com.pgroves.ndslib;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * A representation of the NDS ROM file and its bytes. Data about the from can be accessed 
 * through this class. 
 * 
 * @author Phillip Groves
 * 
 */
public class NdsRom {
	
	/** ROM bytes in little endian format, unless specified explicitly */
	private ByteBuffer romBuffer;
	
	/** Header data about the ROM */
	private NdsRomHeader header;
	
	/** NDS File System interface */
	private NdsRomFileSystem fileSystem;
	
	/**
	 * 
	 * @param buffer NDS file bytes
	 */
	public NdsRom(ByteBuffer buffer) {
		this.romBuffer = buffer;
		this.header = new NdsRomHeader(this);
		this.fileSystem = new NdsRomFileSystem(this);
	}
	
	/**
	 * 
	 * @return Header data about the ROM
	 */
	public NdsRomHeader getHeader() {
		return header;
	}
	
	/**
	 * 
	 * @return NDS file system
	 */
	public NdsRomFileSystem getFileSystem() {
		return fileSystem;
	}
	
	/**
	 * 
	 * @param position Offset to get byte from
	 * @return 1-byte integer read from buffer
	 */
	public int getByte(int position) {
		return romBuffer.get(position) & 0xFF;
	}
	
	/**
	 * 
	 * @return 1-byte integer read from buffer
	 */
	public int getByte() {
		return romBuffer.get() & 0xFF;
	}
	
	/**
	 * 
	 * @param position Offset to get short from
	 * @return 2-byte integer read from buffer
	 */
	public int getShort(int position) {
		return romBuffer.getShort(position) & 0xFFFF;
	}
	
	/**
	 * 
	 * @return 2-byte integer read from buffer
	 */
	public int getShort() {
		return romBuffer.getShort() & 0xFFFF;
	}
	
	/**
	 * 
	 * @param position Offset to get integer from
	 * @return 4-byte integer read from buffer
	 */
	public int getInt(int position) {
		return romBuffer.getInt(position);
	}
	
	/**
	 * 
	 * @return 4-byte integer read from buffer
	 */
	public int getInt() {
		return romBuffer.getInt();
	}
	
	/**
	 * 
	 * @param position Offset to begin reading string from
	 * @param length Amount of characters to read
	 * @return String read from buffer
	 */
	public String getString(int position, int length) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < length; i++) {
			builder.append((char) romBuffer.get());
		}
		return builder.toString().trim();
	}
	
	/**
	 * 
	 * @param length Amount of characters to read
	 * @return String read from buffer
	 */
	public String getString(int length) {
		return getString(romBuffer.position(), length);
	}
	
	/**
	 * 
	 * @param position The underlying buffer position
	 */
	public void setPosition(int position) {
		this.romBuffer.position(position);
	}
	
	/**
	 * 
	 * @return The current buffer position
	 */
	public int getPosition() {
		return romBuffer.position();
	}
	
	public void setEndian(ByteOrder endian) {
		this.romBuffer = romBuffer.order(endian);
	}
	
	public void skip(int amount) {
		for (int i = 0; i < amount; i++)
			getByte();
	}
	
	public void backtrack(int amount) {
		int currPos = getPosition();
		setPosition(currPos - amount);
	}
}
