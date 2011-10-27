package vacuumCleaner;

import java.util.ArrayList;

public class Environment {
	
	public enum VisibilityType {
		MY_CELL,
		MY_NEIGHBOURS,
		ALL
	}
	
	public enum DynamicType {
		STATIC,
		DYNAMIC
	}
	
	int lenght;
	int width;
	Agent agent;
	int numOpAgent = 0;
	static int opBound = 10;
	ActionType currAction;
	DynamicType dynType;
	VisibilityType visType;
	Floor floor;
	
	public static void main(String[] args) {
		int l = 5, w = 5;
		Agent agent = new Agent(2,2,l,w);
		Environment myEnv = Environment.create(l,w,agent,DynamicType.STATIC,VisibilityType.MY_NEIGHBOURS);
		myEnv.start();
		System.out.println("-- End --");
	}
	
	public Environment(int lenght, int width, Agent agent, DynamicType dynType, VisibilityType visType){
		this.lenght = lenght;
		this.width = width;
		this.floor = new Floor(lenght,width);
		
		this.agent = agent;
		
		this.dynType = dynType;
		this.visType = visType;
		
		this.floor.generateObject(20, 0);
	}
	
	public static Environment create(int lenght, int width, Agent agent, DynamicType dynType, VisibilityType visType){
		return new Environment(lenght, width, agent, dynType, visType);
	}
	
	private ArrayList<Perception> getPerceptions() {
		ArrayList<Perception> perceptions = new ArrayList<Perception>();
		switch (visType) {
			case MY_CELL:
				perceptions.add(new Perception(agent.x, agent.y, floor.get(agent.x,agent.y).type));break;
			case MY_NEIGHBOURS:
				perceptions.add(new Perception(agent.x, agent.y, floor.get(agent.x,agent.y).type));
				if(agent.x != 0)
					perceptions.add(new Perception(agent.x-1, agent.y, floor.get(agent.x-1,agent.y).type));
				if(agent.x != lenght-1)
						perceptions.add(new Perception(agent.x+1, agent.y, floor.get(agent.x+1,agent.y).type));
				if(agent.y != 0)
					perceptions.add(new Perception(agent.x, agent.y-1, floor.get(agent.x,agent.y-1).type));
				if(agent.y != width-1)
					perceptions.add(new Perception(agent.x, agent.y+1, floor.get(agent.x,agent.y+1).type));
				break;
			case ALL:
				for(int i=0; i<this.lenght; i++)
					for(int j=0; j<this.width; j++)
						perceptions.add(new Perception(i, j, this.floor.get(i, j).type));
				break;
		}
		return perceptions;
	}

	private void update() {
		numOpAgent++;
		if(currAction == ActionType.SUCK)
			floor.set(agent.x, agent.y, Square.Type.CLEAN);
		if(currAction == ActionType.NORTH)
			agent.x--;
		if(currAction == ActionType.SOUTH)
			agent.x++;
		if(currAction == ActionType.EAST)
			agent.y++;
		if(currAction == ActionType.WEST)
			agent.y--;
	}

	private void getAction(ActionType action) {
		currAction = action;
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<lenght; i++){
			for(int j=0; j<width; j++)
				if(agent.x == i && agent.y == j)
					sb.append("[-o-] ");
				else if(floor.get(i, j).type == Square.Type.DIRTY)
						sb.append("XXXXX ");
				else
					sb.append("----- ");
			sb.append("\n");
		}
		return sb.toString();
	}
	
	public void show(){
		System.out.println(this);
	}
	
	public void start(){
		show();
		while(!agent.goalReached() && numOpAgent<=opBound){
			agent.perceives(getPerceptions());
			agent.update();
			getAction(agent.action());
			System.out.println("Action received: " + currAction);
			update();
			show();
			System.out.println("-----------------------");
		}
	}
}
