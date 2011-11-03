package vacuumCleaner;

import java.io.Serializable;

public class Square implements Serializable {

	private static final long	serialVersionUID	= -4725270244707786274L;

	public enum Type {
        CLEAN, DIRTY, OBSTACLE, UNKNOWN
    }

    public Type type = Type.CLEAN;

    public Square() {

    }

    public Square(Type type) {
        this.type = type;
    }    

    @Override
    public String toString() {
        return type.toString();
    }
}
