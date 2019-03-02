package com.pgroves.ndslib.nitro;

/**
 * A simple enumerated type holding different compression types for 
 * files in the NitroROM file system. In the future, this enum will 
 * hold additional information for each compression type, as well as 
 * static helper methods.
 * 
 * @author Phillip Groves
 */
public enum NitroFileCompression {
	LZ0Vl,
	LZ10,
	LZ11,
	HUFF4,
	HUFF8,
	RLE,
	HUFF,
	NDS,
	GBA,
	Invalid;
}
