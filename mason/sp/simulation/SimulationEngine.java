/*
  Copyright 2006 by Sean Luke and George Mason University
  Licensed under the Academic Free License version 3.0
  See the file "LICENSE" for more information
*/

package sp.simulation;

import java.util.ArrayList;
import java.util.Set;

import sim.engine.*;
import sim.field.grid.*;
import sp.simulation.game.creation.SimpleGameCreationImpl;
import sp.simulation.game.decision.ChangingDecisionByMiAndSigmaImpl;
import sp.simulation.game.decision.ChangingDecisionByMiImpl;
import sp.simulation.game.decision.SimpleDecisionByMiImpl;

public class SimulationEngine extends SimState
{
	
    private int personsLimit = 882; //number of personAgents
    private int stepsLimit = 200; // number of steps, each step consists of each agent trying to initiate game with random agent (not himself)

    private ArrayList<PersonAgent> personAgentArray;
    private ArrayList<PersonAgentGroup> personAgentGroupArray;
    
    private static final long serialVersionUID = 1;

    private double[] uMiThirdClassArr = {0.8, 0.6, 0.4, 0.2, 0.0};
    private double[] uSigmaThirdClassArr = {1.2, 0.9, 0.6, 0.3, 0.0};
    private double[] uMiSecondClassArr = {0.4, 0.3, 0.2, 0.1, 0.0};
    private double[] uSigmaSecondClassArr = {0.6, 0.45, 0.3, 0.15, 0.0};
    private double[] uMiFirstClassArr = {0.0, 0.0, 0.0, 0.0, 0.0};
    private double[] uSigmaFirstClassArr = {0.0, 0.0, 0.0, 0.0, 0.0};
    
    private double uMiThirdClass;
    private double uSigmaThirdClass;
    private double uMiSecondClass;
    private double uSigmaSecondClass;
    private double uMiFirstClass;
    private double uSigmaFirstClass;
    private int cntInGroupThirdClass = 30;
    private int cntInGroupSecondClass = 10;
    private int cntInGroupFirstClass = 2;
    
    
    public SimulationEngine(long seed) {
        super(seed);
    }
    
