package FeedForward;

import java.text.DecimalFormat;
import java.util.ArrayList;



public class Neuron {
	
	private ActivationFunction f;
	private MeanSquaredError loss;
	private ArrayList<Neuron> descendants = new ArrayList<Neuron>();
	private ArrayList<Neuron> ancestors = new ArrayList<Neuron>();
	private ArrayList<Double> inputs;
	private ArrayList<Double> weights;
	private double output = 0;
	private double error = 0;

	// these two only have meaning if it's part of a network
	private int layer = 0;
	private int depth = 0;

	// input has no ancestors
	private boolean isInputNode = false;
	// output has no descendants
	private boolean isOutputNode = false;
	private boolean isBias = false;
	private int bias = 1;
	
	/**
	 * 
	 * @param f activation function
	 * @param layer layer where the neuron lives
	 * @param depth depth in the layer where the neuron lives	
	 * @param numInputs number of inputs the neuron takes
	 */
	public Neuron(ActivationFunction f, int layer, int depth, int numInputs){
		this.f = f;
		this.layer = layer;
		this.depth = depth;
		this.inputs = new ArrayList<Double>();
		loss = new MeanSquaredError();
		initializeWeights(numInputs);
	}
	
	private void initializeWeights(int numInputs){
		// creates random initial weights.
		weights = new ArrayList<Double>();

		for (int i = 0; i < numInputs; i++) {
			if (!isInputNode) {
				weights.add(.5 + Math.random() / 10);
			} else {
				weights.add(0.0);
			}
		}
	}

	
	public double calcOutput(){
		if (isInputNode) {
			// input nodes just output their value
			output = inputs.get(0);
		} else {
			output = 0;
			
			// calculates \sum_i w_i x_i
			for (int i = 0; i < inputs.size(); i++) {
				output += (weights.get(i) * inputs.get(i));
			}
			// output nodes do not use sigmoid function
			if (!isOutputNode) {
				output = f.calcfx(output);
			}
			
			// TODO: testing, remove
			if (isOutputNode){
				System.out.println("Inputs :");
				for (int i = 0; i < inputs.size();i++){
					System.out.print(inputs.get(i) + "  ");
				}
			}
		}
		return output;
	}
	
	public void addInput(Double newInput){
		inputs.add(newInput);
	}
	
	/**
	 * Adds input node to this node's list of ancestors, i.e. those nodes from
	 * which it gets inputs
	 * 
	 * @param n
	 *            the ancestor node to add
	 */
	public void addAncestor(Neuron n) {
		if (!isInputNode) {
			ancestors.add(n);
		}
	}

	/**
	 * Adds input node to this node's list of descendants, i.e. those nodes to
	 * which it sends outputs
	 * 
	 * @param n
	 *            the ancestor node to add
	 */
	public void addDescendant(Neuron n) {
		if (!isOutputNode) {
			descendants.add(n);
		}
	}
	
	public String toString() {
		String s = "";
		if (isBias) {
			s += "BIAS ";
		}

		s += "[L:" + layer + ", N:" + depth + "] " + "weights: <";

		// System.out.print("# weights: " + weights.size() + " ");
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		DecimalFormat threeDForm = new DecimalFormat("#.###");
		for (int w = 0; w < weights.size(); w++) {
			double weight = Double.valueOf(twoDForm.format(weights.get(w)));
			s += weight + " ";
		}

		s += ", output: " + Double.valueOf(threeDForm.format(output)) + ">  ";

		return s;
	}
	
	
	public ArrayList<Neuron> getDescendants() {
		return descendants;
	}

	public void setDescendants(ArrayList<Neuron> descendants) {
		this.descendants = descendants;
	}

	public ArrayList<Neuron> getAncestors() {
		return ancestors;
	}

	public void setAncestors(ArrayList<Neuron> ancestors) {
		this.ancestors = ancestors;
	}

	public ArrayList<Double> getInputs() {
		return inputs;
	}

	public void setInputs(ArrayList<Double> inputs) {
		this.inputs = inputs;
	}

	public ArrayList<Double> getWeights() {
		return weights;
	}

	public void setWeights(ArrayList<Double> weights) {
		this.weights = weights;
	}

	public double getOutput() {
		return output;
	}

	public void setOutput(double output) {
		this.output = output;
	}

	public double getError() {
		return error;
	}

	public void setError(double error) {
		this.error = error;
	}

	public int getLayer() {
		return layer;
	}

	public void setLayer(int layer) {
		this.layer = layer;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public boolean isInputNode() {
		return isInputNode;
	}

	public void setInputNode(boolean isInputNode) {
		this.isInputNode = isInputNode;
	}

	public boolean isOutputNode() {
		return isOutputNode;
	}

	public void setOutputNode(boolean isOutputNode) {
		this.isOutputNode = isOutputNode;
	}

	public boolean isBias() {
		return isBias;
	}

	public void setBias(boolean isBias) {
		this.isBias = isBias;
		if (isBias){
			setOutput(bias);
		}
	}

}
