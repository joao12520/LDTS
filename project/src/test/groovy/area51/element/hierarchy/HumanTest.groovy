package area51.element.hierarchy

import area51.element.hierarchy.Human
import area51.element.trait.Trait
import spock.lang.Specification

class HumanTest extends Specification {
    def "contains drawChar and drawColor"() {
        given:
        def traits = new ArrayList<Trait>()
        def human = new Human(traits)

        expect:
        human.getDrawChar() == 'H'
        human.getDrawColor() == "#ffffff"
    }
}