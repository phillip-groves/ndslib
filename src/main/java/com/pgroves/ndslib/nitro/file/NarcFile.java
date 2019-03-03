package com.pgroves.ndslib.nitro.file;

import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

import com.pgroves.ndslib.NdsRom;
import com.pgroves.ndslib.nitro.NitroFile;
import com.pgroves.ndslib.nitro.NitroFileHeader;
import com.pgroves.ndslib.nitro.NitroFolder;
import com.pgroves.ndslib.nitro.file.narc.NarcFAT;
import com.pgroves.ndslib.nitro.file.narc.NarcFATEntry;
import com.pgroves.ndslib.nitro.file.narc.NarcFIMG;
import com.pgroves.ndslib.nitro.file.narc.NarcFNT;
import com.pgroves.ndslib.nitro.file.narc.NarcFNTRoot;

/**
 * Represents a NARC archive file within a NitroROM file system. This 
 * file holds additional folders and files, which can be accessed through 
 * relevant methods.
 * 
 * @author Phillip Groves
 */
public class NarcFile {
	
	/** The NDS ROM holding this file */
	private NdsRom ndsRom;
	
	/** Basic file information */
	private NitroFile fileInfo;
	private NitroFileHeader header;
	
	/** Archive file system information */
	private NarcFAT fat;
	private NarcFNT fnt;
	private NarcFIMG fimg;
	
	/** Lists of folders and files in this archive */
	private List<NitroFile> files = new ArrayList<NitroFile>();
	private List<NitroFolder> folders = new ArrayList<NitroFolder>();
	
	/**
	 * 
	 * @param ndsRom The ROM holding this archive
	 * @param fileInfo Basic file information
	 */
	public NarcFile(NdsRom ndsRom, NitroFile fileInfo) {
		this.fileInfo = fileInfo;
		this.header = new NitroFileHeader(); // not fully initialized until unpack
		
		// Initialize file system components
		this.ndsRom = ndsRom;
		ndsRom.setPosition(fileInfo.getOffset() + 16);
		this.fat = new NarcFAT(fileInfo.getOffset() + 16);
		
		ndsRom.setPosition(fileInfo.getOffset() + 24);
		this.fnt = new NarcFNT(ndsRom.getInt() * 8 + 28);
		
		ndsRom.setPosition(fnt.getOffset() + 4);
		this.fimg = new NarcFIMG(ndsRom.getInt() + fnt.getOffset());
	}
	
	/**
	 * Obtains a packed NarcFile instance from the NDS ROM at the path 
	 * provided. Unpacking the archive can be done through the object's 
	 * unpack() method.
	 * 
	 * @param ndsRom NDS ROM holding this archive
	 * @param path Internal file system path to archive
	 * @return NARC file
	 */
	public static NarcFile fromPath(NdsRom ndsRom, String path) {
		NitroFile fileInfo = ndsRom.getFileSystem().getFile(path);
		NarcFile narc = new NarcFile(ndsRom, fileInfo);
		
		return narc;
	}
	
