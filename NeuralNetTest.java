package FeedForward;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class NeuralNetTest {

//	@Test
//	public void testSingleOutput() {
//		ArrayList<Double> inputs = new ArrayList<Double>();
//		inputs.add(3.0);
//		inputs.add(0.89);
//		inputs.add(-1.0);
//		//inputs.add(0.22);
//		
//		ArrayList<Double> expectedOutput = new ArrayList<Double>();
//		expectedOutput.add(6.0);
//		
//		NeuralNet net = new NeuralNet(1, 3,
//				inputs, expectedOutput,
//				true); 
//		
//		net.generateOutput();
//		net.print();
//	}

	@Test
	public void testTrainSingleOutput() {
		ArrayList<Double> inputs = new ArrayList<Double>();
		inputs.add(3.0);
		inputs.add(0.89);
		inputs.add(-1.0);
		
		ArrayList<Double> expectedOutput = new ArrayList<Double>();
		expectedOutput.add(6.0);
		
		NeuralNet net = new NeuralNet(1, 3,
				inputs, expectedOutput,
				true); 
		
		net.train();
		net.print();
	}
}
