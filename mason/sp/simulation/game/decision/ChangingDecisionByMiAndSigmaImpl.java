package sp.simulation.game.decision;

import sp.simulation.DoublePair;
import sp.simulation.PersonAgent;
import sp.simulation.game.Game;

/**
 * like in ChangingDecisionByMiImpl agent is changing his decision methods with changing parameter. 
 * This time param says what chances should be for getting value above 0 out of the game.
 * It's getting into account both Mi and Sigma (uncertain Mi and Sigma if agent has non-zero uncertainty)
 */
public class ChangingDecisionByMiAndSigmaImpl implements GameDecisionService{

	double param;
	double startParamValue;
	
	/**
	 * 
	 * @param startParamValue
	 * 
	 * usually 0.5, if someone wants more safety then more than 0.5 when reckless people have less than 0.5
	 */
	public ChangingDecisionByMiAndSigmaImpl(double startParamValue) {
		this.startParamValue = startParamValue;
	}
	@Override
	public boolean ifPlayGame(PersonAgent personAgent, Game game, double trust) {
		if(trust<=0.0) {
			return false;
		}
		else {
			DoublePair uMi = game.getUncertainMi(personAgent.getUncertaintyMi());
			DoublePair uSigma = game.getUncertainSigma(personAgent.getUncertaintySigma());
	    	double meanUMi = (uMi.getR()+uMi.getL())/2;
	    	double meanUSigma = (uSigma.getR()+uSigma.getL())/2;
	    	
	    	if( nonstandardCdf(0.0, meanUMi, meanUSigma) >= param)
	    		return true;
	    	return false;	
		}
	}

	@Override
	public void reactOnGameResult(double result, double wealth) {
		param = startParamValue -wealth/100;//TODO get some logical value
		if(param < startParamValue - 0.25)
			param = startParamValue - 0.25;
		else if(param > startParamValue + 0.25)
			param = startParamValue + 0.25;
		
	}
	
	public String toString() {
		return "changingWithMiAndSigma(param=" + startParamValue + ")";
	}
	
	/**
	 * 
	 * @param x
	 * @param mi
	 * @param sigma
	 * @return probability of getting higher number than given x in non-standard normal distribution
	 * 
	 * firstly standardize distribution and getting value by cdf function
	 */
	public double nonstandardCdf(double x, double mi, double sigma) {
		return (1-cdf((x-mi)/sigma));
	}

	/**
	 * 
	 * Cumulative distribution function
	 * http://en.wikipedia.org/wiki/Normal_distribution
	 * returns probability of getting lower number than given in standard normal distribution
	 */
	public double cdf(double x) {
		double sum = x;
		double value = x;
		for(int i = 1; i < 100; i++) {
			value = value*x*x/(2*i+1);
			sum = sum+value;
		}
		double result = 0.5 + (sum/Math.sqrt(2*Math.PI))*Math.exp(-(x*x)/2);
		return result;
	}

}
