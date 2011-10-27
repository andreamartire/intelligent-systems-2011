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
	Action.Type currAction;
	ArrayList<Action> actionList;
	
	public Agent(int x, int y, int wLenght, int wWidth, VisibilityType visType){
		this.x = x;
		this.y = y;
		this.wLenght = wLenght;
		this.wWidth = wWidth;
		this.visType = visType;
		currAction = Action.Type.NOOP;
		actionList = new ArrayList<Action>();
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
			case MY_CELL:stupidBehaviour();break;
			case MY_NEIGHBOURS:anotherStupidBehaviour();break;
			case ALL:anotherStupidBehaviour();break;
		}
	}
	
	public void stupidBehaviour(){
		if(pList.get(0).state == Square.Type.DIRTY)
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
	
	public void anotherStupidBehaviour(){
		goalReached = updateGoal();
		//Calculate best action from current state
		int max = Integer.MIN_VALUE;
		currAction = Action.Type.NOOP;
		
		//Avoided illegal actions
		if(x!=0){
			Action actNord = new Action(Action.Type.NORTH, x, y);
			System.out.println("Nord: " + actionScore(actNord));
			if(actionScore(actNord)>max){
				currAction = Action.Type.NORTH;
				max = actionScore(actNord);
			}
		}
		if(x!=wLenght-1){
			Action actSud = new Action(Action.Type.SOUTH, x, y);
			System.out.println("Sud: " + actionScore(actSud));
			if(actionScore(actSud)>max){
				currAction = Action.Type.SOUTH;
				max = actionScore(actSud);
			}
		}
		if(y!=wWidth-1){
			Action actEst = new Action(Action.Type.EAST, x, y);
			System.out.println("Est: " + actionScore(actEst));
			if(actionScore(actEst)>max){
				currAction = Action.Type.EAST;
				max = actionScore(actEst);
			}
		}
			
		if(y!=0){
			Action actOvest = new Action(Action.Type.WEST, x, y);
			System.out.println("Ovest: " + actionScore(actOvest));
			if(actionScore(actOvest)>max){
				currAction = Action.Type.WEST;
				max = actionScore(actOvest);
			}
		}
		
		Action actSuck = new Action(Action.Type.SUCK, x, y);
		if(actionScore(actSuck)>max){
			System.out.println("Suck: " + actionScore(actSuck));
			currAction = Action.Type.SUCK;
			max = actionScore(actSuck);
		}
		
		if(currAction == Action.Type.SUCK && pList.get(0).state == Square.Type.DIRTY)
			squaresCleanedByMe++;
	}
	
	public int actionScore(Action actionAgent){
		int bonus = 0;
//		If vacuum-cleaner sucks on a dirty square
		if(actionAgent.type == Action.Type.SUCK && pList.get(0).state == Square.Type.DIRTY)
			bonus += 1;
//		If vacuum-cleaner goes on a dirty square
		if(getSquarePerceivedType(actionAgent.type) == Square.Type.DIRTY)
			bonus += 1;
		
		return squaresNowCleaned() + squaresCleanedByMe + bonus - actionList.size() - 1;
	}
	
	public Square.Type getSquarePerceivedType(Action.Type actionType){
		for(int i=0; i<pList.size(); i++)
			if(pList.get(i).x == x + Action.xVariation(actionType) &&
					pList.get(i).y == y + Action.yVariation(actionType))
				return pList.get(i).state;
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
	
	public Action.Type action(){
		actionList.add(new Action(currAction, x, y));
		return currAction;
	}
}
