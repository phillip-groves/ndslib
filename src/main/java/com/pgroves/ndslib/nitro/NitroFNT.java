package com.pgroves.ndslib.nitro;

import java.util.ArrayList;
import java.util.List;

import com.pgroves.ndslib.NdsRom;

/**
 * Represents the file name table for a NitroROM file system. 
 * 
 * @author Phillip Groves
 */
public class NitroFNT {

	/** The root directory for the NitroROM file system */
	private NitroFolder root;
	
	/**
	 * 
	 * @param ndsRom NDS ROM holding this file name table
	 * @param fat File allocation table from this ROM
	 */
	public NitroFNT(NdsRom ndsRom, NitroFAT fat) {
		NitroFAT.NitroFATEntry[] fatEntries = fat.getEntries();
		int fntOffset = ndsRom.getHeader().getFntOffset();
		int directoryCount = ndsRom.getShort(fntOffset + 6);
		
		List<NitroFolderRoot> mains = new ArrayList<NitroFolderRoot>();
		
		ndsRom.setPosition(fntOffset);
		for (int i = 0; i < directoryCount; i++) {
			NitroFolderRoot main = new NitroFolderRoot();
			main.setOffset(ndsRom.getInt());
			main.setFirstFileId(ndsRom.getShort());
			main.setParentFolderId(ndsRom.getShort());
			
			
			if (i != 0 && ndsRom.getPosition() > fntOffset + mains.get(0).getOffset()) {
				directoryCount--;
				i--;
				continue;
			}
				
			int currentOffset = ndsRom.getPosition();
			int fileId = main.getFirstFileId();
			
			ndsRom.setPosition(fntOffset + main.getOffset());
			int nameLength = ndsRom.getByte();
			while (nameLength != 0) { // 0 = End of subtable
				if (nameLength < 0x80) { // File
					NitroFile file = new NitroFile();
					file.setName(ndsRom.getString(nameLength));
					file.setId(fileId++); // Set fileId then increment
					file.setOffset(fatEntries[file.getId()].getOffset());
					file.setSize(fatEntries[file.getId()].getSize());
					file.setPath(ndsRom.getHeader().getTitle());
					
					// Get format
					int pos = ndsRom.getPosition();
					ndsRom.setPosition(file.getOffset());
					file.setFormat(NitroFileFormat.fromFileData(ndsRom, file.getName(), file.getId(), file.getSize()));
					ndsRom.setPosition(pos);
					
					main.getSubFolder().getFiles().add(file);
				}
				if (nameLength > 0x80) { // Directory
					nameLength -= 0x80;
					NitroFolder folder = new NitroFolder();
					folder.setName(ndsRom.getString(nameLength));
					
					folder.setId(ndsRom.getShort());
					main.getSubFolder().getFolders().add(folder);
				}
				nameLength = ndsRom.getByte();
			} 
			
			mains.add(main);
			ndsRom.setPosition(currentOffset);
		}
		
		// Create hierarchy
		this.root = createFolder(mains, 0, "root");
		this.root.setId(directoryCount);
	}
	
	/**
	 * Recursively builds loaded folders and files into a working file tree, with the 
	 * root file as the result.
	 * 
	 * @param mains The current folders and files
	 * @param folderId The current folderId
	 * @param name The current name
	 * @return Root folder with all files and folders loaded internally
	 */
	private NitroFolder createFolder(List<NitroFolderRoot> mains, int folderId, String name) {
		NitroFolder currentFolder = new NitroFolder();
		currentFolder.setId(folderId);
		currentFolder.setName(name);
		currentFolder.setFiles(mains.get(folderId & 0xFFF).getSubFolder().getFiles());
		
		if (mains.get(folderId & 0xFFF).getSubFolder().getFolders().size() > 0) {
			for (NitroFolder subFolder : mains.get(folderId & 0xFFF).getSubFolder().getFolders()) {
				currentFolder.getFolders().add(createFolder(mains, subFolder.getId(), subFolder.getName()));
			}
		}
		
		return currentFolder;
	}
	
	/**
	 * 
	 * @return The root folder for the NitroROM file system
	 */
	public NitroFolder getRoot() {
		return root;
	}
}
