package FeedForward;

public class MeanSquaredError {
	
	// TODO: add error for whole network
	// also possibly constructor
	
	/**
	 * Calculates the derivative of error with respect to output
	 * @param output the neuron's output
	 * @param target the target output
	 * @return
	 */
	public Double calcDerivwrtOutput(Double output, Double target){
		return (target - output);
	}

}
