package api;

public class Node {
	
	private double bias;

	/**
	 * A single node/perceptron on the network
	 */
	public Node() {
		bias = Math.random();
	}
	
	public double activationFunction(double input) {
		return 1/(1+Math.pow(Math.E, -input));
	}
	
	public void adjustBias(double x) {
		bias+=x;
	}
	
	public void setBias(double x) {
		bias = x;
	}
	
	public double getBias() {
		return bias;
	}
	
}
