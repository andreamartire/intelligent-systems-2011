package vacuumCleaner;

public class Perception {
	
	Floor floor;

	public Perception(Floor perceivedFloor, Square.Type type) {
		super();
		this.floor = new Floor(perceivedFloor.lenght, perceivedFloor.width, type);
	}
}
