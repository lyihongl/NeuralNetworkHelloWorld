package api;

public class Network {

	// input to layers weight;
	private double[][] itlWeight;

	// weight[layer] [row] [column]
	private double[][][] weight;

	private double[][] fWeight;

	// bias on each node [layer] [row]
	private double[][] bias;

//	private double[][] data;

	private short layers, height;
	
	private double fBias;

	/**
	 * Initializes weights, and biases for a network of size layer (column) x rows
	 * 
	 * <p>
	 * </p>
	 * 
	 * Has input[0].length inputs
	 * 
	 * <p>
	 * </p>
	 * 
	 * @param layers    Columns, must be at least 1
	 * @param rows      Height of network
	 * @param inputSize number of parameters to input
	 */
	public Network(short layers, short rows, short inputSize) {

		this.layers = layers;
		this.height = rows;
//		this.data = input;
		itlWeight = new double[inputSize][rows];
		if (layers > 1) {
			weight = new double[layers][rows][rows];
			initWeight(weight);
		}
		fWeight = new double[rows][1];
		bias = new double[layers+1][rows];

		initFWeight(fWeight);
		initBias(bias);
		initBias(itlWeight);
		fBias = Math.random();
	}

	/**
	 * Activation function
	 * 
	 * 1/(1+e^-(input+bias))
	 * 
	 * @param input x
	 * @param bias  constant
	 * @return double that is between 0 and 1.
	 */
	public static double aFunction(double input, double bias) {
		return 1 / (1 + Math.pow(Math.E, -input + bias));
	}

	/**
	 * Initializes final weight array
	 * 
	 * @param weight Array to be initalized
	 */
	private void initFWeight(double[][] weight) {
		for (int i = 0; i < weight.length; i++) {
			weight[i][0] = Math.random();
		}
	}

	/**
	 * Initializes weight array
	 * 
	 * @param weight Array to be initialized
	 */
	private void initWeight(double[][][] weight) {
		for (short i = 0; i < weight.length; i++) {
			for (short j = 0; j < weight[0].length; j++) {
				for (short k = 0; k < weight[0][0].length; k++) {
					weight[i][j][k] = Math.random();
				}
			}
		}
	}

	/**
	 * Initializes bias array
	 * 
	 * @param bias Array to be initialized
	 */
	private void initBias(double[][] bias) {
		for (short i = 0; i < bias.length; i++) {
			for (short j = 0; j < bias[0].length; j++) {
				bias[i][j] = Math.random();
			}
		}
	}

	/**
	 * Prints weights in the format
	 * <p>
	 * n [row] [column]
	 * </p>
	 * <br>
	 * Layer:</br>
	 * <br>
	 * n11, n12, ... , n1x</br>
	 * <br>
	 * n21, n22, ... , n2x</br>
	 * <br>
	 * ...</br>
	 * ny1, ny2, ... , nyx
	 */
	public void printWeight() {
		for (short i = 0; i < weight.length; i++) {
			System.out.println("Layer: " + i);
			for (short j = 0; j < weight[0].length; j++) {
				for (short k = 0; k < weight[0][0].length; k++) {
					System.out.printf("%1.4f ", weight[i][j][k]);
				}
				System.out.println();
			}
		}
		System.out.println();
	}

	/**
	 * Prints bias in the format n [layer] [height level]
	 */
	public void printBias() {
		System.out.println("Bias values: ");
		for (short i = 0; i < height; i++) {
			for (short j = 0; j < layers; j++) {
				System.out.printf("%1.4f ", this.bias[j][i]);
			}
			System.out.println();
		}
		System.out.println();
	}
	
	/**
	 * Multiplies matrix a and b together.
	 * <p>
	 * </p>
	 * Automatically checks if a and b are of proper size to be multiplied.
	 * <p>
	 * </p>
	 * 
	 * @param a Matrix of m x n
	 * @param b Matrix of n x p
	 * @return Matrix of size m x p
	 */
	public static double[][] matrixMult(double[][] a, double[][] b) {

		if (a[0].length != b.length) {
			System.out.println("Error: matrix sizes are wrong");
			System.out.println("a :" + a.length);
			System.out.println("b: " + b[0].length);
			return null;
		}

		double[][] result = new double[a.length][b[0].length];

		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < b[0].length; j++) {
				for (int k = 0; k < a[0].length; k++) {
					result[i][j] += a[i][k] * b[k][j];
				}
			}
		}

		return result;
	}

	/**
	 * Prints 2d array
	 * 
	 * @param array
	 */
	public static void printArray(double[][] array) {
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[0].length; j++) {
				System.out.printf("%1.4f ", array[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}
	/**
	 * Prints 2d array
	 * 
	 * @param array
	 * @param name
	 */
	public static void printArray(double[][] array, String name) {
		System.out.println(name);
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[0].length; j++) {
				System.out.printf("%1.4f ", array[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}

	public double[][] getBias() {
		return bias;
	}

	public double[][][] getWeight() {
		return weight;
	}

	public double[][] getFWeight() {
		return fWeight;
	}
	
	public double[][] getItlWeight(){
		return itlWeight;
	}
	
	public double getFBias() {
		return fBias;
	}

	public static void main(String[] args) {
		double[][] input = { 	{ 0, 0 }, 
								{ 1, 0 }, 
								{ 0, 1 }, 
								{ 1, 1 } };
		Network n = new Network((short) 1, (short) 3, (short) 2);
//		n.printWeight();
		n.printBias();
		Network.printArray(n.getItlWeight(), "Initial Weights: ");
		
		double[][] a = Network.matrixMult(input, n.getItlWeight());
		Network.printArray(a, "Values after multiplying by first set of weights");
		
		double[][] z = new double[a.length][a[0].length];
		
//		System.out.println(n.getBias()[0][2]);
		
		for(int i = 0; i<a.length; i++) {
			for(int j = 0; j<a[0].length; j++) {
				z[i][j] = Network.aFunction(a[i][j], n.getBias()[0][j]);
			}
		}
		
		Network.printArray(z, "Value after passing through activation function");
		
		double[][] zf = Network.matrixMult(z, n.getFWeight());
		
		for(int i = 0; i<zf.length; i++) {
			for(int j = 0; j<zf[0].length; j++) {
				zf[i][j] = Network.aFunction(zf[i][j], n.getFBias());
			}
		}
		
		Network.printArray(zf, "Final values passing through output");
		
		
		
//		double[][] b = {{1},
//						{1},
//						{1}};
//		
//		double[][] a = {{1, 2, 3}};

//		System.out.println("a.length: \t"+input.length);
//		System.out.println("a[0].length: \t"+a[0].length);
//		System.out.println("b.length: \t"+b.length);
//		System.out.println("b[0].length: \t"+b[0].length);

//		System.out.println(n.getWeight()[0].length);
//		System.out.println(n.getWeight()[0][0].length);
//		System.out.println(input[0].length);
//
//		double[][] a = Network.matrixMult(input, n.getWeight()[0]);
//
//		double[][] z = new double[a.length][a[0].length];
//
//		for (int i = 0; i < a.length; i++) {
//			for (int j = 0; j < a[0].length; j++) {
//				z[i][j] = Network.aFunction(a[i][j], n.getBias()[0][j]);
//			}
//		}
//
//		double[][] values = Network.matrixMult(z, n.getFWeight());
//
//		System.out.println("Z: ");
//		Network.printArray(z);
//		System.out.println("values: ");
//		Network.printArray(values);

	}

}