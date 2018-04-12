/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author alu20925473g
 */
public class Shape {

    private Tetrominoes pieceShape;
    private int[][] coordinates;

    private static int[][][] coordsTable = new int[][][]{
        {{0, 0}, {0, 0}, {0, 0}, {0, 0}},
        {{0, -1}, {0, 0}, {-1, 0}, {-1, 1}},
        {{0, -1}, {0, 0}, {1, 0}, {1, 1}},
        {{0, -1}, {0, 0}, {0, 1}, {0, 2}},
        {{-1, 0}, {0, 0}, {1, 0}, {0, 1}},
        {{0, 0}, {1, 0}, {0, 1}, {1, 1}},
        {{-1, -1}, {0, -1}, {0, 0}, {0, 1}},
        {{1, -1}, {0, -1}, {0, 0}, {0, 1}}
    };

    public Shape(Tetrominoes pieceShape) {
        this.pieceShape = pieceShape;
        coordinates = coordsTable[pieceShape.ordinal()];
    }

    public Shape() {
        int randomNumber = (int) (Math.random() * 7 + 1);
        pieceShape = Tetrominoes.values()[randomNumber];

        coordinates = coordsTable[randomNumber];

    }
    public static Shape getRandomShape(){
        return new Shape();
    }
    public int[][] getCoordinates() {
        return coordinates;
    }

    public Tetrominoes getShape() {
        return pieceShape;
    }
    public int getXmin(){
        
        
        int xmin = coordinates[0][0];
        for(int i=1;i<4;i++){
            if(xmin>coordinates[i][0])
                xmin=coordinates[i][0];
        }
        
        return xmin;
    }
    public int getXmax(){
         int xmax = coordinates[0][0];
        for(int i=1;i<4;i++){
            if(xmax<coordinates[i][0])
                xmax=coordinates[i][0];
        }
        
        return xmax;
    }
    public int getYmin(){
         int ymin = coordinates[0][0];
        for(int i=1;i<4;i++){
            if(ymin>coordinates[i][1])
                ymin=coordinates[i][1];
        }
        
        return ymin;
    }
    public int getYmax(){
        int ymax = coordinates[0][0];
        for(int i=1;i<4;i++){
            if(ymax<coordinates[i][1])
                ymax=coordinates[i][1];
        }
        
        return ymax;
    }
    
}
