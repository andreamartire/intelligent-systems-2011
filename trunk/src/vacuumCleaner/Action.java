package vacuumCleaner;

public class Action {
	
	public enum Type {
		NOOP,
		SUCK,
		NORTH,
		SOUTH,
		EAST,
		WEST
	}
	
	Action.Type type;
	int sx, sy;
	
	public Action(Action.Type type, int sx, int sy) {
		super();
		this.type = type;
		this.sx = sx;
		this.sy = sy;
	}
	
	static public int xVar(Action.Type actionType){
		switch (actionType){
			case NORTH:return -1;
			case SOUTH:return 1;
		}
		return 0;
	}
	
	static public int yVar(Action.Type actionType){
		switch (actionType){
			case EAST:return 1;
			case WEST:return -1;
		}
		return 0;
	}
}
