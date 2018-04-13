
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
                    if (canMoveTo(currentShape,currentRow, currentCol - 1)) {
                        currentCol--;
                    }
// whatever
                    break;
                case KeyEvent.VK_RIGHT:
// whatever         
                    if (canMoveTo(currentShape,currentRow, currentCol + 1)) {
                        currentCol++;
                    }
                    break;
                case KeyEvent.VK_UP:
// whatever
                    Shape rotShape = currentShape.rotateRight();
                    if(canMoveTo(rotShape ,currentRow, currentCol)){
                        currentShape=rotShape;
                    }
                    break;
                case KeyEvent.VK_DOWN:
// whatever 
                    if (canMoveTo(currentShape,currentRow + 1, currentCol)) {
                        currentRow++;
                    }
                    break;
                    case KeyEvent.VK_P:
                        if(timer.isRunning()){
                        timer.stop();
                        }else{
                            timer.start();
                        }
                    break;
                    case KeyEvent.VK_ENTER:
                        if(!timer.isRunning()){
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
    
    
    
    public Board() {
        super();
        matrix = new Tetrominoes[NUM_ROWS][NUM_COLS];
        initValues();
        currentShape = null;
        timer = new Timer(deltaTime, this);
        MyKeyAdapter keyb = new MyKeyAdapter();
        addKeyListener(keyb);

    }
    public void setScorer(IncrementScorer scorer){
        this.scorerDelegate=scorer;
    }

    public void initValues() {
        setFocusable(true);
        cleanBoard();
        deltaTime = 500;
        currentShape = new Shape();
        currentRow = INIT_ROW;
        currentCol = NUM_COLS / 2;
    }

    public void initGame() {
        addKeyListener(keyb);
        initValues();
        scorerDelegate.reset();
        timer.start();

    }

    public void cleanBoard() {
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                matrix[row][col] = Tetrominoes.NoShape;
            }
        }
        
    }
    public void gameOver(){
        timer.stop();
        scorerDelegate.getScore();
        //rellenar todo el matrix en cascada utilizando cuadraditos y Añadir Game over dibujado en el matrix y la opcion de play again
    }

    private boolean canMoveTo(Shape shape,int newRow, int newCol) {
        if (newCol + shape.getXmin() < 0
                || newCol + shape.getXmax() >= NUM_COLS
                || newRow + shape.getYmax() >= NUM_ROWS 
                || hitWithMatrix(shape,newRow, newCol)) {

            return false;

        }
        return true;

    }

    private boolean hitWithMatrix(Shape shape,int newRow, int newCol) {
        //Vamos a comparar las coordenadas de la pieza actual con la matriz(si esta pintada con piezas, no avancara)
        int[][] squaresArray = shape.getCoordinates();
        int row;
        int col;

        for (int point = 0; point <= 3; point++) {
            row = newRow + squaresArray[point][1];
            col = newCol + squaresArray[point][0];

            if (row >= 0) {
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
        if (canMoveTo(currentShape,currentRow + 1, currentCol)) {

            currentRow++;
            repaint();
        } else {
            checkGameOver();
            moveCurrentShapeToMatrix();
            currentShape = new Shape();
            currentRow = INIT_ROW;
            currentCol = NUM_COLS / 2;
            checkRows();
        }
        
        

    }
    public void checkGameOver(){
         int[][] squaresArray = currentShape.getCoordinates();
        for (int point = 0; point <= 3; point++) {
            if(currentRow + squaresArray[point][1]<0){
                gameOver();
                
            }
            

        }
    }
    
    public void checkRows(){
        
        boolean clean;
        for(int row =0;row<NUM_ROWS;row++){
            clean=true;
            for(int col=0;col<NUM_COLS;col++){
                if(matrix[row][col]==Tetrominoes.NoShape){
                    clean=false;
                }
            }
            if(clean){
                
                cleanRow(row);
            }
            
        }
    }
    public void cleanRow(int rowCompleted){
        
       for(int row =rowCompleted;row>=1;row--){
            for(int col=0;col<NUM_COLS;col++){
               matrix[row][col]=matrix[row-1][col];
            }    
        }
       for(int col=0;col<NUM_COLS;col++){
               matrix[0][col]=Tetrominoes.NoShape;
            } 
        
        scorerDelegate.increment(100);
       
       repaint();
       
       
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
