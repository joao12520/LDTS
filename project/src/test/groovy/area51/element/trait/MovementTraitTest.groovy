package area51.element.trait

import area51.element.trait.MoveType
import area51.element.trait.MovementTrait
import spock.lang.Specification

class MovementTraitTest extends Specification {
    def "has Move Type"() {
        given:
        def movementTrait = new MovementTrait(1, new MoveType[]{MoveType.RANDOM, MoveType.BY_TARGET})

        expect:
        movementTrait.hasMoveType(MoveType.RANDOM)
        movementTrait.hasMoveType(MoveType.BY_TARGET)
        !movementTrait.hasMoveType(MoveType.BY_DIRECTION)
    }

    def "get random MoveType"() {
        given:
        def movementTrait = new MovementTrait(1, new MoveType[]{MoveType.RANDOM, MoveType.BY_TARGET})

        expect:
        movementTrait.hasMoveType(movementTrait.getRandomMoveType())
    }
}