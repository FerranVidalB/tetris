
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

    public static final int NUM_ROWS = 6;
    public static final int NUM_COLS = 5;
    private Tetrominoes[][] matrix;
    private NextPiecePanel nextPiecePanel;

    public HoldPanel() {
        super();
        matrix = new Tetrominoes[NUM_ROWS][NUM_COLS];
        initValues();

    }

    public void initValues() {
        cleanBoard();

        holdShape = null;
    }

    public void setNextPiecePanel(NextPiecePanel p) {
        nextPiecePanel = p;
    }

    public Shape getHoldShape(Shape newShape) {

        if (holdShape != null) {
            Shape returned = holdShape;
            holdShape = newShape;
            repaint();
            return returned;
        } else {
            holdShape = newShape;
            repaint();
            return nextPiecePanel.getNextShape();
        }
    }

    public void cleanBoard() {
        holdShape = null;
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
           holdShape.draw(g, 2, 2, squareWidth(), squareHeight());
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
