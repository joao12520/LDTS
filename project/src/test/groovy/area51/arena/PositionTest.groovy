package area51.arena


import spock.lang.Specification

class PositionTest extends Specification{
    def "set and get X, Y" () {
        given:
        def position = new Position(1, 10)

        when:
        position.setXnY(2,9)

        then:
        position.getX() == 2
        position.getY() == 9
    }

    def "is equal" () {
        given:
        def position1 = new Position(1, 1)
        def position2 = new Position(1, 1)

        expect:
        position1 == position2
        position1.hashCode() == position2.hashCode()
    }

    def "is different" () {
        given:
        def position1 = new Position(1, 1)
        def position2 = new Position(1, 2)
        def position3 = new Position(2, 1)

        expect:
        position1 != position2
        position1 != position3
        position2.hashCode() != position3.hashCode()
    }

    def "get Up" () {
        given:
        def position = new Position(1, 1)

        when:
        def newPosition = position.getUp()

        then:
        newPosition.getX() == 1
        newPosition.getY() == 0
    }

    def "get Down" () {
        given:
        def position = new Position(1, 1)

        when:
        def newPosition = position.getDown()

        then:
        newPosition.getX() == 1
        newPosition.getY() == 2
    }

    def "get Right" () {
        given:
        def position = new Position(1, 1)

        when:
        def newPosition = position.getRight()

        then:
        newPosition.getX() == 2
        newPosition.getY() == 1
    }

    def "get Left" () {
        given:
        def position = new Position(1, 1)

        when:
        def newPosition = position.getLeft()

        then:
        newPosition.getX() == 0
        newPosition.getY() == 1
    }
}
