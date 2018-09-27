package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class Network {

	private double[][] weight;

	private int height, length;

	public Network(int height, int length) {
		this.height = height;
		this.length = length;
	}

	public void init() {

		weight = new double[height][length];

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < length; j++) {
				weight[i][j] = Math.random();
			}
		}
	}

	public void adjustWeight(int i, int j, double adj) {
		weight[i][j] += adj;
	}

	public double[][] getWeights() {
		return weight;
	}

	public int getHeight() {
		return height;
	}

	public int getLength() {
		return length;
	}

	public static void main(String[] args) throws Exception {
		Network notGate = new Network(1, 2);

		notGate.init();

		double[] input = { 0, 1 };
		double[] desiredOutput = { 1, 0 };

		Node n1 = new Node();
		Node n2 = new Node();

		Scanner scan = new Scanner(System.in);

		double z1, result1, z2, result2, cost = 1, dCost_dW2, dCost_db, dCost_dy, dY_dZ, dZ2_dA1, dA1_dZ1, dZ1_dW1,
				dCost_dW1, dCost_db1;

		int index = 0;
		int generation = 0;

		BufferedReader inFile = new BufferedReader(
				new FileReader("C:\\Users\\yihon\\Documents\\eclipseWorkspace\\NeuralNetwork\\res\\data.txt"));

		String inLine = null;
		boolean run = true;

		String[] tokens = new String[1];

		if (inFile != null) {
			inLine = inFile.readLine();
		}
		if (inLine != null) {
			tokens = inLine.split("\\s+");

			for (int i = 0; i < tokens.length; i++) {
				System.out.println(tokens[i]);
			}

			if (tokens[0].equals("1")) {
				run = false;
				notGate.weight[0][0] = Double.parseDouble(tokens[1]);
				notGate.weight[0][1] = Double.parseDouble(tokens[2]);
				n1.setBias(Double.parseDouble(tokens[3]));
				n2.setBias(Double.parseDouble(tokens[4]));
				do {
					int x = scan.nextInt();

					z1 = x * notGate.getWeights()[0][0] + n1.getBias();

					result1 = n1.activationFunction(z1);

					z2 = result1 * notGate.getWeights()[0][1] + n2.getBias();

					result2 = n2.activationFunction(z2);

					System.out.println("input: " + x + " output: " + result2);

				} while (true);
			}
		}
		while (cost > 0.0001 && run) {
			System.out.println("Input: " + input[index]);
			System.out.println("Weight 2: " + notGate.getWeights()[0][1]);
			System.out.println("Bias 2: " + n2.getBias());
			z1 = input[index] * notGate.getWeights()[0][0] + n1.getBias();

			result1 = n1.activationFunction(z1);

			z2 = result1 * notGate.getWeights()[0][1] + n2.getBias();

			result2 = n2.activationFunction(z2);
			System.out.println("Result 1: " + result1);

			System.out.println("Result 2: " + result2 + " desired output: " + desiredOutput[index]);

			cost = Math.pow(result2 - desiredOutput[index], 2);

			System.out.println("Cost: " + cost);

			dCost_dy = 2 * (result2 - desiredOutput[index]);

			dY_dZ = (1 / Math.pow(Math.E, -z2)) / Math.pow((1 + 1 / Math.pow(Math.E, -z2)), 2);

			dCost_dW2 = dCost_dy * dY_dZ * result1;

			dCost_db = dCost_dy * dY_dZ;

			dZ2_dA1 = notGate.getWeights()[0][1];

			dA1_dZ1 = (1 / Math.pow(Math.E, -z1)) / Math.pow((1 + 1 / Math.pow(Math.E, -z1)), 2);

			dZ1_dW1 = input[index];

			dCost_dW1 = -dCost_dy * dY_dZ * dZ2_dA1 * dA1_dZ1 * dZ1_dW1;

			dCost_db1 = -dCost_dy * dY_dZ * dZ2_dA1 * dA1_dZ1;

			System.out.println("dCost/dW2: " + dCost_dW2);
			System.out.println("dCost/db: " + dCost_db);

			notGate.adjustWeight(0, 0, dCost_dW1);
			notGate.adjustWeight(0, 1, dCost_dW2 * -1);
			n2.adjustBias(dCost_db * -1);
			n1.adjustBias(dCost_db1);
			if (index == 0)
				index = 1;
			else
				index = 0;
			System.out.println();

			generation++;
		}
		;

		System.out.println("Generation: " + generation);

		System.out.println("Weight 1: " + notGate.getWeights()[0][0] + " Weight 2: " + notGate.getWeights()[0][1]);
		System.out.println("Bias 1: " + n1.getBias() + " Bias 2: " + n2.getBias());

//		File dataFile =  new File("/NeuralNetwork/res/data.txt");
		if (inLine==null) {
			PrintWriter writer = new PrintWriter(
					"C:\\Users\\yihon\\Documents\\eclipseWorkspace\\NeuralNetwork\\res\\data.txt", "UTF-8");
			writer.write(
					"1 " + notGate.weight[0][0] + " " + notGate.weight[0][1] + " " + n1.getBias() + " " + n2.getBias());
			writer.close();
		}
		// dcost/dweight2

//		double[][] input = {{0,0},{1, 0},{0,1},{1,1}};
//		double[] desiredOutput = {1, 0, 0, 1};

		// View data and output
//		for(int i = 0; i<input.length; i++) {
//			System.out.print("i: "+i+" --- ");
//			for(int j = 0; j<input[0].length; j++) {
//				System.out.print(input[i][j]+"---");
//			}
//			System.out.println(" Desired output "+desiredOutput[i]);
//		}
	}

}
