package ladder.domain;

import java.util.Random;

public class RandomBooleanGenerator implements LadderGameCreateLineGenerator {

    private final Random random = new Random();

    @Override
    public boolean generate() {
        return random.nextBoolean();
    }
}
