///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            Pants on fire
// Files:            Fireball.java
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
 * This is the class files for the Type Fireball, the methods here determines 
 * the weapon of the Fire used to attack Hero and pant. There are methods 
 * that controls how the fireball are shot randomly or destroy upon collision 
 * with other objects.
 * @author: Eric Chan & Aaron Tan
 */
public class Fireball {

	private Graphic graphic;
	private float speed;
	private boolean isAlive = true;

	//create sets the location and direction of trajectory of a fireball
	/**
	 * Fireball speed should be set to 0.2f. The x, y position and 
	 * directionAngle will be set by the user when new Fireball instances is 
	 * created.
	 * @param x
	 * @param y
	 * @param directionAngle
	 */
	public Fireball(float x, float y, float directionAngle) {
		speed = 0.2f;
		graphic = new Graphic();
		graphic.setType("FIREBALL");
		graphic.setX(x);
		graphic.setY(y);
		graphic.setDirection(directionAngle);
	}
	//draws the fireball at each instance of time, while updating its position
	//as time elapsed
	/**
	 * As Fireballs are updated, they will continuously move in a specific 
	 * direction, similar to Water.  But instead of only travelling 200 pixels 
	 * (like Water), Fireballs will continue to travel until they have gone at 
	 * least 100 pixels beyond the edge of the screen (100 pixels left of the 
	 * left edge of the screen, above the top, below the bottom, or right of the
	 * right edge of the screen).  After a Fireball moves this far off of the 
	 * screen, their Graphic should be destroy()-ed and their isAlive field 
	 * should be changed from true to false.  Once a Fireballs isAlive field 
	 * is switched to false, they should stop moving and drawing themselves 
	 * when update() is called on them.
	 * @param time
	 */
	public void update(int time) {
		graphic.draw();
		graphic.setX(graphic.getX() + graphic.getDirectionX()*speed*time);	
		graphic.setY(graphic.getY() + graphic.getDirectionY()*speed*time);
		//isAlive = graphic.getX() >=-100 |graphic.getX() <=Engine.getWidth()+100|
				//graphic.getY()>=-100|graphic.getY()<=Engine.getHeight()+100;
				isAlive = true;
				if(graphic.getX() <(-100) |graphic.getX() 
						>(Engine.getWidth()+100)|
						graphic.getY()<(-100)|graphic.getY()
						>(Engine.getHeight()+100)){
					isAlive = false;
					graphic.destroy();
				}
	}
	//obtaines the graphic of a fireball
	public Graphic getGraphic() {
		return this.graphic;
	}
	/**
	 * When the Fireball that this method is called on is colliding with any of 
	 * the non-null Water references in the water array parameter, two 
	 * things should happen: 
	 * 1) the Fireballs isAlive field should be set to false and its Graphic
	 *  should be destroy()-ed,
	 *  and 2) the colliding Waters graphic should be destroy()-ed and its 
	 *  reference within the 
	 *  water array should be set to null.  
	 * So colliding Water and Fireball objects will effectively obliterate 
	 * one another.
	 * @param water
	 */
	//keeps track of the fireball and see if it collides with water. If it does
	//collide with water, it would be assigned so that it would be removed from
	//the game
	public void handleWaterCollisions(Water[] water) {
		for(int i =0; i<water.length;i++){
			if (water[i]!=null){
				boolean collideWater = graphic.isCollidingWith
						(water[i].getGraphic());
				if(collideWater){
					isAlive = false;
					graphic.destroy();
					water[i].getGraphic().destroy();
					water[i]=null;
				}
			}
		}
	}
	/*Well also want to destroy the Fireballs graphic and set its isAlive 
	 *  field to false.  To do this well add a destroy method to the Fireball 
	 *  class that does these two things, and well call this
	 *  method whenever we find a Fireball that is colliding with a Pant.
	 *  */
	public void destroy() {
		isAlive = false;
		graphic.destroy();	
	}
	//return true to removes the fireball if it either touches a pant or if 
	//it gets hit by water otherwise, return false
	public boolean shouldRemove() {
		if(isAlive){
			return false;
		}
		return true;
	}
}
