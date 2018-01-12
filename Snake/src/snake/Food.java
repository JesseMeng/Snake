package snake;

import java.awt.*;
import java.util.Random;

public class Food {
	
	//location of food
	private int row;
	private int col;
	
	//size of food is one block
	private static final int BWIDTH = SnakeWindow.BWIDTH;
	private static final int BHEIGHT = SnakeWindow.BHEIGHT;
	
	private static final Random rand = new Random();
	private Color color = Color.RED;
	
	public Food(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	//food appears at random location
	public Food() {
		this((rand.nextInt(SnakeWindow.ROW-2))+2,(rand.nextInt(SnakeWindow.COL-2))+2);
	}
	
	public void appear(){
		this.row = (rand.nextInt(SnakeWindow.ROW-2))+2;
		this.col = (rand.nextInt(SnakeWindow.COL-2))+2;
	} 
	
	public void draw(Graphics g){
		Color c= g.getColor();
		g.setColor(color);
		g.fillOval(col*BWIDTH+5, row*BHEIGHT+5, BWIDTH/2, BHEIGHT/2);
		g.setColor(c);
	}
	
	//used to detect if snake touches the food
	public Rectangle getAreaUnit(){
		return new Rectangle(col*BWIDTH, row*BHEIGHT, BWIDTH, BHEIGHT);
	}
	
}