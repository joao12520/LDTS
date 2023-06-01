package area51.arena.engine

import area51.arena.Position
import area51.element.trait.Direction
import spock.lang.Specification

class PositionsCalculatorTest extends Specification {
    def "calculate by direction" (){
        given:
        def calculator = new PositionsCalculator()
        def position = new Position(1,1)

        when:
        def result = calculator.byDirection(position, Direction.RIGHT)

        then:
        result.getX() == 2
        result.getY() == 1
    }

    def "calculate random" (){
        given:
        def calculator = new PositionsCalculator()
        def position = new Position(1,1)

        when:
        Position result = calculator.random(position)

        then:
        result.getX() != 1 || result.getY() != 1
    }

    def "calculate by target"(){
        expect:
        def calculator = new PositionsCalculator()
        def position = new Position(x,y)
        def target = new Position(tx,ty)
        Position result = calculator.byTarget(position, target)
        result.getX() == foundX
        result.getY() == foundY

        where:
        x | y | tx | ty | foundX | foundY
        1 | 1 | 5 | 1 | 2 | 1
        1 | 1 | 1 | 5 | 1 | 2
        5 | 5 | 1 | 5 | 4 | 5
        5 | 5 | 5 | 1 | 5 | 4
    }

    def "calculate direction by target"(){
        expect:
        def calculator = new PositionsCalculator()
        def position = new Position(x,y)
        def target = new Position(tx,ty)
        Direction result = calculator.directionByTarget(position, target)
        result == direction

        where:
        x | y | tx | ty | direction
        1 | 1 | 5 | 1 | Direction.RIGHT
        1 | 1 | 1 | 5 | Direction.DOWN
        5 | 5 | 1 | 5 | Direction.LEFT
        5 | 5 | 5 | 1 | Direction.UP
    }

    def "area by direction" (){
        given:
        def calculator = new PositionsCalculator()
        def position = new Position(3,3)

        when:
        def result = calculator.areaByDirection(position, Direction.RIGHT)

        then:
        result.size() == 9
        result.get(0) == new Position(5,2)
        result.get(8) == new Position(7,4)
    }
}


