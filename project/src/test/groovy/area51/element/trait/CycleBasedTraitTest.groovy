package area51.element.trait


import area51.element.trait.weapon.LightSaber
import area51.singleton.CycleSingleton
import spock.lang.Specification

class CycleBasedTraitTest extends Specification {
    def setup(){
        def mockedCycle = Mock(CycleSingleton)
        CycleSingleton.injectSingleton(mockedCycle)
    }

    def cleanup(){
        CycleSingleton.injectSingleton(null)
    }

    def "can act in this cycle"() {
        given:
        def elementTrait = new MovementTrait(1, new MoveType[]{MoveType.BY_DIRECTION})

        when:
        2 * CycleSingleton.call().getCycle() >> 1

        then:
        elementTrait.canActThisCycle()
        !elementTrait.canActThisCycle()
    }

    def "is aware of speed"() {
        given:
        def slowTrait = new MovementTrait(2, new MoveType[]{MoveType.BY_DIRECTION})
        def fastTrait = new MovementTrait(1, new MoveType[]{MoveType.BY_DIRECTION})

        when:
        2 * CycleSingleton.call().getCycle() >> 1

        then:
        !slowTrait.canActThisCycle()
        fastTrait.canActThisCycle()
    }

    def "can set cyclesPerAction"(){
        given:
        def elementTrait = new AttackTrait(new LightSaber())

        when:
        1 * CycleSingleton.call().getCycle() >> 1
        elementTrait.setCyclesPerAction(10)

        then:
        !elementTrait.canActThisCycle()
    }
}