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
		this.numOutputs = targetOutputs.size();
		
		
		if (logistic) {
			f = new Logistic();
		}
		
		createNetworkNodes();
		createNetworkLinks();
	}
	
	protected void generateOutput(){
		// input layer
		for (Neuron n : nodes.get(0)){
			for (Neuron d : n.getDescendants()) {
				d.addInput(n.getOutput());
//				 System.out.println("Node " + n.getLayer() + n.getDepth()
//				 + " passed an input to node " + d.getLayer() +
//				 d.getDepth());
			}
		}
		
		
		// hidden layers
		for (int i = 1; i < layers - 1; i++) {
			for (Neuron n : nodes.get(i)) {
				// calculates output of this node
				double nodeOutput = n.calcOutput();

				for (Neuron d : n.getDescendants()) {
					// adds this node's output to the inputs of its descendants
					d.addInput(nodeOutput);
//					 System.out.println("Node " + n.getLayer() + n.getDepth()
//					 + " passed an input to node " + d.getLayer() +
//					 d.getDepth());
				}
			}
		}

		// 4. once you hit the output layer, save the output of that layer into
		// a list/array
		for (Neuron n : nodes.get(layers - 1)) {
			outputs.add(n.calcOutput());
		}

		// TODO: testing, remove
		System.out.println();
		System.out.println("Outputs:");
		for (int i = 0; i < outputs.size(); i++) {
			System.out.println(outputs.get(i));
		}
		System.out.println();
	}

	/** Creates all nodes in network */
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
		for (int l = 1; l < layers - 1; l++){
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
			Neuron node = new Neuron(f, layers-1, n, nodes.get(layers-2).size()+1);
			outputNodes.add(node);
		}
		nodes.add(outputNodes);
	}
	
	/**
	 * Sets ancestor/descendant relationships for each node in network
	 */
	protected void createNetworkLinks() {
		// set descendants
		for (int i = 0; i < layers - 1; i++) {
			for (Neuron n1 : nodes.get(i)) {
				// sets every node in the next layer as a descendant
				for (Neuron n2 : nodes.get(i + 1)) {
					// DON'T DOUBLE COUNT BIAS NODE
					if (!n1.isBias()) {
						n1.addDescendant(n2);
					}
				}
			}
		}
		System.out.println();

		// set ancestors
		for (int i = 1; i < layers; i++) {
			for (Neuron n1 : nodes.get(i)) {
				// sets every node in the previous layer as an ancestor
				for (Neuron n2 : nodes.get(i - 1)) {
					n1.addAncestor(n2);
				}
			}
		}
		
		// bias node (last)
		for (int i = 1; i < layers; i++){
			for (Neuron n : nodes.get(i)){
				nodes.get(0).get(0).addDescendant(n);
			}
		}
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
