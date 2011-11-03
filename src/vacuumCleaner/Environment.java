package vacuumCleaner;

import vacuumCleaner.Square.Type;

public class Environment {
	
	public enum DynamicType {
		STATIC,
		DYNAMIC
	}
	
	public int lenght;
	public int width;
	public AbstractAgent agent;
	public Action.Type currAction;
	public DynamicType dynType;
	public Floor floor;
	
	public Environment(int lenght, int width, AbstractAgent agent, DynamicType dynType){
		this.lenght = lenght;
		this.width = width;
		this.floor = new Floor(lenght, width, Square.Type.CLEAN);
		this.agent = agent;
		this.dynType = dynType;
	}
	
	public Perception getPerceptions() {
		/* create a perception with a floor of unknown state */
		Perception perception = new Perception(new Floor(width, lenght, Type.UNKNOWN));
		/* then add informations according to the agent visibility */
		switch (agent.visType) {
			case MY_CELL:
				/* add just the information about the square the agent is on */
				perception.floor.set(agent.x, agent.y, floor.get(agent.x, agent.y));
				break;
			case MY_NEIGHBOURS:
				/* add informations about the square the agent is on and the 8 squares in 
				 * its Moore neighborhood */
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
				/* add informations about all the squares */
				for (int i = 0; i < floor.lenght; i++)
		            for (int j = 0; j < floor.width; j++)
		                perception.floor.set(i,j,floor.get(i, j));
				break;
		}
		return perception;
	}

	/* update the environment state according to the action performed by the agent */
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
}
