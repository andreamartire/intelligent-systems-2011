package vacuumCleaner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class Agent {
	
	public enum VisibilityType {
		MY_CELL,
		MY_NEIGHBOURS,
		ALL
	}
	
	ArrayList<Perception> pList;
	int x, y;
	int wLenght, wWidth;
	int squaresCleanedByMe = 0;
	boolean goalReached = false;
	VisibilityType visType;
	ActionType currAction;
	ArrayList<ActionAgent> actionList;
	
	public Agent(int x, int y, int wLenght, int wWidth, VisibilityType visType){
		this.x = x;
		this.y = y;
		this.wLenght = wLenght;
		this.wWidth = wWidth;
		this.visType = visType;
		currAction = ActionType.NOOP;
		actionList = new ArrayList<ActionAgent>();
		pList = new ArrayList<Perception>();
		pList.add(new Perception(x, y, Square.Type.DIRTY));
	}
	
	public boolean goalReached(){
		return goalReached;
	}
	
	public void perceives(ArrayList<Perception> pList){
		this.pList = pList;
		System.out.println("My Perceptions");
		for(int i=0; i<pList.size(); i++)
			System.out.println(pList.get(i).x + "," + pList.get(i).y + " " + pList.get(i).state);
	}

	public void update(){
		switch (visType) {
			case MY_CELL:behaviour_MyCell();break;
			case MY_NEIGHBOURS:behaviour_MyNeighbours();break;
			case ALL:break;
		}
	}
	
	/**
	 * for example
	 */
	public void behaviour_MyCell(){
		if(pList.get(0).state == Square.Type.DIRTY)
			currAction = ActionType.SUCK;
		else{
			LinkedList<ActionType> list = new LinkedList<ActionType>();
			list.add(ActionType.NORTH);
			list.add(ActionType.SOUTH);
			list.add(ActionType.EAST);
			list.add(ActionType.WEST);
	        Collections.shuffle(list);
	        currAction = list.getFirst();
		}
	}
	
	public void behaviour_MyNeighbours(){
		goalReached = updateGoal();
		//Calculate best action from current state
		int max = Integer.MIN_VALUE;
		currAction = ActionType.NOOP;
		
		if(x!=0){
			ActionAgent actNord = new ActionAgent(ActionType.NORTH, x, y);
			System.out.println("Nord: " + actionScore(actNord));
			if(actionScore(actNord)>max){
				currAction = ActionType.NORTH;
				max = actionScore(actNord);
			}
		}
		if(x!=wLenght-1){
			ActionAgent actSud = new ActionAgent(ActionType.SOUTH, x, y);
			System.out.println("Sud: " + actionScore(actSud));
			if(actionScore(actSud)>max){
				currAction = ActionType.SOUTH;
				max = actionScore(actSud);
			}
		}
		if(y!=wWidth-1){
			ActionAgent actEst = new ActionAgent(ActionType.EAST, x, y);
			System.out.println("Est: " + actionScore(actEst));
			if(actionScore(actEst)>max){
				currAction = ActionType.EAST;
				max = actionScore(actEst);
			}
		}
			
		if(y!=0){
			ActionAgent actOvest = new ActionAgent(ActionType.WEST, x, y);
			System.out.println("Ovest: " + actionScore(actOvest));
			if(actionScore(actOvest)>max){
				currAction = ActionType.WEST;
				max = actionScore(actOvest);
			}
		}
		
		ActionAgent actSuck = new ActionAgent(ActionType.SUCK, x, y);
		if(actionScore(actSuck)>max){
			System.out.println("Suck: " + actionScore(actSuck));
			currAction = ActionType.SUCK;
			max = actionScore(actSuck);
		}
		
		if(currAction == ActionType.SUCK && pList.get(0).state == Square.Type.DIRTY)
			squaresCleanedByMe++;
	}
	
	public int actionScore(ActionAgent actionAgent){
		int bonus = 0;
//		If vacuum-cleaner sucks on a dirty square
		if(actionAgent.type == ActionType.SUCK && pList.get(0).state == Square.Type.DIRTY)
			bonus += 1;
//		If vacuum-cleaner goes on a dirty square
		if(getSquarePerceivedType(actionAgent.type) == Square.Type.DIRTY)
			bonus += 1;
		
		return squaresNowCleaned() + squaresCleanedByMe + bonus - actionList.size() - 1;
	}
	
	public int getXVariation(ActionType actionType){
		switch (actionType){
			case NORTH:return -1;
			case SOUTH:return 1;
		}
		return 0;
	}
	
	public int getYVariation(ActionType actionType){
		switch (actionType){
			case EAST:return 1;
			case WEST:return -1;
		}
		return 0;
	}
	
	public Square.Type getSquarePerceivedType(ActionType actionType){
		for(int i=0; i<pList.size(); i++)
			if(pList.get(i).x == x + getXVariation(actionType) &&
					pList.get(i).y == y + getYVariation(actionType))
				return pList.get(i).state;
		try {
			new Exception("Error: not founded the state requested");
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(1);
		}
		return Square.Type.OBSTACLE;		
	}
	
	public void showActions(){
		for(int i=0; i<actionList.size(); i++)
			System.out.println(actionList.get(i).type);
	}
	
	public int squaresNowCleaned(){
		int cleanedSquare = 0;
		for(int i=0; i<pList.size(); i++)
			if(pList.get(i).state == Square.Type.CLEAN)
				cleanedSquare++;
		return cleanedSquare;
	}
	
	public int dirtySquares(){
		int dirtySquare = 0;
		for(int i=0; i<pList.size(); i++)
			if(pList.get(i).state == Square.Type.DIRTY)
				dirtySquare++;
		return dirtySquare;
	}
	
	public boolean updateGoal(){
		if(dirtySquares()==0){
			System.out.println("GOAL REACHED");
			return true;
		}
		return false;
	}
	
	public ActionType action(){
		actionList.add(new ActionAgent(currAction, x, y));
		return currAction;
	}
}
