package sp.simulation;

public class DoublePair {

	private double l;
	private double r;
	
//	public DoublePair() {
//	}
	
	public DoublePair(double l, double r) {
		this.l = l;
		this.r = r;
	}
	
	@Override
	public String toString() {
		return "DoublePair [l=" + l + ", r=" + r + "]";
	}

	public double getL() {
		return l;
	}
	
	public void setL(double l) {
		this.l = l;
	}
	
	public double getR() {
		return r;
	}
	
	public void setR(double r) {
		this.r = r;
	}
	
}
