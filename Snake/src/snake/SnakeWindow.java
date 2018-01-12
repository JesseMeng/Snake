package snake;

import java.awt.*;
import java.awt.event.*;

public class SnakeWindow extends Frame{
		//properties of each snake block
		public static final int BWIDTH = 15 ;
		public static final int BHEIGHT = 15 ;
		
		//size of frame in blocks
		public static final int ROW = 40;
		public static final int COL = 40;
		
		//Score of player
		private int score = 0;	
		
		public int getScore() {
			return score;
		}
		
		public void setScore(int score) {
			this.score = score;
		}
		
		//Thread used for drawing
		private PaintThread paintThread = new PaintThread();
		
		private Image offScreenImage = null;
		private Snake snake = new Snake(this);
		private Food food = new Food();
		private boolean gameOver = false;
		private static SnakeWindow snakewindow =null;
		
		public void launch(){
			//basic setups
			this.setTitle("Jesse's Snake Game!");
			this.setSize(ROW*BHEIGHT, COL*BWIDTH);
			this.setLocation(30, 40);
			this.setBackground(Color.GRAY);
			this.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}		
			});
			this.setResizable(false);
			this.setVisible(true);
			//adding the listener for keyboard
			this.addKeyListener(new KeyboardMonitor());
			new Thread(paintThread).start();
		}	
		
		public void gameOver(){
			gameOver = true;
		}
		
		public void update(Graphics g) {
			if(offScreenImage==null){
				offScreenImage = this.createImage(ROW*BHEIGHT, COL*BWIDTH);
			}
			Graphics offg = offScreenImage.getGraphics();
			paint(offg);
			g.drawImage(offScreenImage, 0, 0, null);		
			if(gameOver){
				g.setColor(Color.RED);
				g.drawString("Game Over!!! Feels bad :(", ROW/2*BHEIGHT, COL/2*BWIDTH);
				paintThread.dead();
			}
			snake.draw(g);
			boolean b_Success=snake.eat(food);
			//each time a food is eaten, add 25 to the score
			if(b_Success){
				score+=25;
			}
			food.draw(g);
			display(g);			
		}
		
		//displaying text for players
		public void display(Graphics g){
			Color c = g.getColor();
			g.setColor(Color.YELLOW);
			g.drawString("P: Pause  Space: Continue   F5:Restart", 5*BHEIGHT, 3*BWIDTH);
			g.drawString("Score:"+score, 5*BHEIGHT, 5*BWIDTH);		
			g.setColor(c);
			
		}

		public static void main(String[] args) {
			snakewindow = new SnakeWindow();
			snakewindow.launch();
		}
		
		//thread class that processes pause and restart functions
		private class PaintThread implements Runnable{
			private static final boolean  running = true;
			private boolean  pause = false;
			public void run() {
				while(running){		
					if(pause){	
						try {
							Thread.sleep(100);  //speed of the game
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						continue;
					}
					repaint();
					try {
						Thread.sleep(100);  //speed of the game
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}	
			}
			// special key functions
			public void pause(){
				pause = true;
			}
			public void recover(){
				pause = false;
			}
			public void dead(){
				pause = true;
			}
			public void reStart(){
				snakewindow.gameOver = false;
				this.pause = false;
				snake = new Snake(snakewindow);
			}	
		}
		
		//inner class for detecting special keyboard actions
		private class KeyboardMonitor extends KeyAdapter{
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				// p pauses the game
				if(key == KeyEvent.VK_P){
					paintThread.pause();
				}
				// space starts a paused game
				else if(key == KeyEvent.VK_SPACE){
					paintThread.recover();
				}
				// F5 restarts the game
				else if(key == KeyEvent.VK_F5){
					paintThread.reStart();
				}
				else{
					snake.keyPressed(e);
				}			
			}	
		}
}

