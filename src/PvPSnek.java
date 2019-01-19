//PvP snek
//You win when your opponent loses
//if the heads run into each other the longer snake wins
//otherwise the game is a draw

import hsa_ufa.Console;

import java.awt.*;
import java.net.URL;
import java.applet.AudioClip;
import java.applet.Applet;

public class PvPSnek {

	//Class variable since this will be used in more than 1 method within a class
	static Console c = new Console(809, 700, "2-Player Snek v1.0");//81x65
	//Two snakes, represented by 2 sets of 2 arrays
	//x1 and y1 denotes the coordinates of the first snake, and x2 and y2 for second snake
	//Arrays begin with length 2 so the tail can fall off without triggering an AIOoB error

	/*
	 * Arrays are used here because there will be a variable but definite number of values that needs to be stored
	 * If snakes did not use arrays then it would take hundreds of variables to represent even one coordinate of the snake
	 */
	static int[] x1 = new int[2];
	static int[] y1 = new int[2];		
	static int[] x2 = new int[2];
	static int[] y2 = new int[2];


	//Randomly starts the food somewhere
	//Since coordinates are integral, the int type will be used to best conserve memory
	//These are local variables because these are required within many subroutines and functions within this method
	static int x_food = random_int(0,80);
	static int y_food = random_int(0,64);

	//Lengths of snakes
	static int length1 = 0;
	static int length2 = 0;
	static int dir1 = -1; //snake 1's direction
	static int dir2 = -1; //snake 2's direction

	static AudioClip ding = Applet.newAudioClip(PvPSnek.class.getResource("ding.wav"));

	public static void main(String args[]) throws InterruptedException {

		//Starting locations of the snakes
		x1[0] = 20;
		y1[0] = 32;
		x2[0] = 60;
		y2[0] = 32;

		c.setFont(new Font("Arial", Font.PLAIN, 30));

		//For the "click to continue" component
		c.enableMouse();
		int clicks = 0;

		c.drawString("Welcome to the PvP Snek Game!", 180, 100);
		c.drawString("Player 1: Use WASD to move the snake.", 135, 150);
		c.drawString("Player 2: Use the arrow keys to move the snake.", 80, 200);
		c.drawString("Eat food to grow and gain points.", 180, 250);
		c.drawString("The snake that collides with the body of another snake loses.", 5, 300);
		c.drawString("If both snakes' heads collide, the longer snake wins.", 70, 350);
		c.drawString("Click to continue...", 270, 450);

		/*
		 * While loop: used here because the program wants to wait indefinitely until the mouse is clicked
		 */
		while (clicks == 0){
			clicks += c.getMouseClick();
			Thread.sleep(100);
		} 
		snake();
	}
	//The snake() method contains all the game's logic
	static void snake() throws InterruptedException{

		/*
		 * Again, here is a while loop because the game logic wants to run indefinitely until it is over
		 */
		while (true){

			//Determines the direction of the snake's travel
			//The conditional also prevents the snake from turning into itself 
			//The snake must move sideways to its current direction before reversing direction
			//If statements are used because these are asynchronous and would not fit well with a switch statement
			if (c.isKeyDown('A') && dir1 != 2)
				dir1 = 0; //left
			if (c.isKeyDown('W') && dir1 != 3)
				dir1 = 1; //up
			if (c.isKeyDown('D') && dir1 != 0)
				dir1 = 2; //right
			if (c.isKeyDown('S') && dir1 != 1)
				dir1 = 3; //down

			if (c.isKeyDown(Console.VK_LEFT) && dir2 != 2)
				dir2 = 0; //left
			if (c.isKeyDown(Console.VK_UP) && dir2 != 3)
				dir2 = 1; //up
			if (c.isKeyDown(Console.VK_RIGHT) && dir2 != 0)
				dir2 = 2; //right
			if (c.isKeyDown(Console.VK_DOWN) && dir2 != 1)
				dir2 = 3; //down

			/*
			 * For loop: for when the entire snake array needs to be run through
			 * i.e. # of times to iterate is known
			 */
			//Shifts the snakes by 1
			for (int i=length1; i>=0; i--){
				x1[i+1]=x1[i];
				y1[i+1]=y1[i];				
			}

			for (int i=length2; i>=0; i--){
				x2[i+1]=x2[i];
				y2[i+1]=y2[i];				
			}

			//A switch structure, useful when you want to control the direction of the snake
			//Switch is better in this case because there are 4 non-binary/boolean options
			//The mini if statements allows for snakes to loop around the edges of the screen
			switch (dir1) {
			case 0: 
				x1[0] = (x1[0] == 0) ? 80 : x1[0]-1;				
				break;
			case 1: 
				y1[0] = (y1[0] == 0) ? 64 : y1[0]-1;
				break;
			case 2: 
				x1[0] = (x1[0] == 80) ? 0 : x1[0]+1;
				break;
			case 3: 
				y1[0] = (y1[0] == 64) ? 0 : y1[0]+1;
				break;
			default: break;
			}

			switch (dir2) {
			case 0: 
				x2[0] = (x2[0] == 0) ? 80 : x2[0]-1;				
				break;
			case 1: 
				y2[0] = (y2[0] == 0) ? 64 : y2[0]-1;
				break;
			case 2: 
				x2[0] = (x2[0] == 80) ? 0 : x2[0]+1;
				break;
			case 3: 
				y2[0] = (y2[0] == 64) ? 0 : y2[0]+1;
				break;
			default: break;
			}

			//When a snake eats food that snake grows in size
			//also randomizes the location of the next food
			if ((x1[0] == x_food && y1[0] == y_food)||(x2[0] == x_food && y2[0] == y_food)){
				ding.play();
				if(x1[0] == x_food && y1[0] == y_food){
					length1++;
					int[] x1_new = new int[length1 + 2];
					int[] y1_new = new int[length1 + 2];
					for(int i=0; i<length1+1; i++){
						x1_new[i] = x1[i];
						y1_new[i] = y1[i];
					}
					x1 = x1_new;
					y1 = y1_new;
				} else if (x2[0] == x_food && y2[0] == y_food) {
					length2++;
					int[] x2_new = new int[length2 + 2];
					int[] y2_new = new int[length2 + 2];
					for(int i=0; i<length2+1; i++){
						x2_new[i] = x2[i];
						y2_new[i] = y2[i];
					}
					x2 = x2_new;
					y2 = y2_new;
				}
				do {
					x_food = random_int(0,80);
					y_food = random_int(0,64);		
				} while (head_hits_body(x1[0], y1[0], x1, y1, length1) || head_hits_body(x2[0], y2[0], x2, y2, length2));
			}

			//Draws the snakes and the scores
			synchronized(c){
				c.clear();
				c.setColor(Color.RED);
				for (int i=0; i<=length1; i++) {
					c.fillRect(x1[i]*10, y1[i]*10, 9, 9);
				}
				c.drawString("Score: "+length1, 25, 685);

				c.setColor(Color.BLUE);
				for (int i=0; i<=length2; i++) {
					c.fillRect(x2[i]*10, y2[i]*10, 9, 9);
				}
				c.drawString("Score: "+length2, 650, 685);

				c.setColor(Color.GREEN);
				c.fillRect(x_food*10, y_food*10, 9, 9);

				c.setColor(Color.BLACK);
				c.drawLine(0, 649, 809, 649);
			}

			//Checks if the snake has collided *after* the snakes have been drawn 
			//It is after the drawing because this way it prevents unfairness and confusion
			if (head_hits_body(x1[0], y1[0], x1, y1, length1)||head_hits_body(x1[0], y1[0], x2, y2, length1)){
				game_over(2);
				break;
			}
			if (head_hits_body(x2[0], y2[0], x2, y2, length2)||head_hits_body(x2[0], y2[0], x1, y1, length2)){
				game_over(1);
				break;
			}
			if(x1[0] == x2[0] && y1[0] == y2[0]){
				if (length1 > length2){
					game_over(1);
				} else if (length2 > length1){
					game_over(2);
				} else {
					game_over(0);
				}
				break;
			}
			Thread.sleep(100);
		}

	}

