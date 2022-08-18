package Metatron.Core;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class M_Sys {

	protected static final FileSystem FS = FileSystems.getDefault();

	protected static final String defaultUserPath = System.getProperty("user.home");
	protected static final String defaultDataPath = defaultUserPath + "\\data";

	protected static final String defaultResourcePath = defaultDataPath + "\\resources";
	protected static final String defaultAssetsePath = defaultDataPath + "\\assets";
	protected static final String defaultTempPath = defaultDataPath + "\\temp";
	protected static final String defaultStoragePath = defaultDataPath + "\\storage";

	public static Path Storage;

	public static Path Data;
	public static Path Resources;
	public static Path Assets;
	public static Path Temp;

	static {
		Storage = Path.of(defaultStoragePath, "");
		Data = Path.of(defaultDataPath, "");
		Resources = Path.of(defaultResourcePath, "");
		Assets = Path.of(defaultAssetsePath, "");
		Temp = Path.of(defaultTempPath, "");
	}

	public static Path PathOf(String path) {
		return Path.of(path, "");
	}

	public static String toLog() {
		String log = "";

		log += FS + "\n";
		log += Data + "\n";
		log += Resources + "\n";
		log += Assets + "\n";
		log += Temp + "\n";

		return log;
	}

}
