package area51.element.hierarchy

import area51.element.trait.Trait
import spock.lang.Specification

class AttackedAreaTest extends Specification {
    def "contains drawChar and drawColor"() {
        given:
        def traits = new ArrayList<Trait>()
        def element = new AttackedArea(traits)

        expect:
        element.getDrawChar() == ' '
        element.getDrawColor() == "#ffffff"
    }

    def "contains drawBackgroundColor"() {
        given:
        def traits = new ArrayList<Trait>()
        def element = new AttackedArea(traits)

        expect:
        element.getDrawBackgroundColor() == "#ff0000"
    }
}