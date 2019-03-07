# ndslib
A simple NDS ROM asset viewing library written in Java

## How to use

### Manager
Current implementation of this library allows for one instance of the NdsLib class, which is the main entry point for the API. A reference to this instance can be obtained statically through
`NdsLib.instance()`

Example:

```
File romFile = new File(...);
NdsLib nds = NdsLib.instance();
nds.load(romFile);
```

### NdsRom Directly
Alternatively, you can work without the `NdsLib` object by creating a `NdsRom` instance through the `NdsRom(File file)` constructor.

Example:

```
File romFile = new File(...);
NdsRom rom = new NdsRom(romFile);
String title = rom.getHeader().getTitle();
```

### Files & Folders
Files and folders can be obtained by calling `getFileSystem()` on a `NdsRom` object. This returns an object with methods to get folders and files.

Example:

```
File romFile = new File(...);
NdsLib nds = NdsLib.instance();
nds.load(romFile);

NitroFile file = nds.getCurrentRom().getFileSystem().getFile("a/0/0/5");
String fileName = file.getName();

NitroFolder folder = nds.getCurrentRom().getFileSystem().getFolder("a/0/0");
String folderName = folder.getName();
```
