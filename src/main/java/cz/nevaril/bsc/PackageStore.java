package cz.nevaril.bsc;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PackageStore {

	private final Map<String, List<Package>> packagesByDestination = new HashMap<>();
	private static final String WEIGHT_OUTPUT_FORMAT = "%.3f";

	public void store(List<Package> packages) {
		packages.forEach(this::store);
	}

	public void store(Package singlePackage) {
		packagesByDestination.compute(singlePackage.getDestinationCode(), (code, presentPackages) -> {
			if (presentPackages != null) {
				presentPackages.add(singlePackage);
				return presentPackages;
			}
			List<Package> newValue = new ArrayList<>();
			newValue.add(singlePackage);
			return newValue;
		});
	}

	public List<Map.Entry<String, Double>> getStatisticsByDestination() {
		return packagesByDestination.entrySet().stream()
				.map(entry -> new AbstractMap.SimpleEntry<>(entry.getKey(), entry.getValue().stream().mapToDouble(Package::getWeightInKg).sum()))
				.sorted((dest1, dest2) -> dest2.getValue().compareTo(dest1.getValue()))
				.collect(Collectors.toList());
	}

	public String getCurrentState() {
		final StringBuilder stringBuilder = new StringBuilder("Destination Weight\n");
		getStatisticsByDestination().forEach(entry -> {
			stringBuilder.append(entry.getKey());
			stringBuilder.append(" ");
			stringBuilder.append(String.format(WEIGHT_OUTPUT_FORMAT, entry.getValue()));
			stringBuilder.append("\n");
		});
		return stringBuilder.toString();
	}

}
