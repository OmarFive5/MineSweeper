import java.awt.Component;
import java.util.Random;

import javax.swing.JFrame;

public class Bombs {

	int numb;
	int[][] bombPos;
	private int columns;
	private int rows;
	private Random generator = new Random();
	
	public Bombs(int n, int rows2, int columns2) {
		this.numb = n;
		this.bombPos = new int[columns2][rows2];
		this.columns = columns2;
		this.rows = rows2;
	}
	public Bombs() {
		
	}
	
	
	public void setBombs() {
	
	for(int i = 0; i< numb;i++) {
		int n= generator.nextInt(columns);
		int m = generator.nextInt(rows);
		
		if(bombPos[n][m] != 1) {
		bombPos[n][m] = 1;
		}else i--;
	}
	}
	
	
	public boolean isBomb(int column, int row) {
		if(bombPos[column][row] == 1) {
			return true;
		}else
		return false;
	}
	
	public int[][] getBombPos() {
		return bombPos;
	}
}
