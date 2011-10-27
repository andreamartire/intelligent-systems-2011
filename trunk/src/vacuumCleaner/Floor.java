package vacuumCleaner;

import java.util.Collections;

import java.util.LinkedList;
import java.util.Random;

public class Floor {

    int lenght, width;

    Square [][] floor;

    public Floor(int lenght, int width){
        this.lenght = lenght;
        this.width = width;
        this.floor = new Square[lenght][width];
        for (int i = 0; i < lenght; i++) {
            for (int j = 0; j < width; j++) {
                this.floor[i][j] = new Square();
            }
        }
    }

    public void generateObject(int numDirtySquares, int numOstacles){
        LinkedList<Integer> lista = new LinkedList<Integer>();

        for (int i = 0; i < lenght * width; i++) {
            lista.add(i);
        }

        Collections.shuffle(lista);
        Random randomGen = new Random();
        int size = lista.size() - 1;

        if (numDirtySquares + numOstacles > size){
            numDirtySquares = ( size * numDirtySquares ) / (numDirtySquares + numOstacles);
            numOstacles = size - numDirtySquares;
        }

        for (int i = 0; i < numDirtySquares; i++) {
            int random = Math.abs(randomGen.nextInt()) % lista.size();
            int target = lista.remove(random);
            int l = target / lenght;
            int w = target % lenght;
            floor[l][w].type = Square.Type.DIRTY;
        }

        for (int i = 0; i < numOstacles; i++) {
            int random = Math.abs(randomGen.nextInt()) % lista.size();
            int target = lista.remove(random);
            int l = target / lenght;
            int w = target % lenght;
            floor[l][w].type = Square.Type.OBSTACLE;
        }
    }

    public Square get(int i, int j){
        return this.floor[i][j];
    }

    public void set(int i, int j, Square.Type st){
        this.floor[i][j].type = st;
    }

    public Square.Type getType(int i, int j){
        return this.floor[i][j].type;
    }

    public void setType(int i, int j, Square.Type st){
        this.floor[i][j].type = st;
    }
}
