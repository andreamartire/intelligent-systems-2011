package vacuumCleaner;

import java.util.Collections;
import java.util.LinkedList;

public class Agent extends AbstractAgent {
	
	public Agent(int x, int y, int wLenght, int wWidth, VisibilityType visType, int opBound){
		super(x, y, wLenght, wWidth, visType, opBound);
	}
	
	public boolean goalReached(){
		return goalReached;
	}
	
	public void perceives(Perception perception){
		this.perception = perception;
		// TODO show perception
	}

	public void update(){
		switch (visType) {
			case MY_CELL:stupidBehaviour();break;
			case MY_NEIGHBOURS:stupidBehaviour();break;
			case ALL:stupidBehaviour();break;
		}
	}
	
	public void stupidBehaviour(){
		updateGoal();
		System.out.println("MY CELL " + x + "," + y + ": " + perception.floor.get(x,y));
		if(perception.floor.get(x,y) == Square.Type.DIRTY)
			currAction = Action.Type.SUCK;
		else{
			LinkedList<Action.Type> list = new LinkedList<Action.Type>();
			list.add(Action.Type.NORTH);
			list.add(Action.Type.SOUTH);
			list.add(Action.Type.EAST);
			list.add(Action.Type.WEST);
			Collections.shuffle(list);
			currAction = list.getFirst();
		}
	}
	
	public void showActions(){
		for(int i=0; i<actionList.size(); i++)
			System.out.println(actionList.get(i).type);
	}
	
	public int squaresNowCleaned(){
		int cleanedSquare = 0;
		for (int i = 0; i < perception.floor.lenght; i++)
	        for (int j = 0; j < perception.floor.width; j++)
				if(perception.floor.get(i,j) == Square.Type.CLEAN)
					cleanedSquare++;
		return cleanedSquare;
	}
	
	public int dirtySquares(){
		int dirtySquare = 0;
		for (int i = 0; i < perception.floor.lenght; i++)
	        for (int j = 0; j < perception.floor.width; j++)
				if(perception.floor.get(i,j) == Square.Type.DIRTY)
					dirtySquare++;
		return dirtySquare;
	}
	
	public void updateGoal(){
		if(actionList.size()>=opBound){
			System.out.println("GOAL REACHED");
			goalReached = true;
		}
	}
	
	public Action.Type action(){
		actionList.add(new Action(currAction, x, y));
		return currAction;
	}
}
