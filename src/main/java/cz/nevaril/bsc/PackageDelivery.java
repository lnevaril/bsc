package cz.nevaril.bsc;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PackageDelivery {

	private static final ScheduledExecutorService OUTPUT_EXECUTOR = Executors.newSingleThreadScheduledExecutor();
	private static final Pattern PACKAGE_INPUT_PATTER = Pattern.compile("\\d+(\\.\\d{1,3}+)? \\d{5}+");
	private static final PackageStore PACKAGE_STORE = new PackageStore();

	public static void main(String[] args) {
		if (args.length > 0) {
			String inputFileName = args[0];
			try {
				List<Package> packages = FileReader.readPackagesFromFile(inputFileName);
				print("Loaded " + packages.size() + " from file " + inputFileName);
				PACKAGE_STORE.store(packages);
			} catch (Exception e) { // catching all exceptions to prevent application crash in case of invalid data format in provided file
				print("Failed to process provided file with package information. File: " + inputFileName);
			}
		}
		scheduleStatusOutput();
		printInstructions();
		readFromConsole();
	}

	private static void readFromConsole() {
		final Scanner scanner = new Scanner(System.in);
		while (true) {
			String line = scanner.nextLine().trim();
			if (line.equalsIgnoreCase("quit")) {
				System.exit(0);
			}
			Matcher packageInputMatcher = PACKAGE_INPUT_PATTER.matcher(line);
			if (packageInputMatcher.matches()) {
				Package newPackage = getPackageFromInput(line);
				PACKAGE_STORE.store(newPackage);
				print("Stored new package " + newPackage);
			} else {
				print("Invalid format for package input. No new package was stored.");
			}
		}
	}

	private static void printInstructions() {
		print("Add new package in following format:");
		print("[weight: positive number > 0, maximal 3 decimal places, . (dot) as decimal separator][space][postal code: fixed 5 digits]");
		print("Write 'quit' to end program.");
	}

	/**
	 * @param line in format "weight postalCode", weight is expected to be of type double
	 */
	private static Package getPackageFromInput(String line) {
		String[] lineSplit = line.split(" ");
		double weight = Double.parseDouble(lineSplit[0]);
		String postalCode = lineSplit[1];
		return new Package(weight, postalCode);
	}

	private static void print(String s) {
		System.out.println(s);
	}

	private static void scheduleStatusOutput() {
		OUTPUT_EXECUTOR.scheduleAtFixedRate(() -> {
			print("\nCurrent status");
			print("------------------");
			print(PACKAGE_STORE.getCurrentState());
			print("------------------");
		}, 5, 60, TimeUnit.SECONDS);
	}

}
