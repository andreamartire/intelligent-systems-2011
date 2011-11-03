package vacuumCleaner;

import java.io.Serializable;
import java.util.Collections;

import java.util.LinkedList;
import java.util.Random;

public class Floor implements Serializable {

	private static final long	serialVersionUID	= -2524877175083650036L;

	int lenght, width;
    
    private Square [][] floor;

    public Floor(int lenght, int width, Square.Type squareType){
        this.lenght = lenght;
        this.width = width;
        this.floor = new Square[lenght][width];
        for (int i = 0; i < lenght; i++)
            for (int j = 0; j < width; j++)
                this.floor[i][j] = new Square(squareType);
    }

    public void generateObject(int numDirtySquares, int numOstacles){
        LinkedList<Integer> lista = new LinkedList<Integer>();

        for (int i = 0; i < lenght * width; i++)
            lista.add(i);

        Collections.shuffle(lista);
        Random randomGen = new Random();
        int size = lista.size();

        if (numDirtySquares + numOstacles > size){
            numDirtySquares = ( size * numDirtySquares ) / (numDirtySquares + numOstacles);
            numOstacles = size - numDirtySquares;
        }

        for (int i = 0; i < numDirtySquares; i++) {
            int random = Math.abs(randomGen.nextInt()) % lista.size();
            int target = lista.remove(random);
            int l = (int) target / lenght;
            int w = target % width;
            floor[l][w].type = Square.Type.DIRTY;
        }

        for (int i = 0; i < numOstacles; i++) {
            int random = Math.abs(randomGen.nextInt()) % lista.size();
            int target = lista.remove(random);
            int l = (int) target / lenght;
            int w = target % width;
            floor[l][w].type = Square.Type.OBSTACLE;
        }
    }
    
    public int squaresNowCleaned(){
		int cleanedSquare = 0;
		for (int i = 0; i < lenght; i++)
            for (int j = 0; j < width; j++)
				if(get(i,j) == Square.Type.CLEAN)
					cleanedSquare++;
		return cleanedSquare;
	}
	
    public void clear(){
		for (int i = 0; i < lenght; i++)
            for (int j = 0; j < width; j++)
				floor[i][j].type = Square.Type.CLEAN;
    }
    
    public void load(Floor floor){
    	this.floor = floor.floor;
    	this.width = floor.width;
    	this.lenght = floor.lenght;
    }
    
    public int dirtySquares(){
		int dirtySquare = 0;
		for (int i = 0; i < lenght; i++)
            for (int j = 0; j < width; j++)
				if(get(i,j) == Square.Type.DIRTY)
					dirtySquare++;
		return dirtySquare;
	}
    
    public boolean obstacle(int i, int j) {
		return get(i,j) == Square.Type.OBSTACLE;
	}

    public Square.Type get(int i, int j){
    	if(i<0 || j<0 || i>=lenght || j>=width)
    		return Square.Type.UNKNOWN;
        return this.floor[i][j].type;
    }

    public void set(int i, int j, Square.Type st){
    	if(i<0 || j<0 || i>=lenght || j>=width)
    		return;
        this.floor[i][j].type = st;
    }
}
