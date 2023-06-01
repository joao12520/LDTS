package area51.element.trait;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MovementTrait extends CycleBasedTrait {
    private ArrayList<MoveType> moveTypes = new ArrayList<>();
    private Random random = new Random();

    public MovementTrait(int cyclesPerStep, MoveType[] moveTypes) {
        super(cyclesPerStep);
        this.moveTypes.addAll(Arrays.asList(moveTypes));
    }

    public MoveType getRandomMoveType(){
        return moveTypes.get(random.nextInt(moveTypes.size()));
    }

    public boolean hasMoveType(MoveType moveType) {
        return this.moveTypes.contains(moveType);
    }
}
