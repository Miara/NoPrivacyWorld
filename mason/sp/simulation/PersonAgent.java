/*
  Copyright 2006 by Sean Luke and George Mason University
  Licensed under the Academic Free License version 3.0
  See the file "LICENSE" for more information
*/

package sp.simulation;

import java.util.Set;

import ec.util.MersenneTwisterFast;
import sim.engine.*;
import sim.field.grid.*;

public class PersonAgent implements Steppable
{
    private static final long serialVersionUID = 1;

    // the width and height will change later
	private MersenneTwisterFast random = new MersenneTwisterFast();

    private int number;
    private double wealth;
    private double willnessToInitiateGame;//[0,1]
    private double uncertaintyMi;
    private double uncertaintySigma;
    


	/**	in builder pattern we get MersenneTwisterFast random because as there is stated in documentation:
	 * 
	 * "The standard MT199937 seeding algorithm uses one of Donald Knuth’s plain-jane linear
	 * congruential generators to fill the Mersenne Twister’s arrays. This means that for a short while the algorithm
	 * will initially be outputting a (very slightly) lower quality random number stream until it warms up. After
	 * about 624 calls to the generator, it’ll be warmed up sufficiently. As a result, in SimState.start(), MASON
	 * primes the MT generator for you by calling nextInt() 1249 times."
	 */
	
    
    public static class Builder {
    	
    	private final int number;
    	private final MersenneTwisterFast random;
        private double wealth = 0.0;
        private double willnessToInitiateGame = 0.5; //[0,1]
        private double uncertaintyMi = 0.0;
        private double uncertaintySigma = 0.0;
        
		public Builder(int number, MersenneTwisterFast random) {
			this.number = number;
			this.random = random;
		}
		
		public Builder wealth(double wealth) {
			this.wealth = wealth;
			return this;
		}
		
		public Builder willnessToInitiateGame(double willnessToInitiateGame) {
			this.willnessToInitiateGame = willnessToInitiateGame;
			return this;
		}
		
		public Builder uncertaintyMi(double uncertaintyMi) {
			this.uncertaintyMi = uncertaintyMi;
			return this;
		}
		
		public Builder uncertaintySigma(double uncertaintySigma) {
			this.uncertaintySigma = uncertaintySigma;
			return this;
		}
		
		public PersonAgent build() {
			PersonAgent personAgent = new PersonAgent(this);
			System.out.println("create #" + personAgent.getNumber() + 
        			"  wealth: " + personAgent.getWealth() +
        			"  willnessToInitiate: " + personAgent.getWillnessToInitiateGame() +
        			"  uncertaintyMi: " + personAgent.getUncertaintyMi() +
        			"  uncertaintySigma: " + personAgent.getUncertaintySigma()
        			);
			return new PersonAgent(this);
		}  
    }
    
    private PersonAgent(Builder builder) {
    	this.number = builder.number;
    	this.random = builder.random;
    	this.wealth = builder.wealth;
    	this.willnessToInitiateGame = builder.willnessToInitiateGame;
    	this.uncertaintyMi = builder.uncertaintyMi;
    	this.uncertaintySigma = builder.uncertaintySigma;
    }
    
    /**
     * try to initiate the game - sometimes agents doesn't want to, willnessToInitateGame decides how often he would initiate
     * then check if agent wants to play - some games have to small chances to win
     * then propose game to random other agent and he checks if want to play that game
     * then play
     */
    public void step(SimState state){
        SimulationEngine simulationEngine = (SimulationEngine)state;
        
        if(ifInitiateGame()) {
        	Game game = new Game(random);
        	if(ifPlayGame(game)) {
                int personAgentIndex = getRandomPersonNumber(simulationEngine);
        		if(simulationEngine.getPersonAgent(personAgentIndex).playGameWithMe(number, game)) {
        			playGame(game);
        			System.out.println("   Agent #" + number + " played with agent #" + personAgentIndex + 
        					" in game. mi=" + game.getMi() + " sigma=" + game.getSigma() + 
        					" \n         uMi=" + game.getUncertainMi(uncertaintyMi) + 
        					" uSigma=" + game.getUncertainSigma(uncertaintySigma));
            		
        		}
        		else {
                	System.out.println("   Agent #" + number + " won't play with agent #" + personAgentIndex + 
                			" in game. mi=" + game.getMi() + " sigma=" + game.getSigma() +
        					" \n         uMi=" + game.getUncertainMi(uncertaintyMi) + 
        					" uSigma=" + game.getUncertainSigma(uncertaintySigma));
        		}
        	}
        	else {
            	System.out.println("   Agent #" + number + " doesn't like the game." + 
            			" mi=" + game.getMi() + " sigma=" + game.getSigma() +
    					" \n         uMi=" + game.getUncertainMi(uncertaintyMi) + 
    					" uSigma=" + game.getUncertainSigma(uncertaintySigma));
        	}
        }
        else {
        	System.out.println("   Agent #" + number + " won't initiate the game.");
        }
        
    }
    
    /**
     * 
     * @param game
     * @return
     * 
     * simple check if agent want to play - he checks if mean of left constraint and right constraint is greater than 0.0.
     */
    public boolean ifPlayGame(Game game) {
    	//TODO injection of that method
    	DoublePair uMi = game.getUncertainMi(uncertaintyMi);
    	double mean = (uMi.getR()+uMi.getL())/2;
    	
    	if( mean >= 0.0)
    		return true;
    	return false;
    }
    
    public void playGame(Game game) {
    	double value = game.play();
    	changeWealth(value);
    	System.out.println("      Agent #" + number + " has got winning=" + value);
    }
    
    public boolean playGameWithMe(int gameInitiatorNumber, Game game) {
    	if(ifPlayGame(game)) {
    		playGame(game);
    		return true;
    	}
    	else return false;
    }
    private boolean ifInitiateGame() {
    	double x = random.nextDouble();
        return (x < willnessToInitiateGame);
    }
    
    private int getRandomPersonNumber(SimulationEngine se){
    	
    	int randomPersonNumber;
    	do{
    		randomPersonNumber = random.nextInt(se.getPersonsLimit());
    	}while(randomPersonNumber == number);
    	return randomPersonNumber;
    }
    
    public String introduceYourself() {
    	return ("I'm " + number);
    }
    
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public double getWealth() {
		return wealth;
	}
	
	public double changeWealth(double delta) {
		wealth += delta;
		return wealth;
	}
	
	public double getWillnessToInitiateGame() {
		return willnessToInitiateGame;
	}

	public void setWillnessToInitiateGame(double willnessToInitiateGame) {
		this.willnessToInitiateGame = willnessToInitiateGame;
	}
	
	public double getUncertaintyMi() {
		return uncertaintyMi;
	}

	public void setUncertaintyMi(double uncertaintyMi) {
		this.uncertaintyMi = uncertaintyMi;
	}

	public double getUncertaintySigma() {
		return uncertaintySigma;
	}

	public void setUncertaintySigma(double uncertaintySigma) {
		this.uncertaintySigma = uncertaintySigma;
	}
        
}
