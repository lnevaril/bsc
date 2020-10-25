package cz.nevaril.bsc;

public class Package {

	private final double weightInKg;
	private final String destinationCode;

	public Package(double weightInKg, String destinationCode) {
		this.weightInKg = weightInKg;
		this.destinationCode = destinationCode;
	}

	public double getWeightInKg() {
		return weightInKg;
	}

	public String getDestinationCode() {
		return destinationCode;
	}

	@Override
	public String toString() {
		return "Package with weight " + weightInKg + " kg and destination " + destinationCode;
	}

}
