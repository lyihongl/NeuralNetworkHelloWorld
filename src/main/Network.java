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

	public static void notGate() throws Exception {
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
		if (inLine == null) {
			PrintWriter writer = new PrintWriter(
					"C:\\Users\\yihon\\Documents\\eclipseWorkspace\\NeuralNetwork\\res\\data.txt", "UTF-8");
			writer.write(
					"1 " + notGate.weight[0][0] + " " + notGate.weight[0][1] + " " + n1.getBias() + " " + n2.getBias());
			writer.close();
		}
	}

	public static void andGate() {
		Network andGate = new Network(1, 2);
		andGate.init();
		double sWeight = Math.random();

		double[][] input = { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } };
		double[] desiredOutput = { 0, 0, 0, 1 };

		double result1, result2, result3, resultf, z1, z2, z3, z4, cost = 1, dCost_dY, dY_dZ4, dZ4_dW3, dCost_dw3,
				dCost_db4, dz4_da3, da3_dz3, dz3_dw2, dCost_dw2, dCost_db3, dCost_dw1, dz3_dw1, dCost_db1, dz3_da1,
				dz3_da2, dCost_db2;

		int index = 0;

		Node n1, n2, n3, n4;
		n1 = new Node();
		n2 = new Node();
		n3 = new Node();
		n4 = new Node();
		
		double avgdw1 = 0, avgdw2 = 0, avgdw3 = 0, avgb1 = 0, avgb2 = 0, avgb3 = 0, avgb4 = 0;
		
		while (true) {
			z1 = input[index][0];
			z2 = input[index][1];

			result1 = n1.activationFunction(z1);
			result2 = n1.activationFunction(z2);

			z3 = (result1 * andGate.getWeights()[0][0] + n1.getBias())
					+ (result2 * andGate.getWeights()[0][1] + n2.getBias()) + n3.getBias();

			result3 = n3.activationFunction(z3);

			z4 = result3 * sWeight + n4.getBias();

			resultf = n4.activationFunction(z4);

			cost = Math.pow(resultf - desiredOutput[index], 2);

			dCost_dY = 2 * (resultf - desiredOutput[index]);

			dY_dZ4 = (1 / Math.pow(Math.E, -z3)) / Math.pow((1 + 1 / Math.pow(Math.E, -z3)), 2);

			dZ4_dW3 = result3;

			dCost_dw3 = dCost_dY * dY_dZ4 * dZ4_dW3;

			dCost_db4 = dCost_dY * dY_dZ4;

			dz4_da3 = sWeight;

			da3_dz3 = (1 / Math.pow(Math.E, -z3)) / Math.pow((1 + 1 / Math.pow(Math.E, -z3)), 2);

			dz3_dw2 = result2;

			dCost_dw2 = dCost_dY * dY_dZ4 * dz4_da3 * da3_dz3 * dz3_dw2;

			dCost_db3 = dCost_dY * dY_dZ4 * dz4_da3 * da3_dz3;

			dz3_dw1 = result1;

			dCost_dw1 = dCost_dY * dY_dZ4 * dz4_da3 * da3_dz3 * dz3_dw1;

			dz3_da1 = andGate.getWeights()[0][0];

			dCost_db1 = dCost_dY * dY_dZ4 * dz4_da3 * da3_dz3 * dz3_da1;

			dz3_da2 = andGate.getWeights()[0][1];

			dCost_db2 = dCost_dY * dY_dZ4 * dz4_da3 * da3_dz3 * dz3_da2;

			
			System.out.println("Input: " + input[index][0] + " " + input[index][1] + " Desired output: "
					+ desiredOutput[index] + " Actual output: " + resultf);
			index++;
			
			avgdw1+=dCost_dw1;
			avgdw2+=dCost_dw2;
			avgdw3+=dCost_dw3;
			avgb1+=dCost_db1;
			avgb2+=dCost_db2;
			avgb3+=dCost_db3;
			avgb4+=dCost_db4;
			
//			System.out.printf(" -- dw1: %f dw2: %f dw3: %f b1: %f b2: %f b3: %f b4: %f cost: %f", dCost_dw1, dCost_dw2, dCost_dw3, dCost_db1, dCost_db2, dCost_db3, dCost_db4, cost);
			System.out.printf("w1: %f, w2: %f, w3: %f, b1: %f, b2: %f, b3: %f, b4: %f \n", andGate.weight[0][0], andGate.weight[0][1], sWeight, n1.getBias(), n2.getBias(), n3.getBias(), n4.getBias());
			
			if(index % 4 == 0) {
				index = 0;
				avgdw1/=4;
				avgdw2/=4;
				avgdw3/=4;
				avgb1/=4;
				avgb2/=4;
				avgb3/=4;
				avgb4/=4;
				
//				System.out.printf("dw1: %f dw2: %f dw3: %f b1: %f b2: %f b3: %f b4: %f", avgdw1, avgdw2, avgdw3, avgb1, avgb2, avgb3, avgb4);
				
				andGate.adjustWeight(0, 0, -avgdw1);
				andGate.adjustWeight(0, 1, -avgdw2);
				sWeight -= avgdw3;
				n1.adjustBias(-avgb1);
				n2.adjustBias(-avgb2);
				n3.adjustBias(-avgb3);
				n4.adjustBias(-avgb4);
			}
			
			System.out.println("\n");
		}

//		System.out.printf("%f %f %f %f %f %f %f", cost, dCost_dw3, dCost_dw2, dCost_dw1, dCost_db4, dCost_db3, dCost_db2);

	}

	public static void main(String[] args) throws Exception {
//		Network.notGate();

		Network.notGate();

	}

}