    /**
     * randomly seed all the people and add them to schedule
     * could use multiplyPeopleAgent method to create sets of identical personAgents
     */
    void seedPersonAgentArray() {
    	
    	PersonAgent p;
    	personAgentArray = new ArrayList<PersonAgent>();
    	personAgentGroupArray = new ArrayList<PersonAgentGroup>();
    	int actualNumber = 0;
    	
    	//3 class of people
    	p = new PersonAgent.Builder(actualNumber, random).willnessToInitiateGame(0.05).uncertaintyMi(uMiThirdClass).uncertaintySigma(uSigmaThirdClass)
				.gameDecisionService(new SimpleDecisionByMiImpl()).build();
    	actualNumber = multiplyPeopleAgent(p, cntInGroupThirdClass, actualNumber);
    	p = new PersonAgent.Builder(actualNumber, random).willnessToInitiateGame(0.05).uncertaintyMi(uMiThirdClass).uncertaintySigma(uSigmaThirdClass)
				.gameDecisionService(new ChangingDecisionByMiImpl(0.0)).build();
    	actualNumber = multiplyPeopleAgent(p, cntInGroupThirdClass, actualNumber);
    	p = new PersonAgent.Builder(actualNumber, random).willnessToInitiateGame(0.05).uncertaintyMi(uMiThirdClass).uncertaintySigma(uSigmaThirdClass)
				.gameDecisionService(new ChangingDecisionByMiImpl(-0.5)).build();
    	actualNumber = multiplyPeopleAgent(p, cntInGroupThirdClass, actualNumber);
    	p = new PersonAgent.Builder(actualNumber, random).willnessToInitiateGame(0.05).uncertaintyMi(uMiThirdClass).uncertaintySigma(uSigmaThirdClass)
				.gameDecisionService(new ChangingDecisionByMiImpl(0.5)).build();
    	actualNumber = multiplyPeopleAgent(p, cntInGroupThirdClass, actualNumber);
    	p = new PersonAgent.Builder(actualNumber, random).willnessToInitiateGame(0.05).uncertaintyMi(uMiThirdClass).uncertaintySigma(uSigmaThirdClass)
    			.gameDecisionService(new ChangingDecisionByMiAndSigmaImpl(0.5)).build();
		actualNumber = multiplyPeopleAgent(p, cntInGroupThirdClass, actualNumber);
    	p = new PersonAgent.Builder(actualNumber, random).willnessToInitiateGame(0.05).uncertaintyMi(uMiThirdClass).uncertaintySigma(uSigmaThirdClass)
    			.gameDecisionService(new ChangingDecisionByMiAndSigmaImpl(0.4)).build();
		actualNumber = multiplyPeopleAgent(p, cntInGroupThirdClass, actualNumber);
    	p = new PersonAgent.Builder(actualNumber, random).willnessToInitiateGame(0.05).uncertaintyMi(uMiThirdClass).uncertaintySigma(uSigmaThirdClass)
    			.gameDecisionService(new ChangingDecisionByMiAndSigmaImpl(0.6)).build();
		actualNumber = multiplyPeopleAgent(p, cntInGroupThirdClass, actualNumber);

    	p = new PersonAgent.Builder(actualNumber, random).willnessToInitiateGame(0.3).uncertaintyMi(uMiThirdClass).uncertaintySigma(uSigmaThirdClass)
				.gameDecisionService(new SimpleDecisionByMiImpl()).build();
    	actualNumber = multiplyPeopleAgent(p, cntInGroupThirdClass, actualNumber);
    	p = new PersonAgent.Builder(actualNumber, random).willnessToInitiateGame(0.3).uncertaintyMi(uMiThirdClass).uncertaintySigma(uSigmaThirdClass)
				.gameDecisionService(new ChangingDecisionByMiImpl(0.0)).build();
    	actualNumber = multiplyPeopleAgent(p, cntInGroupThirdClass, actualNumber);
    	p = new PersonAgent.Builder(actualNumber, random).willnessToInitiateGame(0.3).uncertaintyMi(uMiThirdClass).uncertaintySigma(uSigmaThirdClass)
				.gameDecisionService(new ChangingDecisionByMiImpl(-0.5)).build();
    	actualNumber = multiplyPeopleAgent(p, cntInGroupThirdClass, actualNumber);
    	p = new PersonAgent.Builder(actualNumber, random).willnessToInitiateGame(0.3).uncertaintyMi(uMiThirdClass).uncertaintySigma(uSigmaThirdClass)
				.gameDecisionService(new ChangingDecisionByMiImpl(0.5)).build();
    	actualNumber = multiplyPeopleAgent(p, cntInGroupThirdClass, actualNumber);
    	p = new PersonAgent.Builder(actualNumber, random).willnessToInitiateGame(0.3).uncertaintyMi(uMiThirdClass).uncertaintySigma(uSigmaThirdClass)
    			.gameDecisionService(new ChangingDecisionByMiAndSigmaImpl(0.5)).build();
		actualNumber = multiplyPeopleAgent(p, cntInGroupThirdClass, actualNumber);
    	p = new PersonAgent.Builder(actualNumber, random).willnessToInitiateGame(0.3).uncertaintyMi(uMiThirdClass).uncertaintySigma(uSigmaThirdClass)
    			.gameDecisionService(new ChangingDecisionByMiAndSigmaImpl(0.4)).build();
		actualNumber = multiplyPeopleAgent(p, cntInGroupThirdClass, actualNumber);
    	p = new PersonAgent.Builder(actualNumber, random).willnessToInitiateGame(0.3).uncertaintyMi(uMiThirdClass).uncertaintySigma(uSigmaThirdClass)
    			.gameDecisionService(new ChangingDecisionByMiAndSigmaImpl(0.6)).build();
		actualNumber = multiplyPeopleAgent(p, cntInGroupThirdClass, actualNumber);

    	p = new PersonAgent.Builder(actualNumber, random).willnessToInitiateGame(0.7).uncertaintyMi(uMiThirdClass).uncertaintySigma(uSigmaThirdClass)
				.gameDecisionService(new SimpleDecisionByMiImpl()).build();
    	actualNumber = multiplyPeopleAgent(p, cntInGroupThirdClass, actualNumber);
    	p = new PersonAgent.Builder(actualNumber, random).willnessToInitiateGame(0.7).uncertaintyMi(uMiThirdClass).uncertaintySigma(uSigmaThirdClass)
				.gameDecisionService(new ChangingDecisionByMiImpl(0.0)).build();
    	actualNumber = multiplyPeopleAgent(p, cntInGroupThirdClass, actualNumber);
    	p = new PersonAgent.Builder(actualNumber, random).willnessToInitiateGame(0.7).uncertaintyMi(uMiThirdClass).uncertaintySigma(uSigmaThirdClass)
				.gameDecisionService(new ChangingDecisionByMiImpl(-0.5)).build();
    	actualNumber = multiplyPeopleAgent(p, cntInGroupThirdClass, actualNumber);
    	p = new PersonAgent.Builder(actualNumber, random).willnessToInitiateGame(0.7).uncertaintyMi(uMiThirdClass).uncertaintySigma(uSigmaThirdClass)
				.gameDecisionService(new ChangingDecisionByMiImpl(0.5)).build();
    	actualNumber = multiplyPeopleAgent(p, cntInGroupThirdClass, actualNumber);
    	p = new PersonAgent.Builder(actualNumber, random).willnessToInitiateGame(0.7).uncertaintyMi(uMiThirdClass).uncertaintySigma(uSigmaThirdClass)
    			.gameDecisionService(new ChangingDecisionByMiAndSigmaImpl(0.5)).build();
		actualNumber = multiplyPeopleAgent(p, cntInGroupThirdClass, actualNumber);
    	p = new PersonAgent.Builder(actualNumber, random).willnessToInitiateGame(0.7).uncertaintyMi(uMiThirdClass).uncertaintySigma(uSigmaThirdClass)
    			.gameDecisionService(new ChangingDecisionByMiAndSigmaImpl(0.4)).build();
		actualNumber = multiplyPeopleAgent(p, cntInGroupThirdClass, actualNumber);
    	p = new PersonAgent.Builder(actualNumber, random).willnessToInitiateGame(0.7).uncertaintyMi(uMiThirdClass).uncertaintySigma(uSigmaThirdClass)
    			.gameDecisionService(new ChangingDecisionByMiAndSigmaImpl(0.6)).build();
		actualNumber = multiplyPeopleAgent(p, cntInGroupThirdClass, actualNumber);
		
		//2 class of people
		p = new PersonAgent.Builder(actualNumber, random).willnessToInitiateGame(0.05).uncertaintyMi(uMiSecondClass).uncertaintySigma(uSigmaSecondClass)
				.gameDecisionService(new SimpleDecisionByMiImpl()).build();
    	actualNumber = multiplyPeopleAgent(p, cntInGroupSecondClass, actualNumber);
    	p = new PersonAgent.Builder(actualNumber, random).willnessToInitiateGame(0.05).uncertaintyMi(uMiSecondClass).uncertaintySigma(uSigmaSecondClass)
				.gameDecisionService(new ChangingDecisionByMiImpl(0.0)).build();
    	actualNumber = multiplyPeopleAgent(p, cntInGroupSecondClass, actualNumber);
    	p = new PersonAgent.Builder(actualNumber, random).willnessToInitiateGame(0.05).uncertaintyMi(uMiSecondClass).uncertaintySigma(uSigmaSecondClass)
				.gameDecisionService(new ChangingDecisionByMiImpl(-0.5)).build();
    	actualNumber = multiplyPeopleAgent(p, cntInGroupSecondClass, actualNumber);
    	p = new PersonAgent.Builder(actualNumber, random).willnessToInitiateGame(0.05).uncertaintyMi(uMiSecondClass).uncertaintySigma(uSigmaSecondClass)
				.gameDecisionService(new ChangingDecisionByMiImpl(0.5)).build();
    	actualNumber = multiplyPeopleAgent(p, cntInGroupSecondClass, actualNumber);
    	p = new PersonAgent.Builder(actualNumber, random).willnessToInitiateGame(0.05).uncertaintyMi(uMiSecondClass).uncertaintySigma(uSigmaSecondClass)
    			.gameDecisionService(new ChangingDecisionByMiAndSigmaImpl(0.5)).build();
		actualNumber = multiplyPeopleAgent(p, cntInGroupSecondClass, actualNumber);
    	p = new PersonAgent.Builder(actualNumber, random).willnessToInitiateGame(0.05).uncertaintyMi(uMiSecondClass).uncertaintySigma(uSigmaSecondClass)
    			.gameDecisionService(new ChangingDecisionByMiAndSigmaImpl(0.4)).build();
		actualNumber = multiplyPeopleAgent(p, cntInGroupSecondClass, actualNumber);
    	p = new PersonAgent.Builder(actualNumber, random).willnessToInitiateGame(0.05).uncertaintyMi(uMiSecondClass).uncertaintySigma(uSigmaSecondClass)
    			.gameDecisionService(new ChangingDecisionByMiAndSigmaImpl(0.6)).build();
		actualNumber = multiplyPeopleAgent(p, cntInGroupSecondClass, actualNumber);

    	p = new PersonAgent.Builder(actualNumber, random).willnessToInitiateGame(0.3).uncertaintyMi(uMiSecondClass).uncertaintySigma(uSigmaSecondClass)
				.gameDecisionService(new SimpleDecisionByMiImpl()).build();
    	actualNumber = multiplyPeopleAgent(p, cntInGroupSecondClass, actualNumber);
    	p = new PersonAgent.Builder(actualNumber, random).willnessToInitiateGame(0.3).uncertaintyMi(uMiSecondClass).uncertaintySigma(uSigmaSecondClass)
				.gameDecisionService(new ChangingDecisionByMiImpl(0.0)).build();
    	actualNumber = multiplyPeopleAgent(p, cntInGroupSecondClass, actualNumber);
    	p = new PersonAgent.Builder(actualNumber, random).willnessToInitiateGame(0.3).uncertaintyMi(uMiSecondClass).uncertaintySigma(uSigmaSecondClass)
				.gameDecisionService(new ChangingDecisionByMiImpl(-0.5)).build();
    	actualNumber = multiplyPeopleAgent(p, cntInGroupSecondClass, actualNumber);
    	p = new PersonAgent.Builder(actualNumber, random).willnessToInitiateGame(0.3).uncertaintyMi(uMiSecondClass).uncertaintySigma(uSigmaSecondClass)
				.gameDecisionService(new ChangingDecisionByMiImpl(0.5)).build();
    	actualNumber = multiplyPeopleAgent(p, cntInGroupSecondClass, actualNumber);
    	p = new PersonAgent.Builder(actualNumber, random).willnessToInitiateGame(0.3).uncertaintyMi(uMiSecondClass).uncertaintySigma(uSigmaSecondClass)
    			.gameDecisionService(new ChangingDecisionByMiAndSigmaImpl(0.5)).build();
		actualNumber = multiplyPeopleAgent(p, cntInGroupSecondClass, actualNumber);
    	p = new PersonAgent.Builder(actualNumber, random).willnessToInitiateGame(0.3).uncertaintyMi(uMiSecondClass).uncertaintySigma(uSigmaSecondClass)
    			.gameDecisionService(new ChangingDecisionByMiAndSigmaImpl(0.4)).build();
		actualNumber = multiplyPeopleAgent(p, cntInGroupSecondClass, actualNumber);
    	p = new PersonAgent.Builder(actualNumber, random).willnessToInitiateGame(0.3).uncertaintyMi(uMiSecondClass).uncertaintySigma(uSigmaSecondClass)
    			.gameDecisionService(new ChangingDecisionByMiAndSigmaImpl(0.6)).build();
		actualNumber = multiplyPeopleAgent(p, cntInGroupSecondClass, actualNumber);

    	p = new PersonAgent.Builder(actualNumber, random).willnessToInitiateGame(0.7).uncertaintyMi(uMiSecondClass).uncertaintySigma(uSigmaSecondClass)
				.gameDecisionService(new SimpleDecisionByMiImpl()).build();
    	actualNumber = multiplyPeopleAgent(p, cntInGroupSecondClass, actualNumber);
    	p = new PersonAgent.Builder(actualNumber, random).willnessToInitiateGame(0.7).uncertaintyMi(uMiSecondClass).uncertaintySigma(uSigmaSecondClass)
				.gameDecisionService(new ChangingDecisionByMiImpl(0.0)).build();
    	actualNumber = multiplyPeopleAgent(p, cntInGroupSecondClass, actualNumber);
    	p = new PersonAgent.Builder(actualNumber, random).willnessToInitiateGame(0.7).uncertaintyMi(uMiSecondClass).uncertaintySigma(uSigmaSecondClass)
				.gameDecisionService(new ChangingDecisionByMiImpl(-0.5)).build();
    	actualNumber = multiplyPeopleAgent(p, cntInGroupSecondClass, actualNumber);
    	p = new PersonAgent.Builder(actualNumber, random).willnessToInitiateGame(0.7).uncertaintyMi(uMiSecondClass).uncertaintySigma(uSigmaSecondClass)
				.gameDecisionService(new ChangingDecisionByMiImpl(0.5)).build();
    	actualNumber = multiplyPeopleAgent(p, cntInGroupSecondClass, actualNumber);
    	p = new PersonAgent.Builder(actualNumber, random).willnessToInitiateGame(0.7).uncertaintyMi(uMiSecondClass).uncertaintySigma(uSigmaSecondClass)
    			.gameDecisionService(new ChangingDecisionByMiAndSigmaImpl(0.5)).build();
		actualNumber = multiplyPeopleAgent(p, cntInGroupSecondClass, actualNumber);
    	p = new PersonAgent.Builder(actualNumber, random).willnessToInitiateGame(0.7).uncertaintyMi(uMiSecondClass).uncertaintySigma(uSigmaSecondClass)
    			.gameDecisionService(new ChangingDecisionByMiAndSigmaImpl(0.4)).build();
		actualNumber = multiplyPeopleAgent(p, cntInGroupSecondClass, actualNumber);
    	p = new PersonAgent.Builder(actualNumber, random).willnessToInitiateGame(0.7).uncertaintyMi(uMiSecondClass).uncertaintySigma(uSigmaSecondClass)
    			.gameDecisionService(new ChangingDecisionByMiAndSigmaImpl(0.6)).build();
		actualNumber = multiplyPeopleAgent(p, cntInGroupSecondClass, actualNumber);
		
		//1 class agents
		p = new PersonAgent.Builder(actualNumber, random).willnessToInitiateGame(0.05).uncertaintyMi(uMiFirstClass).uncertaintySigma(uSigmaFirstClass)
				.gameDecisionService(new SimpleDecisionByMiImpl()).build();
    	actualNumber = multiplyPeopleAgent(p, cntInGroupFirstClass, actualNumber);
    	p = new PersonAgent.Builder(actualNumber, random).willnessToInitiateGame(0.05).uncertaintyMi(uMiFirstClass).uncertaintySigma(uSigmaFirstClass)
				.gameDecisionService(new ChangingDecisionByMiImpl(0.0)).build();
    	actualNumber = multiplyPeopleAgent(p, cntInGroupFirstClass, actualNumber);
    	p = new PersonAgent.Builder(actualNumber, random).willnessToInitiateGame(0.05).uncertaintyMi(uMiFirstClass).uncertaintySigma(uSigmaFirstClass)
				.gameDecisionService(new ChangingDecisionByMiImpl(-0.5)).build();
    	actualNumber = multiplyPeopleAgent(p, cntInGroupFirstClass, actualNumber);
    	p = new PersonAgent.Builder(actualNumber, random).willnessToInitiateGame(0.05).uncertaintyMi(uMiFirstClass).uncertaintySigma(uSigmaFirstClass)
				.gameDecisionService(new ChangingDecisionByMiImpl(0.5)).build();
    	actualNumber = multiplyPeopleAgent(p, cntInGroupFirstClass, actualNumber);
    	p = new PersonAgent.Builder(actualNumber, random).willnessToInitiateGame(0.05).uncertaintyMi(uMiFirstClass).uncertaintySigma(uSigmaFirstClass)
    			.gameDecisionService(new ChangingDecisionByMiAndSigmaImpl(0.5)).build();
		actualNumber = multiplyPeopleAgent(p, cntInGroupFirstClass, actualNumber);
    	p = new PersonAgent.Builder(actualNumber, random).willnessToInitiateGame(0.05).uncertaintyMi(uMiFirstClass).uncertaintySigma(uSigmaFirstClass)
    			.gameDecisionService(new ChangingDecisionByMiAndSigmaImpl(0.4)).build();
		actualNumber = multiplyPeopleAgent(p, cntInGroupFirstClass, actualNumber);
    	p = new PersonAgent.Builder(actualNumber, random).willnessToInitiateGame(0.05).uncertaintyMi(uMiFirstClass).uncertaintySigma(uSigmaFirstClass)
    			.gameDecisionService(new ChangingDecisionByMiAndSigmaImpl(0.6)).build();
		actualNumber = multiplyPeopleAgent(p, cntInGroupFirstClass, actualNumber);

    	p = new PersonAgent.Builder(actualNumber, random).willnessToInitiateGame(0.3).uncertaintyMi(uMiFirstClass).uncertaintySigma(uSigmaFirstClass)
				.gameDecisionService(new SimpleDecisionByMiImpl()).build();
    	actualNumber = multiplyPeopleAgent(p, cntInGroupFirstClass, actualNumber);
    	p = new PersonAgent.Builder(actualNumber, random).willnessToInitiateGame(0.3).uncertaintyMi(uMiFirstClass).uncertaintySigma(uSigmaFirstClass)
				.gameDecisionService(new ChangingDecisionByMiImpl(0.0)).build();
    	actualNumber = multiplyPeopleAgent(p, cntInGroupFirstClass, actualNumber);
    	p = new PersonAgent.Builder(actualNumber, random).willnessToInitiateGame(0.3).uncertaintyMi(uMiFirstClass).uncertaintySigma(uSigmaFirstClass)
				.gameDecisionService(new ChangingDecisionByMiImpl(-0.5)).build();
    	actualNumber = multiplyPeopleAgent(p, cntInGroupFirstClass, actualNumber);
    	p = new PersonAgent.Builder(actualNumber, random).willnessToInitiateGame(0.3).uncertaintyMi(uMiFirstClass).uncertaintySigma(uSigmaFirstClass)
				.gameDecisionService(new ChangingDecisionByMiImpl(0.5)).build();
    	actualNumber = multiplyPeopleAgent(p, cntInGroupFirstClass, actualNumber);
    	p = new PersonAgent.Builder(actualNumber, random).willnessToInitiateGame(0.3).uncertaintyMi(uMiFirstClass).uncertaintySigma(uSigmaFirstClass)
    			.gameDecisionService(new ChangingDecisionByMiAndSigmaImpl(0.5)).build();
		actualNumber = multiplyPeopleAgent(p, cntInGroupFirstClass, actualNumber);
    	p = new PersonAgent.Builder(actualNumber, random).willnessToInitiateGame(0.3).uncertaintyMi(uMiFirstClass).uncertaintySigma(uSigmaFirstClass)
    			.gameDecisionService(new ChangingDecisionByMiAndSigmaImpl(0.4)).build();
		actualNumber = multiplyPeopleAgent(p, cntInGroupFirstClass, actualNumber);
    	p = new PersonAgent.Builder(actualNumber, random).willnessToInitiateGame(0.3).uncertaintyMi(uMiFirstClass).uncertaintySigma(uSigmaFirstClass)
    			.gameDecisionService(new ChangingDecisionByMiAndSigmaImpl(0.6)).build();
		actualNumber = multiplyPeopleAgent(p, cntInGroupFirstClass, actualNumber);

    	p = new PersonAgent.Builder(actualNumber, random).willnessToInitiateGame(0.7).uncertaintyMi(uMiFirstClass).uncertaintySigma(uSigmaFirstClass)
				.gameDecisionService(new SimpleDecisionByMiImpl()).build();
    	actualNumber = multiplyPeopleAgent(p, cntInGroupFirstClass, actualNumber);
    	p = new PersonAgent.Builder(actualNumber, random).willnessToInitiateGame(0.7).uncertaintyMi(uMiFirstClass).uncertaintySigma(uSigmaFirstClass)
				.gameDecisionService(new ChangingDecisionByMiImpl(0.0)).build();
    	actualNumber = multiplyPeopleAgent(p, cntInGroupFirstClass, actualNumber);
    	p = new PersonAgent.Builder(actualNumber, random).willnessToInitiateGame(0.7).uncertaintyMi(uMiFirstClass).uncertaintySigma(uSigmaFirstClass)
				.gameDecisionService(new ChangingDecisionByMiImpl(-0.5)).build();
    	actualNumber = multiplyPeopleAgent(p, cntInGroupFirstClass, actualNumber);
    	p = new PersonAgent.Builder(actualNumber, random).willnessToInitiateGame(0.7).uncertaintyMi(uMiFirstClass).uncertaintySigma(uSigmaFirstClass)
				.gameDecisionService(new ChangingDecisionByMiImpl(0.5)).build();
    	actualNumber = multiplyPeopleAgent(p, cntInGroupFirstClass, actualNumber);
    	p = new PersonAgent.Builder(actualNumber, random).willnessToInitiateGame(0.7).uncertaintyMi(uMiFirstClass).uncertaintySigma(uSigmaFirstClass)
    			.gameDecisionService(new ChangingDecisionByMiAndSigmaImpl(0.5)).build();
		actualNumber = multiplyPeopleAgent(p, cntInGroupFirstClass, actualNumber);
    	p = new PersonAgent.Builder(actualNumber, random).willnessToInitiateGame(0.7).uncertaintyMi(uMiFirstClass).uncertaintySigma(uSigmaFirstClass)
    			.gameDecisionService(new ChangingDecisionByMiAndSigmaImpl(0.4)).build();
		actualNumber = multiplyPeopleAgent(p, cntInGroupFirstClass, actualNumber);
    	p = new PersonAgent.Builder(actualNumber, random).willnessToInitiateGame(0.7).uncertaintyMi(uMiFirstClass).uncertaintySigma(uSigmaFirstClass)
    			.gameDecisionService(new ChangingDecisionByMiAndSigmaImpl(0.6)).build();
		actualNumber = multiplyPeopleAgent(p, cntInGroupFirstClass, actualNumber);
		
//    	for(int number=actualNumber; number<getPersonsLimit(); number++){
//    		p = new PersonAgent.Builder(number, random).willnessToInitiateGame(0.5).uncertaintyMi(0.0).fairness(Fairness.UNFAIR).build();
//    		personAgentArray.add(p);
//            schedule.scheduleRepeating(p);
//    	}
    }
    /**
     * 
     * @param patternPerson
     * @param times
     * @param actualNumber
     * @return
     * 
     * multiply one agent and build group to aggregate wealth for that group
     */
    int multiplyPeopleAgent(PersonAgent patternPerson, int times, int actualNumber) {
    	
    	double wealth = patternPerson.getWealth();
        double willnessToInitiateGame = patternPerson.getWillnessToInitiateGame();
        double uncertaintyMi = patternPerson.getUncertaintyMi();
        double uncertaintySigma = patternPerson.getUncertaintySigma();
        PersonAgent p;
        
        personAgentGroupArray.add(new PersonAgentGroup(patternPerson, times, actualNumber, this));
        
    	for(int number=actualNumber; (number<actualNumber+times) && (number<getPersonsLimit()); number++) {
    		p = new PersonAgent.Builder(number, random).wealth(wealth).willnessToInitiateGame(willnessToInitiateGame)
    				.uncertaintyMi(uncertaintyMi).uncertaintySigma(uncertaintySigma)
    				.build();
    		personAgentArray.add(p);
            schedule.scheduleRepeating(p);
    	}
    	return actualNumber + times;
    }
    
