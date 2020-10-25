package cz.nevaril.bsc;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PackageStoreTest {

	@Test
	void getStatisticsByDestination() {
		PackageStore store = new PackageStore();
		store.store(new Package(2.1, "11111"));
		Assertions.assertEquals(1, store.getStatisticsByDestination().size());
		store.store(new Package(178, "22222"));
		Assertions.assertEquals(2, store.getStatisticsByDestination().size());
		store.store(new Package(12.0, "22222"));
		List<Map.Entry<String, Double>> byDestination = store.getStatisticsByDestination();
		Assertions.assertEquals(2, byDestination.size());
		Assertions.assertTrue(byDestination.stream().anyMatch(entry -> Objects.equals(entry.getKey(), "22222") && entry.getValue() == 190));
		Assertions.assertTrue(byDestination.stream().anyMatch(entry -> Objects.equals(entry.getKey(), "11111") && entry.getValue() == 2.1));
	}

	@Test
	void getCurrentState() {
		PackageStore store = new PackageStore();
		store.store(new Package(2.1, "11111"));
		store.store(new Package(178, "22222"));
		store.store(new Package(12.0, "22222"));
		store.store(new Package(210, "33333"));
		store.store(new Package(0.01, "33333"));
		store.store(new Package(210.1, "44444"));
		String currentState = store.getCurrentState();
		Assertions.assertTrue(currentState.indexOf("11111") > currentState.indexOf("22222"));
		Assertions.assertTrue(currentState.indexOf("22222") > currentState.indexOf("33333"));
		Assertions.assertTrue(currentState.indexOf("33333") > currentState.indexOf("44444"));
		Assertions.assertTrue(currentState.contains("11111 2.1"));
		Assertions.assertTrue(currentState.contains("22222 190"));
		Assertions.assertTrue(currentState.contains("33333 210.01"));
		Assertions.assertTrue(currentState.contains("44444 210.1"));
	}
}