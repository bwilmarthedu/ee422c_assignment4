package assignment4;
/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * Brian Wilmarth
 * bw24274
 * 15455
 * Slip days used: <0>
 * Fall 2016
 */


import java.util.ArrayList;
import java.util.List;

/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */


public abstract class Critter {
	private static String myPackage;
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();

	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}
	
	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}
	
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }
	
	private int energy = 0;
	protected int getEnergy() { return energy; }
	
	private int x_coord;
	private int y_coord;
	
	protected final void walk(int direction) {
        // TODO TEST this method
        move(direction, 1);
        energy -= Params.walk_energy_cost;
	}
	
	protected final void run(int direction) {
		// TODO TEST this method
        move(direction, 2);
        energy -= Params.run_energy_cost;
	}

	private final void move(int direction, int distance){
        switch(direction){
            case 0:
                x_coord = (x_coord + distance) % Params.world_width;
                break;
            case 1:
                x_coord = (x_coord + distance) % Params.world_width;
                if(y_coord - distance < 0){ y_coord = y_coord - distance + Params.world_height; }
                else{ y_coord -= distance; }
                break;
            case 2:
                if(y_coord - distance < 0){ y_coord = y_coord - distance + Params.world_height; }
                else{ y_coord -= distance; }
                break;
            case 3:
                if(x_coord - distance < 0){ x_coord = x_coord - distance + Params.world_width; }
                else{ x_coord -= distance; }
                if(y_coord - distance < 0){ y_coord = y_coord - distance + Params.world_height; }
                else{ y_coord -= distance; }
                break;
            case 4:
                if(x_coord - distance < 0){ x_coord = x_coord - distance + Params.world_width; }
                else{ x_coord -= distance; }
                break;
            case 5:
                if(x_coord - distance < 0){ x_coord = x_coord - distance + Params.world_width; }
                else{ x_coord -= distance; }
                y_coord = (y_coord + distance) % Params.world_height;
                break;
            case 6:
                y_coord = (y_coord + distance) % Params.world_height;
                break;
            case 7:
                x_coord = (x_coord + distance) % Params.world_width;
                y_coord = (y_coord + distance) % Params.world_height;
                break;
        }
    }

	protected final void reproduce(Critter offspring, int direction) {
        // TODO TEST this method.
        if(this.energy > Params.min_reproduce_energy){
            babies.add(offspring);
            offspring.energy = (int) Math.floor(0.5 * this.energy);
            this.energy = (int) Math.ceil(0.5 * this.energy);
            switch(direction){
                case 0:
                    offspring.x_coord = (this.x_coord + 1) % Params.world_width;
                    break;
                case 1:
                    offspring.x_coord = (this.x_coord + 1) % Params.world_width;
                    if(this.y_coord - 1 < 0){ offspring.y_coord = this.y_coord - 1 + Params.world_height; }
                    else{ offspring.y_coord = this.y_coord - 1; }
                    break;
                case 2:
                    if(this.y_coord - 1 < 0){ offspring.y_coord = this.y_coord - 1 + Params.world_height; }
                    else{ offspring.y_coord = this.y_coord - 1; }
                    break;
                case 3:
                    if(this.x_coord - 1 < 0){ offspring.x_coord = this.x_coord - 1 + Params.world_width; }
                    else{ offspring.x_coord = this.x_coord - 1; }
                    if(this.y_coord - 1 < 0){ offspring.y_coord = this.y_coord - 1 + Params.world_height; }
                    else{ offspring.y_coord = this.y_coord - 1; }
                    break;
                case 4:
                    if(this.x_coord - 1 < 0){ offspring.x_coord = this.x_coord - 1 + Params.world_width; }
                    else{ offspring.x_coord = this.x_coord - 1; }
                    break;
                case 5:
                    if(this.x_coord - 1 < 0){ offspring.x_coord = this.x_coord - 1 + Params.world_width; }
                    else{ offspring.x_coord = this.x_coord - 1; }
                    offspring.y_coord = (this.y_coord + 1) % Params.world_height;
                    break;
                case 6:
                    offspring.y_coord = (this.y_coord + 1) % Params.world_height;
                    break;
                case 7:
                    offspring.x_coord = (this.x_coord + 1) % Params.world_width;
                    offspring.y_coord = (this.y_coord + 1) % Params.world_height;
                    break;
            }
        }
	}

	public abstract void doTimeStep();
	public abstract boolean fight(String oponent);
	
	/**
	 * create and initialize a Critter subclass.
	 * critter_class_name must be the unqualified name of a concrete subclass of Critter, if not,
	 * an InvalidCritterException must be thrown.
	 * (Java weirdness: Exception throwing does not work properly if the parameter has lower-case instead of
	 * upper. For example, if craig is supplied instead of Craig, an error is thrown instead of
	 * an Exception.)
	 * @param critter_class_name
	 * @throws InvalidCritterException
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
	    // TODO TEST this method.
        try {
            Class c = Class.forName(myPackage + "." + critter_class_name);
            Critter cr = (Critter) c.newInstance();
            population.add(cr);
            cr.x_coord = getRandomInt(Params.world_width);
            cr.y_coord = getRandomInt(Params.world_height);
            cr.energy = Params.start_energy;
        }
        catch (Exception e) {
            throw new InvalidCritterException(critter_class_name);
        }
	}
	
	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
	 * @return List of Critters.
	 * @throws InvalidCritterException
	 */
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
	    // TODO TEST this method.
		List<Critter> result = new java.util.ArrayList<Critter>();
        try {
            Class c = Class.forName(myPackage + "." + critter_class_name);
            for(int i = 0; i < population.size(); i++){
                Critter tempCritter = population.get(i);
                if(c.equals(tempCritter.getClass())){
                    result.add(tempCritter);
                }
            }
        }
        catch(Exception e){
            throw new InvalidCritterException(critter_class_name);
        }
		return result;
	}
	
	/**
	 * Prints out how many Critters of each type there are on the board.
	 * @param critters List of Critters.
	 */
	public static void runStats(List<Critter> critters) {
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		System.out.println();		
	}
	
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure that the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}
		
		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}
		
		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}
		
		protected int getX_coord() {
			return super.x_coord;
		}
		
		protected int getY_coord() {
			return super.y_coord;
		}
		

		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}
		
		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population 
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}
	}

	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
	    // TODO TEST this method.
        population.clear();
	}
	
	public static void worldTimeStep() {
		// TODO Complete this method.
        // 1. Increment Timestep
        // 2. doTimeSteps();
        for(Critter c : population){
            c.doTimeStep();
        }
        // 3. doEncounters();

        // 4. updateRestEnergy();
        for(Critter c : population){
            c.energy -= Params.rest_energy_cost;
        }
        // 5. genAlgae();

        // 6. moveBabies();
        population.addAll(babies);
        babies.clear();
	}
	
	public static void displayWorld() {
		// TODO TEST this method.
        char[][] world = new char[Params.world_height + 2][Params.world_width + 2];
        // populate borders and inside
        world[0][0] = '+';
        world[0][Params.world_width + 1] = '+';
        world[Params.world_height + 1][0] = '+';
        world[Params.world_height + 1][Params.world_width + 1] = '+';
        for(int i = 1; i < Params.world_height + 1; i++){
            world[i][0] = '|';
            world[i][Params.world_width + 1] = '|';
            for(int j = 1; j < Params.world_width + 1; j++){
                world[i][j] = ' ';
            }
        }
        for(int j = 1; j < Params.world_width + 1; j++){
            world[0][j] = '-';
            world[Params.world_height + 1][j] = '-';
        }
        // Populate with critters
        for(Critter c : population){
            world[c.y_coord + 1][c.x_coord + 1] = c.toString().charAt(0);
        }
        // Print world
        for(int i = 0; i < Params.world_height + 2; i++){
            System.out.println(world[i]);
        }

	}

	private static void doEncounters(){
	    List<Critter> crittersOnSquare = new ArrayList<Critter>();
	    for(int i = 0; i < Params.world_height; i++){
	        for(int j = 0; j < Params.world_width; j++){
	            for(Critter c : population){
	                if(c.x_coord == i && c.y_coord == j){
	                    crittersOnSquare.add(c);
                    }
                }
            }
        }
        
    }
}
