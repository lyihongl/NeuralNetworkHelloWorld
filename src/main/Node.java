package main;

public class Node {
	
	private double bias;
	
	public Node() {
		bias = Math.random();
	}
	
	public double activationFunction(double input) {
//		double a = input+bias;
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
