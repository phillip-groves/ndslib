package com.pgroves.ndslib;

import com.pgroves.ndslib.nitro.NitroFAT;
import com.pgroves.ndslib.nitro.NitroFNT;
import com.pgroves.ndslib.nitro.NitroFile;
import com.pgroves.ndslib.nitro.NitroFolder;

/**
 * NDS ROMs after use the NitroROM file system to hold game-related files. The 
 * file system is a unique FAT format. File and folder related methods can be 
 * found in this class.
 * 
 * @author Phillip Groves
 *
 */
public class NdsRomFileSystem {
	
	/** NDS custom FAT table */
	private final NitroFAT fileAllocationTable;
	
	/** NDS custom FNT table */
	private final NitroFNT fileNameTable;
	
	/**
	 * 
	 * @param ndsRom The ROM holding this file system
	 */
	public NdsRomFileSystem(NdsRom ndsRom) {
		this.fileAllocationTable = new NitroFAT(ndsRom);
		this.fileNameTable = new NitroFNT(ndsRom, fileAllocationTable);
	}
	
	/** TODO: Get file by file format as unique object */
	public <T extends NitroFile> T getFile(T type, String path) {
		
		
		return null;
	}
	
	/**
	 * Get file in the default file format 
	 * 
	 * @param path File path from root including file name
	 * @return Basic file info
	 */
	public NitroFile getFile(String path) {
		String[] pathData = path.split("/");
		String fileName = pathData[pathData.length - 1];
		
		String folderPath = "";
		for (int i = 0; i < pathData.length - 1; i++) 
			folderPath += pathData[i] + "/";
		//System.out.println("Folder Path: " + folderPath);
		NitroFolder folder = getFolder(folderPath);
		return getFileInFolder(folder, fileName);
	}
	
	/**
	 * 
	 * 
	 * @param path Folder path from root
	 * @return Folder info
	 */
	public NitroFolder getFolder(String path) {
		String[] folderNames = path.split("/");
		NitroFolder[] folders = new NitroFolder[folderNames.length];
		NitroFolder currentFolder = fileNameTable.getRoot();
		
		for (int i = 0; i < folders.length; i++) {
			folders[i] = getFolderInFolder(currentFolder, folderNames[i]);
			
			if (i == (folders.length - 1)) // Return on final iteration
				return folders[i];
			
			currentFolder = folders[i];
		}
		
		throw new IllegalArgumentException("Illegal nitro folder path provided: " + path);
	}
	
	/**
	 * 
	 * @param folder Parent folder to get this folder from
	 * @param name Name of the folder to get
	 * @return Found folder
	 * @throws Exception if path or folder name are invalid
	 */
	public NitroFolder getFolderInFolder(NitroFolder folder, String name) {
		for (int i = 0; i < folder.getFolders().size(); i++) {
			NitroFolder currentFolder = folder.getFolders().get(i);
			
			if (name.equals(currentFolder.getName()))
				return currentFolder;
		}
		throw new IllegalArgumentException("Illegal folder or name provided. Folder: " + folder.getName() + ", Name: " + name);
	}
	
	/**
	 * 
	 * @param folder Parent folder to get this file from
	 * @param name Name of the file to get
	 * @return Found file
	 * @throws Exception if path or file name are invalid
	 */
	public NitroFile getFileInFolder(NitroFolder folder, String fileName) {
		for (int i = 0; i < folder.getFiles().size(); i++) {
			NitroFile currentFile = folder.getFiles().get(i);
			
			if (fileName.equals(currentFile.getName()))
				return currentFile;
		}
		throw new IllegalArgumentException("Illegal file or name provided. File: " + folder.getName() + ", Name: " + fileName);
	}
	
	/**
	 * 
	 * @return Root folder of the NDS file tree
	 */
	public NitroFolder getRoot() {
		return fileNameTable.getRoot();
	}
	
	/**
	 * 
	 * @return NDS file name table
	 */
	public NitroFNT getFNT() {
		return fileNameTable;
	}
	
	/**
	 * 
	 * @return NDS file allocation table
	 */
	public NitroFAT getFAT() {
		return fileAllocationTable;
	}
}
