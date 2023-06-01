package area51.element.hierarchy

import area51.element.hierarchy.Wall
import area51.element.trait.Trait
import spock.lang.Specification

class WallTest extends Specification {
    def "contains drawChar and drawColor"() {
        given:
        def traits = new ArrayList<Trait>()
        def wall = new Wall(traits)

        expect:
        wall.getDrawChar() == '#'
        wall.getDrawColor() == "#333366"
    }
}