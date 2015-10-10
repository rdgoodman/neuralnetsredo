package FeedForward;


public class Logistic extends ActivationFunction{

	private double xo;
	private double L;
	private double k;
	
	
	/** 
	 * Generates a standard logistic function (k=1, xo=0, L=1)
	 */
	public Logistic(){
		// standard is k=1, xo=0, L=1
		xo = 0;
		//L = Integer.MAX_VALUE;
		L = 1;
		k = 1;
	}
	
	public double calcfx(double x){
		return ((L)/(1 + Math.exp(-1*k*(x-xo))));
	}
	
	public Double partialDeriv(Double neuronOutput){
		return (neuronOutput * (1-neuronOutput));
	}
	
}
