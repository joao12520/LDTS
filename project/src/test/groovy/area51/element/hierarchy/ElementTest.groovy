package area51.element.hierarchy

import area51.element.hierarchy.Human
import area51.element.trait.MoveType
import area51.element.trait.MovementTrait
import area51.element.trait.Trait

import spock.lang.Specification

class ElementTest extends Specification {
    def "get trait"() {
        given:
        def traits = new ArrayList<Trait>()
        def elementTrait = new MovementTrait(1, new MoveType[]{MoveType.BY_DIRECTION})
        traits.add(elementTrait)
        def human = new Human(traits)

        expect:
        human.getTrait(MovementTrait.class) == elementTrait
    }
}