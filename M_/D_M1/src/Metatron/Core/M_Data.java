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
	public static Path Resources;
	public static Path Assets;
	public static Path Temp;
	
	static {
		DataPath = Path.of(defaultDataPath, "");
		Resources = Path.of(defaultResourcePath, "");
		Assets = Path.of(defaultAssetsePath, "");
		Temp = Path.of(defaultTempPath, "");
	}
	
	
	public static String toLog()
	{
		String log = "";
		
		log += FS+"\n";
		log += DataPath+"\n";
		log += Resources+"\n";
		log += Temp+"\n";
		
		return log;
	}
	
}
