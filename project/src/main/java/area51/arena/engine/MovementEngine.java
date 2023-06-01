package area51.arena.engine;

import area51.arena.Arena;
import area51.element.hierarchy.Human;
import area51.element.trait.AttackTrait;
import area51.singleton.GuiSingleton;
import area51.singleton.Key;
import area51.arena.Position;
import area51.element.trait.Direction;
import area51.element.hierarchy.Element;
import area51.element.trait.MovementTrait;

import java.util.ArrayList;

public class MovementEngine implements Engine {
    private Arena arena;
    private final PositionsCalculator calculator = new PositionsCalculator();
    private Position heroPosition;

    public MovementEngine(Arena arena) {
        this.arena = arena;
    }

    @Override
    public void execute() {
        ArrayList<Element> elements = arena.getElementsByTrait(MovementTrait.class);
        Key latestKey = GuiSingleton.call().getLatestCurrentKey();
        storeAttackDirection(latestKey);
        for (Element e : elements) {
            moveElement(e, latestKey);
        }
    }

    private void storeAttackDirection(Key key){
        Direction direction = getDirectionFromKey(key);
        if(direction == null)
            return;

        AttackTrait attackTrait = (AttackTrait) arena.getHero().getTrait(AttackTrait.class);
        attackTrait.setAttackDirection(direction);
    }

    private void moveElement(Element element, Key key) {
        if (!canMoveThisCycle(element))
            return;

        MovementTrait movementTrait = (MovementTrait) element.getTrait(MovementTrait.class);
        Position position = arena.getPosition(element);
        Position newPosition = null;
        switch (movementTrait.getRandomMoveType()) {
            case BY_DIRECTION -> newPosition = moveByKey(position, key);
            case BY_TARGET -> newPosition = calculator.byTarget(position, getHeroPosition());
            case RANDOM -> newPosition = calculator.random(position);
        }

        if (newPosition != null && canMove(newPosition) && arena.validPosition(newPosition))
            position.setXnY(newPosition.getX(), newPosition.getY());
    }

    private Position moveByKey(Position position, Key key) {
        Direction direction = getDirectionFromKey(key);
        if(direction == null)
            return null;

        return calculator.byDirection(position, direction);
    }

    private Direction getDirectionFromKey(Key key){
        if(key == null)
            return null;

        Direction direction;
        switch (key) {
            case UP -> direction = Direction.UP;
            case RIGHT -> direction = Direction.RIGHT;
            case DOWN -> direction = Direction.DOWN;
            case LEFT -> direction = Direction.LEFT;
            default -> direction = null;
        }

        if(direction != null)
            GuiSingleton.call().clearLatestCurrentKey();

        return direction;
    }

    private Position getHeroPosition() {
        if (heroPosition == null)
            heroPosition = arena.getPosition(arena.getHero());

        return heroPosition;
    }

    private boolean canMove(Position desiredPosition) {
        return arena.getFirstElement(desiredPosition) == null;
    }

    private boolean canMoveThisCycle(Element element) {
        MovementTrait movementTrait = (MovementTrait) element.getTrait(MovementTrait.class);
        return movementTrait.canActThisCycle();
    }
}

