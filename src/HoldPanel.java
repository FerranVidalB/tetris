

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
public class HoldPanel extends JPanel {
    
    private Shape holdShape;
    
    
    
    public static final int NUM_ROWS = 7;
    public static final int NUM_COLS = 6;
    private Tetrominoes[][] matrix;
    private NextPiecePanel nextPiecePanel;
    
    public HoldPanel(){
        super();
        matrix = new Tetrominoes[NUM_ROWS][NUM_COLS];
        initValues();
        
        
    }
    public void initValues(){
         cleanBoard();
      
        holdShape = new Shape();
    }
    
    
    public Shape getHoldShape(Shape newShape){
        
        if(holdShape!=null){
        Shape returned = holdShape;
        holdShape=newShape;
        repaint();
        return returned;
        }else{
            
            return nextPiecePanel.getNextShape();
        }
    }
     public void cleanBoard() {
         holdShape= null;
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
        
        if (holdShape != null) {
            drawCurrentShape(g);
        }
        drawBorder(g);
    }

    public void drawBorder(Graphics g) {
        g.setColor(Color.red);
        g.drawRect(0, 0, NUM_COLS * squareWidth(), NUM_ROWS * squareHeight());
    }

    public void drawCurrentShape(Graphics g) {
        int[][] squaresArray = holdShape.getCoordinates();
        for (int point = 0; point <= 3; point++) {
           drawSquare(g, 3 + squaresArray[point][1], 3 + squaresArray[point][0], holdShape.getShape());
        }

    }

    public void drawBoard(Graphics g) {
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {

                drawSquare(g, row, col, matrix[row][col]);
            }
        }
    }

    private void drawSquare(Graphics g, int row, int col, Tetrominoes shape) {
        Color colors[] = {new Color(0, 0, 0),
            new Color(204, 102, 102),
            new Color(102, 204, 102), new Color(102, 102, 204),
            new Color(204, 204, 102), new Color(204, 102, 204),
            new Color(102, 204, 204), new Color(218, 170, 0)
        };
        int x = col * squareWidth();
        int y = row * squareHeight();
        Color color = colors[shape.ordinal()];
        g.setColor(color);
        g.fillRect(x + 1, y + 1, squareWidth() - 2, squareHeight() - 2);
        g.setColor(color.brighter());
        g.drawLine(x, y + squareHeight() - 1, x, y);
        g.drawLine(x, y, x + squareWidth() - 1, y);
        g.setColor(color.darker());
        g.drawLine(x + 1, y + squareHeight() - 1,
                x + squareWidth() - 1, y + squareHeight() - 1);
        g.drawLine(x + squareWidth() - 1,
                y + squareHeight() - 1,
                x + squareWidth() - 1, y + 1);
    }

    private int squareWidth() {
        return getWidth() / NUM_COLS;
    }

    private int squareHeight() {
        return getHeight() / NUM_ROWS;
    }

    
    
}
