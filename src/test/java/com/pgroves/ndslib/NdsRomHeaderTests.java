package com.pgroves.ndslib;

import java.io.File;

import org.junit.Test;
import org.junit.Assert;

import junit.framework.TestCase;

public class NdsRomHeaderTests extends TestCase {

	@Test
	public void testReadTitle() {
		// Load the rom
		File romFile = new File("src/test/resources/"
				+ "Pokemon SoulSilver [Extracted From Cartridge 2-15-2019].nds");
		NdsLib manager = NdsLib.instance();
		manager.load(romFile);

		// Check its header
		NdsRomHeader header = manager.getCurrentRom().getHeader();
		Assert.assertEquals(header.getTitle(), "POKEMON SS");
		Assert.assertEquals(header.getGameCode(), "IPGE");
		Assert.assertEquals(header.getFntOffset(), 0x31EC00);
		Assert.assertEquals(header.getFntSize(), 0xB56);
		Assert.assertEquals(header.getFatOffset(), 0x31F800);
		Assert.assertEquals(header.getFatSize(), 0x1008);
	}
	
}
