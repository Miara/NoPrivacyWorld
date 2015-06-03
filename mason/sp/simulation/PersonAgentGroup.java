package sp.simulation;

public class PersonAgentGroup {
	static private int maxGroupId=0;
	private int groupId;
	PersonAgent personAgent;
	int count;
	int startId;
	double meanWealth;
	SimulationEngine se;
	
	public PersonAgentGroup(PersonAgent personAgent, int count, int startId, SimulationEngine se) {
		this.personAgent = personAgent;
		this.count = count;
		this.startId = startId;
		this.se = se;
		groupId = maxGroupId++;
	}
	
	public String toString() {
		return "groupId=" + groupId + "wealth=" + Tools.round(getMeanWealth()) + "; " + personAgent;
	}
	
	private double getMeanWealth() {
		double sum = 0.0;
		for(int i = startId; i < startId + count; i++)
			sum += se.getPersonAgent(i).getWealth();
		meanWealth = sum/count;
		return meanWealth;
	}
}
