package snake;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Snake {
	
	private static final int BWIDTH = SnakeWindow.BWIDTH;
	private static final int BHEIGHT = SnakeWindow.BHEIGHT;
	private Node head = null;
	private Node tail = null;	
	private SnakeWindow snakewindow;
	
	//start property of snake
	private Node node = new Node(3,4,Direction.DOWN);
	private int size = 0;
	
	public Snake(SnakeWindow snakewindow) {
		//start with 1 block
		head = node;
		tail = node;
		++size;
		this.snakewindow = snakewindow;	
	}

	public void draw(Graphics g){
		if(head==null){
			return ;
		}
		move();
		for(Node node = head;node!=null;node = node.next){
			node.draw(g);
		}	
	}
	
	//moving is adding a node to the head and deleting a node from tail
	public void move() {
		addNodeInHead();
		//check if snake is dead
		judge();
		deleteNodeInTail();
	}
	
	private void judge() {
		//check if snake hits the edges
		if(head.row<2||head.row>SnakeWindow.ROW||head.col<0||head.col>SnakeWindow.COL){
			this.snakewindow.gameOver();
		}	
		//check if snake hits itself
		for(Node node =head.next;node!=null;node = node.next){
			if(head.row==node.row&&head.col == node.col){
				this.snakewindow.gameOver();
			}
		}
	}
	
	private void deleteNodeInTail() {
		Node node = tail.pre;
		node.next = null;
		tail = node;
	}
	
	//add the block that the snake would have gone to in the next cycle to the snake
	private void addNodeInHead() {
		Node node = null;
		switch(head.dir){
		case LEFT:
			node = new Node(head.row,head.col-1,head.dir);
			break;
		case UP:
			node = new Node(head.row-1,head.col,head.dir);
			break;
		case RIGHT:
			node = new Node(head.row,head.col+1,head.dir);
			break;
		case DOWN:
			node = new Node(head.row+1,head.col,head.dir);
			break;
		}
		node.next = head;
		head.pre = node;
		head = node;
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		//change directions of snake
		switch(key){
		case KeyEvent.VK_LEFT :
			if(head.dir!=Direction.RIGHT){
				head.dir = Direction.LEFT;
			}
			break;
		case KeyEvent.VK_UP :
			if(head.dir!=Direction.DOWN){
				head.dir = Direction.UP;
			}
			break;
		case KeyEvent.VK_RIGHT :
			if(head.dir!=Direction.LEFT){
				head.dir = Direction.RIGHT;
			}
			break;
		case KeyEvent.VK_DOWN :
			if(head.dir!=Direction.UP){
				head.dir = Direction.DOWN;
			}
			break;
		}
	}
	
	//used to see whether food is eaten
	public Rectangle getHeadUnit(){
		return new Rectangle(head.col*BWIDTH, head.row*BHEIGHT, BWIDTH, BHEIGHT);
	}
	
	public boolean eat(Food food){
		if(this.getHeadUnit().intersects(food.getAreaUnit())){
			addNodeInHead();
			food.appear();
			return true;
		}
		else{
			return false;
		}
	}
	
	public class Node {		
		//each block's location
		private int row;
		private int col;
		//direction of each block
		private Direction dir ;		
		private Node pre; // previous block
		private Node next;	//next block
		public Node(int row, int col, Direction dir) {
			this.row = row;
			this.col = col;
			this.dir = dir;
		}
		public void draw(Graphics g){
			Color color = g.getColor();
			g.setColor(Color.BLACK);
			g.fillRect(col*BWIDTH, row*BHEIGHT, BWIDTH, BHEIGHT);
			g.setColor(color);		
		}
	}
}
