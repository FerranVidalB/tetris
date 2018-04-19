
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author alu20925473g
 */
public class Board extends JPanel implements ActionListener {

    private class MyKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (isPlaying) {
                        if (canMoveTo(currentShape, currentRow, currentCol - 1)) {
                            currentCol--;
                        }
                    }
// whatever
                    break;
                case KeyEvent.VK_RIGHT:
// whatever         
                    if (isPlaying) {
                        if (canMoveTo(currentShape, currentRow, currentCol + 1)) {
                            currentCol++;
                        }
                    }
                    break;
                case KeyEvent.VK_UP:
// whatever
                    if (isPlaying) {
                        Shape rotShape = currentShape.rotateRight();
                        if (canMoveTo(rotShape, currentRow, currentCol)) {
                            currentShape = rotShape;
                        }
                    }
                    break;
                case KeyEvent.VK_DOWN:
// whatever 
                    if (isPlaying) {
                        if (canMoveTo(currentShape, currentRow + 1, currentCol)) {
                            currentRow++;
                        }
                    }
                    break;
                case KeyEvent.VK_P:
                    if (isPlaying) {
                        if (timer.isRunning()) {
                            timer.stop();
                        } else {
                            timer.start();
                        }
                    }
                    break;
                case KeyEvent.VK_ENTER:
                    if (!timer.isRunning()) {
                        initGame();
                    }
                    break;
                default:
                    break;
            }
            repaint();
        }
    }
    public static final int NUM_ROWS = 22;
    public static final int NUM_COLS = 10;
    public static final int INIT_ROW = -2;
    private Tetrominoes[][] matrix;
    private int deltaTime;
    private Shape currentShape;
    private int currentCol;
    private int currentRow;
    public MyKeyAdapter keyb;
    private Timer timer;
    private IncrementScorer scorerDelegate;
    private NextPiecePanel nextPiecePanel;
    private boolean isPlaying;
    private static final int[][] paintGame = new int[][]{
        {0, 1}, {0, 2}, {1, 0}, {2, 0}, {2, 2}, {2, 3}, {3, 0}, {3, 3},
        {4, 1}, {4, 2}, {6, 1}, {6, 2}, {7, 0}, {7, 3}, {8, 0}, {8, 1},
        {8, 2}, {8, 3}, {9, 0}, {9, 3}, {11, 0}, {11, 1}, {11, 3}, {11, 4},
        {12, 0}, {12, 2}, {12, 4}, {13, 0}, {13, 2}, {13, 4}, {14, 0}, {14, 4},
        {15, 0}, {15, 4}, {17, 0}, {17, 1}, {17, 2}, {17, 3}, {18, 0}, {19, 0},
        {19, 1}, {19, 2}, {20, 0}, {21, 0}, {21, 1}, {21, 2}, {21, 3}
    };
    private static final int[][] paintOver = new int[][]{
        {0, 7}, {0, 8}, {1, 6}, {1, 9}, {2, 6}, {2, 9}, {3, 6}, {3, 9}, {4, 7},
        {4, 8}, {6, 6}, {6, 9}, {7, 6}, {7, 9}, {8, 6}, {8, 9}, {9, 7}, {9, 8},
        {11, 6}, {11, 7}, {11, 8}, {11, 9}, {12, 6}, {13, 6}, {13, 7}, {13, 8}, {14, 6},
        {15, 6}, {15, 7}, {15, 8}, {15, 9}, {17, 6}, {17, 7}, {17, 8}, {18, 6}, {18, 9},
        {19, 6}, {19, 9}, {20, 6}, {20, 7}, {20, 8}, {21, 6}, {21, 9}
    };

    public Board() {
        super();
        matrix = new Tetrominoes[NUM_ROWS][NUM_COLS];
        initValues();
        currentShape = null;
       timer = new Timer(deltaTime, this);
        MyKeyAdapter keyb = new MyKeyAdapter();
        addKeyListener(keyb);
       

    }

    public void setNextPiecePanel(NextPiecePanel p) {
        nextPiecePanel = p;
    }

    public void setScorer(IncrementScorer scorer) {
        this.scorerDelegate = scorer;
    }

    public void initValues() {
        setFocusable(true);
        cleanBoard();
        deltaTime = 500;
        isPlaying=false;
        currentRow = INIT_ROW;
        currentCol = NUM_COLS / 2;
        
    }

    public void initGame() {
        
        currentShape = nextPiecePanel.getNextShape();
        Toolkit.getDefaultToolkit().sync();
        addKeyListener(keyb);
        initValues();
        scorerDelegate.reset();
         timer = new Timer(deltaTime, this);
        timer.start();
        isPlaying=true;

    }

    public void cleanBoard() {
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                matrix[row][col] = Tetrominoes.NoShape;
            }
        }

    }

    public void gameOver() {
        timer.stop();
        scorerDelegate.getScore();
        //rellenar todo el matrix en cascada utilizando cuadraditos y Añadir Game over dibujado en el matrix y la opcion de play again
        timer = new Timer(20, new ActionListener() {
            int row = 0;
            int col = 0;

            @Override
            public void actionPerformed(ActionEvent ae) {

                currentShape = null;
                if (row != NUM_ROWS && col != NUM_COLS) {
                    matrix[row][col] = Tetrominoes.LShape;

                    if (col < NUM_COLS) {
                        col++;
                    }
                    if (col == NUM_COLS) {
                        row++;
                        col = 0;
                    }
                    repaint();

                } else {
                    timer.stop();
                    paintGame();

                }

            }
        });
        timer.start();

    }

    public void paintGame() {
        //timer.stop();

        //rellenar  el matrix con GAME
        timer = new Timer(20, new ActionListener() {
            int row = 0;
            int col = 0;
            int pointer = 0;

            @Override
            public void actionPerformed(ActionEvent ae) {

                if (pointer == paintGame.length) {
                    timer.stop();

                    paintOver();
                } else {
                    matrix[paintGame[pointer][0]][paintGame[pointer][1]] = Tetrominoes.ZShape;
                    repaint();

                    if (pointer < paintGame.length) {
                        pointer++;
                    }

                }
            }
        });
        timer.start();

    }

    public void paintOver() {
        //timer.stop();

        //rellenar  el matrix con GAME
        timer = new Timer(20, new ActionListener() {
            int row = 0;
            int col = 0;
            int pointer = 0;

            @Override
            public void actionPerformed(ActionEvent ae) {

                if (pointer == paintOver.length) {
                    timer.stop();

                } else {
                    matrix[paintOver[pointer][0]][paintOver[pointer][1]] = Tetrominoes.ZShape;
                    repaint();

                    if (pointer < paintOver.length) {
                        pointer++;
                    }

                }
            }
        });
        timer.start();

    }

    private boolean canMoveTo(Shape shape, int newRow, int newCol) {
        if (newCol + shape.getXmin() < 0
                || newCol + shape.getXmax() >= NUM_COLS
                || newRow + shape.getYmax() >= NUM_ROWS
                || hitWithMatrix(shape, newRow, newCol)) {

            return false;

        }
        return true;

    }

    private boolean hitWithMatrix(Shape shape, int newRow, int newCol) {
        //Vamos a comparar las coordenadas de la pieza actual con la matriz(si esta pintada con piezas, no avanzara)
        int[][] squaresArray = shape.getCoordinates();
        int row;
        int col;

        for (int point = 0; point <= 3; point++) {
            row = newRow + squaresArray[point][1];
            col = newCol + squaresArray[point][0];

            if (row >= 0 && row < NUM_ROWS) {
                if (matrix[row][col] != Tetrominoes.NoShape) {
                    return true;
                }
            }

        }

        return false;
    }

    //Game Main Loop
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (canMoveTo(currentShape, currentRow + 1, currentCol)) {

            currentRow++;
            repaint();
        } else {

            if (!checkGameOver()) {

                moveCurrentShapeToMatrix();
                currentShape = nextPiecePanel.getNextShape();
                currentRow = INIT_ROW;
                currentCol = NUM_COLS / 2;
                checkRows();
            }
        }

    }

    public boolean checkGameOver() {
        int[][] squaresArray = currentShape.getCoordinates();
        for (int point = 0; point <= 3; point++) {
            if (currentRow + squaresArray[point][1] < 0) {
                isPlaying=false;
                timer.stop();
                gameOver();
                scorerDelegate.paintFinalScore();
                nextPiecePanel.cleanBoard();
                
                return true;

            }

        }
        return false;
    }

    public void checkRows() {

        boolean clean;
        for (int row = 0; row < NUM_ROWS; row++) {
            clean = true;
            for (int col = 0; col < NUM_COLS; col++) {
                if (matrix[row][col] == Tetrominoes.NoShape) {
                    clean = false;
                }
            }
            if (clean) {

                cleanRow(row);
            }

        }
    }

    public void cleanRow(int rowCompleted) {

        for (int row = rowCompleted; row >= 1; row--) {
            for (int col = 0; col < NUM_COLS; col++) {
                matrix[row][col] = matrix[row - 1][col];
            }
        }
        for (int col = 0; col < NUM_COLS; col++) {
            matrix[0][col] = Tetrominoes.NoShape;
        }

        scorerDelegate.increment(100);

        decrementDelay();

        repaint();

    }

    public void decrementDelay() {
        deltaTime *= 0.9;
        timer.setDelay(deltaTime);
    }

    public void moveCurrentShapeToMatrix() {
        int[][] squaresArray = currentShape.getCoordinates();

        for (int point = 0; point <= 3; point++) {
            matrix[currentRow + squaresArray[point][1]][currentCol + squaresArray[point][0]] = currentShape.getShape();

        }

    }

    public void keyPressed(KeyEvent evt) {
        keyb.keyPressed(evt);
    }

    @Override

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);
        if (currentShape != null) {
            drawCurrentShape(g);
        }
        drawBorder(g);
    }

    public void drawBorder(Graphics g) {
        g.setColor(Color.red);
        g.drawRect(0, 0, NUM_COLS * squareWidth(), NUM_ROWS * squareHeight());
    }

    public void drawCurrentShape(Graphics g) {
        int[][] squaresArray = currentShape.getCoordinates();
        for (int point = 0; point <= 3; point++) {
            drawSquare(g, currentRow + squaresArray[point][1], currentCol + squaresArray[point][0], currentShape.getShape());
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
