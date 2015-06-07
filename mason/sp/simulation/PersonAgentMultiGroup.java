package sp.simulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonAgentMultiGroup {
	static private int maxGroupId=0;
	private int groupId;
	private PersonAgent personAgent;
	private List<Integer> personAgentIdList = new ArrayList<Integer>();
	private double meanWealth;
	private SimulationEngine se;
	static private Map<Integer, PersonAgentMultiGroup> pamgMap = new HashMap<Integer, PersonAgentMultiGroup>();
	static private int worldNumber;

	private PersonAgentMultiGroup(int groupId, SimulationEngine se) {
		this.groupId = groupId;
		this.se = se;
	}
	
	public static void clear() {
		pamgMap.clear();
	}
	
	public static PersonAgentMultiGroup getPersonAgentMultiGroup(int groupId, SimulationEngine se) {
		if(!pamgMap.containsKey(new Integer(groupId))) {
			pamgMap.put(new Integer(groupId), new PersonAgentMultiGroup(groupId, se));
		}
		return pamgMap.get(groupId);
	}
	
	public void addAgent(int agentId) {
		if( !personAgentIdList.contains(agentId))
			personAgentIdList.add(agentId);
	}
	
	public void getAgentList() {
		System.out.println("getAgentList " + groupId + ", " + personAgentIdList);
	}
	
	public String toString() {
		
		return worldNumber + "," + groupId + "," + Tools.round(getMeanWealth()) + "\n";
	}
	
	public static String getResults() {
		String result = "";
		for(PersonAgentMultiGroup pamg : pamgMap.values())
			result += pamg;
		return result;
	}
	
	private double getMeanWealth() {
		double sum = 0.0;
		for(int i : personAgentIdList)
			sum += se.getPersonAgent(i).getWealth();
		meanWealth = sum/personAgentIdList.size();
		return meanWealth;
	}
	
	public static int getWorldNumber() {
		return worldNumber;
	}

	public static void setWorldNumber(int worldNumber) {
		PersonAgentMultiGroup.worldNumber = worldNumber;
	}
}
