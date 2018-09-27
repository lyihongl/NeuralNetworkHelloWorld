package api;

public class Network {

	private int height, layers, outputs;

	private Node[][] hiddenLayers;

	private Connection[][] conn;
	
	private Node[] output;
	
	public Network(int height, int layers, int outputs) {
		this.height = height;
		this.layers = layers;
		this.outputs = outputs;

		hiddenLayers = new Node[height][layers];
		conn = new Connection[height][layers - 1];

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < layers; j++) {
				hiddenLayers[i][j] = new Node();
				if (j + 1 > layers)
					conn[i][j] = new Connection(hiddenLayers[i][j], hiddenLayers[i][j + 1]);
			}
		}
	}

}
