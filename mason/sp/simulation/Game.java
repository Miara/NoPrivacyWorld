package sp.simulation;

import ec.util.MersenneTwisterFast;

public class Game {
	
	private double mi, sigma;
	private MersenneTwisterFast random = new MersenneTwisterFast();
	
	public Game(MersenneTwisterFast random) {
		
		this.random = random;
		randomizeMi();
		randomizeSigma();
	}

	public double play() {
		
		double winning = (random.nextGaussian() * sigma) + mi;
		return winning;
	}
	
	/**
	 * for sake of nextGaussian() mi = 0.0 and sigma = 1.0
	 */
	private void randomizeMi() {
		
		mi = random.nextGaussian();
	}
	
	/**
	 * simple random [0.0, 4.0)
	 */
	private void randomizeSigma() {
		sigma = random.nextDouble()*4;
	}
	
	public void setMi(double mi) {
		this.mi = mi;
	}
	
	public double getMi() {
		return mi;
	}
	
	/**
	 * 
	 * @param double uncertaintyMi
	 * @return DoublePair - left constraint and right constraint
	 * 
	 * randomize number y from (-uncertaintyMi, uncertaintyMi), add to real mi and uncertaintyMi
	 * 
	 * i.e. for uncertaintyMi = 2, and mi = 1, there could be y from [-2,2] 
	 * and as a result minimum DoublePair(-3,1) or maximum DoublePair(1,5) or anything inbetween DoublePair(-0.5, 1.5)
	 * 
	 * real mi always lies in and mean of L and R is always only uncertaintyMi far from real mi
	 * of course L or R could lie 2*uncertaintyMi from real mi 
	 */
	public DoublePair getUncertainMi(double uncertaintyMi) {
		
		double x, y;
		double uncertainty = (random.nextGaussian()-0.5)*uncertaintyMi*2;
		x = mi - uncertaintyMi + uncertainty;
		y = mi + uncertaintyMi + uncertainty;
		return new DoublePair(x, y);
	}

	public void setSigma(double sigma) {
		this.sigma = sigma;
	}

	public double getSigma() {
		return sigma;
	}
	/**
	 * 
	 * @param double uncertaintySigma
	 * @return DoublePair
	 * 
	 * description as for getUncertainMi
	 */
	public DoublePair getUncertainSigma(double uncertaintySigma) {

		double x, y;
		double uncertainty = (random.nextGaussian()-0.5)*uncertaintySigma*2;
		x = mi - uncertaintySigma + uncertainty;
		y = mi + uncertaintySigma + uncertainty;
		return new DoublePair(x, y);
	}
}
