package Metatron.Core;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class M_Data {

	protected static final FileSystem FS = FileSystems.getDefault();
	
	protected static final String defaultUserPath = System.getProperty("user.home");
	protected static final String defaultDataPath = defaultUserPath+"\\data";
	
	
	protected static final String defaultResourcePath = defaultDataPath+"\\resources";
	protected static final String defaultAssetsePath = defaultDataPath+"\\resources";
	protected static final String defaultTempPath = defaultDataPath+"\\temp";

	public static Path DataPath;
	
	static {
		DataPath = Path.of(defaultDataPath, "");
	}
	
}
