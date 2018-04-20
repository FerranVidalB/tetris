

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author alu20925473g
 */
public class NextPiecePanel extends JPanel {
    
    private Shape nextShape;
    
    
    
    public static final int NUM_ROWS = 6;
    public static final int NUM_COLS = 5;
    private Tetrominoes[][] matrix;
    
    public NextPiecePanel(){
        super();
        matrix = new Tetrominoes[NUM_ROWS][NUM_COLS];
        initValues();
        
        
    }
    public void initValues(){
         cleanBoard();
      
        nextShape = null;
    }
    public void generateNextShape(){
        nextShape= new Shape();
    }
    
    public Shape getNextShape(){
        if(nextShape!=null){
        Shape returned = nextShape;
        generateNextShape();
        repaint();
        return returned;
        }else{
            generateNextShape();
            repaint();
            return Shape.getRandomShape();
        }
    }
    public Shape seeNextShape(){
        return nextShape;
    }
    
     public void cleanBoard() {
         nextShape= null;
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                matrix[row][col] = Tetrominoes.NoShape;
                
            }
        }
        repaint();
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);
        
        if (nextShape != null) {
            nextShape.draw(g, 2, 2, squareWidth(), squareHeight(),false);
        }
        drawBorder(g);
    }

    public void drawBorder(Graphics g) {
        g.setColor(Color.red);
        g.drawRect(0, 0, NUM_COLS * squareWidth(), NUM_ROWS * squareHeight());
    }

   

    public void drawBoard(Graphics g) {
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {

                Util.drawSquare(g, row, col, matrix[row][col], squareWidth(), squareHeight());
            }
        }
    }

    private int squareWidth() {
        return getWidth() / NUM_COLS;
    }

    private int squareHeight() {
        return getHeight() / NUM_ROWS;
    }

    
    
}
