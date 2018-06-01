///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            Pants on fire
// Files:            Hero.java
// Semester:         CS302 Spring 2016
//
// Author:           Aaron Tze-Rue Tan
// Email:            atan24@wisc.edu
// CS Login:         aaront
// Lecturer's Name:  Jim Williams
// Lab Section:      313
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ////////////////////
//
//                   CHECK ASSIGNMENT PAGE TO see IF PAIR-PROGRAMMING IS ALLOWED
//                   If pair programming is allowed:
//                   1. Read PAIR-PROGRAMMING policy (in cs302 policy) 
//                   2. choose a partner wisely
//                   3. REGISTER THE TEAM BEFORE YOU WORK TOGETHER 
//                      a. one partner creates the team
//                      b. the other partner must join the team
//                   4. complete this section for each program file.
//
// Pair Partner:     Eric Jun Hong Chan
// Email:            echan7@wisc.edu
// CS Login:         echan
// Lecturer's Name:  Jim Williams
// Lab Section:      314
//
////////////////////////////////////////////////////////////////////////////////
/**
 * This is the class file for the type Hero, the main protagonist of this game,
 * here it determines how the player going to control the Hero and win the game
 * or lose if damaged by the villain. Besides, there are methods controlling
 * how the Hero going to use it's weapon, water.
 * @author: Eric Chan & Aaron Tan
 */
import java.util.ArrayList;

public class Hero 
{
	private Graphic graphic;
	private float   speed;
	private int     controlType;

	//create declarations for the specifics of hero, such as the graphic,
	//movement speed and control type
	/**
	 * This is the method to call the main protagonist instances, the speed 
	 * should be set to 0.12f. The x,y position and the controlType will be
	 * set by the user once Hero instances is called. 
	 * @param x
	 * @param y
	 * @param controlType
	 */
	public Hero(float x, float y, int controlType){
		speed = 0.12f;
		this.controlType = controlType;
		graphic = new Graphic ();
		graphic.setType("HERO");

	//set position of HERO to the middle of the screen
		graphic.setX(x);
		graphic.setY(y);
	}
	/**
	 * Controltype 1 determines the Hero move according to the keyboard letters
	 * "W,A,S,D" pressed and the direction will be facing the key pressed.
	 * Controltype 2 determines the Hero to face the direction of the mouse but
	 * movement is still restricted to the key pressed
	 * Controltype 3 determines Hero direction according to the mouse and its 
	 * movement restricted to be always 20 pixels away from the mouse. 
	 * water array should be updated if "SPACE" or left click of the mouse is
	 * pressed, to allow the Hero to shoot waters.
	 * @param time
	 * @param water
	 */
	//updates the hero according to its movement, the water it shoots and 
	//direction it's facing
	public void update(int time, Water[] water){
		
		//draws the hero at each instance
		graphic.draw();
		//Move the Hero to new position when W,S,A or D key is held
		//Hero movement will be the increment of it's speed to time 
		if (controlType == 1){
			if (Engine.isKeyHeld("D")){
				graphic.setDirection(graphic.getX() + (speed*time), 
						graphic.getY());
				graphic.setX(graphic.getX() + (speed*time));

			}
			else if (Engine.isKeyHeld("A")){
				graphic.setDirection(graphic.getX() - (speed*time), 
						graphic.getY());
				graphic.setX(graphic.getX() - (speed*time));

			}
			else if (Engine.isKeyHeld("W")){
				graphic.setDirection(graphic.getX(), graphic.getY()-
						(speed*time));
				graphic.setY(graphic.getY() - (speed*time));
			}
			else if (Engine.isKeyHeld("S")){
				graphic.setDirection(graphic.getX(), graphic.getY()+
						(speed*time));
				graphic.setY(graphic.getY() + (speed*time));
			}
		}
		
		//having movements similar to that of controlType1, but hero would be
		//always facing the cursor instead of the direction it is moving
		if(controlType == 2){
			graphic.setDirection(Engine.getMouseX(), Engine.getMouseY());
			if (Engine.isKeyHeld("D")){
				graphic.setX(graphic.getX() + (speed*time));
			}
			else if (Engine.isKeyHeld("A")){
				graphic.setX(graphic.getX() - (speed*time));
			}
			else if (Engine.isKeyHeld("W")){
				graphic.setY(graphic.getY() - (speed*time));
			}
			else if (Engine.isKeyHeld("S")){
				graphic.setY(graphic.getY() + (speed*time));
			}
		}
		
		//set the hero to face the direction of the cursor of the mouse and to
		//move towards the mouse up until a spacing of 20 pixels from the
		//mouse cursor
		/* Control type 3 starts with getting the hero to face the 
		 * direction of the mouse cursor (as we accomplished in the 
		 * previous step), but instead of moving them according to 
		 * key presses, the hero will continuously move toward the mouse, 
		 * until they get within 20 pixels of its location
		 */
		if(controlType == 3){
			graphic.setDirection(Engine.getMouseX(), Engine.getMouseY());
			float X = Engine.getMouseX()-graphic.getX();
			float Y = Engine.getMouseY()-graphic.getY();
			if (Math.sqrt(X*X+Y*Y)>20){
				graphic.setX(graphic.getX() + 
						graphic.getDirectionX()*speed*time);	
				graphic.setY(graphic.getY() + 
						graphic.getDirectionY()*speed*time);
			}
		}
		/**
		 *  the job of the Heros update method to look through this water 
		 *  array for an empty location (a null).  If a null is found and the 
		 *  player is holding down either the spacebar or the left mouse 
		 *  button, then the hero should create one new Water object and 
		 *  add the reference to this new Water into the previously empty 
		 *  (null) position with the water array. 
		 */
		//fires a water when either the space bar or the left mouse key is
		//pressed
		for (int i=0; i<water.length; i++){
			if (water[i] == null && ((Engine.isKeyHeld("SPACE") | 
					Engine.isKeyHeld("MOUSE")))){
				water[i] = new Water(graphic.getX(), graphic.getY(), 
						graphic.getDirection());
				break;
			}
		}


	}
	//gets the graphics for hero
	public Graphic getGraphic() {
		return this.graphic;
	}
	/**
	 * This method should return true, if and only if the hero is colliding 
	 * with one of these fireballs in this ArrayList.  This method should be 
	 * called from the Games update(), and when it returns true the Games 
	 * update should return "QUIT" to indicate that the player has lost the 
	 * game by being hit by a fireball.  Test this out to ensure that it is 
	 * working as expected.
	 * @param fireballs
	 * @return
	 */
	//hero dies if he is hit by a fireball, and this gets recorded
	public boolean handleFireballCollisions(ArrayList<Fireball> fireballs) {
		for(int i=0; i<fireballs.size(); i++){
			if(fireballs.get(i)!=null){
				boolean collideFireball = this.graphic.isCollidingWith(
						(fireballs.get(i)).getGraphic());
				if(collideFireball){
					return true;
				}
			}
		}
		return false;
	}

}
