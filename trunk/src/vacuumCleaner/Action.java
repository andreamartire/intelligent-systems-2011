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
}
