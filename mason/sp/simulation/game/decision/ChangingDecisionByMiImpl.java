package sp.simulation.game.decision;

import sp.simulation.DoublePair;
import sp.simulation.PersonAgent;
import sp.simulation.game.Game;
/**
 * works like SimpleDecisionByMiImple but mean must be greater or equal to some param which will be changing as agent will be winning or losing his wealth
 * when he is losing then he is more careful, the opposite when he is winning. param has min and max value so he want be changing forever
 *
 */
public class ChangingDecisionByMiImpl implements GameDecisionService{
	
	double startParamValue;
	
	/**
	 * 
	 * @param startParamValue
	 * 
	 * usually 0.0, if someone wants more safety then more than 0.0 when reckless people have less than 0.0
	 */
	public ChangingDecisionByMiImpl(double startParamValue) {
		this.startParamValue = startParamValue;
	}
	
	@Override
	public boolean ifPlayGame(PersonAgent personAgent, Game game, double trust) {
		if(trust<=0.0) {
			return false;
		}
		else {
			DoublePair uMi = game.getUncertainMi(personAgent.getUncertaintyMi());
	    	double mean = (uMi.getR()+uMi.getL())/2;
	    	
	    	if( mean >= 0.0)
	    		return true;
	    	return false;	
		}
	}

	@Override
	public void reactOnGameResult(double result, double wealth) {
		double param = startParamValue - wealth/100;
		if(param < -1)
			param = -1;
		else if(param > 1)
			param = 1;
	}
	
	public String toString() {
		return "changingWithMi(param=" + startParamValue + ")";
	}

}
