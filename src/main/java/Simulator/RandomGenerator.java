package Simulator;

import lombok.Getter;
import org.apache.commons.math3.random.MersenneTwister;

/**
 * RandomGenerator is a class that allows to get random numbers based on Mersenne Twister generator
 * this class implement the singleton pattern to get one instance of this class
 */
public class RandomGenerator {
    /**
     * the instance of the class
     */
    private static RandomGenerator instance = null;
    /**
     * reference of class Mersenne Twister
     */
    @Getter
    private MersenneTwister mersenneTwister;

    /**
     * Constructor of the class
     */
    private RandomGenerator() {
        mersenneTwister = new MersenneTwister();
    }

    /**
     * The method return the instance of the class if initialised else he initialise and return it
     * @return reference of RandomGenerator
     */
    public static RandomGenerator getInstance() {
        if (instance == null) {
            synchronized (RandomGenerator.class) {
                if (instance == null) {
                    instance = new RandomGenerator();
                }
            }
        }
        return instance;
    }

    /**
     * return a random number from 0 to maxValue
     * @param maxValue represent the upper bound of numbers to generate
     * @return return the random number
     */
    public static int generateRandom(int maxValue) {
        RandomGenerator generator = RandomGenerator.getInstance();
        return generator.getMersenneTwister().nextInt(maxValue);
    }

}
