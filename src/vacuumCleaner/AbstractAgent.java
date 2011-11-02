package vacuumCleaner;

import java.util.ArrayList;

/**
 * Agent abstraction;
 * every agent has to implement those methods
 */
public abstract class AbstractAgent {
	
	public enum VisibilityType {
		MY_CELL,
		MY_NEIGHBOURS,
		ALL
	}
	
	Perception perception;
	public int x;
	public int y;
	int wLenght, wWidth;
	int squaresCleanedByMe = 0;
	boolean goalReached = false;
	public VisibilityType visType;
	Action.Type currAction;
	public ArrayList<Action> actionList;
	
	public AbstractAgent(int x, int y, int wLenght, int wWidth, VisibilityType visType){
		this.x = x;
		this.y = y;
		this.wLenght = wLenght;
		this.wWidth = wWidth;
		this.visType = visType;
		currAction = Action.Type.NOOP;
		actionList = new ArrayList<Action>();
	}
	
	/**
	 * @return true if the agent reach his goal
	 */
	public abstract boolean goalReached();

	/**
	 * make the agent perceives
	 * @param perception 
	 */
	public abstract void perceives(Perception perception);

	/**
	 * make the agent reason
	 */
	public abstract void update();
	
	/**
	 * print the agent performed actions
	 */
	public abstract void showActions();

	/** 
	 * do an action
	 * @return the action the agent wants to do
	 */
	public abstract Action.Type action();

}
