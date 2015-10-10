package FeedForward;

import java.util.ArrayList;

public class NeuralNet {

	private int layers;
	private int numOutputs;
	private int numHiddenNodesPerLayer;
	private ArrayList<Double> outputs;
	private ArrayList<ArrayList<Neuron>> nodes;
	private ArrayList<Double> inputs;
	private ArrayList<Double> targetOutputs;
	private ActivationFunction f;

	/**
	 * Creates a new feed-forward neural network
	 * 
	 * @param hiddenLayers
	 *            number of hidden layers in network
	 * @param numHiddenNodesPerLayer
	 *            number of nodes in each hidden layer
	 * @param numOutputs
	 *            number of outputs for network
	 */
	public NeuralNet(int hiddenLayers, int numHiddenNodesPerLayer,
			ArrayList<Double> inputs, ArrayList<Double> targetOutputs,
			boolean logistic) {
		this.layers = hiddenLayers + 2;
		outputs = new ArrayList<Double>();
		nodes = new ArrayList<ArrayList<Neuron>>();
		this.numHiddenNodesPerLayer = numHiddenNodesPerLayer;
		this.targetOutputs = targetOutputs;
		this.inputs = inputs;
		
		
		if (logistic) {
			f = new Logistic();
		}
		
		createNetworkNodes();
	}

	private void createNetworkNodes() {
		// input layer
		ArrayList<Neuron> inputNodes = new ArrayList<Neuron>();
		// bias node
		Neuron biasNode = new Neuron(f,0,0,0);
		biasNode.setBias(true);
		inputNodes.add(biasNode);
		//other nodes
		for (int n = 1; n <= inputs.size(); n++){
			Neuron node = new Neuron(f,0,n,0);
			node.addInput(inputs.get(n-1));
			node.setOutput(inputs.get(n-1));
			inputNodes.add(node);
			
		}
		nodes.add(inputNodes);
		
		// hidden layers
		for (int l = 1; l < layers -1; l++){
			ArrayList<Neuron> newLayer = new ArrayList<Neuron>();
			for (int n = 0; n < numHiddenNodesPerLayer; n++){
				Neuron node = new Neuron(f,l,n,nodes.get(l-1).size()+1);
				newLayer.add(node);
			}
			nodes.add(newLayer);
		}
		
		// output layer
		ArrayList<Neuron> outputNodes = new ArrayList<Neuron>();
		for (int n = 0; n < numOutputs; n++){
			Neuron node = new Neuron(f,layers-1,n,nodes.get(layers-1).size()+1);
			outputNodes.add(node);
			
		}
		nodes.add(outputNodes);
	}
	
	/** To be used for testing */
	public void print() {
		// goes by layer
		for (int l = 0; l < layers; l++) {
			// for each node in the layer:
			for (int n = 0; n < nodes.get(l).size(); n++) {
				System.out.print(nodes.get(l).get(n).toString());
			}
			System.out.println();
			System.out.println();
		}
	}


}
