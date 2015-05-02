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

public class SimulationEngine extends SimState
{

    private int personsLimit = 20; //number of personAgents
    private int stepsLimit = 20; // number of steps, each step consists of each agent trying to initiate game with random agent (not himself)

    private ArrayList<PersonAgent> personAgentArray;
    
    private static final long serialVersionUID = 1;

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
    	int actualNumber = 0;
    	
    	p = new PersonAgent.Builder(actualNumber, random).willnessToInitiateGame(0.3).uncertaintyMi(0.5).uncertaintySigma(0.8).build();
		actualNumber = multiplyPeopleAgent(p, 10, actualNumber);
    	for(int number=actualNumber; number<getPersonsLimit(); number++){
    		p = new PersonAgent.Builder(number, random).willnessToInitiateGame(0.5).uncertaintyMi(0.0).build();
    		personAgentArray.add(p);
            schedule.scheduleRepeating(p);
    	}
    }
    
    int multiplyPeopleAgent(PersonAgent patternPerson, int times, int actualNumber) {
    	
    	double wealth = patternPerson.getWealth();
        double willnessToInitiateGame = patternPerson.getWillnessToInitiateGame();
        double uncertaintyMi = patternPerson.getUncertaintyMi();
        double uncertaintySigma = patternPerson.getUncertaintySigma();
        PersonAgent p;
        
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
        System.out.println("START SIMULATION\n\n");
    }

    public void finish(){
    	
        super.finish();
        System.out.println("\n\nSTOP SIMULATION SCORE");
        for(PersonAgent personAgent : personAgentArray) {
        	System.out.println("#" + personAgent.getNumber() + 
        			"  wealth: " + personAgent.getWealth() +
        			"  willnessToInitiate: " + personAgent.getWillnessToInitiateGame() +
        			"  uncertaintyMi: " + personAgent.getUncertaintyMi() +
        			"  uncertaintySigma: " + personAgent.getUncertaintySigma()
        			);
        }
    }
    
    public static void main(String[] args){
    	
        SimulationEngine simulationEngine = new SimulationEngine(System.currentTimeMillis());
        simulationEngine.runSimulation();
        System.exit(0);
    }

    private void runSimulation() {
    	
    	 start();
         while(schedule.getSteps() < stepsLimit){
             System.out.println("Step: " + schedule.getSteps() + " Time: " + schedule.getTime() + " StepsLimit: " + stepsLimit);
             if (!schedule.step(this))
                 break;
//        	 waitSeconds(1);
         }
         finish();
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
