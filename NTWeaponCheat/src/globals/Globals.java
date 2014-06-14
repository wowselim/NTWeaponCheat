package globals;

import java.io.File;

public class Globals {
	public static String getSeparator() {
		return File.separator;
	}

	public static String getDirectory() {
		String os = System.getProperty("os.name");
		String sep = getSeparator();
		if (os.toLowerCase().contains("linux"))
			return System.getProperty("user.home") + sep + ".config" + sep
					+ "nuclearthrone";
		else if (os.toLowerCase().contains("windows"))
			return System.getProperty("user.home") + sep + "AppData" + sep
					+ "Local" + sep + "nuclearthrone";
		return "";
	}
}
