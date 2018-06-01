///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            Pants on fire
// Files:            Game.java
//					 Fire.java
//					 Fireball.java
//					 Hero.java
//					 Pant.java
//					 Water.java
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
 * This is the main Game class of the source files. All main methods will start 
 * from here. Hero, Pant, Fire, Fireball and Water are all initialized and 
 * updated in this class files. 
 * In addition, there are addition methods to determine whether the player win 
 * or lose the game. Moreover, a scanner method to scan through the settings 
 * from a chosen files and run the game according to it.
 * @author: Eric Chan & Aaron Tan
 */
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;

public class Game 
{
	//declaration of instant variables that would be used for this program
	private Hero hero;
	private Water[] water;
	private ArrayList<Pant> pants;
	private ArrayList<Fireball> fireballs;
	private ArrayList<Fire> fires;
	private Fire updtFire;
	private Random randGen;
	
	//create class declaration
	/**
	 * The main Game method will have String level and and randGen random number
	 * generator as its parameter. It will initialize how many water should have
	 * inside an array and a new arraylist of fireballs. If RANDOM level is 
	 * passed through the String parameter, createRandomLevel is initiated with
	 * default settings of Pant, Hero and Fire.
	 * If any other String parameter is passed through, the load level method 
	 * will be initiated to set Hero, Pant and Fire requested by the settings 
	 * of the String.
	 * @param level
	 * @param randGen
	 */
	public Game(String level, Random randGen) 
	{
		//create the number of water that would be shot by hero
		water = new Water[8];
		//create a random number of fireball to be shot
		fireballs = new ArrayList<>();
		if(level == "RANDOM"){
			this.createRandomLevel();
		}
		else if (level != "RANDOM"){
			this.loadLevel(level);
		}
	}
	
	/**
	 * The main update method for instances Hero, Water, Fire, Fireball and
	 * Pant so the graphics and be drawn and run on the game.
	 * @param time
	 * @return
	 */
	public String update(int time)
	{
		//update hero during movement/stationary
		hero.update(time, water);
		//update water when it is fired from the hero
		for (int i =0; i<water.length; i++ ){
			if(water[i] != null) 
			{water[i] = water[i].update(time);}
		}
		//create the instance of pant, in which specific cases, such as being
		//bunt, would be updated to carry out the necessary actions
		/**
		 * When handFireballCollisions() is called from the Games update() 
		 * method, 
		 * youll need to check its return value.  Whenever this method returns a 
		 * non-null reference to a Fire object, that object should be added to 
		 * the Games ArrayList of Fire, 
		 * so that it can be updated along with the other Fire objects.
		 */
		for (int j =0; j<pants.size();j++){
			if(pants.get(j)!=null){
				pants.get(j).update(time);
				Fire newFire = pants.get(j).handleFireballCollisions(fireballs);
				if(newFire!= null){
					fires.add(newFire);
				}
			}
			if(pants.get(j).shouldRemove()){
				pants.remove(j);
			}
		}

		//updates fire for specific cases, such as being shot by water from the
		//hero
		/**
		 * The Games update() method will then be responsible for adding new 
		 * Fireballs create in this way into its own ArrayList of Fireballs.  
		 * This will ensure that these Fireballs are correctly updated by the 
		 * Game.  
		 */
		for (int k=0; k<fires.size();k++){
			updtFire = fires.get(k);
			if(fires.get(k) !=null){
				Fireball updtFireballs = updtFire.update(time);
				if(updtFireballs != null){
					fireballs.add(updtFireballs);
				}
				updtFire.handleWaterCollisions(water);
			}
			if(fires.get(k).shouldRemove()){
				fires.remove(k);
			}
		}


		//updates fire balls such that if it were to be hit by the water after
		//a number of times, it would be removed from the program. Also keeps
		//track of the progress of the game, whether the player has won, or
		//lost the game, in which the respective prompts would be carried out
		for(int j=0;j<fireballs.size();j++){
			int fireballIndex = j;
			if(fireballs.get(fireballIndex)!=null){
				fireballs.get(fireballIndex).update(time);
				fireballs.get(fireballIndex).handleWaterCollisions(water);
			}		
			if(fireballs.get(fireballIndex).shouldRemove()){
				fireballs.remove(fireballIndex);
			}
		}
		//player has lost the game if fireballs collide with Hero
		if(hero.handleFireballCollisions(fireballs) == true){
			return "QUIT";
		}
		//player has won the game if all fires has been destroyed, the level
		//then restart to a new level
		if(fires.size() == 0){
			return "ADVANCE";
		}
		//player has lost the game if all pants turned into fire
		if(pants.size() == 0){
			return "QUIT";
		}
		return "CONTINUE";		
	}	
	
