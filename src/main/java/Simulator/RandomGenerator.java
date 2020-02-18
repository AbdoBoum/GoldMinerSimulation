package Simulator;

import lombok.Getter;
import org.apache.commons.math3.random.MersenneTwister;

public class RandomGenerator {

    private static RandomGenerator instance = null;

    @Getter
    private MersenneTwister mersenneTwister;

    private RandomGenerator() {
        mersenneTwister = new MersenneTwister();
    }

    public static synchronized RandomGenerator getInstance() {
        if (instance == null) {
                    instance = new RandomGenerator();
        }
        return instance;
    }

    public static int generateRandom(int maxValue) {
        RandomGenerator generator = RandomGenerator.getInstance();
        return generator.getMersenneTwister().nextInt(maxValue);
    }

}
