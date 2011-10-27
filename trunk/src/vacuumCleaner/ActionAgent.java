package vacuumCleaner;

public class ActionAgent {
	ActionType type;
	int sx, sy;
	
	public ActionAgent(ActionType type, int sx, int sy) {
		super();
		this.type = type;
		this.sx = sx;
		this.sy = sy;
	}
}
