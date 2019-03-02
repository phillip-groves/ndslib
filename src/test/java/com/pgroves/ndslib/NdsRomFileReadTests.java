package com.pgroves.ndslib;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import com.pgroves.ndslib.nitro.NitroFile;
import com.pgroves.ndslib.nitro.NitroFolder;

import junit.framework.TestCase;

public class NdsRomFileReadTests extends TestCase {

	@Test
	public void testReadTitle() {
		// Load the rom
		File romFile = new File("src/test/resources/"
				+ "Pokemon SoulSilver [Extracted From Cartridge 2-15-2019].nds");
		NdsLib manager = NdsLib.instance();
		manager.load(romFile);

		NitroFolder folder = manager.getCurrentRom().getFileSystem().getFolder("fielddata/build_model");
		Assert.assertEquals(folder.getId(), 0xF025);
		Assert.assertEquals(folder.getName(), "build_model");
		Assert.assertEquals(folder.getFiles().size(), 4);
		
		NitroFile file = manager.getCurrentRom().getFileSystem().getFile("fielddata/build_model/bm_field.narc");
		Assert.assertEquals(file.getId(), 0x1e1);
		Assert.assertEquals(file.getName(), "bm_field.narc");
		Assert.assertEquals(file.getOffset(), 0xEF7200);
	}
}
