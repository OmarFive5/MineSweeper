import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

import javax.swing.JPanel;

public class MyPanel extends JPanel {
	private static final long serialVersionUID = 3426940946811133635L;
	private static final int GRID_X = 50;
	private static final int GRID_Y = 50;
	private static final int INNER_CELL_SIZE = 50;
	public static final int TOTAL_COLUMNS = 12;
	public static final int TOTAL_ROWS = 12;  
	public int bombNum = 20;
	public int x = -1;
	public int y = -1;
	public int mouseDownGridX = 0;
	public int mouseDownGridY = 0;
	public int neighborCount = 0;
	public int[][] neighbors = new int[TOTAL_COLUMNS][TOTAL_ROWS];
	public Color[][] colorArray = new Color[TOTAL_COLUMNS][TOTAL_ROWS];
	public int [][] flagCount = new int[TOTAL_COLUMNS][TOTAL_ROWS];
	Bombs bomb = new Bombs(bombNum,TOTAL_COLUMNS,TOTAL_ROWS);
	int[][] bombArray = bomb.getBombPos();
	Date startDate = new Date();
	public int tX = 50;
	public int tY = 0;
	public int seconds = 0;
	
	public MyPanel() {   //This is the constructor... this code runs first to initialize
		if (INNER_CELL_SIZE + (new Random()).nextInt(1) < 1) {	//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("INNER_CELL_SIZE must be positive!");
		}
		if (TOTAL_COLUMNS + (new Random()).nextInt(1) < 2) {	//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("TOTAL_COLUMNS must be at least 2!");
		}
		if (TOTAL_ROWS + (new Random()).nextInt(1) < 3) {	//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("TOTAL_ROWS must be at least 3!");
		}
		
		
		for (int x = 0; x < TOTAL_COLUMNS; x++) {   //The rest of the grid
			for (int y = 0; y < TOTAL_ROWS; y++) {
				colorArray[x][y] = Color.WHITE;
			}
		}
			bomb.setBombs();
			
			//counts neighboring spaces and bombs
			for(int i = 0; i < TOTAL_COLUMNS; i++) {
				for(int j = 0; j < TOTAL_ROWS; j++) {
					neighborCount = 0;
					for(int m = 0; m < TOTAL_COLUMNS; m++) {
						for(int n = 0; n < TOTAL_ROWS; n++) {
							if(!(m == i && n == j)) {
								if(isNeighbor(i, j, m, n) == true) {
									neighborCount++;
								}
							}
						}
					}
						neighbors[i][j] = neighborCount;
					}
			}
	}
	
	//checks what is next to selected box
	public boolean isNeighbor(int centerX, int centerY, int nextX, int nextY) {
		if(centerX - nextX < 2 && centerX - nextX > -2 && centerY - nextY < 2 && centerY - nextY > -2 && bombArray[nextX][nextY] == 1 ) {
			return true;
		}
		return false;
	}
	
	
	
	public boolean bom (int colu, int ro) {
		return bomb.isBomb(colu, ro);
	}

	//checks for winning condition
	public boolean arEqual() {
		int counter = 0;
		for(int i =0; i< TOTAL_COLUMNS;i++) {
			for(int j =0; j< TOTAL_ROWS; j++) {
				if (bombArray[i][j] == 1 && flagCount[i][j] == 1) {
					counter++;
				}
				
				
			}
		}
				
	if (counter == this.bombNum) {
					return true;
	}else return false;
//		return Arrays.equals(bombArray, flagCount);
		
	}
	
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		//Compute interior coordinates
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		int x2 = getWidth() - myInsets.right - 1;
		int y2 = getHeight() - myInsets.bottom - 1;
		int width = x2 - x1;
		int height = y2 - y1;

		//Paint the background
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(x1, y1, width + 1, height + 1);