	/**
	 * Unpacks the archive by loading its internal files and folders into 
	 * this object. They can then be obtained through getFiles() and getFolders().
	 */
	public void unpack() {
		ndsRom.setPosition(fileInfo.getOffset());
		
		// Load header info
		this.header.setExtension(ndsRom.getString(4));
		this.header.setEndian(ndsRom.getShort() == 0xFFFE ? ByteOrder.BIG_ENDIAN: ByteOrder.LITTLE_ENDIAN);
		// TODO: Reverse extension if endian is big
		
		ndsRom.skip(4); // Static unknown constant, skipping
		this.header.setFileSize(ndsRom.getInt());
		ndsRom.skip(4); // Static header size = 16, skipping
		this.header.setSectionCount(ndsRom.getShort());
		
		// Load File Allocation Table
		ndsRom.setPosition(fat.getOffset());
		this.fat.setId(ndsRom.getString(4));
		this.fat.setSectionSize(ndsRom.getInt());
		this.fat.setFileCount(ndsRom.getInt());
		
		NarcFATEntry[] entries = new NarcFATEntry[fat.getFileCount()];
		for (int i = 0; i < entries.length; i++) {
			NarcFATEntry entry = new NarcFATEntry();
			entry.setOffset(ndsRom.getInt());
			entry.setSize(ndsRom.getInt() - entry.getOffset());
		
			entries[i] = entry;
		}
		this.fat.setEntries(entries);
		
		// Load File Name Table
		this.fnt.setId(ndsRom.getString(4));
		this.fnt.setSectionSize(ndsRom.getInt());
		
		// Get root folder
		int rootTableOffset = ndsRom.getPosition();
		fimg.setOffset(ndsRom.getPosition() + fnt.getSectionSize());
		
		ndsRom.skip(6);
		int rootCount = ndsRom.getShort();
		ndsRom.backtrack(8);
		
		for (int i = 0; i < rootCount; i++) {
			NarcFNTRoot main = new NarcFNTRoot();
			main.setOffset(ndsRom.getInt());
			main.setFirstFileId(ndsRom.getShort());
			main.setParentFolderId(ndsRom.getShort());
			int fileId = main.getFirstFileId();
			
			if (main.getOffset() < 8) { // There aren't names
				for (int j = 0; j < fat.getFileCount(); j++) {
					NitroFile currentFile = new NitroFile();
					currentFile.setName(fileInfo.getName() + "_" + fileId);
					currentFile.setId(fileId++);
					
					currentFile.setPath(fileInfo.getPath());
					currentFile.setSize(fat.getEntries()[currentFile.getId()].getSize());
					currentFile.setOffset(fat.getEntries()[currentFile.getId()].getOffset() 
								+ fimg.getOffset());
					
					
					// Get file extension
					int currentPosition = ndsRom.getPosition();
					ndsRom.setPosition(currentFile.getOffset());
					
					String baseExt;
					if (currentFile.getSize() < 4)
						baseExt = ndsRom.getString(currentFile.getSize());
					else
						baseExt = ndsRom.getString(4);
					
					String extension = ".";
					for (int e = 0; e < baseExt.length(); e++) {
						char current = baseExt.charAt(e);
						
						if (Character.isAlphabetic(current) || Character.isDigit(current)
								|| current == 0x20) {
							extension += baseExt;
						}
					}
					
					if (!extension.equals(".") && extension.length() == 5 && currentFile.getSize() >= 4) {
						currentFile.setName(currentFile.getName() + extension);
					} else {
						currentFile.setName(currentFile.getName() + ".bin");
					}
					
					ndsRom.setPosition(currentPosition);
					main.getFiles().add(currentFile);
				}
				
				fnt.getEntries().add(main);
				continue;
			}
			
			int mainPosition = ndsRom.getPosition();
			ndsRom.setPosition(main.getOffset() + rootTableOffset);
			
			int flag = 0x80;
			int id = ndsRom.getByte();
			while (id != 0) {
				if ((id & flag) == 0) { // File
					NitroFile currentFile = new NitroFile();
					currentFile.setId(fileId++);
					
					currentFile.setName(ndsRom.getString(id)); // id is same as name length
					currentFile.setPath(fileInfo.getPath());
					currentFile.setOffset(fat.getEntries()[currentFile.getId()].getOffset());
					currentFile.setSize(fat.getEntries()[currentFile.getId()].getSize());
					
					main.getFiles().add(currentFile);
				} else { // Folder
					NitroFolder currentFolder = new NitroFolder();
					currentFolder.setName(ndsRom.getString(id - flag)); // id is same as name length
					currentFolder.setId(ndsRom.getShort());
					
					System.out.println("Folder Id: " + Integer.toHexString(currentFolder.getId()));
					main.getFolders().add(currentFolder);
				}
				id = ndsRom.getByte();
			}
			this.fnt.getEntries().add(main);
			ndsRom.setPosition(mainPosition);
		}
		
		// Create the file tree then add the folders and files to this NARC
		NitroFolder root = createFolderRecursively(fnt.getEntries(), 0xF000, "root");
		for (int i = 0; i < root.getFiles().size(); i++)
			this.files.add(root.getFiles().get(i));
		for (int i = 0; i < root.getFolders().size(); i++)
			this.folders.add(root.getFolders().get(i));
		
		// Load file image data
		ndsRom.setPosition(fimg.getOffset() - 8);
		fimg.setId(ndsRom.getString(4));
		fimg.setSectionSize(ndsRom.getInt());
	}
	
	/**
	 * Recursively loads unsorted files and folders into their correct hierarchy.
	 * 
	 * @param entries Unsorted files and folders
	 * @param folderId Current folder ID
	 * @param folderName Current folder name
	 * @return The root folder of the newly built file tree
	 */
	private NitroFolder createFolderRecursively(List<NarcFNTRoot> entries,int folderId, String folderName) {
		NitroFolder folder = new NitroFolder();
		folder.setName(folderName);
		folder.setId(folderId);
		folder.setFiles(entries.get(folderId & 0xFFF).getFiles());
		
		for (NitroFolder subFolder : entries.get(folderId & 0xFFF).getFolders()) {
			folder.getFolders().add(createFolderRecursively(entries, subFolder.getId(), subFolder.getName()));
		}
		
		return folder;
	}
	
	public NarcFAT getFat() {
		return fat;
	}

	public NarcFNT getFnt() {
		return fnt;
	}

	public NarcFIMG getFimg() {
		return fimg;
	}

	public List<NitroFile> getFiles() {
		return files;
	}

	public void setFiles(List<NitroFile> files) {
		this.files = files;
	}

	public List<NitroFolder> getFolders() {
		return folders;
	}

	public void setFolders(List<NitroFolder> folders) {
		this.folders = folders;
	}
	
	public NitroFile getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(NitroFile fileInfo) {
		this.fileInfo = fileInfo;
	}
}
