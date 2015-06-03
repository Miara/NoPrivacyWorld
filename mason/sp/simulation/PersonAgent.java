/*
  Copyright 2006 by Sean Luke and George Mason University
  Licensed under the Academic Free License version 3.0
  See the file "LICENSE" for more information
*/

package sp.simulation;

import java.util.logging.Logger;

import ec.util.MersenneTwisterFast;
import sim.engine.*;
import sp.simulation.game.Game;
import sp.simulation.game.GamePair;
import sp.simulation.game.creation.GameCreationService;
import sp.simulation.game.creation.MiSymmetricalSimpleGameCreationImpl;
import sp.simulation.game.creation.SimpleGameCreationImpl;
import sp.simulation.game.decision.GameDecisionService;
import sp.simulation.game.decision.SimpleDecisionByMiImpl;

public class PersonAgent implements Steppable
{
    private static final long serialVersionUID = 1;
    static final Logger logger = Logger.getAnonymousLogger();
    
    // the width and height will change later
	private MersenneTwisterFast random = new MersenneTwisterFast();

    private int number;
    private double wealth;
    private double willnessToInitiateGame;//[0,1]
    private double uncertaintyMi;
    private double uncertaintySigma;
    private Fairness fairness;

	static private double winningInitiator;
	static private double winningPartner;

	private GameDecisionService gameDecisionService;
    private GameCreationService gameCreationService;
    
    private double[] trustTable;

    private double lastWinning;
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
        private Fairness fairness = Fairness.FAIR;
        private GameDecisionService gameDecisionService = new SimpleDecisionByMiImpl();
        private GameCreationService gameCreationService = new SimpleGameCreationImpl();
        
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
		
		public Builder fairness(Fairness fairness) {
			this.fairness = fairness;
			return this;
		}
		
		public Builder gameDecisionService(GameDecisionService gameDecisionService) {
			this.gameDecisionService = gameDecisionService;
			return this;
		}
		
		public Builder gameCreationService(GameCreationService gameCreationService) {
			this.gameCreationService = gameCreationService;
			return this;
		}
		
		public PersonAgent build() {
			PersonAgent personAgent = new PersonAgent(this);
//			System.out.println("create #" + personAgent.getNumber() + 
//        			"  wealth: " + personAgent.getWealth() +
//        			"  willnessToInitiate: " + personAgent.getWillnessToInitiateGame() +
//        			"  uncertaintyMi: " + personAgent.getUncertaintyMi() +
//        			"  uncertaintySigma: " + personAgent.getUncertaintySigma() +
//        			"  fairness: " + personAgent.getFairness() +
//        			"  gameDecisionService: " + "COMMENTTOWRITE" +
//        			"  gameCreationService: " + "COMMENTTOWRITE"
//        			);
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
    	this.fairness = builder.fairness;
    	this.gameDecisionService = builder.gameDecisionService;
    	this.gameCreationService = builder.gameCreationService;
    	
    	trustTable = new double[900];
    	for(int i = 0; i < 900; i++) {
    		trustTable[i]=0.01;
    	}
    }
    
    /**
     * try to initiate the game - sometimes agents doesn't want to, willnessToInitateGame decides how often he would initiate
     * then check if agent wants to play - some games have to small chances to win
     * then propose game to random other agent and he checks if want to play that game
     * then play
     */
    public void step(SimState state){
        SimulationEngine simulationEngine = (SimulationEngine)state;
        
        for(double i : trustTable) {
    		i+=0.1;
    	}
        if(ifInitiateGame()) {
        	GamePair gamePair = gameCreationService.createGame(this, random);
        	Game game = gamePair.getL();
        	Game opponentsGame = gamePair.getR();

            int personAgentIndex = getRandomPersonNumber(simulationEngine);
        	if(gameDecisionService.ifPlayGame(this, game, trustTable[personAgentIndex])) {
        		if(simulationEngine.getPersonAgent(personAgentIndex).playGameWithMe(number, opponentsGame)) {
        			winningInitiator = playGame(game);
        			//TODO przesun¹æ jakoœ do playGame
        			trustTable[personAgentIndex] = Tools.round(trustTable[personAgentIndex]+lastWinning);
//        			System.out.println("   " + this + " has played with ; " + simulationEngine.getPersonAgent(personAgentIndex) + 
//        					" \n      Game1. " + game + " winnings=" + winningInitiator + ";" +
//        					" \n      Game2. " + opponentsGame + " winnings=" + winningPartner + ";");
            		
        		}
        		else {
//        			System.out.println("   " + this + " has tried to play with ; " + simulationEngine.getPersonAgent(personAgentIndex) + 
//        					" \n      Game1. " + game +
//        					" \n      Game2. " + opponentsGame);
        		}
        	}
        	else {
//        		System.out.println("   " + this + " hasn't tried to play with ; " + simulationEngine.getPersonAgent(personAgentIndex) + 
//    					" \n      Game1. " + game +
//    					" \n      Game2. " + opponentsGame);
        	}
        }
        else {
//        	System.out.println("   " + this + " doesn't like to play");
        }
        
    }
       
    public double playGame(Game game) {
    	double value = game.play();
    	changeWealth(value);
    	lastWinning = value;
    	return value;
    }
    
    public boolean playGameWithMe(int gameInitiatorNumber, Game game) {
    	if(gameDecisionService.ifPlayGame(this, game, trustTable[gameInitiatorNumber])) {
    		winningPartner = playGame(game);
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
		wealth = Tools.round(wealth + delta);
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
	
    public Fairness getFairness() {
		return fairness;
	}

	public void setFairness(Fairness fairness) {
		this.fairness = fairness;
	}
	
	public String toString() {
		
		return "Agent number=" + this.number + "; willnessToInitiate=" + this.willnessToInitiateGame + "; uncertaintyMi=" + this.uncertaintyMi + "; uncertaintySigma=" + this.uncertaintySigma 
				+ "; fairness=" + fairness + "; gameCreationService=" + this.gameCreationService + "; gameDecisionService=" + this.gameDecisionService + ";";
	}
}
