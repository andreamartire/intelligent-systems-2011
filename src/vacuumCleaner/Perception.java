package vacuumCleaner;

public class Perception {
	
	int x, y;
	Square.Type state;
	
	public Perception(int x, int y, Square.Type state){
		this.x = x;
		this.y = y;
		this.state = state;
	}
}
