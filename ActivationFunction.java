package FeedForward;

public abstract class ActivationFunction {

	abstract double calcfx(double x);

	abstract Double partialDeriv(Double output);
}