	//game_over method: shows the game over screen based on the winner
	//Parameters: winner - 0, 1, or 2, depends on which player won (or if it ended in a tie)
	static void game_over(int winner) throws InterruptedException {
		//Game over screen
		c.clear();
		c.setColor(Color.BLACK);
		c.drawString("GAME OVER!", 300, 200);

		switch (winner){
		case 1:
			c.setColor(Color.RED);
			c.drawString("PLAYER 1 WINS!", 275, 400);
			break;
		case 2: 
			c.setColor(Color.BLUE);
			c.drawString("PLAYER 2 WINS!", 275, 400);
			break;
		case 0:
			c.drawString("DRAW GAME", 300, 400);
		}
		c.setColor(Color.BLACK);
		c.drawString("Click to play a new game", 270, 450);
		//Returns to the game after user clicks
		int clicks = 0;
		while (clicks == 0){
			clicks += c.getMouseClick();
			Thread.sleep(100);
		} 
		//Resets everything for a new game
		x1 = new int[2];
		y1 = new int[2];		
		x2 = new int[2];
		y2 = new int[2];
		x1[0] = 20;
		y1[0] = 32;
		x2[0] = 60;
		y2[0] = 32;
		length1 = 0;
		length2 = 0;
		dir1 = -1; 
		dir2 = -1; 
		x_food = random_int(0,80);
		y_food = random_int(0,64);
		snake();
	}
	/* Randomly generates an integer
	 * Parameters:
	 * int min, int max: The minimum and maximum values (inclusive) that the RNG can generate
	 * Returns:
	 * A random integer between min and max inclusive
	 */
	static int random_int(int min, int max){
		//random integer generator
		return (int)(Math.random() * (max - min + 1)) + min; //[0,1)
	}

	/* Checks if the snakes have hit each other
	 * Parameters:
	 * int x, int y: The x and y coordinates of the snake's head
	 * int[] x1, int x2[]: The x and y coordinates of the snake's body in array form
	 * int length: The length of the snake that being checked for collision
	 * Returns:
	 * True if the snake's head is colliding with a snake's body
	 * False otherwise
	 */
	static boolean head_hits_body(int x, int y, int[] x1, int[] y1, int length){
		for (int i=1; i<length; i++){
			if (x == x1[i] && y == y1[i]){
				return true;
			} 
		}
		return false;
	}
}