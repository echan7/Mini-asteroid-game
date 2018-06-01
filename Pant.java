///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            Pants on fire
// Files:            Pant.java
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
 * This is the class file for the type Pant, the victim of the game. The Hero
 * have to protect all the pants from turning into Fire to win the game.
 * The methods determines how the pant are displayed on the screen and 
 * how it is burned into Fire upon collision with Fireball. 
 * @author: Eric Chan & Aaron Tan
 */
import java.util.Random;
import java.util.ArrayList;

//declares the instantces for the pant class
public class Pant {
	private Graphic graphic;
	private Random randGen;
	private boolean collideFireball;

	//sets the graphics and location of the pants
	/**
	 * All that these pants need to do is create a Graphic in the correct 
	 * position and then draw that graphic every time the update method is 
	 * called. The x,y position should be set by the user. 
	 * randGen is a Random number generator to pass onto any Fire that is 
	 * created as a result of this Pant being hit by a Fireball
	 * @param x
	 * @param y
	 * @param randGen
	 */
	public Pant(float x, float y, Random randGen) {

		graphic = new Graphic();
		graphic.setType("PANT");
		graphic.setX(x);
		graphic.setY(y);
		this.randGen = randGen;
	}
	//The update method of this class to draw the graphic Pant on the screen
	public void update(int time) {
		graphic.draw();
	}
	//The method to retrieve the graphic from this class
	public Graphic getGraphic() {
		return this.graphic;
	}
	/**
	 * Whenever you find a Fireball that is colliding with the current Pant,
	 *  destroy the current Pants graphic and prevent it from being display.  
	 *  Well also want to destroy the Fireballs graphic and set its isAlive 
	 *  field to false.  To do this well add a destroy method to the Fireball 
	 *  class that does these two things, and well call this
	 *  method whenever we find a Fireball that is colliding with a Pant.
	 * @param fireballs
	 * @return
	 */
	/**
	 * In addition to getting rid of the colliding Pant and Fireball objects,
	 *  well also want to create a new Fire whenever one of these Pant-Fireball 
	 *  collisions are detected.  This new fire should placed at the location 
	 *  that was previously occupied by the now burning Pant object, 
	 *  and a reference 
	 *  to this new Fire object should be returned from the 
	 *  handleFireballCollisions() method. 
	 *  When no collision is found, this method should simply return null.
	 */	
	//keeps track of the pants that have been collided with a fireball, removes
	//that specific fireball and adds a fire
	public Fire handleFireballCollisions(ArrayList<Fireball> fireballs) {
		for(int i=0;i<fireballs.size();i++){
			if(fireballs.get(i)!=null){
				collideFireball = graphic.isCollidingWith(fireballs.get(i)
						.getGraphic());
				if(collideFireball){
					Fire fire = new Fire (graphic.getX(),graphic.getY(),
							randGen);
					graphic.destroy();
					fireballs.get(i).destroy();
					return fire;
				}
			}
		}
		return null;}
	
	//return true to removes the pants if it gets burnt by a fireball
	//otherwise return false
	public boolean shouldRemove() {
		if(collideFireball){
			return true;
		}
		return false;
	}
}
