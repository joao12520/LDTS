package area51.arena.builder

import area51.arena.Arena
import area51.arena.Position
import spock.lang.Specification

class ElementsBuilderTest extends Specification {
    def "Generates Hero" () {
        given:
        def arena = new Arena()
        def builder = new ElementsBuilder(arena)

        when:
        builder.generateHero()

        then:
        arena.getElements().size() == 1
    }

    def "Generates Aliens" () {
        given:
        def arena = new Arena()
        def builder = new ElementsBuilder(arena)

        when:
        builder.generateAliens(5)

        then:
        arena.getElements().size() == 5
    }

    def "Generates Walls" () {
        given:
        def arena = new Arena()
        def builder = new ElementsBuilder(arena)

        when:
        builder.generateWalls(3)

        then:
        arena.getElements().size() == 3
    }

    def "Generates Border Walls" () {
        given:
        def arena = new Arena(3,3)
        def builder = new ElementsBuilder(arena)

        when:
        builder.generateBorderWalls()

        then:
        arena.getElements().size() == 8
        arena.getFirstElement(new Position(1,1)) == null
    }
}
