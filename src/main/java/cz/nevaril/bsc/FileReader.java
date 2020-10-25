package cz.nevaril.bsc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class FileReader {

	private static final Pattern POSTAL_CODE_PATTERN = Pattern.compile("\\d{5}+");

	/**
	 * @param filePath fully qualified path to file containing package input in following format:
	 *                 [weight: positive number > 0, maximal 3 decimal places, . (dot) as decimal separator][space][postal code: fixed 5 digits]
	 * @throws FileNotFoundException if the specified file does not exist
	 */
	public static List<Package> readPackagesFromFile(String filePath) throws FileNotFoundException {
		List<Package> packages = new ArrayList<>();
		try (Scanner scanner = new Scanner(new File(filePath))) {
			while (scanner.hasNext()) {
				packages.add(new Package(scanner.nextDouble(), scanner.next(POSTAL_CODE_PATTERN) + ""));
			}
		}
		return packages;
	}
}
