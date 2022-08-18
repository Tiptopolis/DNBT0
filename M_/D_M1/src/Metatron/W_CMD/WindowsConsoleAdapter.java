package Metatron.W_CMD;

import static Metatron.Core._M.M_Utils.*;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;

import Metatron.Core.Primitive.Struct.aMap;
import Metatron.Core.Primitive.Struct.aMultiMap;
import Metatron.Core.System.COM.StreamConsumer;
import Metatron.Core.System.Event.iEvent;
import Metatron.Core.System.Event.iEventHandler;
import Metatron.Core._M.M_Utils;

public class WindowsConsoleAdapter implements iEventHandler {

	public static final WindowsConsoleAdapter WIN;
	static {
		WIN = new WindowsConsoleAdapter();
	}

	public ProcessBuilder Main;
	public SystemData Windows;

	private WindowsConsoleAdapter() {
		boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");

		ProcessBuilder builder = new ProcessBuilder();
		this.Main = builder;
		this.Windows = new SystemData(Main);

		if (isWindows) {
			builder.command("cmd.exe", "/c", "dir");
		} else {
			builder.command("sh", "-c", "ls");
		}

		builder.directory(new File(System.getProperty("user.home")));
		try {
			Log(builder.command());
			Process process = builder.start();
			StreamConsumer streamGobbler = new StreamConsumer(process.getInputStream(), M_Utils::Log);
			Executors.newSingleThreadExecutor().submit(streamGobbler);
			int exitCode = process.waitFor();
			assert exitCode == 0;
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}
	}

	public static class SystemData {
		File WorkingDir;
		aMultiMap<String, String> Environment;

		public SystemData(ProcessBuilder p) {
			this.analyze(p);
		}

		public void analyze(ProcessBuilder p) {
			this.WorkingDir = p.directory();
			this.Environment = new aMultiMap(p.environment());
		}
	}

	public String toLog() {
		String log = "";
		log += "* " + Main + "\n";
		log += "* " + this.Windows.WorkingDir + "\n";
		log += "* " + this.Windows.Environment.toLog();

		return log;
	}

	@Override
	public boolean handle(iEvent o) {
		// TODO Auto-generated method stub
		return false;
	}

}
