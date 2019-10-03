import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MyMouseAdapter extends MouseAdapter {
	private Random generator = new Random();
	public void mousePressed(MouseEvent e) {
		switch (e.getButton()) {
			case 1:		//Left mouse button
				Component c = e.getComponent();
				while (!(c instanceof JFrame)) {
					c = c.getParent();
					if (c == null) {
						return;
					}
				}
				JFrame myFrame = (JFrame) c;
				MyPanel myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);
				Insets myInsets = myFrame.getInsets();
				int x1 = myInsets.left;
				int y1 = myInsets.top;
				e.translatePoint(-x1, -y1);
				int x = e.getX();
				int y = e.getY();
				myPanel.x = x;
				myPanel.y = y;
				myPanel.mouseDownGridX = myPanel.getGridX(x, y);
				myPanel.mouseDownGridY = myPanel.getGridY(x, y);
				myPanel.repaint();
				break;
			case 3:		//Right mouse button
				Component c2 = e.getComponent();
				while (!(c2 instanceof JFrame)) {
					c2 = c2.getParent();
					if (c2 == null) {
						return;
					}
				}
				JFrame myFrame2 = (JFrame) c2;
				MyPanel myPanel2 = (MyPanel) myFrame2.getContentPane().getComponent(0);
				Insets myInsets2 = myFrame2.getInsets();
				int x2 = myInsets2.left;
				int y2 = myInsets2.top;
				e.translatePoint(-x2, -y2);
				int xx = e.getX();
				int yy = e.getY();
				myPanel2.x = xx;
				myPanel2.y = yy;
				myPanel2.mouseDownGridX = myPanel2.getGridX(xx, yy);
				myPanel2.mouseDownGridY = myPanel2.getGridY(xx, yy);
				myPanel2.repaint();
				break;
			default:    //Some other button (2 = Middle mouse button, etc.)
				//Do nothing
				break;
		}
	}
	public void mouseReleased(MouseEvent e) {
		switch (e.getButton()) {
			case 1:		//Left mouse button
				Component c = e.getComponent();
				while (!(c instanceof JFrame)) {
					c = c.getParent();
					if (c == null) {
						return;
					}
				}
				JFrame myFrame = (JFrame)c;
				MyPanel myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);  //Can also loop among components to find MyPanel
				Insets myInsets = myFrame.getInsets();
				int x1 = myInsets.left;
				int y1 = myInsets.top;
				e.translatePoint(-x1, -y1);
				int x = e.getX();
				int y = e.getY();
				myPanel.x = x;
				myPanel.y = y;
				int gridX = myPanel.getGridX(x, y);
				int gridY = myPanel.getGridY(x, y);
				if ((myPanel.mouseDownGridX == -1) || (myPanel.mouseDownGridY == -1)) {
					//Had pressed outside
					//Do nothing
				} else {
					if ((gridX == -1) || (gridY == -1)) {
						//Is releasing outside
						//Do nothing
					} else {
						if ((myPanel.mouseDownGridX != gridX) || (myPanel.mouseDownGridY != gridY)) {
							//Released the mouse button on a different cell where it was pressed
							//Do nothing
						} else {
							
							// when left click on a flag
								if (myPanel.flagCount[myPanel.mouseDownGridX][myPanel.mouseDownGridY] ==1) {  
									// Do nothing
								}else if(myPanel.bom(myPanel.mouseDownGridX, myPanel.mouseDownGridY))   // WHEN LEFT CLICK ON A BOMB
								//draws bombs randomly
								{
									myPanel.colorArray[myPanel.mouseDownGridX ][myPanel.mouseDownGridY] = Color.BLACK;
									
									//reveals all bombs  if one is pressed
									for(int i = 0; i < myPanel.TOTAL_COLUMNS; i++) {
										for(int j = 0; j < myPanel.TOTAL_ROWS; j++) {
											if(myPanel.bom(i, j)) {
												myPanel.colorArray[i ][j] = Color.BLACK;
											}
											
										}
									}
									myPanel.repaint();
							//Displays end game box when mine is pressed
								int response = JOptionPane.showConfirmDialog(null, "Play again?", "GAME OVER",
						                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
						        if (response == JOptionPane.NO_OPTION) {
						                System.exit(0);
						        } 
						            else if (response == JOptionPane.YES_OPTION) {
						            	myFrame.dispose();
						            	Main.main(null);
						        } 
						            else if (response == JOptionPane.CLOSED_OPTION) {
						                System.exit(0);
						        }
						    
								}else {
									
									
									myPanel.revealAdjacent(myPanel.mouseDownGridX , myPanel.mouseDownGridY);
									myPanel.colorArray[myPanel.mouseDownGridX ][myPanel.mouseDownGridY] = Color.gray;
									myPanel.repaint();
									
								}
								
								myPanel.repaint();
						}
					}
				}
				myPanel.repaint();
				break;
			case 3:		//Right mouse button
				Component c2 = e.getComponent();
				while (!(c2 instanceof JFrame)) {
					c2 = c2.getParent();
					if (c2 == null) {
						return;
					}
				}
				JFrame myFrame2 = (JFrame) c2;
				MyPanel myPanel2 = (MyPanel) myFrame2.getContentPane().getComponent(0);
				Insets myInsets2 = myFrame2.getInsets();
				int x2 = myInsets2.left;
				int y2 = myInsets2.top;
				e.translatePoint(-x2, -y2);
				int xx = e.getX();
				int yy = e.getY();
				myPanel2.x = xx;
				myPanel2.y = yy;
				int gridX2 = myPanel2.getGridX(xx, yy);
				int gridY2 = myPanel2.getGridY(xx, yy);
				if ((myPanel2.mouseDownGridX == -1) || (myPanel2.mouseDownGridY == -1)) {
					//Had pressed outside
					//Do nothing
				} else {
					if ((gridX2 == -1) || (gridY2 == -1)) {
						//Is releasing outside
						//Do nothing
					} else {
						if ((myPanel2.mouseDownGridX != gridX2) || (myPanel2.mouseDownGridY != gridY2)) {
							//Released the mouse button on a different cell where it was pressed
							//Do nothing
						} else {
							//Released the mouse button on the same cell where it was pressed
							//flag
							if (myPanel2.flagCount[myPanel2.mouseDownGridX][myPanel2.mouseDownGridY] == 0 && myPanel2.colorArray[myPanel2.mouseDownGridX][myPanel2.mouseDownGridY] != Color.GRAY) {
							myPanel2.colorArray[myPanel2.mouseDownGridX][myPanel2.mouseDownGridY] = Color.RED;
							myPanel2.flagCount[myPanel2.mouseDownGridX][myPanel2.mouseDownGridY] = 1;
							
							//display "play again" box
							if(myPanel2.arEqual()) {
								int response = JOptionPane.showConfirmDialog(null, "You Win", "GAME OVER",
					                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					        if (response == JOptionPane.NO_OPTION) {
					                System.exit(0);
					        } 
					            else if (response == JOptionPane.YES_OPTION) {
					            	myFrame2.dispose();
					            	Main.main(null);
					        } 
					            else if (response == JOptionPane.CLOSED_OPTION) {
					                System.exit(0);
					        }
							}
							
							}else if (myPanel2.flagCount[myPanel2.mouseDownGridX][myPanel2.mouseDownGridY] ==1 && myPanel2.colorArray[myPanel2.mouseDownGridX][myPanel2.mouseDownGridY] != Color.GRAY) {
								myPanel2.colorArray[myPanel2.mouseDownGridX][myPanel2.mouseDownGridY] = Color.white;
								myPanel2.flagCount[myPanel2.mouseDownGridX][myPanel2.mouseDownGridY] = 0;
							}
			
							myPanel2.repaint();
							
						}
					}
				}
				myPanel2.repaint();
				break;
			default:    //Some other button (2 = Middle mouse button, etc.)
				//Do nothing
				break;
		}
	}
}