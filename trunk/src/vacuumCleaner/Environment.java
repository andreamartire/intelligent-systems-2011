package vacuumCleaner;

import java.util.ArrayList;

public class Environment {
	
	public enum DynamicType {
		STATIC,
		DYNAMIC
	}
	
	public int lenght;
	public int width;
	public Agent agent;
	public static int opBound = 100;
	public Action.Type currAction;
	DynamicType dynType;
	public Floor floor;
	
	public Environment(int lenght, int width, Agent agent, DynamicType dynType){
		this.lenght = lenght;
		this.width = width;
		this.floor = new Floor(lenght, width, Square.Type.CLEAN);
		
		this.agent = agent;
		
		this.dynType = dynType;
		
		this.floor.generateObject(25, 7);
	}
	
	public static Environment create(int lenght, int width, Agent agent, DynamicType dynType){
		return new Environment(lenght, width, agent, dynType);
	}
	
	public Perception getPerceptions() {
		Perception perception = new Perception(floor, Square.Type.UNKNOWN);
		switch (agent.visType) {
			case MY_CELL:
				perception.floor.set(agent.x, agent.y, floor.get(agent.x, agent.y));
				break;
			case MY_NEIGHBOURS:
				perception.floor.set(agent.x, agent.y, floor.get(agent.x, agent.y));
				perception.floor.set(agent.x-1, agent.y, floor.get(agent.x-1, agent.y));
				perception.floor.set(agent.x+1, agent.y, floor.get(agent.x+1, agent.y));
				perception.floor.set(agent.x, agent.y-1, floor.get(agent.x, agent.y-1));
				perception.floor.set(agent.x-1, agent.y-1, floor.get(agent.x-1, agent.y-1));
				perception.floor.set(agent.x+1, agent.y-1, floor.get(agent.x+1, agent.y-1));
				perception.floor.set(agent.x, agent.y+1, floor.get(agent.x, agent.y+1));
				perception.floor.set(agent.x-1, agent.y+1, floor.get(agent.x-1, agent.y+1));
				perception.floor.set(agent.x+1, agent.y+1, floor.get(agent.x+1, agent.y+1));
				break;
			case ALL:
				for (int i = 0; i < floor.lenght; i++)
		            for (int j = 0; j < floor.width; j++)
		                perception.floor.set(i,j,floor.get(i, j));
				break;
		}
		return perception;
	}

	public void update() {
		if(currAction == Action.Type.SUCK){
			System.out.println("MY CELL BEFORE: " + agent.x + "," + agent.y + ": " + floor.get(agent.x,agent.y));
			floor.set(agent.x, agent.y, Square.Type.CLEAN);
			System.out.println("MY CELL AFTER: " + agent.x + "," + agent.y + ": " + floor.get(agent.x,agent.y));
		}
		if(currAction == Action.Type.NORTH && agent.x-1>=0 && !floor.obstacle(agent.x-1,agent.y))
			agent.x--;
		if(currAction == Action.Type.SOUTH && agent.x+1<lenght && !floor.obstacle(agent.x+1,agent.y))
			agent.x++;
		if(currAction == Action.Type.EAST && agent.y+1<width && !floor.obstacle(agent.x,agent.y+1))
			agent.y++;
		if(currAction == Action.Type.WEST && agent.y-1>=0 && !floor.obstacle(agent.x,agent.y-1))
			agent.y--;
	}

	public void getAction(Action.Type action) {
		currAction = action;
	}
	
	public int performanceMeasure(){
		return floor.squaresNowCleaned() - agent.actionList.size();
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<lenght; i++){
			for(int j=0; j<width; j++)
				if(agent.x == i && agent.y == j)
					sb.append("[[=]] ");
				else
					if(floor.get(i, j) == Square.Type.DIRTY)
						sb.append("XXXXX ");
				else if(floor.get(i, j) == Square.Type.OBSTACLE)
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
		while(!agent.goalReached() && agent.actionList.size()<opBound){
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
