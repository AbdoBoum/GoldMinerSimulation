package Utils;

import org.apache.commons.math3.random.MersenneTwister;

public class RandomGenerator {

    private static MersenneTwister INSTANCE = null;

    public static MersenneTwister getInstance() {
        if (INSTANCE == null) {
            synchronized (RandomGenerator.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MersenneTwister();
                }
            }
        }
        return INSTANCE;
    }

    public static int generateRandom(int maxValue) {
        MersenneTwister mersenneTwister = RandomGenerator.getInstance();
        return mersenneTwister.nextInt(maxValue);
    }

}