    public void start(){
    	
        super.start();
        seedPersonAgentArray();
        System.out.println("START SIMULATION");
    }

    public void finish(){
    	
        super.finish();
        System.out.println("STOP SIMULATION SCORE");
//        for(PersonAgent personAgent : personAgentArray) {
//        	System.out.println("#" + personAgent.getNumber() + 
//        			"  wealth: " + personAgent.getWealth() +
//        			"  willnessToInitiate: " + personAgent.getWillnessToInitiateGame() +
//        			"  uncertaintyMi: " + personAgent.getUncertaintyMi() +
//        			"  uncertaintySigma: " + personAgent.getUncertaintySigma() +
//        			"  fairness: " + personAgent.getFairness()
//        			);
//        }
        for(PersonAgentGroup pag : personAgentGroupArray) {
        	System.out.println(pag);
        }
    }
    
    public static void main(String[] args){
    	
        SimulationEngine simulationEngine = new SimulationEngine(System.currentTimeMillis());
        simulationEngine.runSimulation();
        System.exit(0);
    }

    private void runSimulation() {
    	
    	for(int i = 0; i < 5; i++) {

    	    uMiFirstClass = uMiFirstClassArr[i];
    	    uSigmaFirstClass = uSigmaFirstClassArr[i];
    	    uMiSecondClass = uMiSecondClassArr[i];
    	    uSigmaSecondClass = uSigmaSecondClassArr[i];
    	    uMiThirdClass = uMiThirdClassArr[i];
    	    uSigmaThirdClass = uSigmaThirdClassArr[i];
    	    
	    	start();
	        while(schedule.getSteps() < stepsLimit){
	//            System.out.println("Step: " + schedule.getSteps() + " Time: " + schedule.getTime() + " StepsLimit: " + stepsLimit);
	            if (!schedule.step(this))
	                break;
	//        	 waitSeconds(1);
	        }
	        finish();
    	}
    }
    
    private void waitSeconds(int x) {
    	
   	 	try {
			Thread.sleep(1000 *x);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	public int getStepsLimit() {
		return stepsLimit;
	}

	public void setStepsLimit(int stepsLimit) {
		this.stepsLimit = stepsLimit;
	}

	public int getPersonsLimit() {
		return personsLimit;
	}

	public void setPersonsLimit(int personsLimit) {
		this.personsLimit = personsLimit;
	}    
	
	public PersonAgent getPersonAgent(int index) {
		return personAgentArray.get(index);
	}
	
	public void setPersonAgent(int index, PersonAgent personAgent) {
		personAgentArray.set(index, personAgent);
	}

  
}
