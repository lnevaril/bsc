package cz.nevaril.bsc;

import java.io.FileNotFoundException;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FileReaderTest {

	@Test
	void readPackagesFromExistingFile() throws FileNotFoundException {
		String inputFilepath = getClass().getClassLoader().getResource("testInput.txt").getPath();
		List<Package> packages = FileReader.readPackagesFromFile(inputFilepath);
		Assertions.assertEquals(5, packages.size());
		Assertions.assertEquals(3.4, packages.get(0).getWeightInKg());
		Assertions.assertEquals("08801", packages.get(0).getDestinationCode());
		Assertions.assertEquals(2, packages.get(1).getWeightInKg());
		Assertions.assertEquals("90005", packages.get(1).getDestinationCode());
	}

	@Test
	void readPackagesNotExistingFile() {
		Assertions.assertThrows(FileNotFoundException.class, () -> FileReader.readPackagesFromFile("missing.txt"));
	}

}