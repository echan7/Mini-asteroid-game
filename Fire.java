///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            Pants on fire
// Files:            Fire.java
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
 * This is the class files for the Type Fire, the methods here determines 
 * how the Fire, the main villain of this game are set and determines
 * how it is going to attack the Hero and Pants with fireballs. Besides, it also
 * contains methods that determines how it is going to be destroyed by the Hero.
 * @author: Eric Chan & Aaron Tan
 */
import java.util.Random;

//create a declaration of the instant variables that is needed/used in this
//class
public class Fire {
	private Graphic graphic;
	private Random randGen;
	private int fireballCountdown;
	private Fireball fireball;
	private int heat;
	private boolean collideWater;

	//Create sets up the specific location of a fire and its hit point, denoted
	//by the variable heat
	/**
	 * The fireballCountdown field represents the number of milliseconds 
	 * before the next Fireball is hurled from this Fire.  It should be 
	 * initialized to a random value between 3000 and 5999 (~3-6 seconds), 
	 * and then decremented whenever the Fires update() method is called. 
	 * heat variable is to determine whether Fire should be alive of not.  
	 * @param x
	 * @param y
	 * @param randGen
	 */
	public Fire(float x, float y, Random randGen) {
		heat = 40;
		graphic = new Graphic();
		graphic.setType("FIRE");
		graphic.setX(x);
		graphic.setY(y);
		fireballCountdown = randGen.nextInt(2999)+3000;
		this.randGen = randGen;
	}
	//draws the fire in the game, and keeps track of the number of fire present
	//in the game and also creates fireballs
	/**
	 * When fireballCountdown <0 , a new Fireball 
	 * should be created and this fireballCountdown should be reset to a 
	 * new random value between 3000 and 5999.  Whenever a fireball is created,
	 * its position should match the position of the fire that is ejecting it,
	 * and its direction should be randomly chosen from a value between 0 
	 * and 2pi.  The Fires update method should return null unless a new 
	 * Fireball has just been created, in which case a reference to that new 
	 * Fireball will be returned instead. 
	 * @param time
	 * @return
	 */
	public Fireball update(int time) {
		graphic.draw();
		fireballCountdown-=time;
		if(fireballCountdown <= 0){
			if(heat>0){
				fireball = new Fireball (this.graphic.getX(),
						this.graphic.getY(), 
						(float)(randGen.nextFloat()*Math.PI*2));
				fireballCountdown = randGen.nextInt(2999)+3000;
				return fireball;
			}
		}
		return null;

	}
	//gets the graphics of fire
	public Graphic getGraphic() {
		return this.graphic;
	}
	/**
	 * Every time this method finds a Water object colliding with a Fire, 
	 * that Fires heat should be decreased by one.  The Water objects 
	 * graphic should also be destory()-ed and its array position should be
	 * reset to null, whenever this happens. If the Fires heat ever drops 
	 * below one, 
	 * then the Fires graphic should be destroy()-ed, 
	 * it should stop being displayed, and it should also stop throwing
	 *  Fireballs.
	 */
	public void handleWaterCollisions(Water[] water) {
		for(int i=0; i<water.length;i++){
			if(water[i]!=null){
				collideWater =graphic.isCollidingWith(water[i].getGraphic());
				if(collideWater){
					heat--;
					water[i].getGraphic().destroy();
					water[i]=null;
					if(heat<1){
						graphic.destroy();
					}
				}
			}
		}

	}
	//return true to removes a fire when it has been hit 
	//by a water after 40 times otherwise, return false
	public boolean shouldRemove() {
		if(heat<1){
			return true;
		}
		return false;}
}
