package area51.element.hierarchy

import area51.element.hierarchy.Alien
import area51.element.trait.Trait
import spock.lang.Specification

class AlienTest extends Specification {
    def "contains drawChar and drawColor"() {
        given:
        def traits = new ArrayList<Trait>()
        def alien = new Alien(traits)

        expect:
        alien.getDrawChar() == 'A'
        alien.getDrawColor() == "#339933"
    }
}