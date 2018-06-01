///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            Pants on fire
// Files:            Water.java
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
 * This is the class file for the type Water, the weapon of the Hero. There are
 * methods that deteremine how the waters are going to travel for in the game.
 * @author: Eric Chan & Aaron Tan
 */
//declares the instance variables used for the water class
public class Water {

	private Graphic graphic;
	private float speed;
	private float distanceTraveled;

	//sets the specifics of water, such as the traveling speed, graphics, and
	//direction it travels
	/**
	 * Water speed should be set to 0.7f. The x, y position and 
	 * direction will be set by the Hero when new water instances is 
	 * created.
	 * @param x
	 * @param y
	 * @param direction
	 */
	public Water(float x, float y, float direction) {
		speed = 0.7f;
		graphic = new Graphic();
		graphic.setType("WATER");
		graphic.setDirection(direction);

		//set position of WATER on the screen
		graphic.setX(x);
		graphic.setY(y);
	}
	//updates the water graphic as it moves on the screen after being shot by
	//the hero, and also keeps track of the distance traveled such that after 
	//traveling 200 pixels, it would be removed
	/**
	 * Each water object should travel no more than 200 pixels, before it 
	 * splashes into the ground.  Each water object already has a 
	 * distanceTraveled field to keep track of how far it has moved.  
	 * Be sure to initialize this field, keep it up to date as each Water 
	 * object moves, and check whether it has traveled beyond 200 pixels at 
	 * the end of every update.  While a Water object has traveled less than 
	 * or equal to 200 pixels, it should return a reference to itself.  
	 * As soon as the Water has traveled more than 200 pixels it should return 
	 * null instead.  This will help our Game class only update the water when 
	 * there is a Water that as traveled less than 200 pixels and still needs 
	 * to be updated
	 * @param time
	 * @return
	 */
	public Water update(int time) {
		graphic.draw();
		float startDistance = graphic.getX();
		float startDistance2 = graphic.getY();
		graphic.setX(graphic.getX() + graphic.getDirectionX()*speed*time);	
		graphic.setY(graphic.getY() + graphic.getDirectionY()*speed*time);
		float X = graphic.getX() - startDistance;
		float Y = graphic.getY() - startDistance2;
		float distance = (float)Math.sqrt(X*X+Y*Y);
		if (distance !=0){
			distanceTraveled+=distance;
		}
		if (distanceTraveled >200){
			return null;
		}
		return this;
	}
	//gets the graphics of water
	public Graphic getGraphic() {
		return this.graphic;
	}
}
