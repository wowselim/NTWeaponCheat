package util;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Modifier {
	public static String setWeapon(int weaponID) {
		boolean error = false;
		String appdata = System.getProperty("user.home");
		appdata += "\\AppData\\Local\\nuclearthrone";

		List<String> fileNames = new ArrayList<>();
		try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(Paths
				.get(appdata))) {
			for (Path path : dirStream) {
				String directory = path.toString();
				if (directory.contains("nuclearthrone")
						&& directory.endsWith(".sav"))
					fileNames.add(path.toString());
			}
		} catch (IOException e) {
			System.out.println("Error reading files.");
			error = true;
		}

		Collections.sort(fileNames);
		final Path versionFile = Paths.get(fileNames.get(fileNames.size() - 1));
		System.out.println("Path of file: " + versionFile.toString());
		List<String> lines = null;

		try {
			lines = Files.readAllLines(versionFile);
		} catch (IOException e) {
			System.out.println("Error reading current version file.");
			error = true;
		}

		boolean toggle = true;
		boolean found = false;
		for (int i = 0; i < lines.size(); i++) {
			if (found && toggle) {
				lines.set(i, String.valueOf(weaponID));
			}

			if (found)
				toggle = !toggle;

			if (lines.get(i).equals("DATA"))
				found = true;
		}

		try {
			if (found && versionFile.toString().endsWith(".sav"))
				Files.write(versionFile, lines);
			else {
				System.out.println("Could not identify version file.");
				error = true;
			}
		} catch (IOException e) {
			System.out.println("Error writing version file.");
			error = true;
		}

		if (!error)
			return "Version file has been successfully modified.";
		else
			return "Something's wrong. Check console output.";

	}
}
