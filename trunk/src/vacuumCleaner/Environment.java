package vacuumCleaner;

import java.util.ArrayList;

public class Environment {
	
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
	Floor floor;
	
	public static void main(String[] args) {
		int l = 5, w = 5;
		Agent agent = new Agent(0,0,l,w,Agent.VisibilityType.MY_CELL);
		Environment myEnv = Environment.create(l,w,agent,DynamicType.STATIC);
		myEnv.start();
		System.out.println("-- End --");
	}
	
	public Environment(int lenght, int width, Agent agent, DynamicType dynType){
		this.lenght = lenght;
		this.width = width;
		this.floor = new Floor(lenght,width);
		
		this.agent = agent;
		
		this.dynType = dynType;
		
		this.floor.generateObject(10, 5);
	}
	
	public static Environment create(int lenght, int width, Agent agent, DynamicType dynType){
		return new Environment(lenght, width, agent, dynType);
	}
	
	private ArrayList<Perception> getPerceptions() {
		ArrayList<Perception> perceptions = new ArrayList<Perception>();
		switch (agent.visType) {
			case MY_CELL:
				System.out.println("X " + agent.x + " Y " + agent.y);
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
		if(currAction == ActionType.NORTH && agent.x-1>=0 && !obstacle(agent.x-1,agent.y))
			agent.x--;
		if(currAction == ActionType.SOUTH && agent.x+1<lenght && !obstacle(agent.x+1,agent.y))
			agent.x++;
		if(currAction == ActionType.EAST && agent.y+1<width && !obstacle(agent.x,agent.y+1))
			agent.y++;
		if(currAction == ActionType.WEST && agent.y-1>=0 && !obstacle(agent.x,agent.y-1))
			agent.y--;
	}

	private boolean obstacle(int i, int j) {
		return this.floor.get(i,j).type == Square.Type.OBSTACLE;
	}

	private void getAction(ActionType action) {
		currAction = action;
	}
	
	private int performanceMeasure(){
		return floor.squaresNowCleaned() - numOpAgent;
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<lenght; i++){
			for(int j=0; j<width; j++)
//				if(agent.x == i && agent.y == j)
//					sb.append("[[=]] ");
//				else
					if(floor.get(i, j).type == Square.Type.DIRTY)
						sb.append("XXXXX ");
				else if(floor.get(i, j).type == Square.Type.OBSTACLE)
					sb.append("OOOOO ");
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
		while(!agent.goalReached() && numOpAgent<opBound){
			agent.perceives(getPerceptions());
			agent.update();
			getAction(agent.action());
			System.out.println("Action received: " + currAction);
			update();
			show();
			System.out.println("-------------------");
		}
		System.out.println("Num actions: " + agent.actionList.size());
		agent.showActions();
		System.out.println("Performance: " + performanceMeasure() );
	}
}
