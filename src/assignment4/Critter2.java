package assignment4;
/* CRITTERS Critter2.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * Brian Wilmarth
 * bw24274
 * 15455
 * Slip days used: <0>
 * Spring 2018
 */

/**
 * This critter runs up and down only and surrenders to all Critter1s. All children will travel in the opposite direction to the parent.
 */
public class Critter2 extends Critter{

    @Override
    public String toString() { return "2"; }

    private int dir;
    private int dist;

    /**
     * Constructor for Critter2
     */
    public Critter2() {
        dist = 0;
        dir = Critter.getRandomInt(2);
        if(dir == 1){ dir = 0; }
        if(dir == 0){ dir = 6; }
    }

    /**
     * Chooses wither or not Critter2 will fight.
     * @param critterType
     * @return false if fighting a Critter1, true otherwise
     */
    public boolean fight(String critterType) {
        if(critterType.equals("1")){
            return false;
        }
        else{
            return true;
        }
    }

    @Override
    /**
     * Performs one time step for this Critter2
     */
    public void doTimeStep() {
        /* take one step forward */
        run(dir);
        dist++;

        if (getEnergy() > 150) {
            int childDir;
            if(dir == 1){ childDir = 6; }
            else{ childDir = 2; }
            Critter2 child = new Critter2();
            reproduce(child, childDir);
        }

    }

    /**
     * Prints stats for a list fo Critter2s
     * @param critter2s the list of Critter2s
     */
    public static void runStats(java.util.List<Critter> critter2s) {
        System.out.println("" + critter2s.size() + " total Critter1s    ");
        Integer critter2Num = 0;
        for (Object obj : critter2s) {
            Critter2 c = (Critter2) obj;
            System.out.println("Critter2 #" + critter2Num.toString() + " has traveled " + c.dist + " spaces.");
            critter2Num++;
        }

    }
}