	//displays the record of the number of pants and fire left in the game
	public String getHUDMessage() { 
		return "Pants Left: " + pants.size()+"\n" +"Fires Left: "+ fires.size(); 
	}
	
	//generates random levels, with different starting position of the hero, 
	//pants and fire.
	//hero should be set to default at center of the screen with random 
	//controlType. 
	//pants should be set to random position with default number of 20
	//fire should be set to random position with default number of 6
	public void createRandomLevel() { 
		randGen = new Random();
		hero = new Hero(Engine.getWidth()/2,Engine.getHeight()/2,
				randGen.nextInt(3)+1); 
		pants = new ArrayList<>();
		for (int i =0; i<20; i++ ){
			pants.add(new Pant(randGen.nextFloat()*Engine.getWidth(),
					randGen.nextFloat()*Engine.getHeight(), randGen));
		}   
		fires = new ArrayList<>();
		for (int j =0; j<6; j++){
			fires.add(new Fire(randGen.nextFloat()*Engine.getWidth(),
					randGen.nextFloat()*Engine.getHeight(), randGen));
		}
	}
	
	//calls the level configuration of the game, if no specific configuration
	//is called, the random configuration would be used for the game
	public void loadLevel(String level) { 
		//one scanner is initialize to scan the level parameter passed
		Scanner scnr = new Scanner(level);
		randGen = new Random();
		fires = new ArrayList<>();
		pants = new ArrayList<>();
		int controlType = 0;
		while(scnr.hasNextLine()){
			//scan for the controlType required
			if(scnr.hasNext("ControlType:")){
				scnr.next("ControlType:");
				controlType = scnr.nextInt();
				scnr.nextLine();
			}
			//scan for the Fire settings require, parsed through the string
			//so that the next String could turned into a float number
			//before passing through the new Fire parameter
			else if (scnr.hasNext("FIRE")){	
				scnr.next();
				scnr.next();
				String parseString = scnr.next().replace(",", "");
				float x = Float.parseFloat(parseString);
				fires.add(new Fire(x, scnr.nextFloat(), randGen));
				scnr.nextLine();
			}
			//scan for the Pant settings require, parsed through the string
			//so that the next String could turned into a float number
			//before passing through the new Fire parameter
			else if (scnr.hasNext("PANT")){
				scnr.next();
				scnr.next();
				String parseString = scnr.next().replace(",", "");
				float x = Float.parseFloat(parseString);
				pants.add(new Pant(x, scnr.nextFloat(), randGen));
				scnr.nextLine();
			}
			//scan for the Hero settings require, parsed through the string
			//so that the next String could turned into a float number
			//before passing through the new Fire parameter
			else if (scnr.hasNext("HERO")){
				scnr.next();
				scnr.next();
				String parseString = scnr.next().replace(",", "");
				float x = Float.parseFloat(parseString);
				hero = new Hero(x, scnr.nextFloat(), controlType);
				scnr.nextLine();
			}
		}
		scnr.close();
	}

	//Runs the game
	public static void main(String[] args)
	{
		Application.startEngineAndGame(args);		
	}
}
