package api;

public class Connection {
	
	private Node input, output;
	private double weight;
	
	/**
	 * Connects 2 nodes on the network
	 * @param input the node that this connection is accepting a value from
	 * @param output the node that this connection is passing a value to
	 */
	public Connection(Node input, Node output) {
		this.input = input;
		this.output = output;
		weight = Math.random();
	}
	
	public void adjustWeight(double adjust) {
		this.weight+=adjust;
	}
	
	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	public double getWeight() {
		return weight;
	}
	
	public Node getInput() {
		return input;
	}
	
	public Node getOutput() {
		return output;
	}
	
}
