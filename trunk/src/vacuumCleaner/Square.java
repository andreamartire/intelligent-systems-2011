package vacuumCleaner;

public class Square {

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