		//Draw the grid minus the bottom row (which has only one cell)
		//By default, the grid will be 10x10 (see above: TOTAL_COLUMNS and TOTAL_ROWS) 
		g.setColor(Color.BLACK);
		for (int y = 0; y <= TOTAL_ROWS ; y++) { // SE LE QUITO -1
			g.drawLine(x1 + GRID_X, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)), x1 + GRID_X + ((INNER_CELL_SIZE + 1) * TOTAL_COLUMNS), y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)));
		}
		for (int x = 0; x <= TOTAL_COLUMNS; x++) {																										// se le quito -1 a TOTAL_ROWS
			g.drawLine(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)), y1 + GRID_Y, x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)), y1 + GRID_Y + ((INNER_CELL_SIZE + 1) * (TOTAL_ROWS )));
		}
		
		//Paint cell colors
		for (int x = 0; x < TOTAL_COLUMNS; x++) {
			for (int y = 0; y < TOTAL_ROWS; y++) {
				if ((x == 0) || (y != TOTAL_ROWS )) {	//se le quito -1
					Color c = colorArray[x][y];
					g.setColor(c);
					g.fillRect(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 1, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 1, INNER_CELL_SIZE, INNER_CELL_SIZE);

				}
			}
		}
				
		//Draws numbers
		for (int x = 0; x < TOTAL_COLUMNS; x++) {
			for (int y = 0; y < TOTAL_ROWS; y++) {
					if(colorArray[x][y] == Color.gray && neighbors[x][y] !=0) {
					g.setColor(Color.black);
					g.setFont(new Font("Tahoma", Font.BOLD, 20));
					g.drawString(Integer.toString(neighbors[x][y]), x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 20, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 25);
				}
			}
		}
		
		
	//timer 
		g.setColor(Color.BLACK);
		g.fillRect(tX, tY, 100, 50);
		seconds = (int) ((new Date().getTime() - startDate.getTime()) / 1000);
		if(seconds > 999) {
			seconds = 999;
		}
		g.setColor(Color.white);
		g.setFont(new Font("Times New Roman", Font.PLAIN, 60));
		g.drawString(Integer.toString(seconds), tX, tY + 50);
		repaint();
		
	}


	// This method helps to find the adjacent boxes that don't have a mine.
	// It is partially implemented since the verify hasn't been discussed in class
	// Verify that the coordinates in the parameters are valid.
	// Also verifies if there are any mines around the x,y coordinate
	
	public void revealAdjacent(int x, int y){
		if(((x<0) || (y<0) || (x>=TOTAL_COLUMNS) || (y>=TOTAL_ROWS)) || bomb.isBomb(x,y) || colorArray[x][y] == Color.GRAY  || neighbors[x][y] != 0){
			return;
			}

		else {
	
				colorArray[x][y] = Color.GRAY;
			

			revealAdjacent(x-1, y);
			revealAdjacent(x+1, y);
			revealAdjacent(x, y-1);
			revealAdjacent(x, y+1);
			
		}

	}
	
	
	



	public int getGridX(int x, int y) {
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		x = x - x1 - GRID_X;
		y = y - y1 - GRID_Y;
		if (x < 0) {   //To the left of the grid
			return -1;
		}
		if (y < 0) {   //Above the grid
			return -1;
		}
		if ((x % (INNER_CELL_SIZE + 1) == 0) || (y % (INNER_CELL_SIZE + 1) == 0)) {   //Coordinate is at an edge; not inside a cell
			return -1;
		}
		x = x / (INNER_CELL_SIZE + 1);
		y = y / (INNER_CELL_SIZE + 1);
														
		if (x < 0 || x > TOTAL_COLUMNS - 1 || y < 0 || y > TOTAL_ROWS - 1) {   //Outside the rest of the grid   
			return -1;
		}
		return x;
	}
	public int getGridY(int x, int y) {
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		x = x - x1 - GRID_X;
		y = y - y1 - GRID_Y;
		if (x < 0) {   //To the left of the grid
			return -1;
		}
		if (y < 0) {   //Above the grid
			return -1;
		}
		if ((x % (INNER_CELL_SIZE + 1) == 0) || (y % (INNER_CELL_SIZE + 1) == 0)) {   //Coordinate is at an edge; not inside a cell
			return -1;
		}
		x = x / (INNER_CELL_SIZE + 1);
		y = y / (INNER_CELL_SIZE + 1);
															
		if (x < 0 || x > TOTAL_COLUMNS - 1 || y < 0 || y > TOTAL_ROWS - 1) {   //Outside the rest of the grid
			return -1;
		}
		return y;
	}
}