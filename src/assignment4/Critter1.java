package assignment4;
/* CRITTERS Critter1.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * Brian Wilmarth
 * bw24274
 * 15455
 * Slip days used: <0>
 * Spring 2018
 */

/**
 * This critter walks left and right only and fights every time there is an encounter. All children will travel in the opposite direction to the parent.
 */
public class Critter1 extends Critter{

    @Override
    public String toString() { return "1"; }

    private int dir;
    private int numFights;

    /**
     * Constructor for Critter1
     */
    public Critter1() {
        numFights = 0;
        dir = Critter.getRandomInt(2);
        if(dir == 1){ dir = 0; }
        if(dir == 0){ dir = 4; }
    }

    /**
     * Determines if Critter1 will fight and keeps track of the number of fights won.
     * @param not_used n/a
     * @return true to fight every time.
     */
    public boolean fight(String not_used) {
        numFights++;
        return true;
    }

    @Override
    /**
     * Performs one time step for this Critter1
     */
    public void doTimeStep() {
        /* take one step forward */
        walk(dir);

        if (getEnergy() > 150) {
            int childDir;
            if(dir == 1){ childDir = 4; }
            else{ childDir = 0; }
            Critter1 child = new Critter1();
            reproduce(child, childDir);
        }

    }

    /**
     * Prints stats for a list fo Critter1s
     * @param critter1s the list of Critter1s
     */
    public static void runStats(java.util.List<Critter> critter1s) {
        System.out.println("" + critter1s.size() + " total Critter1s    ");
        Integer critter1Num = 0;
        for (Object obj : critter1s) {
            Critter1 c = (Critter1) obj;
            System.out.println("Critter1 #" + critter1Num.toString() + " has won " + c.numFights + " fights.");
            critter1Num++;
        }

    }
}
