package area51.arena.engine;

import area51.arena.Position;
import area51.element.trait.Direction;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.abs;

public class PositionsCalculator {
    public Position byDirection(Position position, Direction direction){
        Position p = null;
        switch (direction){
            case UP -> p = position.getUp();
            case RIGHT -> p = position.getRight();
            case DOWN -> p = position.getDown();
            case LEFT -> p = position.getLeft();
        }
        return p;
    }

    public Position random(Position position){
        Random random = new Random();
        Position p = null;
        switch(random.nextInt(4)){
            case 0 -> p = position.getUp();
            case 1 -> p = position.getRight();
            case 2 -> p = position.getDown();
            case 3 -> p = position.getLeft();
        }
        return p;
    }

    public Position byTarget(Position position, Position targetPosition){
        int x = targetPosition.getX() - position.getX();
        int y = targetPosition.getY() - position.getY();

        Position p;
        if(abs(x) > abs(y))
            p = x > 0 ? position.getRight() : position.getLeft();
        else
            p = y > 0 ? position.getDown() : position.getUp();

        return p;
    }

    public Direction directionByTarget(Position position, Position targetPosition){
        int x = targetPosition.getX() - position.getX();
        int y = targetPosition.getY() - position.getY();

        Direction d;
        if (abs(x) > abs(y))
            d = x > 0 ? Direction.RIGHT : Direction.LEFT;
        else
            d = y > 0 ? Direction.DOWN : Direction.UP;

        return d;
    }

    public ArrayList<Position> areaByDirection(Position elementPosition, Direction attackDirection){
        Position centerPosition = elementPosition.clone();
        switch (attackDirection){
            case UP -> centerPosition = centerPosition.getUp().getUp().getUp();
            case RIGHT -> centerPosition = centerPosition.getRight().getRight().getRight();
            case DOWN -> centerPosition = centerPosition.getDown().getDown().getDown();
            case LEFT -> centerPosition = centerPosition.getLeft().getLeft().getLeft();
        }

        ArrayList<Position> areaAttackedPositions = new ArrayList<>();
        for(int i = -1 ; i<2; i++){
            for(int j = -1 ; j<2; j++){
                areaAttackedPositions.add(new Position(centerPosition.getX() + i, centerPosition.getY() + j));
            }
        }

        return areaAttackedPositions;
    }
}
