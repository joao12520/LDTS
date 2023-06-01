package area51.arena

import area51.element.hierarchy.AttackedArea
import area51.element.trait.LifeTrait
import area51.element.trait.Trait
import area51.element.hierarchy.Alien
import area51.element.hierarchy.Human
import area51.element.hierarchy.Wall
import spock.lang.Specification

class ArenaTest extends Specification {
    def "Add and Get Elements" () {
        given:
        def arena = new Arena()
        def traits = new ArrayList<Trait>()
        def human = new Human(traits)
        def position = new Position(3, 3)

        when:
        arena.upsertElement(human, position)

        then:
        arena.getElements().size() == 1
    }

    def "Should not add element beyond arena limits"() {
        given:
        def arena = new Arena(20,20)
        def traits = new ArrayList<Trait>()
        def position1 = new Position(30, 30)
        def position2 = new Position(-10, -10)

        when:
        arena.upsertElement(new Human(traits), position1)
        arena.upsertElement(new Human(traits), position2)

        then:
        arena.getElements().size() == 0
    }

    def "Remove Element" () {
        given:
        def arena = new Arena()
        def traits = new ArrayList<Trait>()
        def human = new Human(traits)
        def position = new Position(3, 3)

        when:
        arena.upsertElement(human, position)
        arena.removeElement(human)

        then:
        arena.getElements().size() == 0
    }

    def "Get Positions" () {
        given:
        def arena = new Arena()
        def traits = new ArrayList<Trait>()
        def human = new Human(traits)
        def position = new Position(3, 3)

        when:
        arena.upsertElement(human, position)

        then:
        arena.getPositions().first() == position
    }

    def "Get Positions sends unique values" () {
        given:
        def arena = new Arena()
        def traits = new ArrayList<Trait>()
        def human = new Human(traits)
        def attackedArea = new AttackedArea(traits)
        def position = new Position(3, 3)
        def position2 = new Position(3, 3)

        when:
        arena.upsertElement(human, position)
        arena.upsertElement(attackedArea, position2)

        then:
        arena.getPositions().size() == 1
    }

    def "Updates and Gets Element Position" () {
        given:
        def arena = new Arena()
        def traits = new ArrayList<Trait>()
        def human = new Human(traits)
        def position1 = new Position(3, 3)
        def position2 = new Position(4, 4)

        when:
        arena.upsertElement(human, position1)
        arena.upsertElement(human, position2)

        then:
        arena.getPosition(human) == position2
    }

    def "Get first Element based on Position" () {
        given:
        def arena = new Arena()
        def traits = new ArrayList<Trait>()
        def human = new Human(traits)
        def position = new Position(3, 3)

        when:
        arena.upsertElement(human, position)

        then:
        arena.getFirstElement(new Position(3, 3)) == human
    }

    def "Get all Elements of a Position" () {
        given:
        def arena = new Arena()
        def traits = new ArrayList<Trait>()
        def human = new Human(traits)
        def human2 = new Human(traits)
        def position = new Position(3, 3)

        when:
        arena.upsertElement(human, position)
        arena.upsertElement(human2, position)

        then:
        arena.getElements(new Position(3, 3)).size() == 2
    }

    def "get Area Element by Position"(){
        given:
        def arena = new Arena()
        def traits = new ArrayList<Trait>()
        def area = new AttackedArea(traits)
        def position = new Position(3, 3)

        when:
        arena.upsertElement(area, position)

        then:
        arena.getAreaElement(new Position(3, 3)) == area
    }

    def "Get Elements By Trait" () {
        given:
        def arena = new Arena()
        def traits = new ArrayList<Trait>()
        traits.add(new LifeTrait(100))
        def human = new Human(traits)
        def traits2 = new ArrayList<Trait>()
        def wall = new Wall(traits2)
        def position = new Position(3, 3)

        when:
        arena.upsertElement(human, position)
        arena.upsertElement(wall, position)

        then:
        arena.getElementsByTrait(LifeTrait.class).size() == 1
    }

    def "Aliens Killed" () {
        given:
        def arena = new Arena()
        def traits = new ArrayList<Trait>()
        def position = new Position(3, 3)
        def wall = new Wall(traits)

        when:
        arena.upsertElement(wall, position)

        then:
        arena.aliensKilled()
    }

    def "Aliens Not Killed" () {
        given:
        def arena = new Arena()
        def traits = new ArrayList<Trait>()
        def alien = new Alien(traits)
        def position = new Position(3, 3)


        when:
        arena.upsertElement(alien, position)

        then:
        !arena.aliensKilled()
    }

    def "Get Elements By Class" () {
        given:
        def arena = new Arena()
        def traits = new ArrayList<Trait>()
        def alien1 = new Alien(traits)
        def alien2 = new Alien(traits)
        def position = new Position(3, 3)
        def wall = new Wall(traits)

        when:
        arena.upsertElement(alien1, position)
        arena.upsertElement(alien2, position)
        arena.upsertElement(wall, position)

        then:
        arena.getElementsByClass(Alien.class).size() == 2
    }

    def "Get Hero" () {
        given:
        def arena = new Arena()
        def traits = new ArrayList<Trait>()
        def human = new Human(traits)
        def position = new Position(3, 3)

        when:
        arena.upsertElement(human, position)

        then:
        arena.getHero() == human
    }

    def "Get an available position"(){
        given:
        def arena = new Arena()

        when:
        def position = arena.getRandomAvailablePosition()

        then:
        position != null
        arena.getFirstElement(position) == null
    }

    def "available position should return null when no position available"(){
        given:
        def arena = new Arena(1,1)
        def traits = new ArrayList<Trait>()

        when:
        arena.upsertElement(new Human(traits), new Position(0,0))
        def position = arena.getRandomAvailablePosition()

        then:
        position == null
    }

    def "Get nr of available positions"(){
        given:
        def arena = new Arena(2,2)
        def traits = new ArrayList<Trait>()

        when:
        arena.upsertElement(new Human(traits), new Position(0,0))

        then:
        arena.nrAvailablePositions() == 3
    }

    def "Get max edge position"(){
        given:
        def arena = new Arena(2,2)
        def edgePosition = new Position(1,1)

        expect:
        arena.getMaxEdgePosition().equals(edgePosition)
    }
}
